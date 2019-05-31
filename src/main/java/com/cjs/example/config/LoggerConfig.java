package com.cjs.example.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author ChengJianSheng
 * @date 2019-05-31
 */
@Slf4j
@Configuration
public class LoggerConfig {

    private static final String LOGGER_TAG = "logging.level.";

    /**
     * 注入默认的命名空间配置
     */
    @ApolloConfig
    private Config config;

    @Autowired
    private LoggingSystem loggingSystem;

    @ApolloConfigChangeListener
    private void onChange(ConfigChangeEvent configChangeEvent) {
        System.out.println("配置发生变化");
        System.out.println("Changes for namespace " + configChangeEvent.getNamespace());
        for (String key : configChangeEvent.changedKeys()) {
            ConfigChange change = configChangeEvent.getChange(key);
            System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
        }

        Set<String> keyNames = config.getPropertyNames();
        for (String key : keyNames) {
            if (StringUtils.isEmpty(key)) {
                continue;
            }
            if (!key.startsWith(LOGGER_TAG)) {
                continue;
            }

            String loggerName = key.replace(LOGGER_TAG, "");
            String strLevel = config.getProperty(key, "info");
            LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
            loggingSystem.setLogLevel(loggerName, level);

            log.info("{}:{}", key, strLevel);
        }
    }

}
