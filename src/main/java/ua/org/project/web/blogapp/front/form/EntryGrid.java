package ua.org.project.web.blogapp.front.form;

import ua.org.project.domain.impl.Entry;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/29/14.
 */
public class EntryGrid {

    private int totalPages;

    private int currentPage;

    private long totalRecords;

    private List<Entry> entryData;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Entry> getEntryData() {
        return entryData;
    }

    public void setEntryData(List<Entry> entryData) {
        this.entryData = entryData;
    }
}
