package sajeevan.task_track.Controller;

import org.springframework.web.bind.annotation.RestController;

import sajeevan.task_track.Entity.TaskHistory;
import sajeevan.task_track.Entity.Tasks;
import sajeevan.task_track.Service.TaskHistoryService;
import sajeevan.task_track.Service.TaskService;
import sajeevan.task_track.enums.Severity;
import sajeevan.task_track.enums.TaskStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskHistoryService taskhistoryService;

    @PostMapping("/newTask/{project_id}")
    public ResponseEntity<?> createNewTask(@PathVariable Long project_id, @RequestBody Tasks task) {
        // if(task==null){
        // return ResponseEntity.badRequest()
        // .body("Invalid Task data.");
        // }
        Tasks saveTask = taskService.createNewTask(project_id, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveTask);
    }

    @PatchMapping("{task_id}/stateUpdate")
    public ResponseEntity<Tasks> updateTask(
            @PathVariable Long task_id,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Severity severity) {
        Tasks savedTask = taskhistoryService.updateTaskState(task_id, status, severity);
        return ResponseEntity.ok(savedTask);
    }

    @GetMapping("/{task_id}/history")
    public ResponseEntity<List<TaskHistory>> getHistory(@PathVariable Long task_id) {
        return ResponseEntity.ok(taskhistoryService.getHistoryById(task_id));
    }
    

}
