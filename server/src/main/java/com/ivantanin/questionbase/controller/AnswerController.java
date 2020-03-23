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
import org.springframework.web.server.ResponseStatusException;

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
    public AnswerDto createAnswer(@RequestBody AnswerDto answerDto) throws Exception {
        try {
            Answer answer = answerService.convertToEntity(answerDto);
            Answer answerCreated = answerService.toAnswer(answer);
            return answerService.convertToDto(answerCreated);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
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
    public AnswerDto updateAnswer(@RequestBody AnswerDto answerDto) throws ParseException {
        Answer answer = answerService.convertToEntity(answerDto);
        return answerService.convertToDto(answerService.updateAnswer(answer));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable("id") Long id){
        try {
            answerService.delete(id);
            return "Answer has deleted!";
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "There is no entity with id " + id, e);
        }
    }

    @DeleteMapping("/all")
    public String deleteAllQuestions(){
        answerService.deleteAll();
        return "All answers have deleted!";
    }
}

