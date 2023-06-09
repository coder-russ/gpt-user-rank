package com.collabwork.gptuserrank.service;

import com.collabwork.gptuserrank.models.User;
import com.sybit.airtable.Airtable;
import com.sybit.airtable.Base;
import com.sybit.airtable.Table;
import com.sybit.airtable.exception.AirtableException;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirTableService {
    @Value("${airtable.apiKey}")
    private String apiKey;
    @Value("${airtable.baseId}")
    private String baseId;
    @Value("${airtable.tableName}")
    private String tableName;
    private Airtable airtable;
    public AirTableService() {
        airtable = new Airtable();
    }

    public List<User> getUsersFromAirTable() throws AirtableException, HttpResponseException {
        airtable.configure(apiKey);
        Base base = airtable.base(baseId);
        Table<User> userTable = base.table(tableName, User.class);
        return userTable.select();
    }
}
