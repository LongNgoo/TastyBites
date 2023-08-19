package com.poly.model.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Amount {
    @JsonProperty("currency_code")
    private String currencyCode;
    private String value;
}
