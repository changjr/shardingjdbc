package com.changjr.shardingjdbcdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_log")
@Data
public class Log {
  @TableId
  private Long id;
  private String memo;
}
