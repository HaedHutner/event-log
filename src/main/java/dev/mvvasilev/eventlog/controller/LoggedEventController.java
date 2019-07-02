package dev.mvvasilev.eventlog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.mvvasilev.eventlog.dto.ResponseLoggedEventDTO;
import dev.mvvasilev.eventlog.dto.SubmitLoggedEventDTO;
import dev.mvvasilev.eventlog.facade.LoggedEventFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("/api/events")
@RestController
public class LoggedEventController {

    private LoggedEventFacade loggedEventFacade;

    public LoggedEventController(LoggedEventFacade loggedEventFacade) {
        this.loggedEventFacade = loggedEventFacade;
    }

    @PostMapping("/")
    public ResponseLoggedEventDTO postEvent(@RequestBody @Valid SubmitLoggedEventDTO submitLoggedEventDTO) throws JsonProcessingException {
        return loggedEventFacade.postEvent(submitLoggedEventDTO);
    }

    @GetMapping("/")
    public Page<ResponseLoggedEventDTO> getEvents(@NotNull Pageable pageable) {
        return loggedEventFacade.fetchPagedEvents(pageable);
    }
}
