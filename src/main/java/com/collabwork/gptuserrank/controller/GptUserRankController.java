package com.collabwork.gptuserrank.controller;

import com.collabwork.gptuserrank.entity.GptScoreEntity;
import com.collabwork.gptuserrank.models.JobRequest;
import com.collabwork.gptuserrank.models.Records;
import com.collabwork.gptuserrank.models.User;
import com.collabwork.gptuserrank.service.AirTableService;
import com.collabwork.gptuserrank.service.GptScoreService;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gptuserrank")
public class GptUserRankController {
    private AirTableService airTableService;
    private GptService gptService;
    private GptScoreService gptScoreService;
    @PostMapping("/job")
    public ResponseEntity<String> createNewJob(@RequestBody JobRequest jobRequest) {
        List<User> userRecords = null;
        try {
            userRecords = airTableService.getUsersFromAirTable();
        } catch (AirtableException | HttpResponseException e) {
            throw new RuntimeException(e);
        }
        String[] gptRankings = gptService.getGptScore(userRecords, jobRequest).split(",");
        List<String> gptRankingsList = Arrays.stream(gptRankings).map(String::toLowerCase).toList();
        List<User> rankedUsers= userRecords.stream().filter(user -> gptRankingsList.contains(user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase())).toList();
        GptScoreEntity newEntity = GptScoreEntity.builder().jobDescription(jobRequest.getDescription()).company(jobRequest.getName()).rankedApplicants(rankedUsers).build();
        gptScoreService.saveGptScoreEntity(newEntity);

        // Example response
        String response = "New job created successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
