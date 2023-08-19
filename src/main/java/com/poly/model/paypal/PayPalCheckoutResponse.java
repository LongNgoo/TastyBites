package com.poly.model.paypal;

import com.poly.model.paypal.PayPalLink;
import lombok.Data;

import java.util.List;

@Data
public class PayPalCheckoutResponse {
    private String id;
    private String status;
    private List<PayPalLink> links;
}
