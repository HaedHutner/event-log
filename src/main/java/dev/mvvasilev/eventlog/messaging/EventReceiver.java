package dev.mvvasilev.eventlog.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mvvasilev.common.dto.EventLog;
import dev.mvvasilev.eventlog.facade.LoggedEventFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.util.Map;

@Component
public class EventReceiver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LoggedEventFacade loggedEventFacade;

    private ObjectMapper objectMapper;

    private Validator validator;

    public EventReceiver(LoggedEventFacade loggedEventFacade, ObjectMapper objectMapper, Validator mvcValidator) {
        this.loggedEventFacade = loggedEventFacade;
        this.objectMapper = objectMapper;
        this.validator = mvcValidator;
    }

    public void receiveMessage(EventLog message) {
        try {
            BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(this, message.toString());

            validator.validate(message, bindingResult);

            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(error -> logger.error(error.toString()));
                return;
            }

            loggedEventFacade.postEvent(message);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
