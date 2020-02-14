package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.QuestionDto;
import com.ivantanin.questionbase.dto.TopicDto;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("question")
@RestController
public class QuestionController {

    @Autowired QuestionService questionService;
    @Autowired ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public QuestionDto createQuestion(@RequestBody QuestionDto questionDto) throws ParseException {
        Question question = convertToEntity(questionDto);
        Question questionCreated = questionService.createQuestion(question);
        return convertToDto(questionCreated);
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public Question getQuestion(@PathVariable("id") Long id){
        return questionService.get(id);
    }

    @GetMapping("all")
    @ResponseBody
    public List<QuestionDto> getQuestions(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort) {

        List<Question> questions = questionService.getQuestionList(page, size, sortDir, sort);
        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody QuestionDto questionDto) throws ParseException {
        Question question = convertToEntity(questionDto);
        questionService.updateQuestion(question);
    }


    @DeleteMapping("{id}" )
    public String deleteQuestion(@PathVariable("id") Long id){
        questionService.delete(1L);
        return "Question has deleted!";
    }

    @DeleteMapping("deleteAll")
    public String deleteAllQuestions(){
        questionService.deleteAll();
        return "All deleted";
    }

    @PutMapping("{id}/addTopic")
    public String addTopic(@PathVariable Long id, @RequestBody TopicDto topicDto){
       Topic topic = convertToEntity(topicDto);
       questionService.addTopic(questionService.get(id),topic);
       return "Success";
    }

    private QuestionDto convertToDto(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }

    private Question convertToEntity(QuestionDto questionDto) throws ParseException {
        return modelMapper.map(questionDto, Question.class);
    }

    private Topic convertToEntity(TopicDto topicDto) throws ParseException {
        return modelMapper.map(topicDto, Topic.class);
    }
}
