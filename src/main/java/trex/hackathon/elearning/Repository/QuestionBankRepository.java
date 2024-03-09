package trex.hackathon.elearning.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trex.hackathon.elearning.Model.QuestionBankModel;

@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBankModel, Long>{
}
