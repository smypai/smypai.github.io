## @SuppressWarnings

@SuppressWarnings注解是jse提供的注解。作用是屏蔽一些无关紧要的警告。使开发者能看到一些他们真正关心的警告。从而提高开发者的效率

### 简介：
java.lang.SuppressWarnings是J2SE 5.0中标准的Annotation之一。可以标注在类、字段、方法、参数、构造方法，以及局部变量上。作用：告诉编译器忽略指定的警告，不用在编译完成后出现警告信息。
使用：
- @SuppressWarnings(“”)
- @SuppressWarnings({})
- @SuppressWarnings(value={})

根据sun的官方文档描述：
value - 将由编译器在注释的元素中取消显示的警告集。允许使用重复的名称。忽略第二个和后面出现的名称。出现未被识别的警告名不是 错误：编译器必须忽略无法识别的所有警告名。但如果某个注释包含未被识别的警告名，那么编译器可以随意发出一个警告。
各编译器供应商应该将它们所支持的警告名连同注释类型一起记录。鼓励各供应商之间相互合作，确保在多个编译器中使用相同的名称。

### 示例：
- @SuppressWarnings(“unchecked”)
告诉编译器忽略 unchecked 警告信息，如使用List，ArrayList等未进行参数化产生的警告信息。
- @SuppressWarnings(“serial”)
如果编译器出现这样的警告信息：The serializable class WmailCalendar does not declare a static final serialVersionUID field of type long，使用这个注释将警告信息去掉。
- @SuppressWarnings(“deprecation”)
如果使用了使用@Deprecated注释的方法，编译器将出现警告信息。使用这个注释将警告信息去掉。
- @SuppressWarnings(“unchecked”, “deprecation”)
告诉编译器同时忽略unchecked和deprecation的警告信息。
- @SuppressWarnings(value={“unchecked”, “deprecation”})
等同于@SuppressWarnings(“unchecked”, “deprecation”)

- @SuppressWarnings注解的作用
J2SE 提供的最后一个批注是 @SuppressWarnings。该批注的作用是给编译器一条指令，告诉它对被批注的代码元素内部的某些警告保持静默。
@SuppressWarnings 批注允许您选择性地取消特定代码段（即，类或方法）中的警告。其中的想法是当您看到警告时，您将调查它，如果您确定它不是问题，
您就可以添加一个 @SuppressWarnings 批注，以使您不会再看到警告。虽然它听起来似乎会屏蔽潜在的错误，但实际上它将提高代码安全性，因为它将防止
您对警告无动于衷 — 您看到的每一个警告都将值得注意。

我经常遇到的问题是不晓得什么时候用@SupressWarnings的什么批注好，所以做了如下整理
使用：
@SuppressWarnings(“”)
@SuppressWarnings({})
@SuppressWarnings(value={})

一.@SuppressWarings注解
作用：用于抑制编译器产生警告信息。
示例1——抑制单类型的警告：
@SuppressWarnings(“unchecked”)
public void addItems(String item){
@SuppressWarnings(“rawtypes”)
List items = new ArrayList();
items.add(item);
}

示例2——抑制多类型的警告：
@SuppressWarnings(value={“unchecked”, “rawtypes”})
public void addItems(String item){
List items = new ArrayList();
items.add(item);
}

示例3——抑制所有类型的警告：
@SuppressWarnings(“all”)
public void addItems(String item){
List items = new ArrayList();
items.add(item);
}

二.注解目标
通过@SuppressWarnings 的源码可知，其注解目标为类、字段、函数、函数入参、构造函数和函数的局部变量。而大家建议注解应声明在最接近警告发生的位置。

三.抑制警告的关键字
关键字 用途
```text


all to suppress all warnings （抑制所有警告）
boxing to suppress warnings relative to boxing/unboxing operations（抑制装箱、拆箱操作时候的警告）
cast to suppress warnings relative to cast operations （抑制映射相关的警告）
dep-ann to suppress warnings relative to deprecated annotation（抑制启用注释的警告）
deprecation to suppress warnings relative to deprecation（抑制过期方法警告）
fallthrough to suppress warnings relative to missing breaks in switch statements（抑制确在switch中缺失breaks的警告）
finally to suppress warnings relative to finally block that don’t return （抑制finally模块没有返回的警告）
hiding to suppress warnings relative to locals that hide variable（）
incomplete-switch to suppress warnings relative to missing entries in a switch statement (enum case)(忽略没有完整的switch语句)
nls to suppress warnings relative to non-nls string literals(忽略非nls格式的字符)
null to suppress warnings relative to null analysis(忽略对null的操作)
rawtypes to suppress warnings relative to un-specific types when using generics on class params(使用generics时忽略没有指定相应的类型)
restriction to suppress warnings relative to usage of discouraged or forbidden references
serial to suppress warnings relative to missing serialVersionUID field for a serializable class(忽略在serializable类中没有声明serialVersionUID变量)
static-access to suppress warnings relative to incorrect static access（抑制不正确的静态访问方式警告)
synthetic-access to suppress warnings relative to unoptimized access from inner classes（抑制子类没有按最优方法访问内部类的警告）
unchecked to suppress warnings relative to unchecked operations（抑制没有进行类型检查操作的警告）
unqualified-field-access to suppress warnings relative to field access unqualified （抑制没有权限访问的域的警告）
unused to suppress warnings relative to unused code （抑制没被使用过的代码的警告）
————————————————
版权声明：本文为CSDN博主「勿栀枝」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_43036466/article/details/121649652
```