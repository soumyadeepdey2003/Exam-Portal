package trex.hackathon.elearning.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "question_bank")
@NoArgsConstructor
public class QuestionBankModel {

    public QuestionBankModel(String name, String description, Long noOfQuestionPapers, List<QuestionPaperModel> questionPaper) {
        this.name = name;
        this.description = description;
        this.noOfQuestionPapers = noOfQuestionPapers;
        this.questionPaper = questionPaper;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Long noOfQuestionPapers;

    @OneToMany
    private List<QuestionPaperModel> questionPaper;
}
