package trex.hackathon.elearning.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import trex.hackathon.elearning.Model.QuestionPaperModel;
import trex.hackathon.elearning.Service.QuestionPaperService;

import java.util.concurrent.CompletableFuture;

@Async
@RestController
@RequestMapping(value = "/api/v1/")
public class QuestionPaperController {

    @Autowired
    private QuestionPaperService questionPaperService;

    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    /*
     * POST /api/v1/questionPaper
     * */
    @Async
    @PostMapping(value = "/questionPaper")
    public CompletableFuture<ResponseEntity<?>> createQuestionPaper (
            @RequestBody QuestionPaperModel question
    ) throws Exception {
        return questionPaperService.createQuestionPaper(question).thenApply( q -> {
            if(q != null) {
                return ResponseEntity.ok().body(q);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
     * PUT /api/v1/questionPaper/{id}
     * */
    @Async
    @PutMapping(value = "/questionPaper/{id}")
    public CompletableFuture<ResponseEntity<?>> updateQuestionPaper (
            @RequestBody QuestionPaperModel question,
            @PathVariable Long id
    ) throws Exception {
        return questionPaperService.updateQuestionPaper(question, id).thenApply( q -> {
            if(q != null) {
                return ResponseEntity.ok().body(q);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
     * DELETE /api/v1/questionPaper/{id}
     * */
    @Async
    @DeleteMapping(value = "/questionPaper/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteQuestionPaper (
            @PathVariable Long id
    ) throws Exception {
        return questionPaperService.deleteQuestionPaper(id).thenApply( q -> {
            return ResponseEntity.ok().body(q);
        }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }

    /*
     * GET /api/v1/questionPaper/{id}
     * */
    @Async
    @GetMapping("/questionPaper/{id}")
    public CompletableFuture<ResponseEntity<QuestionPaperModel>> getQuestionPaperById (
            @PathVariable("id") Long id
    ) throws Exception {
        try {
            return questionPaperService.getQuestionPaperById(id).thenApply( q -> {
                return ResponseEntity.ok().body(q);
            }).exceptionally(ex-> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
