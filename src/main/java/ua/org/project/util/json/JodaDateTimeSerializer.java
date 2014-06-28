package ua.org.project.util.json;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Dmitry Petrov on 26.06.14.
 */
@Service("jodaDateTimeSerializer")
public class JodaDateTimeSerializer extends JsonSerializer<DateTime> {

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public MessageSource getMessageSource() {
        return messageSource;
    }

    @Override
    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        String dateFormatPattern = messageSource.getMessage(
                "date_format_pattern_comments",
                new Object[]{},
                LocaleContextHolder.getLocale());
        String dateTimeString = "";

        if (value != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormatPattern);
            dateTimeString = dateTimeFormatter.print(value);
        }

        jgen.writeString(dateTimeString);
    }




}
