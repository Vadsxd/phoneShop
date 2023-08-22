package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.Property;
import com.shop.phoneshop.domain.UserFeedback;
import com.shop.phoneshop.dto.PropertyDto;
import com.shop.phoneshop.dto.UserFeedbackDto;

import java.util.List;
import java.util.stream.Collectors;

public interface PropertyMapper {
    static PropertyDto fromPropertyToDto(Property property) {
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setId(property.getId());
        propertyDto.setName(property.getName());
        propertyDto.setValue(property.getValue());

        return propertyDto;
    }

    static List<PropertyDto> fromPropertiesToDtos(List<Property> properties) {
        return properties.stream()
                .map(PropertyMapper::fromPropertyToDto)
                .collect(Collectors.toList());
    }
}
