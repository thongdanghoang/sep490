package enterprise.mappers;

import enterprise.dtos.PaymentDTO;
import enterprise.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import vn.payos.type.CheckoutResponseData;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    
    PaymentDTO paymentEntityToPaymentDTO(PaymentEntity paymentEntity);
    
    void updatePaymentFromCheckoutData(CheckoutResponseData payOSResult, @MappingTarget PaymentEntity payment);
}
