package dev.mvvasilev.eventlog;

import dev.mvvasilev.eventlog.config.EventLogProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Collections;

@SpringBootApplication
@EnableConfigurationProperties(EventLogProperties.class)
public class EventLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventLogApplication.class, args);
    }

}
