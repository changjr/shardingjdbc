package com.changjr.shardingjdbcdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.changjr.shardingjdbcdemo.dto.CourseSelectedInfo;
import com.changjr.shardingjdbcdemo.entity.Course;
import com.changjr.shardingjdbcdemo.entity.User;
import com.changjr.shardingjdbcdemo.mapper.CourseMapper;
import com.changjr.shardingjdbcdemo.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcdemoApplicationAlgorithmTests {

  @Autowired private CourseMapper courseMapper;

  @Autowired private UserMapper userMapper;

  //利用PreciseShardingAlgorithm接口进行策略匹配
  @Test
  public void findCoursePrecise() {
    QueryWrapper<Course> wrapper = new QueryWrapper<>();
    wrapper.eq("status", 2022);
    List<Course> courses = courseMapper.selectList(wrapper);
    System.out.println(courses.toString());
  }

  // ComplexKeysShardingAlgorithm 进行复杂策略分片
  //  Complex_Table_ShardingAlgorithm类控制表
  //  ComplexShardingAlgorithm类控制数据源
  @Test
  public void findCourseComplex() {
    QueryWrapper<Course> wrapper = new QueryWrapper<>();
    wrapper.in("status", 2022);
    wrapper.in("user_id", 718521855870238721L, 718521855996067841L);
    List<Course> courses = courseMapper.selectList(wrapper);
    System.out.println(courses.toString());
  }
}
