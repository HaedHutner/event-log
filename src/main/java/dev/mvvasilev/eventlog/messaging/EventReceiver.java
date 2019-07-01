package dev.mvvasilev.eventlog.messaging;

import dev.mvvasilev.eventlog.dto.SubmitLoggedEventDTO;
import dev.mvvasilev.eventlog.facade.LoggedEventFacade;
import org.springframework.stereotype.Component;

@Component
public class EventReceiver {

    private LoggedEventFacade loggedEventFacade;

    public EventReceiver(LoggedEventFacade loggedEventFacade) {
        this.loggedEventFacade = loggedEventFacade;
    }

    public void receive(SubmitLoggedEventDTO submitLoggedEventDTO) {
        loggedEventFacade.postEvent(submitLoggedEventDTO);
    }
}
