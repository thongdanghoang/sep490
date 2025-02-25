package enterprise.services;

import enterprise.dtos.SubscribeRequestDTO;
import enterprise.entities.CreditConvertRatioEntity;
import jakarta.validation.Valid;

import java.util.List;

public interface SubscriptionService {
    List<CreditConvertRatioEntity> getCreditConvertRatios();
    
    void subscribe(@Valid SubscribeRequestDTO request);
}
