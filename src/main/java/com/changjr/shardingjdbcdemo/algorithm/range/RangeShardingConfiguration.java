package com.changjr.shardingjdbcdemo.algorithm.range;

import cn.hutool.core.date.DateUtil;
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

public class RangeShardingConfiguration
    implements RangeShardingAlgorithm<Integer> {
  
  
  @Override
  public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Integer> rangeShardingValue) {
    System.out.println("范围匹配：RangeShardingConfiguration");
    Set<String> result = new LinkedHashSet<>();
    // between and 的起始值
    int lower = rangeShardingValue.getValueRange().lowerEndpoint();
    int upper = rangeShardingValue.getValueRange().upperEndpoint();
    // 循环范围计算分库逻辑
    for (int i = lower; i <= upper; i++) {
      for (String databaseName : collection) {
        if (databaseName.endsWith(i % collection.size() + "")) {
          result.add(databaseName);
        }
      }
    }
    return result;
  }
}
