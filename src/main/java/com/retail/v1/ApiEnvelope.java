package com.retail.v1;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiEnvelope<T> {
    private boolean success;
    private String message;
    private T data;
    private Map<String, Object> meta;
    private ErrorBody error;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorBody {
        private String code;
        private String message;
        private List<FieldErrorDetail> details;
    }

    @Data
    @AllArgsConstructor
    public static class FieldErrorDetail {
        private String field;
        private String message;
    }

    public static <T> ApiEnvelope<T> ok(String message, T data) {
        return ApiEnvelope.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiEnvelope<T> okList(String message, T data, int total, int page, int perPage) {
        return ApiEnvelope.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .meta(Map.of("total", total, "page", page, "perPage", perPage))
                .build();
    }

    public static <T> ApiEnvelope<T> fail(String code, String message, List<FieldErrorDetail> details) {
        return ApiEnvelope.<T>builder()
                .success(false)
                .error(ErrorBody.builder()
                        .code(code)
                        .message(message)
                        .details(details == null || details.isEmpty() ? null : details)
                        .build())
                .build();
    }
}