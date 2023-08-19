package com.poly.model.paypal;

import lombok.Data;

@Data
public class PayPalLink {
    private String href;
    private String rel;
    private String method;
}
