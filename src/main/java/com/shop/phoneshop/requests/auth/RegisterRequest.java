package com.shop.phoneshop.requests.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @ApiModelProperty(notes = "Имя пользователя", required = true)
    @NotBlank
    private String firstName;

    @ApiModelProperty(notes = "Фамилия пользователя", required = true)
    @NotBlank
    private String lastName;

    @ApiModelProperty(notes = "Mail пользователя", required = true)
    @NotBlank
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String userEmail;

    @ApiModelProperty(notes = "Пароль пользователя", required = true)
    @NotBlank
    private String userPassword;
}
