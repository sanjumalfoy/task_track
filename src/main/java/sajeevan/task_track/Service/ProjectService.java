package sajeevan.task_track.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import sajeevan.task_track.DTO.CurrentUser;
import sajeevan.task_track.Entity.Projects;
import sajeevan.task_track.Entity.User;
import sajeevan.task_track.Entity.UserPrinciple;
import sajeevan.task_track.Repository.ProjectRepository;
import sajeevan.task_track.Repository.UserRepository;
import sajeevan.task_track.enums.ProjectStatus;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProjectRepository projectRepo;

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

    public Long getCurrentUserId(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return null;
        }
        Long userId=null;
        Object principle=authentication.getPrincipal();

        if(principle instanceof UserPrinciple){
            UserPrinciple userPrinciple=(UserPrinciple) principle;
            userId=userPrinciple.getId();
        }
        return userId;
    }

    public Projects createProject(Projects project){
        Long userID=getCurrentUserId();
        User user=userRepo.getReferenceById(userID);
        project.setOwner(user);
        if(project.getStatus()==null){
            project.setStatus(ProjectStatus.SCHEDULED);
        }
        return projectRepo.save(project);
    }
}
