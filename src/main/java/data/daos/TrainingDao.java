package data.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import data.entities.Training;

public interface TrainingDao extends JpaRepository<Training, Integer>  {
	
	@Transactional
	@Modifying
	@Query(value = "delete from Training training where training.id = ?1")
    void deleteById(int idTraining);
	
}
