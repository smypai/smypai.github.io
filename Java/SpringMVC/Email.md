### 1. pom.xml
```xml
<dependency>
   <groupId>javax.mail</groupId>
   <artifactId>mail</artifactId>
   <version>1.4.5</version>
</dependency>
<dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-context-support</artifactId>
   <version>4.2.4.RELEASE</version>
</dependency>
```
### 2. 在spring-context.xml 中添加如下配置
```xml
<bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
   <property name="host" value="${mail.smtp.host}"/>
   <property name="port" value="${mail.smtp.port}"/>
   <property name="username" value="${mail.smtp.username}"/>
   <property name="password" value="${mail.smtp.password}"/>
   <property name="defaultEncoding" value="${mail.smtp.defaultEncoding}"/>
   <property name="javaMailProperties">
      <props>
         <prop key="mail.smtp.auth">${mail.smtp.auth}</prop>
         <prop key="mail.smtp.ssl.timeout">${mail.smtp.timeout}</prop>
         <prop key="mail.smtp.socketFactory.port">${mail.smtp.port}</prop>
         <prop key="mail.smtp.socketFactory.class">javax.net.ssl.SSLSocketFactory</prop>
         <prop key="mail.smtp.ssl.trust">${mail.smtp.host}</prop>
         <prop key="mail.smtp.starttls.enable">true</prop>
         <prop key="mail.smtp.starttls.required">true</prop>
         <prop key="mail.smtp.port">${mail.smtp.port}</prop>
         <prop key="mail.debug">true</prop>
      </props>
   </property>
</bean>
```
### 3. 配置文件中添加如下配置
```properties
#邮件发送配置
#服务器主机名 smtp.xx.com
mail.smtp.host=smtp.163.com
mail.smtp.port=465
mail.smtp.username=发邮件的人邮箱用户名
#客户端授权码
mail.smtp.password=你的发邮件的邮箱授权码
#编码字符
mail.smtp.defaultEncoding=utf-8
#是否进行用户名密码校验
mail.smtp.auth=true
#设置超时时间
mail.smtp.timeout=20000
```
### 4. 编写service接口
```java
/**
 * 发送邮件service
 * @author tcf
 * @date 2022/04/14
 */
public interface ISendMailService {
    boolean sendEmail(String recipient,  String subject, String content);
}
```
### 5.编写service实现类
```java
import com.javamalls.admin.service.ISendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
 
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
 
/**
 * 发送邮件service
 * @author tcf
 * @date 2022/04/14
 */
@Slf4j
@Service
public class SendMailServiceImpl implements ISendMailService {
    /*private JavaMailSender javaMailSender;
    private SimpleMailMessage simpleMailMessage;*/
    /**
     *      JavaMailSenderImpl支持MimeMessages和SimpleMailMessages。
     *      MimeMessages为复杂邮件模板，支持文本、附件、html、图片等。
     *      SimpleMailMessages实现了MimeMessageHelper，为普通邮件模板，支持文本
     */
 
    private JavaMailSenderImpl mailSender;
 
    @Autowired
    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }
 
    /**
     * 发件人邮箱地址
     */
    @Value("${mail.smtp.username}")
    private String emailFrom;
 
    /**
     * 单发
     *
     * @param recipient 收件人邮箱
     * @param subject   邮件主题
     * @param content   邮件内容
     */
    @Override
    public boolean sendEmail(String recipient, String subject, String content){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
 
            /** 发件人的邮箱地址 */
            messageHelper.setFrom(emailFrom);
            /** 收件人邮箱地址 */
            messageHelper.setTo(recipient);
            /** 主题 */
            messageHelper.setSubject(subject);
            /** 内容 */
            messageHelper.setText(content, true);//true代表支持html格式
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            log.error("邮件发送失败", e);
            return false;
        }
 
    }
}
```
### 6. 编写controller测试类
```java
import com.javamalls.admin.service.ISendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class TestMailController {
 
    @Autowired
    private ISendMailService iSendMailService;
 
    @RequestMapping("/sendMail")
    public String testSendMail() {
        iSendMailService.sendEmail("接收邮件的邮箱", "测试邮件", "你好我是一封测试邮件");
        return "success";
    }
}