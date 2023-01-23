package com.shop.phoneshop.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackRequest {
    @NotNull
    @ApiModelProperty(notes = "Url фотографий в отзыве", required = true)
    private List<String> picturesUrls;

    @ApiModelProperty(notes = "Комментарий отзыва", required = true)
    private String comment;

    @NotNull
    @Min(1)
    @Max(5)
    @ApiModelProperty(notes = "Оценка в отзыве (от 1 до 5)", required = true)
    private Long feedback;

}
