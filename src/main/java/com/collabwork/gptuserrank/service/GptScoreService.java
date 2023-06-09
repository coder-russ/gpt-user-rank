package com.collabwork.gptuserrank.service;

import com.collabwork.gptuserrank.entity.GptScoreEntity;
import com.collabwork.gptuserrank.repository.GptScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GptScoreService {
    @Autowired
    GptScoreRepository gptScoreRepository;

    public void saveGptScoreEntity(GptScoreEntity gptScoreEntity) {
        gptScoreRepository.save(gptScoreEntity);
    }
}
