package ua.org.project.domain;

import ua.org.project.domain.impl.Entry;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public class Entries {
    private List<Entry> entries;

    public Entries() {
    }

    public Entries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

}
