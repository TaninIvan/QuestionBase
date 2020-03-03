package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.AnswerDto;
import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/answer")
@RestController
public class AnswerController {

    // Constructor Based Injection
    @Autowired AnswerService answerService;
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AnswerDto createAnswer(@RequestBody AnswerDto answerDto) throws ParseException {
        Answer answer = answerService.convertToEntity(answerDto);
        Answer answerCreated = answerService.createAnswer(answer.getUser().getId(),
                answer.getQuestion().getId(),answer);
        return answerService.convertToDto(answerCreated);
    }

    // GET
    @GetMapping("/{id}")
    @ResponseBody
    public AnswerDto getAnswer(@PathVariable("id") Long id){
        return answerService.convertToDto(answerService.get(id));
    }

    @GetMapping("byUser/{userId}")
    @ResponseBody
    public List<AnswerDto> getAnswersByUserId(@PathVariable("userId") Long userId,
                                              @PageableDefault(sort = {"text"},
                                                      direction = Sort.Direction.ASC) Pageable pageable){
        return answerService.getAnswersByUserId(userId, pageable).stream()
                .map(answerService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("page")
    @ResponseBody
    public List<AnswerDto> getAnswers(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return answerService.getAnswerPage(pageable).stream()
                .map(answerService::convertToDto)
                .collect(Collectors.toList());
    }

    // PUT
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateAnswer(@RequestBody AnswerDto answerDto) throws ParseException {
        Answer answer = answerService.convertToEntity(answerDto);
        answerService.updateAnswer(answer);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable("id") Long id){
        answerService.delete(id);
        return "Answer has deleted!";
    }

    @DeleteMapping("/all")
    public String deleteAllQuestions(){
        answerService.deleteAll();
        return "All answers have deleted!";
    }
}

