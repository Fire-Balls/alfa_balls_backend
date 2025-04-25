package org.fireballs.alfaballs.extern.dto.newdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageDto {
    @NotBlank(message = "Message cannot be blank")
    String message;
}
