package com.changjr.shardingjdbcdemo.dto;

import lombok.Data;

@Data
public class CourseSelectedInfo {

  private Long cid;
  private String cname;
  private Long userId;
  private Integer status;
  private String username;
}
