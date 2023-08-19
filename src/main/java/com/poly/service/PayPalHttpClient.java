package com.poly.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.*;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.model.paypal.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import static com.poly.PayPalEndpoints.*;

@Component
public class PayPalHttpClient {
    private final HttpClient httpClient;
    private final PaypalConfig paypalConfig;
    private final ObjectMapper objectMapper;

    public PayPalHttpClient(PaypalConfig paypalConfig, ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        this.paypalConfig = paypalConfig;
        this.objectMapper = objectMapper;
    }

    public String createOrder(Order order) throws Exception {
        var accessTokenDto = getAccessToken();

        var appContext = new PayPalAppContextDTO();
        appContext.setReturnUrl("http://localhost:8080/order/checkout/success/" + order.getId());
        appContext.setBrandName("My brand");
        appContext.setLandingPage(PaymentLandingPage.BILLING);

        var amount = order.getOrderDetails().stream().mapToDouble(o -> o.getPrice()).sum();
        var purchase = new PurchaseUnit();
        purchase.setAmount(new Amount("USD", String.valueOf(amount)));

        var payload = new PayPalPayload();
        payload.setApplicationContext(appContext);
        payload.setPurchaseUnits(Arrays.asList(purchase));
        var request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalConfig.getBaseUrl(), ORDER_CHECKOUT)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenDto.getAccessToken())
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(payload)))
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var content = response.body();
        var checkoutResponse = objectMapper.readValue(content.toString(), PayPalCheckoutResponse.class);
        var url = checkoutResponse.getLinks().stream().filter(co -> StringUtils.equals("approve", co.getRel())).map(co -> co.getHref()).findFirst();
        if (url.isPresent()) return url.get();
        return null;
    }

    public AccessTokenResponseDTO getAccessToken() throws Exception {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl("https://api-m.sandbox.paypal.com", GET_ACCESS_TOKEN)))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, encodeBasicCredentials())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en_US")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var content = response.body();
        return objectMapper.readValue(content, AccessTokenResponseDTO.class);
    }

    private String encodeBasicCredentials() {
        var str = "AYQzwXi9KRhdMKBbSK3iOrxpcDPi7Mt-N5sbSyuUXKt8qaNhWCbUDkT0V7V5WBC49Bhdi8kfQ-l4VMuQ" + ":" + "EA7LiNyFJ4IxS8tziSUXcYe1MXE5YiOKy79ckIt74YEGD87F8QvyvRKBYxTrlZL6j1pOcE0LlQwMkucT";
        return "Basic " + Base64.getEncoder().encodeToString(str.getBytes());
    }
}
