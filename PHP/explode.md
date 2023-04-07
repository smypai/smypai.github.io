```php
//explode 把字符串打散为数组：
$str = "Hello world. I love Shanghai!";
print_r (explode(" ",$str));


//separator	必需。规定在哪里分割字符串。
//string	必需。要分割的字符串。
//limit	    可选。规定所返回的数组元素的数目。

//limit 可能的值：
//大于 0 - 返回包含最多 limit 个元素的数组
//小于 0 - 返回包含除了最后的 -limit 个元素以外的所有元素的数组
//0 - 返回包含一个元素的数组