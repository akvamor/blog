package ua.org.project.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Dmitry Petrov on 5/31/14.
 */
public class AuditorAwareBean implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = null;
        if (authentication != null)
            currentUser = authentication.getName();
        else
            currentUser = "batch";
        return currentUser;
    }
}
