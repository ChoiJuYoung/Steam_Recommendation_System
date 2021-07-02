class Sign_In():
    def __init__(self, db):
        self.db = db

    def check(self, id):
        sql = "select * from user where id = \'" + id + "\'"
        ret = self.db.select(sql)

        if len(ret) == 0:
            return "OK"
        else:
            return "EXIST"

    def regist(self, msg):
        if not "__" in msg:
            return "ERR"

        args = msg.split("__")
        sql = "insert into user(id, passwd) values(\'" + args[0] + "\', \'" + args[1] + "\')"
        ret = self.db.insert(sql)

        return "SIGN IN OK"