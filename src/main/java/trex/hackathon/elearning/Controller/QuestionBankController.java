package trex.hackathon.elearning.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import trex.hackathon.elearning.Model.QuestionBankModel;
import trex.hackathon.elearning.Service.QuestionBankService;

import java.util.concurrent.CompletableFuture;

@Async
@RestController
@RequestMapping(value = "/api/v1/")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    /*
     * POST /api/v1/questionBank 1
     * */
    @Async
    @PostMapping(value = "/questionBank")
    public CompletableFuture<ResponseEntity<?>> createQuestion (
            @RequestBody QuestionBankModel question
    ) throws Exception {
        return questionBankService.createQuestionBank(question).thenApply( q -> {
            if(q != null) {
                return ResponseEntity.ok().body(q);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
     * PUT /api/v1/questioBank/{id}
     * */
    @Async
    @PutMapping(value = "/questionBank/{id}")
    public CompletableFuture<ResponseEntity<?>> updateQuestion (
            @RequestBody QuestionBankModel question,
            @PathVariable Long id
    ) throws Exception {
        return questionBankService.updateQuestionBank(question, id).thenApply( q -> {
            if(q != null) {
                return ResponseEntity.ok().body(q);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
     * DELETE /api/v1/questionBank/{id}
     * */
    @Async
    @DeleteMapping(value = "/questionBank/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteQuestion (
            @PathVariable Long id
    ) throws Exception {
        return questionBankService.deleteQuestionBank(id).thenApply( q -> {
            return ResponseEntity.ok().body(q);
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
     * GET /api/v1/questionBank/{id}
     * */
    @Async
    @GetMapping("/questionBank/{id}")
    public CompletableFuture<ResponseEntity<QuestionBankModel>> getQuestionById (
            @PathVariable("id") Long id
    ) throws Exception {
        try {
            return questionBankService.getQuestionBankById(id).thenApply( q -> {
                return ResponseEntity.ok().body(q);
            }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
