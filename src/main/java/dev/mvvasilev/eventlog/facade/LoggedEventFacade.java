package dev.mvvasilev.eventlog.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mvvasilev.eventlog.config.EventLogProperties;
import dev.mvvasilev.eventlog.dto.ResponseLoggedEventDTO;
import dev.mvvasilev.eventlog.dto.SubmitLoggedEventDTO;
import dev.mvvasilev.eventlog.service.LoggedEventService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class LoggedEventFacade {

    private EventLogProperties properties;

    private ModelMapper modelMapper;

    private ObjectMapper objectMapper;

    private LoggedEventService loggedEventService;

    private RabbitTemplate rabbitTemplate;

    public LoggedEventFacade(EventLogProperties properties, ModelMapper modelMapper, ObjectMapper objectMapper, LoggedEventService loggedEventService, RabbitTemplate rabbitTemplate) {
        this.properties = properties;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.loggedEventService = loggedEventService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseLoggedEventDTO postEvent(SubmitLoggedEventDTO submitLoggedEventDTO) throws JsonProcessingException {
        ResponseLoggedEventDTO response = modelMapper.map(loggedEventService.logEvent(
                submitLoggedEventDTO.getEventType(),
                submitLoggedEventDTO.getSubmittedAt(),
                submitLoggedEventDTO.getSource(),
                submitLoggedEventDTO.getVersion(),
                submitLoggedEventDTO.getData()
        ), ResponseLoggedEventDTO.class);

        rabbitTemplate.convertAndSend(
                properties.getExchange(),
                properties.getPublishRoutingKey(),
                objectMapper.writeValueAsString(response)
        );

        return response;
    }

    public Page<ResponseLoggedEventDTO> fetchPagedEvents(Pageable pageable) {
        return loggedEventService.fetchPagedEvents(pageable).map(entity -> modelMapper.map(entity, ResponseLoggedEventDTO.class));
    }
}
