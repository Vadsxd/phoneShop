package com.shop.phoneshop.requests;

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

    private List<String> picturesUrls;
    @NotBlank
    private String comment;
    @Min(1)
    @Max(5)
    private Long feedback;

//    "picturesUrls": [
//            "kmkcklc",
//            "pepep"
//            ],
//            "comment": "good phone",
//            "feedback": 5
}
