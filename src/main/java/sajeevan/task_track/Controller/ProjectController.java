package sajeevan.task_track.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sajeevan.task_track.DTO.CurrentUser;
import sajeevan.task_track.Entity.Projects;
import sajeevan.task_track.Service.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    /*
     * Baode to create a project
     * {
     * "name": "E-Commerce Backend",
     * "description": "Building a high-scale retail API",
     * "status": "STARTED"
     * }
     */

    @PostMapping("/createProject")
    public ResponseEntity<?> createNewProject(@RequestBody Projects project) {
        if (project == null) {
            return ResponseEntity.badRequest()
                    .body("Unable to create a Project without a name");
        }
        Projects newProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProject);
    }

}
