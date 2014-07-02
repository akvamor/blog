package ua.org.project.util.xml;

import org.exolab.castor.mapping.GeneralizedFieldHandler;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.EntryService;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
public class EntryHandler extends GeneralizedFieldHandler{

    @Autowired
    static EntryService entryService;

    @Override
    public Object convertUponGet(Object value) {
        Entry entry = (Entry) value;
        return entry.getId();
    }

    @Override
    public Object convertUponSet(Object value) {
        Long id = (Long) value;
        Entry entry = entryService.findById(id);
        return entry;
    }

    @Override
    public Class getFieldType() {
        return Entry.class;
    }
}
