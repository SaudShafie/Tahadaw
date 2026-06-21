package org.example.tahadaw;

import org.assertj.core.api.Assertions;
import org.example.tahadaw.Model.GiftMessage;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.GiftMessageRepository;
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
public class GiftMessageRepositoryTest {

    @Autowired
    GiftMessageRepository giftMessageRepository;

    @Autowired
    UserRepository userRepository;

    User user;
    GiftMessage message1, message2;

    @BeforeEach
    public void setup() {
        user = newUser("saud", "saud@example.com");
        userRepository.save(user);

        message1 = newMessage("warm", "en", "First message", LocalDateTime.now().minusDays(1));
        message2 = newMessage("formal", "ar", "Second message", LocalDateTime.now());
        giftMessageRepository.save(message1);
    }

    @Test
    void findGiftMessageById() {
        Optional<GiftMessage> found = giftMessageRepository.findGiftMessageById(message1.getId());
        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getMessageText()).isEqualTo("First message");
    }

    @Test
    void findByUserOrderedByCreatedAtDesc() {
        giftMessageRepository.save(message2);
        List<GiftMessage> result = giftMessageRepository.findByUser_IdOrderByCreatedAtDesc(user.getId());
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).getMessageText()).isEqualTo("Second message");
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

    private GiftMessage newMessage(String tone, String language, String text, LocalDateTime createdAt) {
        GiftMessage message = new GiftMessage();
        message.setUser(user);
        message.setTone(tone);
        message.setLanguage(language);
        message.setMessageText(text);
        message.setCreatedAt(createdAt);
        return message;
    }
}
