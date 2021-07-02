import Communication
import threading
import Work
from datetime import datetime

work = Work.WorkClass()
now = datetime.now()
work.getItemSim()
now = datetime.now() - now
print("getItemSim : " + str(now))

username = "Firo"

now = datetime.now()
work.getFinalResult(username)
now = datetime.now() - now
print("getFinalResult : " + str(now))


now = datetime.now()
work.getUserSim(username)
now = datetime.now() - now
print("getUserSim : " + str(now))



sock = Communication.Server()
work.getSock(sock)

receiver = threading.Thread(target = sock.getMsg, args = (work,))
receiver.start()


while True:
    sendData = input()
    sock.sendMsg(sendData)