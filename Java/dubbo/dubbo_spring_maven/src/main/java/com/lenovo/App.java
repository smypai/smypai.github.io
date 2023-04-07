package com.lenovo;

import com.lenovo.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

//        System.out.println( "Hello World!" );
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
//        HelloService hello = context.getBean(HelloService.class);
//        hello.sayHello();

        ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("classpath:spring.xml");
        context.start();
        System.in.read(); //阻塞当前进程

    }
}
