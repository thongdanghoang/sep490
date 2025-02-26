package greenbuildings.enterprise.services;

import greenbuildings.enterprise.dtos.SubscribeRequestDTO;
import greenbuildings.enterprise.entities.CreditConvertRatioEntity;
import jakarta.validation.Valid;

import java.util.List;

public interface SubscriptionService {
    List<CreditConvertRatioEntity> getCreditConvertRatios();
    
    void subscribe(@Valid SubscribeRequestDTO request);
}
