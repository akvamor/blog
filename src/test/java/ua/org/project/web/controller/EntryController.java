package ua.org.project.web.controller;


import org.junit.Test;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.EntryService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 5/27/15.
 */
public class EntryController extends AbstractControllerTest {

    private final List<Entry> entries = new ArrayList<>(1);

    private EntryService entryService;

    @org.junit.Before
    public void initEntryController(){

    }

    @Test
    public void testShow() throws Exception {
        entryService = mock(EntryService.class);
        long id = 1l;
        when(entryService.findById(id)).thenReturn(entries.get(0));
    }

}
