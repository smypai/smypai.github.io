```
从 JDK 15 开始，Java 提供了一个称为 Text Blocks 的语法，你可以在代码中直接使用这个功能，如果你安装这个版本的 JDK 的话，那么需要使用一些特定方法实现这个目标。

文本块

我们可以使用文本块来在代码中定义，文本块使用的是 3 个双引号 “”" (three double quote 3 个双引号)：

public String textBlocks() {
    return """
        Get busy living
        or
        get busy dying.
        --Stephen King""";
}
这种定义方式在当前 Java 中最方便的实现了，但是因为 JDK 版本的限制，很多项目可能根本没有办法使用这种定义方式。

如果你还没有使用 Java 15，但是使用了 Java 13 和 14 版本的话，我们可以启用预览功能来进行支持。 在后面的文章中，我们将探讨下肯能有的其他实现方式来实现文本块的功能。

获得行分隔符

每个操作系统使用自己的方式来确定是否开始一个新行。

在 Java 中，能够非常容易的获得针对运行的操作系统使用的是什么行分隔符，使用下面的代码就可以获得行分隔符了。

String newLine = System.getProperty("line.separator");
如果你的 JDK 版本是在 7 以上的版本的话，你可以直接使用系统提供的：

System.out.println(System.lineSeparator());
方法。

我们将会使用 newLine 变量在后续的代码中插入一个新行。

String 字符串拼接

String concat 方法能够让我们非常容易的对字符串进行拼接，请考察下面的代码：

public String stringConcatenation() {
    return "Get busy living"
            .concat(newLine)
            .concat("or")
            .concat(newLine)
            .concat("get busy dying.")
            .concat(newLine)
            .concat("--Stephen King");
}
使用 + 操作符，是能够实现上面字符串拼接的另外一种方法。

Java 编译器将会把 concat() 和 + 操作符编译成相同的代码：

public String stringConcatenation() {
    return "Get busy living"
            + newLine
            + "or"
            + newLine
            + "get busy dying."
            + newLine
            + "--Stephen King";
}
字符串 String Join

Java 8 使用了一个叫做 String#join 新方法，这个方法将会使用一系列的字符串作为参数。

这个方法将会返回使用分隔符分隔的所有以字符串为参数的字符串。

public String stringJoin() {
    return String.join(newLine,
                       "Get busy living",
                       "or",
                       "get busy dying.",
                       "--Stephen King");
}
String Builder

StringBuilder 通常被用来帮助构建字符串，相同的还有一个 StringBuffer，这 2 者的区别主要在于是否是线程安全的问题。请参考：Java 的 StringBuffer 和 StringBuilder 的不同 文章中的内容。

最简单的解释就是 StringBuilder 是线程不安全的。

在基本 Java 实践中，如果我们需要在程序中构建字符串，通常都会使用 StringBuilder 或者 StringBuffer，而尽量避免使用 Java String 的 + 操作符：

public String stringBuilder() {
    return new StringBuilder()
            .append("Get busy living")
            .append(newLine)
            .append("or")
            .append(newLine)
            .append("get busy dying.")
            .append(newLine)
            .append("--Stephen King")
            .toString();
}
String Writer

StringWriter 是另外一种可以用来创建多行字符串的方法。

在这个方法中，我们不需要使用 newLine ，因为我们使用了 PrintWriter 对象，这个对象将会自动添加一个方法 println(); （如果你查看下 JDK 的源代码的话就了解了。）

public String stringWriter() {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    printWriter.println("Get busy living");
    printWriter.println("or");
    printWriter.println("get busy dying.");
    printWriter.println("--Stephen King");
    return stringWriter.toString();
}
Guava Joiner

使用外部库，通常对字符串拼接这个问题来说意义不大。

如果你的项目已经使用了外部库的话，你也是可以直接拿来用的。

例如，大部分项目中都会使用的 Guava。我们可以使用 Guava 中的 Joiner 类。

public String guavaJoiner() {
    return Joiner.on(newLine).join(ImmutableList.of("Get busy living",
        "or",
        "get busy dying.",
        "--Stephen King"));
}
从文件中载入

Java 读取文件和从文件中的输出是相同的。

换句话说，Java 从文件中读到什么就会显示什么，因为对于比较长的文本，可以使用属性文件或者文件的方式来把这些数据从程序中分离出来。

读取文件的方式有非常多种，我们通常会使用第三方的库来读取，因为 Java 读取文件的时候比较容易出现异常，同时读取文件有时候还需要处理文件读取的流。

例如下面的代码，我们使用的是原生的文件读取方式：

public String loadFromFile() throws IOException {
    return new String(Files.readAllBytes(Paths.get("src/main/resources/stephenking.txt")));
}
使用 IDE 的特性

很多的 IDE 都能够支持长字符串的复制和粘贴。

尤其在粘贴的时候，IDE 通常都能够自动在你拷贝文本的后面添加回车换行符号，就是我们常说的 \r\n。

需要注意的是，这个是没有办法在运行时使用的。

这个功能就是简单的将一段长文本添加了回车换行，也让你不用每行后面都去自己添加回车换行符了。

结论

在本文中，我们对 Java 使用的多行字符串进行了探讨。

好消息是从 Java 15 开始，我们有了原生实现的方式来进行处理了。但，当前绝大部分环境还在使用 Java 8 或者 11，因此不少情况下还是需要使用替代的实现方式。

引入 https://maimai.cn/article/detail?fid=1746522921&efid=rXH24HRl1dYjd5KsMNYh0w
