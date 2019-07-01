package dev.mvvasilev.eventlog.entity;

import dev.mvvasilev.eventlog.enums.EventType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Document
public class LoggedEvent {

    @Id
    private String id;

    @Field
    private EventType eventType;

    @Field
    private LocalDateTime submittedAt;

    @Field
    private String source;

    @Field
    private int version;

    @Field
    private Map<String, Object> eventData;

    public LoggedEvent(EventType eventType, LocalDateTime submittedAt, String source, int version, Map<String, Object> eventData) {
        this.eventType = eventType;
        this.submittedAt = submittedAt;
        this.source = source;
        this.version = version;
        this.eventData = eventData;
    }

    public String getId() {
        return id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getSource() {
        return source;
    }

    public int getVersion() {
        return version;
    }

    public Map<String, Object> getEventData() {
        return Collections.unmodifiableMap(eventData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoggedEvent that = (LoggedEvent) o;
        return version == that.version &&
                Objects.equals(id, that.id) &&
                eventType == that.eventType &&
                Objects.equals(submittedAt, that.submittedAt) &&
                Objects.equals(source, that.source) &&
                Objects.equals(eventData, that.eventData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventType, submittedAt, source, version, eventData);
    }
}
