package com.ss.core.config.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * hash 算法分表策略 （针对于雪花算法尾数为总数偶数的情况）
 * @author zhangxk
 *
 */
@Slf4j
public class  HashPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
	
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
		Long id = shardingValue.getValue();
		int hashCode = hash(id);
		List<String> strings = new ArrayList<>(availableTargetNames);
		// 2 幂次方 分库分表
		return strings.get(hashCode & (availableTargetNames.size() - 1));
	}


	static int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

}



