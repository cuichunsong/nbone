package org.nbone.web.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(value={ApplicationProperties.class})
public class TestAutoConfiguration implements InitializingBean{
	
	@Autowired
	private ApplicationProperties properties;
	
	@Autowired
	private Environment environment;
	
	/*@Autowired
	private MyProperties myProperties;*/

	private String username;
	private String password;
	

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("--------------");
		String name = environment.getProperty("spring.username");
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
