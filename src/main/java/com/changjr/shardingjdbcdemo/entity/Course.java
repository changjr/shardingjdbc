package com.changjr.shardingjdbcdemo.entity;

import lombok.Data;

@Data
public class Course {
    private Long cid;
    private String cname;
    private Long userId;
    private Integer status;
}
