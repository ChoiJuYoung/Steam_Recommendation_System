import pymysql

class DB():
    def __init__(self):
        self.db = pymysql.connect(host = 'godzero.iptime.org', port = 3306, user = 'root', passwd = 'root', db = 'sw', charset = 'utf8')

    def insert(self, sql):
        with self.db.cursor() as cursor:
            cursor.execute(sql)
        self.db.commit()
        return cursor.lastrowid

    def select(self, sql):
        print(sql)
        ret = []
        with self.db.cursor() as cursor:
            cursor.execute(sql)

            for result in cursor.fechall():
                ret.append(result)

        return ret