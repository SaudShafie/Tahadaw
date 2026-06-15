package org.example.tahadaw.Service;

import org.example.tahadaw.Model.GiftCard;
import org.example.tahadaw.Model.GroupGift;
import org.example.tahadaw.Model.GroupGiftInvite;
import org.example.tahadaw.Model.Payment;
import org.example.tahadaw.Model.Reminder;
import org.example.tahadaw.Model.User;

public final class EmailHtmlTemplates {

    private static final String SYSTEM_NAME = "Tahadaw";

    private EmailHtmlTemplates() {
    }

    public static String buildGiftCardEmailHtml(GiftCard giftCard, User sender) {
        return """
                <html><body>
                <h2>%s — Your Gift Card</h2>
                <p>Hello %s,</p>
                <p>%s sent you a gift card through %s.</p>
                <p>Enjoy your gift!</p>
                </body></html>
                """.formatted(SYSTEM_NAME, giftCard.getRecipientName(), sender.getFullName(), SYSTEM_NAME);
    }

    public static String buildGiftCardEmailPlainText(GiftCard giftCard, User sender) {
        return SYSTEM_NAME + " — Your Gift Card\n\n"
                + "Hello " + giftCard.getRecipientName() + ",\n\n"
                + sender.getFullName() + " sent you a gift card through " + SYSTEM_NAME + ".\n\n"
                + "Enjoy your gift!";
    }

    public static String buildPaymentReceiptHtml(User user, Payment payment) {
        return """
                <html><body>
                <h2>%s — Premium Payment Receipt</h2>
                <p>Hello %s,</p>
                <p>Thank you for your premium purchase. Your account now unlocks Surprise Plan and Gift Card features.</p>
                <p>Amount: %d %s</p>
                </body></html>
                """.formatted(SYSTEM_NAME, user.getFullName(), payment.getAmountMinor(), payment.getCurrency());
    }

    public static String buildPaymentReceiptPlainText(User user, Payment payment) {
        return SYSTEM_NAME + " — Premium Payment Receipt\n\n"
                + "Hello " + user.getFullName() + ",\n\n"
                + "Thank you for your premium purchase.\n"
                + "Amount: " + payment.getAmountMinor() + " " + payment.getCurrency();
    }

    public static String buildReminderHtml(User user, Reminder reminder) {
        return """
                <html><body>
                <h2>%s — Gift Reminder</h2>
                <p>Hello %s,</p>
                <p>%s</p>
                </body></html>
                """.formatted(SYSTEM_NAME, user.getFullName(), reminder.getMessage());
    }

    public static String buildReminderPlainText(User user, Reminder reminder) {
        return SYSTEM_NAME + " — Gift Reminder\n\n"
                + "Hello " + user.getFullName() + ",\n\n"
                + reminder.getMessage();
    }

    public static String buildGroupGiftInviteHtml(GroupGiftInvite invite, GroupGift groupGift, String voteUrl) {
        return """
                <html><body>
                <h2>%s — Group Gift Vote</h2>
                <p>Hello %s,</p>
                <p>You are invited to vote on a group gift: <strong>%s</strong>.</p>
                <p><a href="%s">Vote here</a></p>
                </body></html>
                """.formatted(SYSTEM_NAME, invite.getInviteeName(), groupGift.getTitle(), voteUrl);
    }

    public static String buildGroupGiftInvitePlainText(GroupGiftInvite invite, GroupGift groupGift, String voteUrl) {
        return SYSTEM_NAME + " — Group Gift Vote\n\n"
                + "Hello " + invite.getInviteeName() + ",\n\n"
                + "You are invited to vote on a group gift: " + groupGift.getTitle() + ".\n\n"
                + "Vote here: " + voteUrl;
    }
}
