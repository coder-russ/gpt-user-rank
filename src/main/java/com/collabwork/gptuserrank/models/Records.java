package com.collabwork.gptuserrank.models;

import lombok.Data;

@Data
public class Records {
    private String id;
    private String createdTime;

    private User fields;

    private Number commentCount;

}
