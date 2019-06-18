package com.gmail.kirill.ked.telegram.service.converter;

public interface Converter<ObjectDTO, Object> {
    ObjectDTO toDTO(Object object);

    Object toEntity(ObjectDTO ObjectDTO);
}