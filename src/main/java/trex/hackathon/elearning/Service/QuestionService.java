package trex.hackathon.elearning.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import trex.hackathon.elearning.Model.QuestionModel;
import trex.hackathon.elearning.Repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    /*
    * create a new Question : POST /questions
    * */
    @Async
    public CompletableFuture<QuestionModel> createQuestion (QuestionModel questionModel) throws Exception {

        // checking if the no of options are lesser than the number of options required for the question
        if(questionModel.getOptions().size() < questionModel.getTotalOption()) {
            throw new IllegalArgumentException("The number of options must be greater than the number of options available for the question ");
        }

        // list of values for the question
        List<String> values = new ArrayList<>();
        for(int i = 0; i < questionModel.getTotalOption(); i++) {
            values.add(questionModel.getOptions().get(i));
        }
        questionModel.setOptions(values);

        // saving the question
        return CompletableFuture.completedFuture(questionRepository.save(questionModel));
    }

    /*
    * update the question : PUT /questions/{id}
    * */
    @Async
    public CompletableFuture<QuestionModel> updateQuestion(QuestionModel questionModel, Long id) throws Exception {

        // getting the question model by its id
        Optional<QuestionModel> updatedQuestion = questionRepository.findById(id);

        if(!updatedQuestion.isPresent()) {
            throw new IllegalArgumentException("The question with id " + id + " does not exist");
        } else {
            updatedQuestion.get().setQuestion(questionModel.getQuestion());
            updatedQuestion.get().setTotalOption(questionModel.getTotalOption());
            updatedQuestion.get().setCorrectAnswer(questionModel.getCorrectAnswer());

            // checking if the updated Question has options less than the total option
            if(updatedQuestion.get().getOptions().size() < updatedQuestion.get().getTotalOption()) {
                throw new IllegalArgumentException("The number of options must be greater than the number of options available for the question ");
            }

            List<String> values = new ArrayList<>();
            for(int i = 0; i < updatedQuestion.get().getTotalOption(); i++) {
                values.add(updatedQuestion.get().getOptions().get(i));
            }

            updatedQuestion.get().setOptions(values);
            // saving the question
            return CompletableFuture.completedFuture(questionRepository.save(updatedQuestion.get()));
        }
    }

    /*
    * delete the question : DELETE /questions/{id}
    * */
    @Async
    public CompletableFuture<String> deleteQuestion(Long id) {
        QuestionModel questionModel = questionRepository.findById(id).orElseThrow( () -> {
            throw new IllegalArgumentException("The question with id " + id + " does not exist");
        });
        questionRepository.delete(questionModel);
        return CompletableFuture.completedFuture("Question deleted successfully");
    }

    /*
    * get question by id : GET /questions/{id}
    * */
    @Async
    public CompletableFuture<QuestionModel> getQuestionById(Long id) throws Exception {
        QuestionModel questionModel = questionRepository.findById(id).orElseThrow( () -> {
            throw new IllegalArgumentException("The question with id " + id + " does not exist");
        });

        return CompletableFuture.completedFuture(questionModel);
    }
}
