/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.converter;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class CsvHttpMessageConverter<T> extends AbstractHttpMessageConverter<T> {

    public CsvHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Reading CSV is not supported");
    }

    @Override
    protected void writeInternal(T t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(t.getClass()).withHeader();

        try (Writer writer = new OutputStreamWriter(outputMessage.getBody())) {
            csvMapper.writer(schema).writeValue(writer, t);
        }
    }
}
