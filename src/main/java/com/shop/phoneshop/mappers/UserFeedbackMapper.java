package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.UserFeedback;
import com.shop.phoneshop.dto.UserFeedbackDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface UserFeedbackMapper {
    static UserFeedbackDto fromUserFeedbackToDto(UserFeedback userFeedback) {
        UserFeedbackDto userFeedbackDto = new UserFeedbackDto();
        userFeedbackDto.setFeedback(userFeedback.getFeedback());
        userFeedbackDto.setComment(userFeedback.getComment());
        userFeedbackDto.setPicturesUrls(userFeedback.getPhotos().stream()
                .map(photo -> photo.getPictureUrl())
                .collect(Collectors.toList()));
        return userFeedbackDto;
    }

    static List<UserFeedbackDto> fromUserFeedbacksToDtos(List<UserFeedback> userFeedbacks) {
        return userFeedbacks.stream()
                .map(UserFeedbackMapper::fromUserFeedbackToDto)
                .collect(Collectors.toList());
    }
}
