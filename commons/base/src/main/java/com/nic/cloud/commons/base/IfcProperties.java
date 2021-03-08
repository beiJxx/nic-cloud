package com.nic.cloud.commons.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/8 9:06
 */
@Data
@ConfigurationProperties(prefix = "ifc")
public class IfcProperties {

	private Set<String> tokenIgnoreUrls;
	private Set<String> signIgnoreUrls;

	private Long tokenExpire;

}
