package sajeevan.task_track.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sajeevan.task_track.DTO.CurrentUser;
import sajeevan.task_track.Service.ProjectService;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/getName")
    public ResponseEntity<CurrentUser> getMethodName() {
        CurrentUser currentUser = projectService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(currentUser);
    }
}
