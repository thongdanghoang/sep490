package sep490.idp.service;

import sep490.idp.dto.CreditPurchaseItem;
import vn.payos.type.CheckoutResponseData;

public interface PaymentService {
    public CheckoutResponseData getCheckoutData(CreditPurchaseItem creditPurchaseItem);
    
}
