package com.grekoff.market.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос")
public class StringResponse {
    @Schema(description = "Запрашиваемое значение", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringResponse() {
    }

    public StringResponse(String value) {
        this.value = value;
    }
}
