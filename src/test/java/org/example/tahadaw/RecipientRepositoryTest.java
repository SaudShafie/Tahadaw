package org.example.tahadaw;

import org.assertj.core.api.Assertions;
import org.example.tahadaw.Model.Recipient;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.RecipientRepository;
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
public class RecipientRepositoryTest {

    @Autowired
    RecipientRepository recipientRepository;

    @Autowired
    UserRepository userRepository;

    User user;
    Recipient recipient1, recipient2;

    @BeforeEach
    public void setup() {
        user = newUser("bayan", "bayan@example.com");
        userRepository.save(user);

        recipient1 = newRecipient("Mom", "mother");
        recipient2 = newRecipient("Dad", "father");
        recipientRepository.save(recipient1);
    }

    @Test
    void findRecipientById() {
        Optional<Recipient> found = recipientRepository.findRecipientById(recipient1.getId());
        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getName()).isEqualTo("Mom");
    }

    @Test
    void findAllByUserId() {
        recipientRepository.save(recipient2);
        List<Recipient> result = recipientRepository.findAllByUser_Id(user.getId());
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result).allMatch(r -> r.getUser().getId().equals(user.getId()));
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

    private Recipient newRecipient(String name, String relationship) {
        Recipient r = new Recipient();
        r.setUser(user);
        r.setName(name);
        r.setRelationship(relationship);
        r.setCreatedAt(LocalDateTime.now());
        r.setUpdatedAt(LocalDateTime.now());
        return r;
    }
}
