package trex.hackathon.elearning.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import trex.hackathon.elearning.Model.QuestionModel;
import trex.hackathon.elearning.Service.QuestionService;

import java.util.concurrent.CompletableFuture;

@Async
@RestController
@RequestMapping(value = "/api/v1/")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    /*
    * POST /api/v1/question
    * */
    @Async
    @PostMapping(value = "/question")
    public CompletableFuture<ResponseEntity<?>> createQuestion (
            @RequestBody QuestionModel question
    ) throws Exception {
        return questionService.createQuestion(question).thenApply( q -> {
            if(q != null) {
                return ResponseEntity.ok().body(q);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
    * PUT /api/v1/question/{id}
    * */
    @Async
    @PutMapping(value = "/question/{id}")
    public CompletableFuture<ResponseEntity<?>> updateQuestion (
            @RequestBody QuestionModel question,

            @PathVariable Long id
    ) throws Exception {
        return questionService.updateQuestion(question, id).thenApply( q -> {
            if(q != null) {
                return ResponseEntity.ok().body(q);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
    * DELETE /api/v1/question/{id}
    * */
    @Async
    @DeleteMapping(value = "/question/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteQuestion (
            @PathVariable Long id
    ) throws Exception {
        return questionService.deleteQuestion(id).thenApply( q -> {
            return ResponseEntity.ok().body(q);
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
    * GET /api/v1/question/{id}
    * */
    @Async
    @GetMapping("/question/{id}")
    public CompletableFuture<ResponseEntity<QuestionModel>> getQuestionById (
            @PathVariable("id") Long id
    ) throws Exception {
        try {
            return questionService.getQuestionById(id).thenApply( q -> {
                return ResponseEntity.ok().body(q);
            }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
