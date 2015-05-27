package ua.org.project.web.controller;


import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.EntryService;
import ua.org.project.web.controller.front.EntryController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dmitriy on 5/27/15.
 */
public class EntryControllerTest extends AbstractControllerTest {

    private final List<Entry> entries = new ArrayList<>(1);

    private EntryService entryService;

    private Validator validator;

    @org.junit.Before
    public void initEntryController(){
        Entry entry = new Entry();
        entry.setId(1l);
        entry.setBody("Body");
        entries.add(entry);
    }

    @Test
    public void testShow() throws Exception {
        entryService = mock(EntryService.class);
        validator = mock(Validator.class);

        long id = 1l;
        when(entryService.findById(id)).thenReturn(entries.get(0));
        when(validator.validate(entries.get(0))).thenReturn(new HashSet<ConstraintViolation<Entry>>());

        EntryController controller = new EntryController();

        ReflectionTestUtils.setField(controller, "entryService", entryService);
        ReflectionTestUtils.setField(controller, "validator", validator);

        ExtendedModelMap uiModel = new ExtendedModelMap();
        String result = controller.show(id, uiModel);

        assertNotNull(result);
        assertEquals(result, "blogs/show");

        Entry modelEntry = (Entry) uiModel.get(EntryController.ENTRY);
        assertNotNull(modelEntry);
    }

}
