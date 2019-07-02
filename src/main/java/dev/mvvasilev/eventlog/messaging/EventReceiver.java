package dev.mvvasilev.eventlog.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mvvasilev.eventlog.dto.SubmitLoggedEventDTO;
import dev.mvvasilev.eventlog.facade.LoggedEventFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.io.IOException;

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

    public void receiveMessage(byte[] message) throws IOException {
        SubmitLoggedEventDTO dto = objectMapper.readValue(message, SubmitLoggedEventDTO.class);

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(this, dto.toString());

        validator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.error(error.toString()));
            return;
        }

        loggedEventFacade.postEvent(dto);
    }
}
