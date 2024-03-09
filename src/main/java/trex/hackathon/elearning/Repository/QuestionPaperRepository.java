package trex.hackathon.elearning.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trex.hackathon.elearning.Model.QuestionPaperModel;

@Repository
public interface QuestionPaperRepository extends JpaRepository<QuestionPaperModel, Long> {
}
