package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequiredQuestionDTOOut {

    private Long id;
    private String questionText;
    private String questionType;
    private Integer displayOrder;
}
