package org.example.tahadaw.Service;

import org.example.tahadaw.Api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Low-level Moyasar sandbox client.
 * Uses the same mock/test flow as Project_3: real Moyasar test API with test card numbers.
 */
@Service
public class MoyasarService {

    private static final JsonMapper JSON = JsonMapper.builder().build();

    @Value("${moyasar.api.key:}")
    private String apiKey;

    @Value("${moyasar.api-url:https://api.moyasar.com/v1/payments}")
    private String apiUrl;

    public JsonNode createCardPayment(String name,
                                      String number,
                                      String cvc,
                                      String month,
                                      String year,
                                      long amountMinor,
                                      String currency,
                                      String description,
                                      String callbackUrl) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ApiException(
                    "Moyasar is not configured. Add moyasar.api.key to application-local.properties (use sk_test_ key for sandbox).");
        }

        String resolvedCallback = callbackUrl != null && !callbackUrl.isBlank()
                ? callbackUrl
                : "http://localhost:8080/api/v1/payments/webhook/moyasar";

        String requestBody = "source[type]=card"
                + "&source[name]=" + encode(name)
                + "&source[number]=" + encode(number)
                + "&source[cvc]=" + encode(cvc)
                + "&source[month]=" + encode(month)
                + "&source[year]=" + encode(year)
                + "&amount=" + amountMinor
                + "&currency=" + encode(currency)
                + "&description=" + encode(description)
                + "&callback_url=" + encode(resolvedCallback);

        try {
            RestClient client = RestClient.builder()
                    .baseUrl(apiUrl)
                    .defaultHeaders(headers -> {
                        headers.setBasicAuth(apiKey, "");
                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    })
                    .build();

            String responseBody = client.post()
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            return JSON.readTree(responseBody);
        } catch (RestClientResponseException ex) {
            throw new ApiException("Moyasar API error: " + ex.getStatusCode() + " — " + ex.getResponseBodyAsString());
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException("Moyasar payment request failed: " + ex.getMessage());
        }
    }

    public JsonNode fetchPayment(String paymentId) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ApiException("Moyasar is not configured.");
        }

        try {
            RestClient client = RestClient.builder()
                    .baseUrl(apiUrl)
                    .defaultHeaders(headers -> headers.setBasicAuth(apiKey, ""))
                    .build();

            String responseBody = client.get()
                    .uri("/{paymentId}", paymentId)
                    .retrieve()
                    .body(String.class);

            return JSON.readTree(responseBody);
        } catch (RestClientResponseException ex) {
            throw new ApiException("Moyasar API error: " + ex.getStatusCode() + " — " + ex.getResponseBodyAsString());
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException("Moyasar status request failed: " + ex.getMessage());
        }
    }

    private static String encode(String value) {
        return value == null ? "" : URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static String readStatus(JsonNode response) {
        if (response == null || response.isNull()) {
            return null;
        }
        return response.path("status").asString(null);
    }

    public static String readPaymentId(JsonNode response) {
        if (response == null || response.isNull()) {
            return null;
        }
        return response.path("id").asString(null);
    }

    /**
     * 3-D Secure approval URL Moyasar returns for sandbox card payments (under source.transaction_url).
     * The payer opens it to approve before the payment becomes "paid".
     */
    public static String readTransactionUrl(JsonNode response) {
        if (response == null || response.isNull()) {
            return null;
        }
        return response.path("source").path("transaction_url").asString(null);
    }
}
