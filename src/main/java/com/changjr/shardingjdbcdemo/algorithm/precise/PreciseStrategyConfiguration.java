package com.changjr.shardingjdbcdemo.algorithm.precise;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;

public class PreciseStrategyConfiguration implements PreciseShardingAlgorithm<Integer> {
  @Override
  public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
    System.out.println("精准匹配策略：PreciseStrategyConfiguration");

    for (String tableName : collection) {
      Integer value = preciseShardingValue.getValue() % 2 + 1;
      if (tableName.endsWith(String.valueOf(value))) {
        return tableName;
      }
    }
    throw new IllegalArgumentException();
  }
}
