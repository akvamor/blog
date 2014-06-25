package ua.org.project.web.form;

import ua.org.project.domain.Attachment;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.impl.Entry;

import java.util.List;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/31/14.
 */
public class EntryShow {

    private Entry entry;

    private List<String> images;

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
