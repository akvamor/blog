package ua.org.project.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Created by Dmitry Petrov on 27.06.14.
 */
@Service("customJodaMapper")
public class CustomJodaMapper extends ObjectMapper {

    @Autowired
    public CustomJodaMapper(JodaDateTimeSerializer jsonJodaDateTimeSerializer, JodaDateTimeDeserializer jsonJodaDateTimeDeserializer){

        SimpleModule module = new SimpleModule();
        module.addSerializer(DateTime.class, jsonJodaDateTimeSerializer);
        module.addDeserializer(DateTime.class, jsonJodaDateTimeDeserializer);
        this.registerModule(module);
    }
}
