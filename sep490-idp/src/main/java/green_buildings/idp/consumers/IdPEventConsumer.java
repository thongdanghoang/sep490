package green_buildings.idp.consumers;

import green_buildings.commons.api.events.CommitEnterpriseRegisterEvent;
import green_buildings.commons.api.events.RollbackEnterpriseRegisterEvent;
import green_buildings.commons.api.exceptions.BusinessErrorResponse;
import green_buildings.commons.api.utils.MDCContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import green_buildings.idp.dto.SignupDTO;
import green_buildings.idp.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IdPEventConsumer {
    
    private final UserService userService;
    
    @KafkaListener(topics = CommitEnterpriseRegisterEvent.TOPIC)
    public void commitEnterpriseOwnerCreationEvent(ConsumerRecord<String, Object> consumerRecord) {
        if (consumerRecord.value() instanceof CommitEnterpriseRegisterEvent(UUID enterpriseId)) {
            /**
             * Notify waiting thread {@link UserService#signup(SignupDTO, Model)}
             */
            userService.commitEnterpriseOwnerCreation(enterpriseId, getCorrelationId(consumerRecord));
        }
    }
    
    @KafkaListener(topics = RollbackEnterpriseRegisterEvent.TOPIC)
    public void rollbackEnterpriseOwnerCreationEvent(ConsumerRecord<String, Object> consumerRecord) {
        if (consumerRecord.value() instanceof RollbackEnterpriseRegisterEvent event) {
            userService.rollbackEnterpriseOwnerCreation(new BusinessErrorResponse(getCorrelationId(consumerRecord), event.field(), event.i18nKey(), event.args()));
            return;
        }
    }
    
    private String getCorrelationId(ConsumerRecord<String, Object> consumerRecord) {
        return StringUtils.toEncodedString(consumerRecord.headers().lastHeader(MDCContext.CORRELATION_ID).value(), StandardCharsets.UTF_8);
    }
}
