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
public class ShardingjdbcdemoApplicationTests {

  @Autowired private CourseMapper courseMapper;

  @Autowired private UserMapper userMapper;

  //    新增用户 按照 ustatus 分库  user_id 分表
  @Test
  public void initUser() {
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      User user = new User();
      user.setUsername("UserName_" + i);
      if (random.nextInt(10) % 2 == 0) {
        user.setUstatus(2022);
      } else {
        user.setUstatus(2021);
      }
      userMapper.insert(user);
    }
  }
  
  // 添加操作 按照人员维度 每人四门课 按照status分库 按照user_id分表
  @Test
  public void addCourseDb() {
    List<User> users = userMapper.selectList(null);
    for (User user : users) {
      for (int i = 0; i < 5; i++) {
        Course course = new Course();
        course.setCname("Course_" + user.getUstatus());
        course.setUserId(user.getUserId());
        course.setStatus(user.getUstatus());
        courseMapper.insert(course);
      }
    }
  }
  
  // 查询user 按照不同维度 按照分库的维度
  @Test
  public void findUser() {
    PageHelper.startPage(2, 10);
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("ustatus", 2021); // 指定之后根据 或锁定查询的库
    //    wrapper.in("ustatus", 2021,2022); //
    //    wrapper.eq("username","UserName_0");
    wrapper.eq("user_id", 718521855022989313L);

    List<User> users = userMapper.selectList(wrapper);
    System.out.println(users.toString());
  }
  

  //  按照分表user_id查询 user_id为奇数 只会命中course_2， 没有分库标识 会查询所有分库
  @Test
  public void findCourseDbByIn() {
    QueryWrapper<Course> wrapper = new QueryWrapper<>();
    wrapper.in("user_id", 718521855668912129L, 718521855740215297L, 718521855807324161L);
    //    wrapper.eq("status", 2021);
    List<Course> courses = courseMapper.selectList(wrapper);
    System.out.println(courses.toString());
  }

  // 查询操作 分页排序 原理
  @Test
  public void findCourseByStatus() {
    PageHelper.startPage(10, 5);
    QueryWrapper<Course> wrapper = new QueryWrapper<>();

    // 设置cid值
    wrapper.eq("status", 2022);
    wrapper.orderByAsc("cid");
    List<Course> courses = courseMapper.selectList(wrapper);
    PageInfo<Course> pageInfo = new PageInfo<>(courses);
    System.out.println(pageInfo.toString());
    System.out.println(pageInfo.getList());
  }

  // 跨库跨表 join 根据user_id 可以锁定需要查询的表
  @Test
  public void findSelectedCourseByUserId() {
    List<CourseSelectedInfo> lists = courseMapper.findSelectedCourseByUserId(718521855668912129L);
    System.out.println(lists.toString());
  }
  
  
  // 跨表join 根据status可以确定 所在的库
  @Test
  public void findSelectedCourseByStatus() {
    List<CourseSelectedInfo> lists = courseMapper.findSelectedCourseByStatus(2021);
    System.out.println(lists.toString());
  }
}
