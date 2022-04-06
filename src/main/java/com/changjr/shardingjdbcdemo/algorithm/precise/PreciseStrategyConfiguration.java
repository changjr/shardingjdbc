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
  public String doSharding(
      Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
    System.out.println("PreciseStrategyConfiguration````````````````````````````````````");
    System.out.println("精准匹配策略：PreciseStrategyConfiguration");

    /**
     * tableNames 对应分片库中所有分片表的集合 shardingValue 为分片属性，其中 logicTableName 为逻辑表，columnName 分片健（字段），value
     * 为从 SQL 中解析出的分片健的值
     */
    for (String tableName : collection) {
      /** 取模算法，分片健 % 表数量 */
      Integer value = preciseShardingValue.getValue() % 2 + 1;
      if (tableName.endsWith(String.valueOf(value))) {
        return tableName;
      }
    }
    throw new IllegalArgumentException();
  }
}
