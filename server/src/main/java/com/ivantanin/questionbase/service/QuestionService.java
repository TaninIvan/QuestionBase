package com.ivantanin.questionbase.service;

import com.ivantanin.questionbase.dto.QuestionDto;
import com.ivantanin.questionbase.entity.Question;
import com.ivantanin.questionbase.entity.Topic;
import com.ivantanin.questionbase.repository.AnswerRepository;
import com.ivantanin.questionbase.repository.QuestionRepository;
import com.ivantanin.questionbase.repository.TopicRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Log
public class QuestionService {

    @Autowired QuestionRepository questionRepository;
    @Autowired TopicService topicService;
    @Autowired TopicRepository topicRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired AnswerRepository answerRepository;

    public Question createQuestion(Question question){

        // To avoid errors when loading from json file in InsertTestData.java
        question.getTopics().forEach(topic -> {
            try {
                addTopic(question, topic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // All correct answers are stored without capital letters
        question.setCorrectAnswers(question.getCorrectAnswers().toLowerCase());

        log.fine("New question saved");
        return questionRepository.save(question);

    }

    // get
    public Question get(Long id) throws Exception {
        Question question = questionRepository.findById(id).orElse(null);
        if (question == null)
            throw new Exception("Question with id " + id + " does not exist!");
        return question;
    }

    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionPage(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions.getContent();
    }

    public List<Question> getLastQuestions(int last) {
        return questionRepository.findLastQuestions(last);
    }

    public  List<Question> getMostPopularQuestion() {
        return questionRepository.findMostPopularQuestion();
    }

    public  List<Question> getMostPricedQuestion() {
        return questionRepository.findMostPricedQuestion();
    }

    public List<Question> filterQuestionsByHeaders(Map<String,String> headers, Pageable pageable)
            throws java.text.ParseException {
        if (headers.isEmpty())
            return questionRepository.findAll(pageable).getContent();
        else
            return questionRepository.filterQuestionsByHeaders(headers, pageable);
    }

    // update
    @Transactional
    public Question updateQuestion(Long questionId, Question newQuestion) throws Exception {
        Question question = questionRepository.findById(questionId).orElse(null);

        if (question == null)
            throw new Exception("Attempt to update a nonexistent question!");
        else if (!question.equals(newQuestion)) {

            question.setReward(newQuestion.getReward());
            question.setAuthor(newQuestion.getAuthor());
            question.setQuestionText(newQuestion.getQuestionText());

            // All correct answers are stored without capital letters
            question.setCorrectAnswers(newQuestion.getCorrectAnswers().toLowerCase());

            return questionRepository.save(question);
        }
        return question;
    }

    // delete
    @Transactional
    public void delete(Long id) {
        answerRepository.deleteQuestionId(id);
        questionRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        answerRepository.deleteAllQuestionId();
        questionRepository.deleteAll();
    }

    @Transactional
    // Add topic
    public String addTopic(Question question, Topic newtopic) throws Exception {
        String topicName = newtopic.getTopicName();
        Topic topic = topicService.get(topicName);

        if (topic == null) {
            topic = newtopic;
            topicService.createTopic(topic);
        }

        if (question.getTopics().contains(topic) && question.getId() != null)
            throw new Exception("The question '" + question.getId() + "' already contains topic '" + topicName + "'!");
        question.addTopic(topic);
        topicService.addQuestion(topic, question);
        topicService.update(topic);
        return topicName + " added!";
    }

    @Transactional
    // Delete topics
    public String deleteTopic(Question question, Topic deletedTopic) throws Exception {
        if (question.getTopics().contains(deletedTopic)) {
            questionRepository.deleteTopicFromQuestion(question.getId(),deletedTopic.getTopicName());
            return deletedTopic.getTopicName() + " deleted!";
        } else  throw new Exception("The question does not contain topic '" + deletedTopic.getTopicName() + "'!");
    }

    // CONVERTERS
    public QuestionDto convertToDto(Question question) {
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        question.getTopics().forEach(topic -> questionDto.addTopicName(topic.getTopicName()));
        return questionDto;
    }

    public Question convertToEntity(QuestionDto questionDto) throws ParseException {
        Question question = modelMapper.map(questionDto, Question.class);
        questionDto.getTopicNameSet().forEach(topicName -> {
            try {
                addTopic(question,new Topic(topicName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // All correct answers are stored without capital letters
        question.setCorrectAnswers(question.getCorrectAnswers().toLowerCase());
        return question;
    }
}
