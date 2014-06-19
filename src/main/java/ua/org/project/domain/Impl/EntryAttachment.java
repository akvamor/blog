package ua.org.project.domain.impl;

import ua.org.project.domain.Attachment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Table(name = "ENTRY_ATTACHMENT_DETAIL")
public class EntryAttachment extends Attachment implements Serializable {
    private Entry entry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTRY_ID")
    public Entry getEntry(){
        return this.entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }
}
