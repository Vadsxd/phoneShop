package com.shop.phoneshop.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackRequest {
    @ApiModelProperty(notes = "Url фотографий в отзыве", required = true)
    private List<String> picturesUrls;
    @ApiModelProperty(notes = "Комментарий отзыва", required = true)
    @NotBlank
    private String comment;
    @ApiModelProperty(notes = "Оценка в отзыве (от 1 до 5)", required = true)
    @Min(1)
    @Max(5)
    private Long feedback;

//    "picturesUrls": [
//            "kmkcklc",
//            "pepep"
//            ],
//     "comment": "good phone",
//     "feedback": 5
}
