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

public class RangeShardingConfiguration implements RangeShardingAlgorithm {
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
      //      根据业务代码处理选库逻辑
      if (i > 2022 || i < 2021) {
        continue;
      }
      for (Object databaseName : collection) {
        if (databaseName.toString().endsWith(String.valueOf(i % 2 + 1))) {
          result.add(databaseName.toString());
        }
      }
    }
    return result;
  }
}
