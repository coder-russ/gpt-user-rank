package com.collabwork.gptuserrank.entity;

import com.collabwork.gptuserrank.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="GPTSCORE")
@Data
public class GptScoreEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="COMPANY", length=50, nullable=false)
    private String company;

    @Column(name="JOB_DESCRIPTION", length=50)
    private String jobDescription;

    @Column(name="RANKED_APPLICANTS")
    private List<User> rankedApplicants;
}
