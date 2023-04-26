```java
package org.lenovo.lc.ecommerces.approval.enums;

public enum NoticeTypeEnum {
    NOTICE_TYPE_APPROVE("approve","审核"),
    NOTICE_TYPE_NOTICE("notice","提醒"),
    NOTICE_TYPE_INVENTORY("inventory","盘点"),
    NOTICE_TYPE_MY_FORM("myform","我提交的"),
    NOTICE_TYPE_SELF_INVENTORY("selfInventory","自盘点");

    private String key;
    private String desc;

    NoticeTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDesc(String key){
        for(NoticeTypeEnum keyName : NoticeTypeEnum.values()){
            if(key.equals(keyName.key)) return keyName.desc;
        }
        return null;
    }

    public static String getKey(String desc){
        for(NoticeTypeEnum keyName : NoticeTypeEnum.values()){
            if(keyName.desc.equals(desc)) return keyName.key;
        }
        return null;
    }


}

```