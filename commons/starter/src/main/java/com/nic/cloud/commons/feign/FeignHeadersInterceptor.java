package com.nic.cloud.commons.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/5 16:02
 */
@Configuration
@Slf4j
public class FeignHeadersInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate requestTemplate) {
		HttpServletRequest request = getHttpServletRequest();

		if (Objects.isNull(request)) {
			return;
		}

		Map<String, String> headers = getHeaders(request);
		// 把请求过来的header请求头 原样设置到feign请求头中
		// 包括token
		headers.forEach(requestTemplate::header);
	}

	private HttpServletRequest getHttpServletRequest() {
		try {
			// 这种方式获取的HttpServletRequest是线程安全的
			return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		} catch (Exception e) {
			log.error("FeignHeadersInterceptor exception", e);
			return null;
		}
	}

	private Map<String, String> getHeaders(HttpServletRequest request) {
		Map<String, String> map = new LinkedHashMap<>();
		Enumeration<String> enums = request.getHeaderNames();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}
