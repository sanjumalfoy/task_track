package sajeevan.task_track.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import sajeevan.task_track.DTO.CurrentUser;
import sajeevan.task_track.Entity.UserPrinciple;

@Service
public class ProjectService {

    // Get the current username and id from the security context
    public CurrentUser getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        String userName = null;
        Long userId = null;

        if (principal instanceof UserPrinciple) {
            UserPrinciple userPrinciple = (UserPrinciple) principal;
            userName = userPrinciple.getUsername();
            userId = userPrinciple.getId();
        } else if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else if (principal != null) {
            userName = principal.toString();
        }

        return new CurrentUser(userName, userId);
    }
}
