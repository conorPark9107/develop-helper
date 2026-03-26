package com.albionhelper.helper.domain.battleapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EquipmentTypeConverter implements AttributeConverter<String, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute; // 이미 String 타입이므로 그대로 저장
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData; // DB에서 불러올 때도 그대로 사용
    }
}
