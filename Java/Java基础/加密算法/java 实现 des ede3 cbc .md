```java

package org.lenovo.lc.ecommerces.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
public class Encryption {
    /*密钥*/
    private static final String KEY = "key123key123";
    /*向量，8个或10个字符*/
    private static final String IV = "val123Val123";


    public String encrypt(String data){
        try {
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,//加密
                    new SecretKeySpec(KEY.getBytes("UTF-8"), "DESede"),
                    new IvParameterSpec(IV.getBytes("UTF-8")));
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
        }catch (Exception e){
            log.error("加密失败. [message={}]", e.getMessage(), e);
        }
        return data ;
    }


    public String decrypt(String data){
        try{
            // base64 反转
            byte[] dataDecode = Base64.getDecoder().decode(data.getBytes());
            //解密
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, //解密
                    new SecretKeySpec(KEY.getBytes("UTF-8"), "DESede"),,
                    new IvParameterSpec(IV.getBytes("UTF-8")));
            return new String(cipher.doFinal(dataDecode));

        }catch (Exception e){
            log.error("解密失败. [message={}]", e.getMessage(), e);
        }
        return null;
    }

}
