package com.example.Model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

public class TaskRequest {
    private String t_code;
    private String t_title;
    private String t_description;
    private Integer assignedto;
    private Date duedate;
    private String t_status;
    private Integer project_id;
    private Integer c_id;
    private Integer priority_id;
}
