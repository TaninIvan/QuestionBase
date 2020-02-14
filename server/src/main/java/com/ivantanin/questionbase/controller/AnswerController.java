package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.AnswerDto;
import com.ivantanin.questionbase.dto.UserDto;
import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.entity.User;
import com.ivantanin.questionbase.service.AnswerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/answer")
@RestController
public class AnswerController {

    @Autowired AnswerService answerService;
    @Autowired ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AnswerDto createAnswer(@RequestBody AnswerDto answerDto) throws ParseException {
        Answer answer = convertToEntity(answerDto);
        Answer answerCreated = answerService.createAnswer(answer.getUser().getId(),
                answer.getQuestion().getId(),answer);
        return convertToDto(answerCreated);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Answer getAnswer(@PathVariable("id") Long id){
        return answerService.get(id);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<AnswerDto> getAnswers(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort) {

        List<Answer> posts = answerService.getAnswerList(page, size, sortDir, sort);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateAnswer(@RequestBody AnswerDto answerDto) throws ParseException {
        Answer answer = convertToEntity(answerDto);
        answerService.updateAnswer(answer);
    }

    @GetMapping("delete")
    public String deleteAnswer(@PathVariable("id") Long id){
        answerService.delete(id);
        return "Answer has deleted!";
    }

    @GetMapping("deleteAll")
    public String deleteAllQuestions(){
        answerService.deleteAll();
        return "All answers have deleted!";
    }

    private AnswerDto convertToDto(Answer answer) {
        return modelMapper.map(answer, AnswerDto.class);
    }

    private Answer convertToEntity(AnswerDto answerDto) throws ParseException {
        return modelMapper.map(answerDto, Answer.class);
    }
}

