package com.nic.cloud.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/18 9:53
 */
public class GatewayUtil {

	private static final String UNKNOWN = "unknown";
	private static final String LOCALHOST_IPV4 = "127.0.0.1";
	private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

	private static final AntPathMatcher PATH_MATCH = new AntPathMatcher();

	/**
	 * 获取请求中的 ip 地址
	 *
	 * @param request request
	 * @return IP
	 */
	public static String getIpAddress(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		String ip = headers.getFirst("x-forwarded-for");
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddress().getAddress().getHostAddress();
			// request.getRemoteAddr() 获取客户端的 IP 地址在大部分情况下都是有效的
			// 但是在通过了 Apache，Squid 等反向代理软件就不能获取到客户端的真实 IP 地址
			// 如果通过了多级反向代理的话 X-Forwarded-For 的值并不止一个
			// 而是一串 IP 值，例如：192.168.1.110,192.168.1.120,192.168.1.130,192.168.1.100
			// 其中第一个 192.168.1.110 才是用户真实的 IP
			if (LOCALHOST_IPV4.equals(ip) || LOCALHOST_IPV6.equals(ip)) {
				// 根据网卡取本机配置的 IP，而不是环回地址
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
				} catch (final UnknownHostException ignored) {
				}
			}
		}
		// 多个 IP 中取第一个
		final String ch = ",";
		if (!StringUtils.isEmpty(ip) && ip.contains(ch)) {
			ip = ip.substring(0, ip.indexOf(ch));
		}
		return ip;
	}

	public static boolean ignoreUrl(String requestPath, Set<String> ignoreUrls) {
		for (String ignoreUrl : ignoreUrls) {
			if (PATH_MATCH.match(ignoreUrl, requestPath)) {
				return true;
			}
		}
		return false;
	}

}
