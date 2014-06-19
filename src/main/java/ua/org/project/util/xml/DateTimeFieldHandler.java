package ua.org.project.util.xml;

import org.exolab.castor.mapping.GeneralizedFieldHandler;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
public class DateTimeFieldHandler extends GeneralizedFieldHandler {

    @Autowired
    private ApplicationContext applicationContext;

    String dateFormatPattern;

    public void setConfiguration(Properties config) {
        Locale locale = applicationContext.getBean("locale", Locale.class);
        MessageSource messageSource = applicationContext.getBean("messageSource", MessageSource.class);
        dateFormatPattern = messageSource.getMessage("date_format_pattern_comments", new Object[]{}, locale);
    }

    @Override
    public Object convertUponGet(Object value) {
        DateTime dateTime = (DateTime) value;
        return format(dateTime);
    }

    @Override
    public Object convertUponSet(Object value) {
        String dateTimeString = (String) value;
        return parse(dateTimeString);
    }

    @Override
    public Class getFieldType() {
        return DateTime.class;
    }

    protected String format(final  DateTime dateTime){
        String dateTimeString = "";
        if (dateTime != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormatPattern);
            dateTimeString = dateTimeFormatter.print(dateTime);
        }
        return dateTimeString;
    }

    protected DateTime parse(final String dateTimeString){
        DateTime dateTime = new DateTime();
        if (dateTimeString != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormatPattern);
            dateTime = dateTimeFormatter.parseDateTime(dateTimeString);
        }
        return dateTime;
    }
}
