## python使用Serial模块读取串口数据 

> pyserial 模块封装了对串口的访问，兼容各种平台。

### 模块安装

> pip install pyserial

### 初始化 

```python
# 不同平台下初始化
import serial
ser = serial.Serial('com1', 9600, timeout=1)
ser = serial.Serial("/dev/ttyUSB0", 9600, timeout=0.5) # 使用USB连接串行口
ser = serial.Serial("/dev/ttyAMA0", 9600, timeout=0.5) # 使用树莓派的GPIO口连接串行口
ser = serial.Serial(1, 9600, timeout=0.5)# winsows系统使用com1口连接串行口
ser = serial.Serial("com1", 9600, timeout=0.5)# winsows系统使用com1口连接串行口
ser = serial.Serial("/dev/ttyS1", 9600, timeout=0.5)# Linux系统使用com1口连接串行口
```

### 对象属性

- name——设备名字
- port——读或者写端口
- baudrate——波特率
- bytesize——字节大小
- parity——校验位
- stopbits——停止位
- timeout——读超时设置
- writeTimeout——写超时
- xonxoff——软件流控
- rtscts——硬件流控
- dsrdtr——硬件流控
- interCharTimeout——字符间隔超时

### 对象常用方法

- ser.isOpen()——查看端口是否被打开
- ser.open() ——打开端口
- ser.close()——关闭端口
- ser.read()——从端口读字节数据。默认1个字节
- ser.read_all()——从端口接收全部数据
- ser.write("hello")——向端口写数据
- ser.readline()——读一行数据
- ser.readlines()——读多行数据
- in_waiting()——返回接收缓存中的字节数
- flush()——等待所有数据写出
- flushInput()——丢弃接收缓存中的所有数据
- flushOutput()——终止当前写操作，并丢弃发送缓存中的数据。

### 实例

```python
#coding=gb18030
 
import threading
import time
import serial
 
class ComThread:
 def __init__(self, Port='COM3'):
  self.l_serial = None
  self.alive = False
  self.waitEnd = None
  self.port = Port
  self.ID = None
  self.data = None
 
 def waiting(self):
  if not self.waitEnd is None:
   self.waitEnd.wait()
 
 def SetStopEvent(self):
  if not self.waitEnd is None:
   self.waitEnd.set()
  self.alive = False
  self.stop()
 
 def start(self):
  self.l_serial = serial.Serial()
  self.l_serial.port = self.port
  self.l_serial.baudrate = 115200
  self.l_serial.timeout = 2
  self.l_serial.open()
  if self.l_serial.isOpen():
   self.waitEnd = threading.Event()
   self.alive = True
   self.thread_read = None
   self.thread_read = threading.Thread(target=self.FirstReader)
   self.thread_read.setDaemon(1)
   self.thread_read.start()
   return True
  else:
   return False
 
 def SendDate(self,i_msg,send):
  lmsg = ''
  isOK = False
  if isinstance(i_msg):
   lmsg = i_msg.encode('gb18030')
  else:
   lmsg = i_msg
  try:
   # 发送数据到相应的处理组件
   self.l_serial.write(send)
  except Exception as ex:
   pass;
  return isOK
 
 def FirstReader(self):
  while self.alive:
   time.sleep(0.1)
 
   data = ''
   data = data.encode('utf-8')
 
   n = self.l_serial.inWaiting()
   if n:
     data = data + self.l_serial.read(n)
     print('get data from serial port:', data)
     print(type(data))
 
   n = self.l_serial.inWaiting()
   if len(data)>0 and n==0:
    try:
     temp = data.decode('gb18030')
     print(type(temp))
     print(temp)
     car,temp = str(temp).split("\n",1)
     print(car,temp)
 
     string = str(temp).strip().split(":")[1]
     str_ID,str_data = str(string).split("*",1)
 
     print(str_ID)
     print(str_data)
     print(type(str_ID),type(str_data))
 
     if str_data[-1]== '*':
      break
     else:
      print(str_data[-1])
      print('str_data[-1]!=*')
    except:
     print("读卡错误，请重试！\n")
 
  self.ID = str_ID
  self.data = str_data[0:-1]
  self.waitEnd.set()
  self.alive = False
 
 def stop(self):
  self.alive = False
  self.thread_read.join()
  if self.l_serial.isOpen():
   self.l_serial.close()
#调用串口，测试串口
def main():
 rt = ComThread()
 rt.sendport = '**1*80*'
 try:
  if rt.start():
   print(rt.l_serial.name)
   rt.waiting()
   print("The data is:%s,The Id is:%s"%(rt.data,rt.ID))
   rt.stop()
  else:
   pass
 except Exception as se:
  print(str(se))
 
 if rt.alive:
  rt.stop()
 
 print('')
 print ('End OK .')
 temp_ID=rt.ID
 temp_data=rt.data
 del rt
 return temp_ID,temp_data
 
 
if __name__ == '__main__':
 
 #设置一个主函数，用来运行窗口，便于若其他地方下需要调用串口是可以直接调用main函数
 ID,data = main()
 
 print("******")
 print(ID,data)
```