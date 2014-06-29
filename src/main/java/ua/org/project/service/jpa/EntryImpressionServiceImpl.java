package ua.org.project.service.jpa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.Impression;
import ua.org.project.repository.ImpressionRepository;
import ua.org.project.service.EntryImpressionService;

/**
 * Created by Dmitry Petrov on 28.06.14.
 */
@Service("impressionService")
@Repository
public class EntryImpressionServiceImpl implements EntryImpressionService {

    @Autowired
    private ImpressionRepository impressionRepository;

    @Transactional(readOnly = true)
    public Impression findById(Long id) {
        return impressionRepository.findOne(id);
    }

    public Impression save(Impression impression) {
        return impressionRepository.save(impression);
    }

}
