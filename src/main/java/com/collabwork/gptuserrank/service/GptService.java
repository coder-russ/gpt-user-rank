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
    public String getGptScore(List<User> users, JobRequest newJob) {
        ChatMessage prompt = new ChatMessage("user","I am going to give you a job description and a list of job applicant resumes. " +
                "I would like you to respond with the job applicants ranked in order of most qualified for the given job.");
        ChatMessage jobDescription = new ChatMessage("user", "The job description is: " + newJob.getDescription());
        ChatMessage listOfApplicantResumes = new ChatMessage("user", "The list of applicant resumes are: " + users.toString());
        ChatMessage assistantExample = new ChatMessage("assistant", "Name, Name, Name, Name");
        ChatMessage systemPrompt = new ChatMessage("system", "You are an expert at comparing resumes to job descriptions and ranking them in order of most qualified to least qualified. " +
                "You only reply with Names ranked with a comma delimiter. You will always rank each candidate even if they are not qualified for the job description. You will not provide any explanation for your rankings. " +
                "You will not put a number in front of each name. You will always put a comma between each name. You will never reply with more than 10 names.");

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(List.of(prompt, jobDescription, listOfApplicantResumes, assistantExample))
                .model("gpt-3.5-turbo")
                .temperature(0.25)
                .build();
        return service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();
    }
}
