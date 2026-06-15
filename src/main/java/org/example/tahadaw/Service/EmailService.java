package org.example.tahadaw.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiException;
import org.example.tahadaw.Model.GiftCard;
import org.example.tahadaw.Model.GroupGift;
import org.example.tahadaw.Model.GroupGiftInvite;
import org.example.tahadaw.Model.Payment;
import org.example.tahadaw.Model.Reminder;
import org.example.tahadaw.Model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String SYSTEM_NAME = "Tahadaw";

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailFrom;

    public void sendGiftCardEmail(GiftCard giftCard, User sender, String toEmail) {
        String subject = SYSTEM_NAME + " — Your Gift Card";

        if (giftCard.getGiftCardImage() != null && giftCard.getGiftCardImage().length > 0) {
            sendHtmlEmailWithImageAttachment(
                    toEmail,
                    subject,
                    EmailHtmlTemplates.buildGiftCardEmailHtml(giftCard, sender),
                    EmailHtmlTemplates.buildGiftCardEmailPlainText(giftCard, sender),
                    "gift-card.png",
                    giftCard.getGiftCardImage(),
                    "image/png"
            );
        } else {
            sendHtmlEmail(
                    toEmail,
                    subject,
                    EmailHtmlTemplates.buildGiftCardEmailHtml(giftCard, sender),
                    EmailHtmlTemplates.buildGiftCardEmailPlainText(giftCard, sender)
            );
        }
    }

    public void sendPaymentReceiptEmail(User user, Payment payment) {
        String subject = SYSTEM_NAME + " — Premium Payment Receipt";

        sendHtmlEmail(
                user.getEmail(),
                subject,
                EmailHtmlTemplates.buildPaymentReceiptHtml(user, payment),
                EmailHtmlTemplates.buildPaymentReceiptPlainText(user, payment)
        );
    }

    public void sendReminderEmail(User user, Reminder reminder) {
        String subject = SYSTEM_NAME + " — Gift Reminder";

        sendHtmlEmail(
                user.getEmail(),
                subject,
                EmailHtmlTemplates.buildReminderHtml(user, reminder),
                EmailHtmlTemplates.buildReminderPlainText(user, reminder)
        );
    }

    public void sendGroupGiftInviteEmail(GroupGiftInvite invite, GroupGift groupGift, String voteUrl) {
        String subject = SYSTEM_NAME + " — Group Gift Vote";

        sendHtmlEmail(
                invite.getInviteeEmail(),
                subject,
                EmailHtmlTemplates.buildGroupGiftInviteHtml(invite, groupGift, voteUrl),
                EmailHtmlTemplates.buildGroupGiftInvitePlainText(invite, groupGift, voteUrl)
        );
    }

    private void sendHtmlEmail(String to, String subject, String htmlBody, String plainBody) {
        if (to == null || to.isBlank()) {
            return;
        }

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(plainBody, htmlBody);

            if (mailFrom != null && !mailFrom.isBlank()) {
                helper.setFrom(mailFrom);
            }

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new ApiException("Failed to send email: " + e.getMessage());
        }
    }

    private void sendHtmlEmailWithImageAttachment(String to,
                                                  String subject,
                                                  String htmlBody,
                                                  String plainBody,
                                                  String attachmentName,
                                                  byte[] imageBytes,
                                                  String contentType) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(plainBody, htmlBody);

            if (mailFrom != null && !mailFrom.isBlank()) {
                helper.setFrom(mailFrom);
            }

            helper.addAttachment(
                    attachmentName,
                    new ByteArrayResource(imageBytes),
                    contentType
            );

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new ApiException("Failed to send gift card email: " + e.getMessage());
        }
    }
}
