package com.shop.phoneshop.requests.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequest {
    @ApiModelProperty(notes = "Mail пользователя", required = true)
    @NotBlank
    private String userEmail;

    @ApiModelProperty(notes = "Пароль пользователя", required = true)
    @NotBlank
    private String userPassword;
}
