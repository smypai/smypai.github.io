*  addslashes() 函数返回在预定义字符之前添加反斜杠的字符串。

```php
<?php
$str = "Who's Bill Gates?";
echo $str . " This is not safe in a database query.<br>";
echo addslashes($str) . " This is safe in a database query.";
?>
```