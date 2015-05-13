package ua.org.project.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitry Petrov on 27.06.14.
 */
@Service("customJodaMapper")
public class CustomJodaMapper extends ObjectMapper {

    private static final long serialVersionUID = 4300885735883356562L;

	@Autowired
    public CustomJodaMapper(
            JodaDateTimeSerializer jsonJodaDateTimeSerializer,
            JodaDateTimeDeserializer jsonJodaDateTimeDeserializer){

        SimpleModule module = new SimpleModule();
        module.addSerializer(DateTime.class, jsonJodaDateTimeSerializer);
        module.addDeserializer(DateTime.class, jsonJodaDateTimeDeserializer);
        this.registerModule(module);
    }
}
