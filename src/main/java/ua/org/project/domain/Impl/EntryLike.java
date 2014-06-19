package ua.org.project.domain.impl;

import ua.org.project.domain.AbstractLike;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
@Entity
@Table(name = "ENTRY_LIKE")
public class EntryLike extends AbstractLike implements Serializable {
    private Entry entry;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTRY_ID")
    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }
}
