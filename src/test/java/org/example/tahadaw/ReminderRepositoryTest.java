package org.example.tahadaw;

import org.assertj.core.api.Assertions;
import org.example.tahadaw.Model.Recipient;
import org.example.tahadaw.Model.Reminder;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.RecipientRepository;
import org.example.tahadaw.Repository.ReminderRepository;
import org.example.tahadaw.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReminderRepositoryTest {

    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipientRepository recipientRepository;

    User user;
    Recipient recipient;
    Reminder reminder1, reminder2;

    @BeforeEach
    public void setup() {
        user = newUser("bayan", "bayan@example.com");
        userRepository.save(user);

        recipient = new Recipient();
        recipient.setUser(user);
        recipient.setName("Mom");
        recipient.setCreatedAt(LocalDateTime.now());
        recipient.setUpdatedAt(LocalDateTime.now());
        recipientRepository.save(recipient);

        reminder1 = newReminder(LocalDateTime.now().plusDays(2), "Later reminder");
        reminder2 = newReminder(LocalDateTime.now().plusDays(1), "Sooner reminder");
        reminderRepository.save(reminder1);
    }

    @Test
    void findReminderById() {
        Optional<Reminder> found = reminderRepository.findReminderById(reminder1.getId());
        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getMessage()).isEqualTo("Later reminder");
    }

    @Test
    void findAllByUserOrderedByReminderDateAsc() {
        reminderRepository.save(reminder2);
        List<Reminder> result = reminderRepository.findAllByUser_IdOrderByReminderDateAsc(user.getId());
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).getMessage()).isEqualTo("Sooner reminder");
    }

    private User newUser(String username, String email) {
        User u = new User();
        u.setUsername(username);
        u.setPassword("12345");
        u.setFullName("Test User");
        u.setEmail(email);
        u.setRole("USER");
        u.setIsPremium(false);
        u.setCreatedAt(LocalDateTime.now());
        u.setUpdatedAt(LocalDateTime.now());
        return u;
    }

    private Reminder newReminder(LocalDateTime reminderDate, String message) {
        Reminder r = new Reminder();
        r.setUser(user);
        r.setRecipient(recipient);
        r.setReminderDate(reminderDate);
        r.setStatus("PENDING");
        r.setMessage(message);
        r.setCreatedAt(LocalDateTime.now());
        return r;
    }
}
