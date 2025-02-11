package enterprise.mappers;

import enterprise.dtos.PaymentDTO;
import enterprise.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    
    PaymentDTO paymentEntityToPaymentDTO(PaymentEntity paymentEntity);
}
