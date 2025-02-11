package enterprise.consumers;

import enterprise.mappers.EnterpriseMapper;
import enterprise.producers.EnterpriseEventProducer;
import enterprise.services.EnterpriseService;
import green_buildings.commons.api.events.PendingEnterpriseRegisterEvent;
import green_buildings.commons.api.exceptions.BusinessException;
import green_buildings.commons.api.utils.MDCContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnterpriseEventConsumer {
    
    private final EnterpriseEventProducer enterpriseEventProducer;
    private final EnterpriseService enterpriseService;
    private final EnterpriseMapper enterpriseMapper;
    
    @KafkaListener(topics = PendingEnterpriseRegisterEvent.TOPIC)
    public void handleEnterpriseRegisterEvent(ConsumerRecord<String, Object> event) {
        var correlationId = getCorrelationId(event);
        if (event.value() instanceof PendingEnterpriseRegisterEvent enterpriseCreateEvent) {
            try {
                var enterprise = enterpriseMapper.createEnterprise(enterpriseCreateEvent);
                var enterpriseId = enterpriseService.createEnterprise(enterprise);
                enterpriseEventProducer.publishEnterpriseCreatedEvent(correlationId, enterpriseId);
            } catch (Exception exception) {
                if (exception instanceof BusinessException businessException) {
                    enterpriseEventProducer.publishEnterpriseCreationFailedEvent(correlationId, businessException);
                }
                enterpriseEventProducer.publishEnterpriseCreationFailedEvent(correlationId, new BusinessException("enterprise", "UserRegister.UnexpectedError", List.of()));
            }
        }
    }
    
    private String getCorrelationId(ConsumerRecord<String, Object> consumerRecord) {
        return StringUtils.toEncodedString(consumerRecord.headers().lastHeader(MDCContext.CORRELATION_ID).value(), StandardCharsets.UTF_8);
    }
    
}
