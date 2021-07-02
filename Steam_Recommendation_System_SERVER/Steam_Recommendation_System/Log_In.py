class Log_In():
    def __init__(self, db):
        self.db = db

    def log_in(self, msg):
        if not "__" in msg:
            return "INCORRECT ID OR PASSWD"

        args = msg.split("__")

        sql = "select * from user where id = \'" + args[0] + "\' and passwd = \'" + args[1] + "\'"
        ret = self.db.select(sql)
        print(ret)

        if len(ret) == 0:
            return "INCORRECT ID OR PASSWD"
        else:
            return self.calcCode(ret[0][0])

    def calcCode(self, num):
        num *= 5
        num >>= 1
        num *= 10
        num >>= 2
        num ^= 682
        return num