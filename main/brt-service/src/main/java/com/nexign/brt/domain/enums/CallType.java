package com.nexign.brt.domain.enums;

public enum CallType {
    INCOMING_CALL("01"),
    OUTCOMING_CALL("02");

    private String code;

    CallType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CallType getByCode(String code) {
        for (CallType type : CallType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown CallType code: " + code);
    }
}
