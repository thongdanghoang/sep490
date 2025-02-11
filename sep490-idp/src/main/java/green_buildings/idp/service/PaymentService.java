package green_buildings.idp.service;

import green_buildings.idp.dto.CreditPurchaseItem;
import vn.payos.type.CheckoutResponseData;

public interface PaymentService {
    public CheckoutResponseData getCheckoutData(CreditPurchaseItem creditPurchaseItem);
    
}
