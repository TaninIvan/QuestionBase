package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.AnswerDto;
import com.ivantanin.questionbase.entity.Answer;
import com.ivantanin.questionbase.service.AnswerService;
import org.modelmapper.ModelMapper;
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

    @Autowired AnswerService answerService;
    @Autowired ModelMapper modelMapper;

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AnswerDto createAnswer(@RequestBody AnswerDto answerDto) throws ParseException {
        Answer answer = convertToEntity(answerDto);
        Answer answerCreated = answerService.createAnswer(answer.getUser().getId(),
                answer.getQuestion().getId(),answer);
        return convertToDto(answerCreated);
    }

    // GET
    @GetMapping("/{id}")
    @ResponseBody
    public AnswerDto getAnswer(@PathVariable("id") Long id){
        return convertToDto(answerService.get(id));
    }

    @GetMapping("/all")
    @ResponseBody
    public List<AnswerDto> getAnswers(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        List<Answer> answers = answerService.getAnswerPage(pageable);
        return answers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // PUT
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateAnswer(@RequestBody AnswerDto answerDto) throws ParseException {
        Answer answer = convertToEntity(answerDto);
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

    // CONVERTERS
    private AnswerDto convertToDto(Answer answer) {
        return modelMapper.map(answer, AnswerDto.class);
    }

    private Answer convertToEntity(AnswerDto answerDto) throws ParseException {
        return modelMapper.map(answerDto, Answer.class);
    }
}

