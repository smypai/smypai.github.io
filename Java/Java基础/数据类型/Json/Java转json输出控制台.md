## java如何将json数据格式化输出到控制台

```java
    /**
     * 输出json
     *
     * @param jsonObject json格式响应实体
     */
    public static JSONObject output(JSONObject jsonObject) {
        if (MapUtils.isEmpty(jsonObject)) {
            output("json 对象是空的！");
            return jsonObject;
        }
        String start = SourceCode.getManyString(SPACE_1, 4);
        String jsonStr = jsonObject.toString();// 先将json对象转化为string对象
        jsonStr = jsonStr.replaceAll("\\\\/", OR);
        int level = 0;// 用户标记层级
        StringBuffer jsonResultStr = new StringBuffer("＞  ");// 新建stringbuffer对象，用户接收转化好的string字符串
        for (int i = 0; i < jsonStr.length(); i++) {// 循环遍历每一个字符
            char piece = jsonStr.charAt(i);// 获取当前字符
            // 如果上一个字符是断行，则在本行开始按照level数值添加标记符，排除第一行
            if (i != 0 && '\n' == jsonResultStr.charAt(jsonResultStr.length() - 1)) {
                for (int k = 0; k < level; k++) {
                    jsonResultStr.append(start);
                }
            }
            switch (piece) {
                case ',':
                    // 如果是“,”，则断行
                    char last = jsonStr.charAt(i - 1);
                    if ("\"0123456789le]}".contains(last + EMPTY)) {
                        jsonResultStr.append(piece + LINE);
                    } else {
                        jsonResultStr.append(piece);
                    }
                    break;
                case '{':
                case '[':
                    // 如果字符是{或者[，则断行，level加1
                    jsonResultStr.append(piece + LINE);
                    level++;
                    break;
                case '}':
                case ']':
                    // 如果是}或者]，则断行，level减1
                    jsonResultStr.append(LINE);
                    level--;
                    for (int k = 0; k < level; k++) {
                        jsonResultStr.append(start);
                    }
                    jsonResultStr.append(piece);
                    break;
                default:
                    jsonResultStr.append(piece);
                    break;
            }
        }
        output(LINE + "↘ ↘ ↘ ↘ ↘ ↘ ↘ ↘ json ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙" + LINE + jsonResultStr.toString().replaceAll(LINE, LINE + "＞  ") + LINE + "↘ ↘ ↘ ↘ ↘ ↘ ↘ ↘ json ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙ ↙");
        return jsonObject;
    }
    
```