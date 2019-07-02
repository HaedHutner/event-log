package dev.mvvasilev.eventlog.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.mvvasilev.eventlog.dto.SubmitLoggedEventDTO;
import dev.mvvasilev.eventlog.facade.LoggedEventFacade;
import org.springframework.stereotype.Component;

@Component
public class EventReceiver {

    private LoggedEventFacade loggedEventFacade;

    public EventReceiver(LoggedEventFacade loggedEventFacade) {
        this.loggedEventFacade = loggedEventFacade;
    }

    public void receive(SubmitLoggedEventDTO submitLoggedEventDTO) throws JsonProcessingException {
        loggedEventFacade.postEvent(submitLoggedEventDTO);
    }
}
