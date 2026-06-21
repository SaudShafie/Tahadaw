package org.example.tahadaw;

import org.example.tahadaw.AI.AiService;
import org.example.tahadaw.DTO.IN.GiftMessageCreateDTOIn;
import org.example.tahadaw.DTO.IN.GiftMessageGenerateDTOIn;
import org.example.tahadaw.DTO.IN.GiftMessageUpdateDTOIn;
import org.example.tahadaw.DTO.OUT.GiftMessageDTOOut;
import org.example.tahadaw.Model.GiftMessage;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.GiftMessageRepository;
import org.example.tahadaw.Repository.UserRepository;
import org.example.tahadaw.Service.GiftMessageService;
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
public class GiftMessageServiceTest {

    @InjectMocks
    private GiftMessageService giftMessageService;

    @Mock
    private GiftMessageRepository giftMessageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AiService aiService;

    User user;
    GiftMessage message1;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("saud");

        message1 = new GiftMessage();
        message1.setId(1L);
        message1.setUser(user);
        message1.setTone("warm");
        message1.setLanguage("en");
        message1.setMessageText("First message");
    }

    @Test
    void generate() {
        Long userId = 1L;

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(aiService.ask(anyString())).thenReturn("{\"messageText\":\"Happy birthday, Sara!\"}");
        when(giftMessageRepository.save(any(GiftMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GiftMessageGenerateDTOIn request = new GiftMessageGenerateDTOIn(
                "Sara", "sister", "birthday", "watch", "warm", "en", null);

        GiftMessageDTOOut result = giftMessageService.generate(userId, request);

        Assertions.assertEquals("Happy birthday, Sara!", result.getMessageText());
        Assertions.assertEquals("warm", result.getTone());

        verify(giftMessageRepository, times(1)).save(any(GiftMessage.class));
    }

    @Test
    void createManual() {
        Long userId = 1L;

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(giftMessageRepository.save(any(GiftMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GiftMessageCreateDTOIn request = new GiftMessageCreateDTOIn("My own words");

        GiftMessageDTOOut result = giftMessageService.createManual(userId, request);

        Assertions.assertEquals("My own words", result.getMessageText());
        Assertions.assertEquals("custom", result.getTone());

        verify(giftMessageRepository, times(1)).save(any(GiftMessage.class));
    }

    @Test
    void update() {
        Long userId = 1L;
        Long messageId = 1L;

        when(giftMessageRepository.findGiftMessageById(messageId)).thenReturn(Optional.of(message1));
        when(giftMessageRepository.save(any(GiftMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GiftMessageUpdateDTOIn request = new GiftMessageUpdateDTOIn("Updated text", "formal", "ar");

        GiftMessageDTOOut result = giftMessageService.update(userId, messageId, request);

        Assertions.assertEquals("Updated text", result.getMessageText());
        Assertions.assertEquals("formal", result.getTone());

        verify(giftMessageRepository, times(1)).save(message1);
    }
}
