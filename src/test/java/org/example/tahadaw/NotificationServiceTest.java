package org.example.tahadaw;

import org.example.tahadaw.DTO.IN.NotificationCreateDTOIn;
import org.example.tahadaw.DTO.IN.NotificationUpdateDTOIn;
import org.example.tahadaw.DTO.OUT.NotificationDTOOut;
import org.example.tahadaw.Model.Notification;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.NotificationRepository;
import org.example.tahadaw.Repository.UserRepository;
import org.example.tahadaw.Service.NotificationService;
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
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    User user;
    Notification notification1;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("saud");

        notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUser(user);
        notification1.setTitle("Welcome");
        notification1.setMessage("Thanks for joining");
        notification1.setType("SYSTEM");
        notification1.setStatus("UNREAD");
    }

    @Test
    void create() {
        Long userId = 1L;

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationCreateDTOIn request = new NotificationCreateDTOIn("Title", "Body", "SYSTEM", null);

        NotificationDTOOut result = notificationService.create(userId, request);

        Assertions.assertEquals("Title", result.getTitle());
        Assertions.assertEquals("UNREAD", result.getStatus());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void update() {
        Long userId = 1L;
        Long notificationId = 1L;

        when(notificationRepository.findNotificationById(notificationId)).thenReturn(Optional.of(notification1));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationUpdateDTOIn request = new NotificationUpdateDTOIn(null, null, null, "READ");

        NotificationDTOOut result = notificationService.update(userId, notificationId, request);

        Assertions.assertEquals("READ", result.getStatus());
        verify(notificationRepository, times(1)).save(notification1);
    }
}
