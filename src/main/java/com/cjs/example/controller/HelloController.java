package com.cjs.example.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChengJianSheng
 * @date 2019-05-29
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Value("${my.name}")
    private String name;

    @GetMapping("/index")
    public String index() {
        return name;
    }

    @GetMapping("/sayHello")
    public String sayHello() {
        Config config = ConfigService.getAppConfig();
        String str = config.getProperty("my.hello", "Hello World");
        System.out.println(str);

        return str;
    }

}
