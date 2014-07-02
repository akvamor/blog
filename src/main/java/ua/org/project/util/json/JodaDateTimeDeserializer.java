package ua.org.project.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Locale;


/**
 * Created by Dmitry Petrov on 26.06.14.
 */
@Service("jodaDateTimeDeserializer")
public class JodaDateTimeDeserializer extends JsonDeserializer<DateTime> {

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public MessageSource getMessageSource() {
        return messageSource;
    }

    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateFormatPattern = messageSource.getMessage(
                "date_format_pattern_comments",
                new Object[]{},
                LocaleContextHolder.getLocale());
        DateTime dateTime = new DateTime();

        if (jp.getText() != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormatPattern);
            dateTime = dateTimeFormatter.parseDateTime(jp.getText());
        }
        return dateTime;
    }


}