package org.example.tahadaw.Service;

import org.example.tahadaw.Model.GroupGift;
import org.example.tahadaw.Model.GroupGiftInvite;
import org.example.tahadaw.Model.Reminder;
import org.example.tahadaw.Model.User;

public final class WhatsAppTemplates {

    private static final String SYSTEM_NAME = "Tahadaw";

    private WhatsAppTemplates() {
    }

    public static String buildReminder(User user, Reminder reminder) {
        return "Hello " + user.getFullName() + "\n\n"
                + reminder.getMessage() + "\n\n"
                + SYSTEM_NAME + " team";
    }

    public static String buildGroupGiftVoteReminder(GroupGiftInvite invite, GroupGift groupGift, String voteUrl) {
        return "Hello " + invite.getInviteeName() + "\n\n"
                + "Reminder: please vote on the group gift \"" + groupGift.getTitle() + "\".\n\n"
                + voteUrl + "\n\n"
                + SYSTEM_NAME + " team";
    }

    public static String buildOccasionReminder(User user, String recipientName, String occasionType, String occasionDate) {
        return "Hello " + user.getFullName() + "\n\n"
                + "Reminder: " + recipientName + "'s " + occasionType + " is coming up on " + occasionDate + ".\n\n"
                + "Plan a meaningful gift with " + SYSTEM_NAME + ".\n\n"
                + SYSTEM_NAME + " team";
    }
}
