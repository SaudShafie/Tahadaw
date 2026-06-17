package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationDTOOut {

    private Long id;
    private Long userId;
    private String title;
    private String message;
    private String type;
    private String status;
    private LocalDateTime createdAt;
}
