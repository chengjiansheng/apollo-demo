package com.cjs.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author ChengJianSheng
 * @date 2019-05-31
 */
@Slf4j
@Service
public class PrintLogger {

    @PostConstruct
    public void printLogger() throws Exception{
        while (true) {
            log.error("我是error级别日志");
            log.warn("我是warn级别日志");
            log.info("我是info级别日志");
            log.debug("我是debug级别日志");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
