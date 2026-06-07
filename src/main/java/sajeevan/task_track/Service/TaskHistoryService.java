package sajeevan.task_track.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sajeevan.task_track.Entity.TaskHistory;
import sajeevan.task_track.Entity.Tasks;
import sajeevan.task_track.Repository.TaskHistoryRepository;
import sajeevan.task_track.Repository.TaskRepository;
import sajeevan.task_track.enums.Severity;
import sajeevan.task_track.enums.TaskStatus;

@Service
public class TaskHistoryService {

    @Autowired
    private TaskHistoryRepository taskHistoryRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Transactional
    public Tasks updateTaskState(Long taskId, TaskStatus newStatus, Severity newSeverity) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Tasks task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Wrong Task ID"));
        
        if(newStatus!=null && !task.getStatus().equals(newStatus)){
            TaskHistory history=new TaskHistory();
            history.setTaskId(taskId);
            history.setChangedField("STATUS");
            history.setOldValue(task.getStatus().name());
            history.setNewValue(newStatus.name());
            history.setUpdatedBy(userName);
            history.setUpdatedAt(LocalDateTime.now());
            taskHistoryRepo.save(history);

            task.setStatus(newStatus);
        }

        if(newSeverity!=null && !task.getSeverity().equals(newSeverity)){
            TaskHistory history=new TaskHistory();
            history.setTaskId(taskId);
            history.setChangedField("SEVERITY");
            history.setOldValue(task.getSeverity().name());
            history.setOldValue(newSeverity.name());
            history.setUpdatedBy(userName);
            history.setUpdatedAt(LocalDateTime.now());
            taskHistoryRepo.save(history);

            task.setSeverity(newSeverity);
        }

        task.setUpdatedAt(LocalDateTime.now());
        return taskRepo.save(task);
    }

    public List<TaskHistory> getHistoryById(Long taskId){
        return taskHistoryRepo.findByTaskIdOrderByUpdatedAtDesc(taskId);
    }
}
