package sajeevan.task_track.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sajeevan.task_track.Entity.Projects;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, Long>{

    List<Projects> findByOwner(Long userId);
  
} 