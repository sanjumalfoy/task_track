package sajeevan.task_track.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sajeevan.task_track.Entity.Projects;
import sajeevan.task_track.Entity.Tasks;
import sajeevan.task_track.Repository.ProjectRepository;
import sajeevan.task_track.Repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepo;

    @Autowired
    ProjectRepository projectRepo;

    public Tasks createNewTask(Long ProjectId, Tasks task) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // // 2. Fetch the project and verify it exists
        // Project project = projectRepository.findById(projectId)
        //         .orElseThrow(() -> new RuntimeException("Project not found"));

        
        Projects project = projectRepo.findById(ProjectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 3. SECURE CHECK: Does the current user own this project?
        if (!project.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized: You do not own this project");
        }
        
        task.setProject(project);
        return taskRepo.save(task);
    }

}
