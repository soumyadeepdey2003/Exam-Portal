package trex.hackathon.elearning.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "questions")
@NoArgsConstructor
public class QuestionModel {

    public QuestionModel(String question, int totalOption, List<String> options, String correctAnswer) {
        this.question = question;
        this.totalOption = totalOption;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private int totalOption;

    private List<String> options;

    private String correctAnswer;
}
