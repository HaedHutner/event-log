package dev.mvvasilev.eventlog.facade;

import dev.mvvasilev.eventlog.dto.ResponseLoggedEventDTO;
import dev.mvvasilev.eventlog.dto.SubmitLoggedEventDTO;
import dev.mvvasilev.eventlog.service.LoggedEventService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static dev.mvvasilev.eventlog.config.RabbitMQConfiguration.PUBLISH_EVENT_LOG_ROUTING_KEY;

@Component
public class LoggedEventFacade {

    private ModelMapper modelMapper;

    private LoggedEventService loggedEventService;

    private RabbitTemplate rabbitTemplate;

    public LoggedEventFacade(ModelMapper modelMapper, LoggedEventService loggedEventService, RabbitTemplate rabbitTemplate) {
        this.modelMapper = modelMapper;
        this.loggedEventService = loggedEventService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseLoggedEventDTO postEvent(SubmitLoggedEventDTO submitLoggedEventDTO) {
        ResponseLoggedEventDTO response = modelMapper.map(loggedEventService.logEvent(
                submitLoggedEventDTO.getEventType(),
                submitLoggedEventDTO.getSubmittedAt(),
                submitLoggedEventDTO.getSource(),
                submitLoggedEventDTO.getVersion(),
                submitLoggedEventDTO.getData()
        ), ResponseLoggedEventDTO.class);

        rabbitTemplate.convertAndSend(String.format(PUBLISH_EVENT_LOG_ROUTING_KEY, response.getEventType()), response);

        return response;
    }

    public Page<ResponseLoggedEventDTO> fetchPagedEvents(Pageable pageable) {
        return loggedEventService.fetchPagedEvents(pageable).map(entity -> modelMapper.map(entity, ResponseLoggedEventDTO.class));
    }
}
