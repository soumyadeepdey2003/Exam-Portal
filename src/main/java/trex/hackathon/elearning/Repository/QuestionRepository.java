package trex.hackathon.elearning.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trex.hackathon.elearning.Model.QuestionModel;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel, Long> {
}
