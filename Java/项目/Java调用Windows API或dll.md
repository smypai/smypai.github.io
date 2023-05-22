```java
Windows平台使用Java调用Windows API或dll

由于项目的需要，使用Java开发Windows桌面应用，调用外设的C语言写的驱动，看了下jni，过程太复杂，果断放弃。于是在github上找到了下面的项目：
https://github.com/java-native-access/jna
JNA的全称是Java Native Access，你只要在一个java接口中描述本地库中的函数与结构， JNA将在运行期动态访问本地库，自动实现Java接口到本地库函数的映射。
使用maven可以直接加入下面的依赖：
        <dependency>
            <groupId>net.java.dev.jna</groupId>
            <artifactId>jna</artifactId>
            <version>4.5.1</version>
        </dependency>
        <dependency>
            <groupId>net.java.dev.jna</groupId>
            <artifactId>jna-platform</artifactId>
            <version>4.5.1</version>
        </dependency>
项目中有一个QDPos.dll，提供了两个函数用于设备的枚举，其文件头的声明如下：
BOOL WINAPI enumBloothPos(WCHAR* filter,WCHAR* deviceNames[10]);
BOOL WINAPI enumCOMPos(WCHAR* filter,TCHAR* comNames[10]);
对应的jna接口为：
package qdone.pos;
 
import com.sun.jna.Native;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
 
public interface QDPos extends StdCallLibrary {
 
   QDPos INSTANCE = (QDPos) Native.loadLibrary("QDPos", QDPos.class);
 
    /**枚举蓝牙设备*/
    WinDef.BOOL enumBloothPos(WString filter, WString[] deviceNames);
 
    /**枚举串口设备*/
    WinDef.BOOL enumCOMPos(WString filter, WString[] deviceNames);
}
将QDPos.dll放到类搜索路径下或者Windows系统目录下，保证java在运行时能找到QDPos.dll，直接调用该接口就可以访问dll中对应的C语言实现的函数，以下为java中调用的测试用例：

    @Test
    public void enumBloothPos() {
        QDPos qdPos=QDPos.INSTANCE;
        WString[] deviceNames=new WString[10];
        WString perfix=new WString("M36");
        qdPos.enumBloothPos(perfix,deviceNames);
        for(WString deviceName:deviceNames)
        {
            if(null!=deviceName) {
                logger.info("蓝牙设备:{}", deviceName);
            }
        }
    }
 
    @Test
    public void enumCOMPos() {
        QDPos qdPos=QDPos.INSTANCE;
        WString[] deviceNames=new WString[10];
        WString perfix=new WString("Landi");
        qdPos.enumCOMPos(perfix,deviceNames);
        for(WString deviceName:deviceNames)
        {
            if(null!=deviceName) {
                logger.info("串口设备:{}", deviceName);
            }
        }
    }
关于java和C语言头文件中数据类型映射，项目中给出了说明：
Default Type Mappings
Java primitive types (and their object equivalents) map directly to the native C type of the same size.

Native Type	Size	Java Type	Common Windows Types
char	8-bit integer	byte	BYTE, TCHAR
short	16-bit integer	short	WORD
wchar_t	16/32-bit character	char	TCHAR
int	32-bit integer	int	DWORD
int	boolean value	boolean	BOOL
long	32/64-bit integer	NativeLong	LONG
long long	64-bit integer	long	__int64
float	32-bit FP	float	 
double	64-bit FP	double	 
char*	C string	String	LPTCSTR
void*	pointer	Pointer	LPVOID, HANDLE, LPXXX
Unsigned types use the same mappings as signed types. C enums are usually interchangeable with "int".


```