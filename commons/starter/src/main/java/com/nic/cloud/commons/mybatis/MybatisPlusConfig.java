package com.nic.cloud.commons.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/20 14:43
 */
@Slf4j
@Configuration
@MapperScan("com.nic.cloud.mapper")
public class MybatisPlusConfig {


	/**
	 * 分页插件
	 */
	@ConditionalOnMissingBean(PaginationInterceptor.class)
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
		log.info("PaginationInterceptor [{}]", paginationInterceptor);
		return paginationInterceptor;
	}

}