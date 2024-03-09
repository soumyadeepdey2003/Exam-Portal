package trex.hackathon.elearning.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "question_paper")
@NoArgsConstructor
public class QuestionPaperModel {

    public QuestionPaperModel(String name, String description, int fullMarks, Long totalQuestions, List<QuestionModel> questions) {
        this.name = name;
        this.description = description;
        this.fullMarks = fullMarks;
        this.totalQuestions = totalQuestions;
        this.questions = questions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int fullMarks;

    private Long totalQuestions;

    @OneToMany(fetch = FetchType.EAGER)
    private List<QuestionModel> questions;
}
