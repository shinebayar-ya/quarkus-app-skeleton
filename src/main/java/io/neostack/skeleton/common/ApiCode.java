package io.neostack.skeleton.common;

public enum ApiCode {

  // General
  SUCCESS("success"),
  INTERNAL_ERROR("internal.error"),

  // User related
  USER_NOT_FOUND("user.not_found"),
  USER_ALREADY_EXISTS("user.already_exists"),
  AUTH_INVALID_TOKEN("auth.invalid_token"),
  AUTH_UNAUTHORIZED("auth.unauthorized"),

  // Validation
  BAD_REQUEST("validation.bad_request"),
  VALIDATION_FAILED("validation.failed");

  private final String messageKey;

  ApiCode(String messageKey) {
    this.messageKey = messageKey;
  }

  public String getMessage() {
    return messageKey;
  }
}
