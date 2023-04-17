```java
   @RequestMapping(value = "/vposts/{uid}",method = RequestMethod.POST)
    public String vposts(
            @PathVariable("uid") String uid,  //url参数
            @RequestParam("id") Integer id,   // url 链接参数
            String sss,                       // url 链接参数
            @RequestBody Map<String,String> param){   //post 消息提参数
        System.out.println("uid = " + uid + ", id = " + id + ", sss = " + sss + ", param = " + param);
        return param.toString();
    }
```