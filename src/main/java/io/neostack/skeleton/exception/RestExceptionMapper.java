package io.neostack.skeleton.exception;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import io.neostack.skeleton.common.ApiCode;
import io.neostack.skeleton.common.ApiResponse;
import io.neostack.skeleton.utils.Translator;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable> {

  @Inject
  Translator translator;

  private static final String DEFAULT_ERROR_CODE = ApiCode.INTERNAL_ERROR.name();
  private static final String DEFAULT_ERROR_MESSAGE = ApiCode.INTERNAL_ERROR.getMessage();

  @Override
  public Response toResponse(Throwable exception) {
    int statusCode;
    String errorCode;
    String message;

    if (exception instanceof WebApplicationException webEx) {
      statusCode = webEx.getResponse().getStatus();
      message = webEx.getMessage();
      errorCode = "HTTP_" + statusCode;
    } else {
      statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
      message = DEFAULT_ERROR_MESSAGE;
      errorCode = DEFAULT_ERROR_CODE;
    }

    message = translator.translate(message);

    ApiResponse<Object> errorResponse = ApiResponse.error(errorCode, message);
    return Response.status(statusCode).entity(errorResponse).build();
  }
}
