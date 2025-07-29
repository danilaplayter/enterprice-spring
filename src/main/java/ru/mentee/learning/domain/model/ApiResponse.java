package ru.mentee.learning.domain.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiResponse<T> {
  private boolean success;
  private T data;
  private Map<String, Object> metadata;
  private String message;

  public static <T> ApiResponse<T> success(T data) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(true);
    response.setData(data);
    return response;
  }

  public static <T> ApiResponse<T> error(String message) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setMessage(message);
    return response;
  }

  public ApiResponse<T> withMetadata(String key, Object value) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, value);
    return this;
  }
}