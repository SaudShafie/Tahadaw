package org.example.tahadaw;

import org.assertj.core.api.Assertions;
import org.example.tahadaw.Model.GiftCard;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.GiftCardRepository;
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
public class GiftCardRepositoryTest {

    @Autowired
    GiftCardRepository giftCardRepository;

    @Autowired
    UserRepository userRepository;

    User user;
    GiftCard card1, card2;

    @BeforeEach
    public void setup() {
        user = newUser("saud", "saud@example.com");
        userRepository.save(user);

        card1 = newCard("Mom", "Saud", LocalDateTime.now().minusDays(1));
        card2 = newCard("Dad", "Saud", LocalDateTime.now());
        giftCardRepository.save(card1);
    }

    @Test
    void findGiftCardById() {
        Optional<GiftCard> found = giftCardRepository.findGiftCardById(card1.getId());
        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getRecipientName()).isEqualTo("Mom");
    }

    @Test
    void findByUserOrderedByCreatedAtDesc() {
        giftCardRepository.save(card2);
        List<GiftCard> result = giftCardRepository.findByUser_IdOrderByCreatedAtDesc(user.getId());
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).getRecipientName()).isEqualTo("Dad");
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

    private GiftCard newCard(String recipientName, String senderName, LocalDateTime createdAt) {
        GiftCard c = new GiftCard();
        c.setUser(user);
        c.setRecipientName(recipientName);
        c.setSenderName(senderName);
        c.setStatus("DRAFT");
        c.setCreatedAt(createdAt);
        return c;
    }
}
