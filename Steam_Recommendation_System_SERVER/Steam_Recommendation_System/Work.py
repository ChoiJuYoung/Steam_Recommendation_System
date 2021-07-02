import DB
import Sign_In
import Log_In
import math

class WorkClass():
    def __init__(self):
        self.db = DB.DB()
        self.si = Sign_In.Sign_In(self.db)
        self.li = Log_In.Log_In(self.db)
        self.match = {}

    def getSock(self, sock):
        this.sock = sock

    def getMsg(self, msg):
        print(msg)
        if msg.indexOf("+") != -1:
            self.idNum = msg.split("+")[0]
            
            if not idNum in self.match:
                self.sock.sendMsg("ERR")
                return

            msg = msg.split("+")[1]


        if msg.indexOf("//") == -1:
            if msg == "achieve":
                self.sock.sendMsg(self.getAchievementReco())
                return
            elif msg == "game":
                self.sock.sendMsg(self.getGameReco())
                return
            elif msg == "friend":
                self.sock.sendMsg(self.getFriendReco())
                return
            else:
                self.sock.sendMsg("ERR")
                return

        op = msg.split("//")
        
        if op[0] == "signinchk": # Check Duplicated Id
            result = self.si.check(op[1])
            self.sock.sendMsg(result)
        elif op[0] == "signinreq": # Request Sing In
            result = self.si.regist(op[1])
            self.sock.sendMsg(result)
        elif op[0] == "login":
            result = self.li.log_in(op[1])
            if result != "INCORRECT ID OR PASSWD" and not result in self.match.keys():
                if not "__" in op[1]:
                    result = "INCORRECT ID OR PASSWD"
                else:
                    self.match[result] = op[1].split("__")[0]
            print(self.match)

            if result == "INCORRECT ID OR PASSWD":
                self.sock.sendMsg(result)
                return

            self.getFinalResult(self.match[result]) # 로그인 성공 시 게임 추천도 계산
            self.getUserSim(self.match[result])
            self.sock.sendMsg(str(result))
        elif op[0] == "search":
            result = self.search(op[1])
            self.sock.sendMsg(result)
        elif op[0] == "review":
            if op[1] in self.user_list:
                self.sock.sendMsg("ERR")
            else:
                sql = "select appid from app_id_info where title = \'" + op[1] + "\'"
                product_id = self.db.select(sql)[0][0]
                sql = "insert into reveiws values(\'" + self.match[idNum] + "\', " + product_id + ",0.0,1)"
                self.db.insert(sql)
                self.sock.sendMsg("OK")

    def search(self, word):
        sql = "select title from app_id_info where title = \'" + word + "\' and type = \'game\'"
        result = self.db.select(sql)
        ret = []

        for x in result:
            ret.append(x)

        sql = "select title from app_id_info where title like \'%" + word + "%\' and type = \'game\'"
        result = self.db.select(sql)

        if len(result) > 10:
            result = sorted(result, key = lambda k : len(word) - len(result[k][0]))
            
        for x in result:
            ret.append(x)

        ret = ret[:10]

        tmp = ""
        for x in ret:
            tmp += (x[0] + "__")
        tmp = tmp[:len(tmp) - 2]

        return "search//" + tmp

    def getNoTop10(self, type):
        
        sql = "select distinct " + type + " from reviews group by " + type + " order by count(*) desc limit 10;"

        ret = self.db.select(sql)

        result = []

        for x in ret:
            result.append(x[0])

        return result


    def getFewTop10(self, type):
        tmp = ""
        for x in self.user_list:
            tmp += (str(x) + " OR ")
        tmp = tmp[:len(tmp) - 4]

        genre = {}

        sql = "select distinct genre from games_genres where appid = " + tmp + " union select distinct genre from games_genres_old where appid = " + tmp
        ret = self.db.select(sql)
        for x in ret:
            if x[0] in genre:
                genre[x[0]] += 1
            else:
                genre[x[0]] = 0
        genre = sorted(genre, key = lambda k:genre[k], reverse = True)

        sql = "select r." + type + " from reviews as r inner join games_genres as g on g.appid = r.product_id and g.genre = \'" + genre[0] + "\' group by r." + type + " limit 10"
        ret = self.db.select(sql)
        result = []
        for x in ret:
            result.append(x[0])

        return result

    def getFinalResult(self, username):
        self.user_list = {}

        sql = "select distinct product_id, hours from reviews where username = \'" + username + "\'"

        ret = self.db.select(sql)
        for x in ret:
            self.user_list[x[0]] = x[1]


        if len(self.user_list) == 0 : # user가 recommend한 product가 0개일 경우
            self.game_reco = self.getNoTop10("product_id")
            return
        elif len(self.user_list) < 10: # user가 recommend한 product가 10개 미만인 경우
            self.game_reco = self.getFewTop10("product_id")
            return
        else: # 유저가 10개 이상의 product를 recommend한 경우
            sm = 0.0 #제곱의 평균
            self.ms = 0.0 #평균의 제곱
            for x in self.user_list:
                sm += (self.user_list[x] ** 2)
                self.ms += self.user_list[x]

            sm /= len(self.user_list)
            self.ms /= len(self.user_list)
            self.ms **= 2

            self.sig = math.sqrt(sm - self.ms) # 표준편차 = sqrt(제평 - 평제)

            for x in self.user_list:
                self.user_list[x] -= math.sqrt(self.ms)
                self.user_list[x] /= self.sig
                # 값들을 모두 (값 - 평균) / 표준편차 로 정규화


        #print(self.user_list)

        game_reco = {}

        specList = []

        for y in self.user_list:
            if not y in self.compareList:
                specList.append(y)

        self.getSpecificSim(specList)

        for x in self.compareList:
            if x in self.user_list:
                continue

            game_reco[x] = 0.0
            for y in self.user_list:
                game_reco[x] += self.itemSim[x][y] * self.user_list[y]

        #print(game_reco)
        self.game_reco = sorted(game_reco, key=lambda k : game_reco[k], reverse=True)
        #print(self.game_reco)
        get_GameName()

    def get_GameName(self):
        self.game_reco_name = {}

        for x in self.game_reco:
            sql = "select title from app_id_info where appid = " + str(x)

            ret = self.db.select(sql)

            if len(ret) == 0:
                continue
                #뭔가 err
            
            self.game_reco_name[x] = ret[0][0]

    def getSpecificSim(self, sList):
        element = {}
        for y in sList:
            sql = "select distinct username from reviews where product_id = " + str(y)
            element[y] = []

            ret = self.db.select(sql)
            for x in ret:
                element[y].append(x[0])

        for x, xs in self.element.items():
            if x not in self.itemSim.keys():
                self.itemSim[x] = {}
            dic = {}
            for y, ys in element.items():
                if y in self.itemSim.keys():
                    if x in self.itemSim[y].keys():
                        continue
                else:
                    self.itemSim[y] = {}

                tmp = list(set(xs) - set(ys)) + list(set(ys) - set(xs))
                value = (len(xs) + len(ys) - len(tmp)) / ((1.0) * (len(xs) + len(ys) + len(tmp)))

                self.itemSim[x][y] = value
                self.itemSim[y][x] = value


    def getItemSim(self):
        self.compareList = []
        self.element = {}
        limit = 500
        sql = "select distinct product_id from reviews group by product_id having count(*) > " + str(limit)
        ret = self.db.select(sql)
        for x in ret:
            self.compareList.append(x[0])
            self.element[x[0]] = []
        # 여기까지, 추천한 유저가 limit보다 큰 게임 목록 가져오기
        #print(len(self.compareList))

        sql = "select distinct username, product_id from reviews where product_id IN (select product_id from reviews group by product_id having count(*) > " + str(limit) + ")"
        print("GET LIST")
        ret = self.db.select(sql)
        for x in ret:
            if x[1] in self.compareList:
                self.element[x[1]].append(x[0])

        '''
        for x in self.compareList:
            sql = "select username from reviews where product_id = " + str(x)
            tmp = self.db.select(sql)
            ret = []
            for y in tmp:
                ret.append(y[0])
            self.element[x] = ret
        '''
        # 각 게임 별 추천한 유저 목록 가져오기
        # self.compareList = []
        # Jaccard Similarity 계산 시작
        print("START SIM CALC")
        self.itemSim = {}

        for x, xs in self.element.items():
            if x not in self.itemSim.keys():
                self.itemSim[x] = {}
            dic = {}
            for y, ys in self.element.items():
                if x == y:
                    continue
                if y in self.itemSim.keys():
                    if x in self.itemSim[y].keys():
                        continue
                else:
                    self.itemSim[y] = {}

                tmp = list(set(xs) - set(ys)) + list(set(ys) - set(xs))
                value = (len(xs) + len(ys) - len(tmp)) / ((1.0) * (len(xs) + len(ys) + len(tmp)))

                self.itemSim[x][y] = value
                self.itemSim[y][x] = value

                #print(str(x) + " // " + str(y) + " : " + str(value))

    def getUserSim(self, username):
        if len(self.user_list) == 0 : # user가 recommend한 product가 0개일 경우
            self.user_reco = self.getNoTop10("username")
            return
        elif len(self.user_list) < 10: # user가 recommend한 product가 10개 미만인 경우
            self.user_reco = self.getFewTop10("username")
            return
        
        # 10개이상 추천한 경우
        # 이미 game_reco에서 정규분포화 완료


        #print(self.user_list)


        self.userCompareList = []
        self.userElement = {}
        limit = 50
        sql = "select distinct username from reviews group by username having count(*) > " + str(limit)
        ret = self.db.select(sql)
        for x in ret:
            if "\\" in x[0]:
                continue
            if x[0] == '':
                continue
            self.userCompareList.append(x[0])
            self.userElement[x[0]] = []
        # 여기까지, 추천한 유저가 limit보다 큰 게임 목록 가져오기
        #print(len(self.compareList))

        sql = "select distinct username, product_id from reviews where username IN (select username from reviews group by username having count(*) > " + str(limit) + ")"
        print("GET User LIST")
        ret = self.db.select(sql)
        for x in ret:
            if "\\" in x[0]:
                continue
            if x[0] == '':
                continue
            if x[0] in self.userCompareList:
                self.userElement[x[0]].append(x[1])

        # Jaccard Similarity 계산 시작
        print("START SIM CALC")

        self.userSim = {}
        myList = []

        for x in self.user_list:
            myList.append(x)

        for x, xs in self.userElement.items():
            tmp = list(set(xs) - set(myList)) + list(set(myList) - set(xs))
            value = (len(xs) + len(myList) - len(tmp)) / ((1.0) * (len(xs) + len(myList) + len(tmp)))

            tmp = set(xs + myList) - set(tmp)

            alpha = 0.0

            for a in tmp:
                alpha += self.user_list[a]

            value *= alpha

            self.userSim[x] = value

            #print(x + " // " + str(value))

        #print(game_reco)
        self.user_reco = sorted(self.userSim, key=lambda k : self.userSim[k], reverse=True)
        #print(self.game_reco)'''

        self.getAchievement()
    
    def getAchievement(self):
        achi_user = self.user_reco[:10 if len(self.user_reco) > 10 else len(self.user_reco)]

        tmp = ""
        for x in achi_user:
            tmp += ("\'" + str(x) + "\' OR ")
        tmp = tmp[:len(tmp) - 4]

        sql = "select distinct a.Title, i.name, i.description from achievement_info as i inner join app_id_info as a on a.appid = i.product_id and (i.product_id, i.achievement_id) IN (select * from (select product_id, achivement_id from achievement_user where username = " + tmp + " group by product_id, achivement_id order by count(*) desc limit 10) as tmp)"

        ret = self.db.select(sql)

        result = []

        for x in ret:
            result.append(x)

        self.achi_user = result

    def getAchievementReco(self):
        tmp = ""
        for x in self.achi_user:
            tmp += (x[0] + "," + x[1] + "," + x[2] + "__")
        tmp = tmp[:len(tmp) - 2]

        return "achieve//" + tmp

    def getGameReco(self):
        tmp = ""
        for x in self.game_reco:
            tmp += (self.game_reco_name[x] + "__")
        tmp = tmp[:len(tmp) - 2]

        return "game//" + tmp

    def getFriendReco(self):
        tmp = ""
        for x in self.user_reco:
            tmp += (x + "__")
        tmp = tmp[:len(tmp) - 2]

        return "friend//" + tmp