package ua.org.project.service;

import ua.org.project.domain.Impression;

/**
 * Created by Dmitry Petrov on 28.06.14.
 */
public interface EntryImpressionService {
    public Impression findById(Long id);
    public Impression save(Impression impression);
}
