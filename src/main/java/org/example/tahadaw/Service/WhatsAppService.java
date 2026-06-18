package org.example.tahadaw.Service;

import jakarta.annotation.PostConstruct;
import org.example.tahadaw.Model.GroupGift;
import org.example.tahadaw.Model.GroupGiftInvite;
import org.example.tahadaw.Model.Reminder;
import org.example.tahadaw.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WhatsAppService {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppService.class);
    private static final JsonMapper JSON = JsonMapper.builder().build();

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${twilio.api-url:https://api.twilio.com/2010-04-01}")
    private String apiUrl;

    @Value("${twilio.account-sid:}")
    private String accountSid;

    @Value("${twilio.auth-token:}")
    private String authToken;

    @Value("${twilio.whatsapp-from:}")
    private String whatsappFrom;

    @PostConstruct
    void validateConfig() {
        if (!isConfigured()) {
            log.error("Twilio WhatsApp is not configured (twilio.account-sid / twilio.auth-token / twilio.whatsapp-from). Messages will not be sent.");
            return;
        }

        log.info("Twilio WhatsApp configured for account {} (from: {})", accountSidSuffix(), whatsappFrom);
    }


    public boolean sendWhatsApp(String toPhone, String message) {

        if (!isConfigured()) {
            log.error("Skipping WhatsApp send because Twilio is not configured");
            return false;
        }

        String to = toWhatsAppFormat(toPhone);
        if (to == null) {
            log.warn("Skipping WhatsApp send because phone number is missing or invalid (raw value: {})", toPhone);
            return false;
        }

        try {
            Map<String, String> form = new LinkedHashMap<>();
            form.put("From", whatsappFrom.trim());
            form.put("To", to);
            form.put("Body", message);

            String encodedBody = form.entrySet().stream()
                    .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                    .collect(Collectors.joining("&"));

            URI requestUri = URI.create(apiUrl + "/Accounts/" + accountSid.trim() + "/Messages.json");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(requestUri)
                    .header("Authorization", basicAuthHeader())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(encodedBody))
                    .build();

            log.info("Sending WhatsApp via Twilio to {}", to);

            HttpResponse<String> httpResponse = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            int status = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            if (status < 200 || status >= 300) {
                log.error("Failed to send WhatsApp to {} (HTTP {}): {}", to, status, responseBody);
                return false;
            }

            JsonNode response = JSON.readTree(responseBody);
            String messageSid = response.path("sid").asString(null);
            String messageStatus = response.path("status").asString(null);

            log.info("Twilio WhatsApp queued to {} (sid: {}, status: {})", to, messageSid, messageStatus);
            return messageSid != null && !messageSid.isBlank();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.error("WhatsApp send interrupted for {}: {}", to, ex.getMessage(), ex);
            return false;
        } catch (Exception ex) {
            log.error("Failed to send WhatsApp to {}: {}", to, ex.getMessage(), ex);
            return false;
        }
    }


    public boolean sendReminder(User user, Reminder reminder) {
        return sendWhatsApp(user.getPhoneNumber(), WhatsAppTemplates.buildReminder(user, reminder));
    }

    public boolean sendGroupGiftVoteReminder(String phoneNumber, GroupGiftInvite invite, GroupGift groupGift, String voteUrl) {
        return sendWhatsApp(
                phoneNumber,
                WhatsAppTemplates.buildGroupGiftVoteReminder(invite, groupGift, voteUrl)
        );
    }

    public boolean sendOccasionReminder(User user, String recipientName, String occasionType, String occasionDate) {
        return sendWhatsApp(
                user.getPhoneNumber(),
                WhatsAppTemplates.buildOccasionReminder(user, recipientName, occasionType, occasionDate)
        );
    }

    private boolean isConfigured() {
        return accountSid != null && !accountSid.isBlank()
                && authToken != null && !authToken.isBlank()
                && whatsappFrom != null && !whatsappFrom.isBlank();
    }

    private String basicAuthHeader() {
        String credentials = accountSid.trim() + ":" + authToken.trim();
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    
    private String toWhatsAppFormat(String phone) {
        if (phone == null || phone.isBlank()) {
            return null;
        }

        String trimmed = phone.trim();

        if (trimmed.startsWith("whatsapp:")) {
            return trimmed;
        }

        String digits = trimmed.replaceAll("\\D", "");

        if (digits.isBlank()) {
            return null;
        }

        if (digits.startsWith("05") && digits.length() == 10) {
            digits = "966" + digits.substring(1);
        }

        return "whatsapp:+" + digits;
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String accountSidSuffix() {
        if (accountSid == null || accountSid.length() < 4) {
            return "****";
        }
        return "..." + accountSid.substring(accountSid.length() - 4);
    }
}
