package com.shop.phoneshop.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserFeedbackDto {
    @ApiModelProperty(notes = "ID отзыва", required = true)
    private Long userFeedbackId;

    @ApiModelProperty(notes = "Имя пользователя", required = true)
    private String firstName;

    @ApiModelProperty(notes = "Фамилия пользователя", required = true)
    private String lastName;

    @ApiModelProperty(notes = "Ссылки на картинки для отзыва", required = true)
    private List<String> picturesUrls;

    @ApiModelProperty(notes = "Комментарий отзыва (null, если комментария нет)")
    private String comment;

    @ApiModelProperty(notes = "Оценка товара (от 1 до 5)", required = true)
    private Long feedback;
}
