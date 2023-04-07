httpClient 使用详解
一、
HttpClient:是一个接口

首先需要先创建一个DefaultHttpClient的实例

HttpClient httpClient=new DefaultHttpClient(); 或者 CloseableHttpClient httpclient = HttpClients.createDefault();

1、发送GET请求

HttpGet httpget = new HttpGet("http://xxx");

 httpclient.execute(httpget);  

2、发送post请求

HttpPost httpPost = new HttpPost(url);

添加参数

response = httpclient.execute(httpPost);

二、
1. 基于标准、纯净的java语言。实现了Http1.0和Http1.1

2. 以可扩展的面向对象的结构实现了Http全部的方法（GET, POST, PUT, DELETE, HEAD, OPTIONS, and TRACE）。

3. 支持HTTPS协议。

4. 通过Http代理建立透明的连接。

5. 利用CONNECT方法通过Http代理建立隧道的https连接。

6. Basic, Digest, NTLMv1, NTLMv2, NTLM2 Session, SNPNEGO/Kerberos认证方案。

7. 插件式的自定义认证方案。

8. 便携可靠的套接字工厂使它更容易的使用第三方解决方案。

9. 连接管理器支持多线程应用。支持设置最大连接数，同时支持设置每个主机的最大连接数，发现并关闭过期的连接。

10. 自动处理Set-Cookie中的Cookie。

11. 插件式的自定义Cookie策略。

12. Request的输出流可以避免流中内容直接缓冲到socket服务器。

13. Response的输入流可以有效的从socket服务器直接读取相应内容。

14. 在http1.0和http1.1中利用KeepAlive保持持久连接。

15. 直接获取服务器发送的response code和 headers。

16. 设置连接超时的能力。

17. 实验性的支持http1.1 response caching。

18. 源代码基于Apache License 可免费获取。

三、
使用HttpClient发送请求、接收响应很简单，一般需要如下几步即可。

1. 创建HttpClient对象。

2. 创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。

3. 如果需要发送请求参数，可调用HttpGet、HttpPost共同的setParams(HetpParams params)方法来添加请求参数；对于HttpPost对象而言，也可调用setEntity(HttpEntity entity)方法来设置请求参数。

4. 调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。

5. 调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。

6. 释放连接。无论执行方法是否成功，都必须释放连接

 四、例子
```java
package com.neo.utils.http;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
 
public class HttpClientDemo {
    public static void main(String[] args) throws Exception {
        String url = "www.xxx";
//       postJson();
//       get();
//       postForm();
    }
 
    public static void postJson(String url, String jsonParams) throws Exception {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        // 接收响应结果
        CloseableHttpResponse response = null;
        // HttpClient httpclients = HttpClients.createDefault();
        try {
            // 创建httppost
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
            // 参数
            StringEntity se = new StringEntity(jsonParams);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");// 发送json需要设置contentType
            httpPost.setEntity(se);
            response = httpclient.execute(httpPost);
            // 解析返结果
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resStr = EntityUtils.toString(entity, "UTF-8");
                System.out.println(resStr);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
            response.close();
        }
    }
 
    /**
     * post方式提交表单（模拟用户登录请求）
     *
     * @throws Exception
     */
    public static void postForm(String url, Map<String, String> params) throws Exception {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        // 发送请求
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            // 创建httppost
 
            HttpPost httppost = new HttpPost(url);
            // 创建参数队列 ,通过一个NameValuePair集合来存放待提交的参数,并将这个参数集合传入到一个
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            // 设置参数
            for (String key : params.keySet()) {
                formparams.add(new BasicNameValuePair(key, params.get(key)));
            }
            // 参数转码
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            response = httpclient.execute(httppost);
            //打印响应状态
            System.out.println(response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity, "UTF-8"));
            }
            // 释放连接
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
            response.close();
        }
    }
 
    /**
     * 发送 get请求
     *
     * @throws Exception
     */
    public static void get(String url) throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // 执行get请求.
            response = httpclient.execute(httpget);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
 
            // 打印响应状态
            System.out.println(response.getStatusLine().getStatusCode());
            if (entity != null) {
                // 打印响应内容
                System.out.println("Response content: " + EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
            response.close();
        }
    }
 
}