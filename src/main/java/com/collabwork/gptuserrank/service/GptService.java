package com.collabwork.gptuserrank.service;

import com.collabwork.gptuserrank.models.AirTableResponse;
import com.collabwork.gptuserrank.models.JobRequest;
import com.collabwork.gptuserrank.models.Records;
import com.collabwork.gptuserrank.models.User;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GptService {
    @Value("${gpt.apiKey}")
    private String gptKey;
    private final OpenAiService service;

    public GptService() {
        this.service = new OpenAiService(gptKey);
    }
    public ChatCompletionResult getGptScore(List<User> users, JobRequest newJob) {
        ChatMessage prompt = new ChatMessage("user","I am going to give you a job description and a list of job applicant resumes. " +
                "I would like you to respond with the job applicants ranked in order of most qualified for the given job.");
        ChatMessage jobDescription = new ChatMessage("user", "The job description is: " + newJob.getDescription());
        ChatMessage listOfApplicantResumes = new ChatMessage("user", "The list of applicant resumes are: " + users.toString());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(List.of(prompt, jobDescription, listOfApplicantResumes))
                .model("gpt-4")
                .temperature(0.25)
                .build();
        return service.createChatCompletion(chatCompletionRequest);
    }
}
