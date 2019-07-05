package dev.mvvasilev.eventlog.service;

import dev.mvvasilev.common.enums.EventType;
import dev.mvvasilev.eventlog.entity.LoggedEvent;
import dev.mvvasilev.eventlog.repository.LoggedEventRepository;
import dev.mvvasilev.eventlog.util.LoggedEventBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class LoggedEventService {

    private LoggedEventRepository loggedEventRepository;

    public LoggedEventService(LoggedEventRepository loggedEventRepository) {
        this.loggedEventRepository = loggedEventRepository;
    }

    public LoggedEvent logEvent(EventType eventType, LocalDateTime dateSubmitted, String source, int version, Map<String, Object> data) {
        return loggedEventRepository.save(new LoggedEventBuilder()
                .eventType(eventType)
                .submittedAt(dateSubmitted)
                .source(source)
                .version(version)
                .eventData(data)
                .build()
        );
    }

    public Page<LoggedEvent> fetchPagedEvents(Pageable pageable) {
        return loggedEventRepository.findAll(pageable);
    }

}
