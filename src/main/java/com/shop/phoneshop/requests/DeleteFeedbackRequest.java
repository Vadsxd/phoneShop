package com.shop.phoneshop.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class DeleteFeedbackRequest {
    @ApiModelProperty(notes = "Id отзыва на сайте", required = true)
    @NotNull
    private Long feedbackId;
}
