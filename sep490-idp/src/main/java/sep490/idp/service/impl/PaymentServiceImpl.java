package sep490.idp.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import sep490.common.api.exceptions.TechnicalException;
import sep490.idp.dto.CreditPurchaseItem;
import sep490.idp.service.PaymentService;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    
    private final PayOS payOS;
    
    @Value("${payment.payos.returnPath}")
    private String returnPath;
    
    @Value("${payment.payos.cancelPath}")
    private String cancelPath;
    
    @Value("${spring.application.homepage}")
    private String homepage;
    
    private String returnUrl;
    private String cancelUrl;
    
    @PostConstruct
    public void setUrls() {
        returnUrl = UriComponentsBuilder.fromUriString(homepage)
                                        .path(returnPath)
                                        .build()
                                        .toUriString();
        cancelUrl = UriComponentsBuilder.fromUriString(homepage)
                                        .path(cancelPath)
                                        .build()
                                        .toUriString();
    }
    
    public CheckoutResponseData getCheckoutData(CreditPurchaseItem creditPurchaseItem) {
        try {
            log.info("Creating payment link for item: {}", creditPurchaseItem);
            PaymentData paymentData = createPaymentData(creditPurchaseItem);
            log.debug("Generated PaymentData: {}", paymentData);
            return payOS.createPaymentLink(paymentData);
        } catch (Exception ex) {
            log.error("Error creating payment link for item: {}", creditPurchaseItem);
            throw new TechnicalException("Error creating payment link for item", ex);
        }
    }
    
    private PaymentData createPaymentData(CreditPurchaseItem creditPurchaseItem) {
        ItemData itemData = createItemData(creditPurchaseItem);
        return PaymentData.builder()
                          .orderCode(creditPurchaseItem.code())
                          .amount(creditPurchaseItem.price())
                          .item(itemData)
                          .returnUrl(returnUrl)
                          .cancelUrl(cancelUrl)
                          .build();
    }
    
    private ItemData createItemData(CreditPurchaseItem creditPurchaseItem) {
        return ItemData.builder()
                       .name("Credit Purchase")
                       .quantity(creditPurchaseItem.amount())
                       .price(creditPurchaseItem.price())
                       .build();
    }
}
