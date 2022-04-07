package com.changjr.shardingjdbcdemo.algorithm.complex;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm {
  
  
  @Override
  public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
    System.out.println("复合匹配策略：PreciseStrategyConfiguration");
  
    // 得到每个分片健对应的值
    Collection<Integer> status = this.getShardingValue(complexKeysShardingValue, "status");
    Collection<Long> userIdValues = this.getShardingValue(complexKeysShardingValue, "user_id");
  
    List<String> shardingSuffix = new ArrayList<>();
      for (Integer stat : status) {
        Integer suffix = stat % 2 + 1 ;
        for (Object databaseName : collection) {
          if (databaseName.toString().endsWith(suffix.toString())) {
            shardingSuffix.add(databaseName.toString());
          }
        }
    }
    return shardingSuffix;
  }
  
  
  private Collection<Integer> getShardingValue(
          ComplexKeysShardingValue<Integer> shardingValues, final String key) {
    Collection<Integer> valueSet = new ArrayList<>();
    Map<String, Collection<Integer>> columnNameAndShardingValuesMap =
            shardingValues.getColumnNameAndShardingValuesMap();
    if (columnNameAndShardingValuesMap.containsKey(key)) {
      valueSet.addAll(columnNameAndShardingValuesMap.get(key));
    }
    return valueSet;
  }
}
