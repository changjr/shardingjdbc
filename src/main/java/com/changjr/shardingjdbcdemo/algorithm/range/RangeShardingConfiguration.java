package com.changjr.shardingjdbcdemo.algorithm.range;

import cn.hutool.core.date.DateUtil;
import com.changjr.shardingjdbcdemo.algorithm.precise.PreciseStrategyConfiguration;
import com.google.common.collect.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class RangeShardingConfiguration  implements RangeShardingAlgorithm,PreciseShardingAlgorithm {
  @Override
  public Collection<String> doSharding(
      Collection collection, RangeShardingValue rangeShardingValue) {
    System.out.println("范围匹配：RangeShardingConfiguration");
    Set<String> result = new LinkedHashSet<>();
    // between and 的起始值
    Integer lower = Integer.parseInt(rangeShardingValue.getValueRange().lowerEndpoint().toString());
    Integer upper = Integer.parseInt(rangeShardingValue.getValueRange().upperEndpoint().toString());
    // 循环范围计算分库逻辑
    for (int i = lower; i <= upper; i++) {
      for (Object databaseName : collection) {
        if (databaseName.toString().endsWith(String.valueOf( i % 2 + 1))) {
          result.add(databaseName.toString());
        }
      }
    }
    return result;
  }
  
  @Override
  public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
    System.out.println("精准匹配策略：PreciseStrategyConfiguration");
  
    /**
     * tableNames 对应分片库中所有分片表的集合 shardingValue 为分片属性，其中 logicTableName 为逻辑表，columnName 分片健（字段），value
     * 为从 SQL 中解析出的分片健的值
     */
    for (Object tableName : collection) {
      /** 取模算法，分片健 % 表数量 */
      Integer value =Integer.valueOf(preciseShardingValue.getValue().toString()) % 2 + 1;
      if (tableName.toString().endsWith(String.valueOf(value))) {
        return tableName.toString();
      }
    }
    throw new IllegalArgumentException();
  }
  
}
