package com.changjr.shardingjdbcdemo.mapper;

import com.changjr.shardingjdbcdemo.dto.CourseSelectedInfo;
import com.changjr.shardingjdbcdemo.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper extends BaseMapper<Course> {
  
  @Select("<script>select c.*,u.username from course c , user u  where c.user_id = u.user_id and u.user_id =#{userId}</script>")
  List<CourseSelectedInfo> findSelectedCourseByUserId(Long userId);
  
  @Select("<script>select c.*,u.username from course c , user u  where c.user_id = u.user_id and c.status =#{status}</script>")
  List<CourseSelectedInfo> findSelectedCourseByStatus(int status);
}
