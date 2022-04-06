package com.changjr.shardingjdbcdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class User {
    private Long userId;
    private String username;
    private Integer ustatus;

}
