package com.collabwork.gptuserrank.controller;

import com.collabwork.gptuserrank.models.JobRequest;
import com.collabwork.gptuserrank.models.Records;
import com.collabwork.gptuserrank.models.User;
import com.collabwork.gptuserrank.service.AirTableService;
import com.collabwork.gptuserrank.service.GptService;
import com.sybit.airtable.exception.AirtableException;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import org.apache.http.client.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gptuserrank")
public class GptUserRankController {
    private AirTableService airTableService;
    private GptService gptService;
    @PostMapping("/job")
    public ResponseEntity<String> createNewJob(@RequestBody JobRequest jobRequest) {
        // Process the job request and create a new job
        List<User> userRecords = null;
        try {
            userRecords = airTableService.getUsersFromAirTable();
        } catch (AirtableException | HttpResponseException e) {
            throw new RuntimeException(e);
        }
        ChatCompletionResult gptScore = gptService.getGptScore(userRecords, jobRequest);

        // Example response
        String response = "New job created successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
