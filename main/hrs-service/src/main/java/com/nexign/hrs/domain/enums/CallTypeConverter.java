package com.nexign.hrs.domain.enums;

import jakarta.persistence.AttributeConverter;

public class CallTypeConverter implements AttributeConverter<CallType, String> {

    @Override
    public String convertToDatabaseColumn(CallType callType) {
        if (callType == null) {
            return null;
        }
        return callType.getCode();
    }

    @Override
    public CallType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return CallType.getByCode(code);
    }
    
}
