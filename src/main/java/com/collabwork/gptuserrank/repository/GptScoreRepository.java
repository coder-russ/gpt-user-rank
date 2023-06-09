package com.collabwork.gptuserrank.repository;

import com.collabwork.gptuserrank.entity.GptScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface GptScoreRepository extends JpaRepository<GptScoreEntity, Long> {
}
