package dev.mvvasilev.eventlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.mvvasilev.eventlog.enums.EventType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class SubmitLoggedEventDTO {

    @NotNull
    private EventType eventType;

    @NotEmpty
    private String source;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddThh:dd:ss.SSS")
    private LocalDateTime submittedAt;

    private int version;

    private Map<String, Object> data;

    public SubmitLoggedEventDTO() {
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmitLoggedEventDTO that = (SubmitLoggedEventDTO) o;
        return version == that.version &&
                eventType == that.eventType &&
                Objects.equals(source, that.source) &&
                Objects.equals(submittedAt, that.submittedAt) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, source, submittedAt, version, data);
    }
}
