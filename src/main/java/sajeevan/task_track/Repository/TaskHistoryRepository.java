package sajeevan.task_track.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sajeevan.task_track.Entity.TaskHistory;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long >{
    List<TaskHistory> findByTaskIdOrderByUpdatedAtDesc(Long id);
}
