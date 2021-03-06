package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.QuestionDto;
import com.ivantanin.questionbase.dto.TopicDto;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.service.QuestionService;
import com.ivantanin.questionbase.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("question")
@RestController
public class QuestionController {

    @Autowired QuestionService questionService;
    @Autowired TopicService topicService;

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public QuestionDto createQuestion(@RequestBody QuestionDto questionDto) throws ParseException {
        Question question = questionService.convertToEntity(questionDto);
        try {
            return questionService.convertToDto(questionService.createQuestion(question));
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(),e);
        }
    }

    // GET
    @GetMapping(value = "{id}")
    @ResponseBody
    public QuestionDto getQuestion(
            @PathVariable("id") Long id, @RequestHeader Map<String,String> headers) throws Exception {
        return questionService.convertToDto(questionService.get(id));
    }

    @GetMapping("page")
    @ResponseBody
    public List<QuestionDto> getQuestions(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        List<Question> questions = questionService.getQuestionPage(pageable);
        return questions.stream()
                .map(questionService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("last")
    @ResponseBody
    public List<QuestionDto> getLastQuestions(@RequestParam(required = false) Optional<Integer> last) {

        List<Question> questions = questionService.getLastQuestions(last.orElse(5));
        return questions.stream()
                .map(questionService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("most/popular")
    @ResponseBody
    public List<QuestionDto> getPopularQuestion() {
       return questionService.getMostPopularQuestion().stream()
               .map(questionService::convertToDto)
               .collect(Collectors.toList());
    }

    @GetMapping("most/priced")
    @ResponseBody
    public List<QuestionDto> getPricedQuestion() {
        return questionService.getMostPricedQuestion().stream()
                .map(questionService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("filter")
    public List<QuestionDto> filter(
            @RequestHeader  Map<String,String> headers, Pageable pageable) throws java.text.ParseException {
        List<Question> questions = questionService.filterQuestionsByHeaders(headers, pageable);
        return questions.stream()
                .map(questionService::convertToDto)
                .collect(Collectors.toList());
    }

    // PUT
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDto updateQuestion(@PathVariable Long id, @RequestBody QuestionDto questionDto) throws Exception {
        Question question = questionService.updateQuestion(id,questionService.convertToEntity(questionDto));
        return questionService.convertToDto(question);
    }

    @PutMapping("{id}/addTopic")
    public String addTopic(@PathVariable Long id, @RequestBody TopicDto topicDto) throws Exception {
        Topic topic = topicService.convertToEntity(topicDto);
        try {
            return questionService.addTopic(questionService.get(id),topic);
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(),e);
        }
    }

    // DELETE
    @PutMapping("{id}/deleteTopic")
    public String deleteTopic(@PathVariable Long id, @RequestBody TopicDto topicDto) throws Exception {
        Topic topic = topicService.convertToEntity(topicDto);
        try {
            return questionService.deleteTopic(questionService.get(id),topic);
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(),e);
        }
    }

    @DeleteMapping("{id}" )
    public String deleteQuestion(@PathVariable("id") Long id){
        try {
            questionService.delete(id);
            return "Question has deleted!";
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(),e);
        }
    }

    @DeleteMapping("all")
    public String deleteAllQuestions(){
        questionService.deleteAll();
        return "All deleted";
    }
}
