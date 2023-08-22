package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.Photo;
import com.shop.phoneshop.domain.UserFeedback;
import com.shop.phoneshop.dto.UserFeedbackDto;

import java.util.List;
import java.util.stream.Collectors;

public interface UserFeedbackMapper {
    static UserFeedbackDto fromUserFeedbackToDto(UserFeedback userFeedback) {
        UserFeedbackDto userFeedbackDto = new UserFeedbackDto();
        userFeedbackDto.setUserFeedbackId(userFeedback.getId());
        userFeedbackDto.setFirstName(userFeedback.getUser().getFirstName());
        userFeedbackDto.setLastName(userFeedback.getUser().getLastName());
        userFeedbackDto.setFeedback(userFeedback.getFeedback());
        userFeedbackDto.setComment(userFeedback.getComment());
        userFeedbackDto.setPicturesUrls(userFeedback.getPhotos().stream()
                .map(Photo::getPictureUrl)
                .collect(Collectors.toList()));
        return userFeedbackDto;
    }

    static List<UserFeedbackDto> fromUserFeedbacksToDtos(List<UserFeedback> userFeedbacks) {
        return userFeedbacks.stream()
                .map(UserFeedbackMapper::fromUserFeedbackToDto)
                .collect(Collectors.toList());
    }
}
