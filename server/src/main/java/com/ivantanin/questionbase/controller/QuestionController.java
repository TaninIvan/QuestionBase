package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.dto.QuestionDto;
import com.ivantanin.questionbase.dto.TopicDto;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("question")
@RestController
public class QuestionController {

    @Autowired QuestionService questionService;
    @Autowired ModelMapper modelMapper;

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public QuestionDto createQuestion(@RequestBody QuestionDto questionDto) throws ParseException {
        Question question = convertToEntity(questionDto);
        return convertToDto(questionService.createQuestion(question));
    }

    // GET
    @GetMapping(value = "{id}")
    @ResponseBody
    public QuestionDto getQuestion(@PathVariable("id") Long id, @RequestHeader Map<String,String> headers) {
        return convertToDto(questionService.get(id));
    }

    @GetMapping("all")
    public Iterable<Question> getAllQuestions(){
        return questionService.getAll();
    }

    @GetMapping("all/page")
    @ResponseBody
    public List<QuestionDto> getQuestions(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<Question> questions = questionService.getQuestionPage(pageable);
        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("last")
    @ResponseBody
    public List<QuestionDto> getLastQuestions(@RequestParam(required = false) Optional<Integer> last) {

        List<Question> questions = questionService.getLastQuestions(last.orElse(5));
        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("most/popular")
    @ResponseBody
    public List<QuestionDto> getPopularQuestion() {
       return questionService.getMostPopularQuestion().stream()
               .map(this::convertToDto)
               .collect(Collectors.toList());
    }

    @GetMapping("most/priced")
    @ResponseBody
    public List<QuestionDto> getPricedQuestion() {
        return questionService.getMostPricedQuestion().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("filter")
    public List<QuestionDto> filter(@RequestHeader  Map<String,String> headers) throws java.text.ParseException {
        List<Question> questions = questionService.getAll();
        if(headers.containsKey("author")) {
            questions = questions.stream().filter(question -> question.getAuthor().equals(headers.get("author"))).collect(Collectors.toList());
        }

        if(headers.containsKey("from")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date from = format.parse(headers.get("from"));
            questions = questions.stream().filter(question -> question.getCreationDate().after(from)).collect(Collectors.toList());
        }

        if(headers.containsKey("to")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date to = format.parse(headers.get("to"));
            questions = questions.stream().filter(question -> question.getCreationDate().before(to)).collect(Collectors.toList());
        }

        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // PUT
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody QuestionDto questionDto) throws ParseException {
        Question question = convertToEntity(questionDto);
        questionService.updateQuestion(question);
    }

    @PutMapping("{id}/addTopic")
    public String addTopic(@PathVariable Long id, @RequestBody TopicDto topicDto){
        Topic topic = convertToEntity(topicDto);
        return questionService.addTopic(questionService.get(id),topic);
    }

    // DELETE
    @DeleteMapping("{id}" )
    public String deleteQuestion(@PathVariable("id") Long id){
        questionService.delete(1L);
        return "Question has deleted!";
    }

    @DeleteMapping("all")
    public String deleteAllQuestions(){
        questionService.deleteAll();
        return "All deleted";
    }

    // CONVERTERS
    private QuestionDto convertToDto(Question question) {
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        question.getTopics().forEach(topic -> questionDto.addTopicName(topic.getTopicName()));
        return questionDto;
    }

    private Question convertToEntity(QuestionDto questionDto) throws ParseException {
        Question question = modelMapper.map(questionDto, Question.class);
        questionDto.getTopicNameSet().forEach(topicName -> questionService.addTopic(question,new Topic(topicName)));
        // All correct answers are stored without capital letters
        question.setCorrectAnswers(question.getCorrectAnswers().toLowerCase());
        return question;
    }

    private Topic convertToEntity(TopicDto topicDto) throws ParseException {
        return modelMapper.map(topicDto, Topic.class);
    }
}
