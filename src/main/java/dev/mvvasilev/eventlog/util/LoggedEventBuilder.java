package dev.mvvasilev.eventlog.util;

import dev.mvvasilev.eventlog.entity.LoggedEvent;
import dev.mvvasilev.eventlog.enums.EventType;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class LoggedEventBuilder {

    private EventType eventType;

    private LocalDateTime submittedAt;

    private String source;

    private int version;

    private Map<String, Object> eventData = new HashMap<>();

    public LoggedEventBuilder() {
    }

    public LoggedEventBuilder eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public LoggedEventBuilder submittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
        return this;
    }

    public LoggedEventBuilder source(String source) {
        this.source = source;
        return this;
    }

    public LoggedEventBuilder version(int version) {
        this.version = version;
        return this;
    }

    public LoggedEventBuilder eventData(Map<String, Object> eventData) {
        this.eventData = eventData;
        return this;
    }

    public LoggedEventBuilder addData(String key, Object value) {
        this.eventData.put(key, value);
        return this;
    }

    public LoggedEvent build() {
        return new LoggedEvent(
                eventType,
                submittedAt,
                source,
                version,
                eventData
        );
    }
}
