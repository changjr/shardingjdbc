package com.changjr.shardingjdbcdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.changjr.shardingjdbcdemo.entity.Course;
import com.changjr.shardingjdbcdemo.entity.User;
import com.changjr.shardingjdbcdemo.mapper.CourseMapper;
import com.changjr.shardingjdbcdemo.mapper.UdictMapper;
import com.changjr.shardingjdbcdemo.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcdemoApplicationTests {

    //注入mapper
    @Autowired
    private CourseMapper courseMapper;

    //注入user的mapper
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UdictMapper udictMapper;


    //======================测试水平分库=====================
    //添加操作 初始化数据
    @Test
    public void addCourseDb() {
        for (long i = 0; i < 11; i++) {
            Course course = new Course();
            course.setCname("javademo1");
            //分库根据user_id
            course.setUserId(i);
            course.setCstatus("Normal1");
            courseMapper.insert(course);
        }

    }


    @Test
    public void findCourseDbByIn() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.in("cid", 718207816099168258L, 718209180791472128L, 718209213838393345L);
        List<Course> courses = courseMapper.selectList(wrapper);
        System.out.println(courses.toString());
    }


    //查询操作 分页排序 原理
    @Test
    public void findCourseDb() {
        PageHelper.startPage(10, 3);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        //设置cid值
        wrapper.eq("cstatus", "Normal1");
        wrapper.orderByAsc("cid");
//        wrapper.in("cid",718207816099168258L,718209180791472128L,718209213838393345L);
        List<Course> courses = courseMapper.selectList(wrapper);
        System.out.println(courses.toString());
    }

    //=======================测试水平分表===================
    //添加课程的方法
    @Test
    public void addCourse() {
        for (int i = 1; i <= 10; i++) {
            Course course = new Course();
            course.setCname("java" + i);
            course.setUserId(100L);
            course.setCstatus("Normal" + i);
            courseMapper.insert(course);
        }
    }

    //查询课程的方法
    @Test
    public void findCourse() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 465114666322886656L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }


    //主库新增数据
    @Test
    public void insertUser() {
        User user = new User();
        user.setUsername("新增");
        userMapper.insert(user);
    }

    //查询user
    @Test
    public void findUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 1);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }
}
