package com.hanchun.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.hanchun.listener.JobListener;

@Configuration
@ConditionalOnExpression("'${elastic.zookeeper.server-lists}'.length() >0")
public class ElasticJobConfig {
	
	/**
     * 初始化配置
     * @param serverList
     * @param namespace
     * @return
     */
	@Bean(initMethod = "init")
	public ZookeeperRegistryCenter regCenter(@Value("${elaticjob.zookeeper.server-lists}") final String serverList,
			@Value("${elaticjob.zookeeper.namespace}") final String namespace) {
		return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
	}
	
	/**
     * 设置活动监听，前提是已经设置好了监听，见下一个目录
     * @return
     */
    @Bean
    public ElasticJobListener elasticJobListener() {
		return new JobListener(100, 100);
    }
}