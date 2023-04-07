package com.lenovo.service.impl;

import com.lenovo.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    public String sayHello() {
        System.out.println("say hello");
        return "hello !";
    }
}
