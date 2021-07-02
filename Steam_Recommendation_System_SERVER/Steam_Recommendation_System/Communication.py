from socket import *

class Server():
    def __init__(self):
        self.getSock()

    def sendMsg(self, msg):
        #msg += "\n"
        self.connectionSock.send(msg.encode('utf-8'))

    def getMsg(self, main):
        while True:
            try:
                msg = self.connectionSock.recv(1024)
                main.getMsg(msg.decode('utf-8'))
            except ConnectionResetError:
                self.getSock()

    def getSock(self):
        self.serverSock = socket(AF_INET, SOCK_STREAM)
        self.serverSock.bind(('', 6000))
        self.serverSock.listen(1)
        print("LISTENING")
        self.connectionSock, addr = self.serverSock.accept()
        print("ACCEPTED")
