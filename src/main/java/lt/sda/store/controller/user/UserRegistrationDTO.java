package lt.sda.store.controller.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationDTO {
    private String name;
    private String surname;
    @NotBlank(message = "email can't be empty")
    @Email
    private String email;
}
