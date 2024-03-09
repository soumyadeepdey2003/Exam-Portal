package trex.hackathon.elearning.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import trex.hackathon.elearning.Model.QuestionModel;
import trex.hackathon.elearning.Model.QuestionBankModel;
import trex.hackathon.elearning.Model.QuestionPaperModel;
import trex.hackathon.elearning.Repository.QuestionBankRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class QuestionBankService {

    @Autowired
    private QuestionBankRepository questionBankRepository;

    /*
     * create a new Question bank : POST /questionBank
     * */
    @Async
    public CompletableFuture<QuestionBankModel> createQuestionBank (QuestionBankModel questionBankModel) throws Exception {

        // checking if the no of options are lesser than the number of options required for the question
        if(questionBankModel.getQuestionPaper().size() < questionBankModel.getNoOfQuestionPapers()) {
            throw new IllegalArgumentException("The number of Question papers must be greater than the number of Question papers available for the bank ");
        }

        // list of values for the question
        List<QuestionPaperModel> values = new ArrayList<>();
        for(int i = 0; i < questionBankModel.getNoOfQuestionPapers(); i++) {
            values.add(questionBankModel.getQuestionPaper().get(i));
        }
        questionBankModel.setQuestionPaper(values);

        // saving the question
        return CompletableFuture.completedFuture(questionBankRepository.save(questionBankModel));
    }

    /*
     * update the question bank : PUT /questionBank/update
     * */
    @Async
    public CompletableFuture<QuestionBankModel> updateQuestionBank (QuestionBankModel questionBankModel, Long id) throws Exception {

        Optional<QuestionBankModel> updatedQuestion = questionBankRepository.findById(id);

        if(!updatedQuestion.isEmpty()) {
            updatedQuestion.get().setName(questionBankModel.getName());
            updatedQuestion.get().setDescription(questionBankModel.getDescription());
            updatedQuestion.get().setNoOfQuestionPapers(questionBankModel.getNoOfQuestionPapers());

            if(updatedQuestion.get().getQuestionPaper().size() < updatedQuestion.get().getNoOfQuestionPapers()) {
                throw new IllegalArgumentException("The number of options must be greater than the number of options available for the question ");
            }

            List<QuestionPaperModel> values = new ArrayList<>();
            for(int i = 0; i < updatedQuestion.get().getNoOfQuestionPapers(); i++) {
                values.add(updatedQuestion.get().getQuestionPaper().get(i));
            }

            updatedQuestion.get().setQuestionPaper(values);
            // saving the question paper
            return CompletableFuture.completedFuture(questionBankRepository.save(updatedQuestion.get()));
        } else {
            throw new IllegalArgumentException("The question with id " + id + " does not exist");
        }
    }

    /*
     * delete the question : DELETE /questionBank/{id}
     * */
    @Async
    public CompletableFuture<String> deleteQuestionBank (Long id) {
        QuestionBankModel questionBankModel = questionBankRepository.findById(id).orElseThrow( () -> {
            throw new IllegalArgumentException("The question with id " + id + " does not exist");
        });
        questionBankRepository.delete(questionBankModel);
        if(questionBankModel==null) {
            return CompletableFuture.completedFuture("SUCCESS");
        } else {
            throw new IllegalArgumentException("Internal Server Error, Refresh your page");
        }
    }

    /*
     * get question by id : GET /questionBank/{id}
     * */
    @Async
    public CompletableFuture<QuestionBankModel> getQuestionBankById (Long id) throws Exception {
        QuestionBankModel questionBankModel = questionBankRepository.findById(id).orElseThrow( () -> {
            throw new IllegalArgumentException("The question with id " + id + " does not exist");
        });

        return CompletableFuture.completedFuture(questionBankModel);
    }
}
