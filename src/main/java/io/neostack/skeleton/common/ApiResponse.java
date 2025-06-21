package io.neostack.skeleton.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private boolean success;
  private String code;
  private String message;
  private T data;
  private Meta meta;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T> builder()
        .success(true)
        .code(ApiCode.SUCCESS.name())
        .data(data)
        .build();
  }

  public static <T> ApiResponse<T> error(String code, String message) {
    return ApiResponse.<T> builder()
        .success(false)
        .code(code)
        .message(message)
        .build();
  }
}