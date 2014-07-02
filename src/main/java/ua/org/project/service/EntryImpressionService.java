package ua.org.project.service;

import ua.org.project.domain.Impression;

/**
 * Created by Dmitry Petrov on 28.06.14.
 */
public interface EntryImpressionService {
    Impression findById(Long id);
    Impression save(Impression impression);
}
