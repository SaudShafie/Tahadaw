package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class UserDTOOut {

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String role;

    private Boolean isPremium;
}
