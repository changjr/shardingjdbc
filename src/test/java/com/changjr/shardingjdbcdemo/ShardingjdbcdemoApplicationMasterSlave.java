package com.changjr.shardingjdbcdemo;

import com.changjr.shardingjdbcdemo.entity.Log;
import com.changjr.shardingjdbcdemo.mapper.LogMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcdemoApplicationMasterSlave {
  @Autowired LogMapper logMapper;

  //  读写分离策略 insert update默认读取主库master
  @Test
  public void insert_Master() {
    Log log = new Log();
    log.setMemo("master");
    logMapper.insert(log);
    List<Log> logs1 = logMapper.selectList(null);
    System.out.println(logs1.toString());
    
  }

  // select语句默认读取从库slave
  @Test
  public void query_Slave() {
    List<Log> logs = logMapper.selectList(null);
    System.out.println(logs.toString());
    
  }
  
  @Test
  public void query_insert_Slave() {
    List<Log> logs = logMapper.selectList(null);
    System.out.println(logs.toString());
  }
}
