package trex.hackathon.elearning.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import trex.hackathon.elearning.Model.QuestionModel;
import trex.hackathon.elearning.Model.QuestionPaperModel;
import trex.hackathon.elearning.Repository.QuestionPaperRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class QuestionPaperService {

    @Autowired
    private QuestionPaperRepository questionPaperRepository;

    /*
     * create a new Question paper : POST /questionPaper
     * */
    @Async
    public CompletableFuture<QuestionPaperModel> createQuestionPaper (QuestionPaperModel questionPaperModel) throws Exception {

        // checking if the no of options are lesser than the number of options required for the question
        if(questionPaperModel.getQuestions().size() < questionPaperModel.getTotalQuestions()) {
            throw new IllegalArgumentException("The number of questions must be greater than the number of Questions available for the paper");
        }

        // list of values for the question
        List<QuestionModel> values = new ArrayList<>();
        for(int i = 0; i < questionPaperModel.getTotalQuestions(); i++) {
            values.add(questionPaperModel.getQuestions().get(i));
        }
        questionPaperModel.setQuestions(values);

        // saving the question
        return CompletableFuture.completedFuture(questionPaperRepository.save(questionPaperModel));
    }

    /*
     * update the question paper : PUT /questionPaper/{id}
     * */
    @Async
    public CompletableFuture<QuestionPaperModel> updateQuestionPaper (QuestionPaperModel questionPaperModel, Long id) throws Exception {

        Optional<QuestionPaperModel> updatedQuestion = questionPaperRepository.findById(id);

        if(!updatedQuestion.isPresent()) {
            throw new IllegalArgumentException("The number of questions must be greater than the number of Questions available for the bank");
        } else {
            updatedQuestion.get().setName(questionPaperModel.getName());
            updatedQuestion.get().setDescription(questionPaperModel.getDescription());
            updatedQuestion.get().setFullMarks(questionPaperModel.getFullMarks());
            updatedQuestion.get().setTotalQuestions(questionPaperModel.getTotalQuestions());

            if(updatedQuestion.get().getQuestions().size() < updatedQuestion.get().getTotalQuestions()) {
                throw new IllegalArgumentException("The question paper with id " + id + " does not exst");
            }

            List<QuestionModel> values = new ArrayList<>();
            for(int i = 0; i < updatedQuestion.get().getTotalQuestions(); i++) {
                values.add(updatedQuestion.get().getQuestions().get(i));
            }

            updatedQuestion.get().setQuestions(values);
            // saving the question paper
            return CompletableFuture.completedFuture(questionPaperRepository.save(updatedQuestion.get()));
        }
    }

    /*
     * delete the question paper : DELETE /questionPaper/{id}
     * */
    @Async
    public CompletableFuture<String> deleteQuestionPaper (Long id) {
        QuestionPaperModel questionPaperModel = questionPaperRepository.findById(id).orElseThrow( () -> {
            throw new IllegalArgumentException("The question paper with id " + id + " does not exist");
        });
        questionPaperRepository.delete(questionPaperModel);
        return CompletableFuture.completedFuture("Question Paper deleted successfully");
    }

    /*
     * get question paper by id : GET /questionPaper/{id}
     * */
    @Async
    public CompletableFuture<QuestionPaperModel> getQuestionPaperById (Long id) throws Exception {
        QuestionPaperModel questionPaperModel = questionPaperRepository.findById(id).orElseThrow( () -> {
            throw new IllegalArgumentException("The question paper with id " + id + " does not exist");
        });

        return CompletableFuture.completedFuture(questionPaperModel);
    }
}
