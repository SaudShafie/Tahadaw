package org.example.tahadaw;

import org.example.tahadaw.Model.Recipient;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.RecipientRepository;
import org.example.tahadaw.Repository.UserRepository;
import org.example.tahadaw.Service.RecipientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipientServiceTest {

    @InjectMocks
    private RecipientService recipientService;

    @Mock
    private RecipientRepository recipientRepository;

    @Mock
    private UserRepository userRepository;

    User user;
    Recipient recipient1;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("bayan");

        recipient1 = new Recipient();
        recipient1.setId(1L);
        recipient1.setUser(user);
        recipient1.setName("Mom");
        recipient1.setRelationship("mother");
    }

    @Test
    void addRecipient() {
        Long userId = 1L;
        Recipient newRecipient = new Recipient();
        newRecipient.setName("Sister");

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));

        recipientService.addRecipient(userId, newRecipient);

        Assertions.assertEquals(user, newRecipient.getUser());
        verify(recipientRepository, times(1)).save(newRecipient);
    }

    @Test
    void updateRecipient() {
        Long userId = 1L;
        Long recipientId = 1L;

        Recipient changes = new Recipient();
        changes.setName("Updated Name");
        changes.setRelationship("aunt");

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(recipientRepository.findRecipientByIdAndUser_Id(recipientId, userId)).thenReturn(Optional.of(recipient1));

        recipientService.updateRecipient(userId, recipientId, changes);

        Assertions.assertEquals("Updated Name", recipient1.getName());
        Assertions.assertEquals("aunt", recipient1.getRelationship());
        verify(recipientRepository, times(1)).save(recipient1);
    }
}
