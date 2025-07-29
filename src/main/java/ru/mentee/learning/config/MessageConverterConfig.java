/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.config;

import java.util.List;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.mentee.learning.converter.CsvHttpMessageConverter;

public class MessageConverterConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new CsvHttpMessageConverter<>());
    }
}
