/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.api.advice;

import java.util.Collection;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ru.mentee.learning.domain.model.ApiResponse;

@ControllerAdvice
public class ResponseEnvelopeAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(ApiResponse.class)
                && !returnType.getParameterType().equals(ResponseEntity.class)
                && !returnType.getParameterType().equals(Void.class);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        if (body == null) {
            return ApiResponse.success(null);
        }

        if (body instanceof Collection) {
            Collection<?> collection = (Collection<?>) body;
            return ApiResponse.success(body).withMetadata("count", collection.size());
        }

        return ApiResponse.success(body);
    }
}
