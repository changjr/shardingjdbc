package com.changjr.shardingjdbcdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changjr.shardingjdbcdemo.entity.Log;
import com.changjr.shardingjdbcdemo.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMapper extends BaseMapper<Log> {}
