package io.github.rrbca2022.pms.configuration;

import io.github.rrbca2022.pms.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final SessionInterceptor sessionInterceptor;

	public WebConfig(SessionInterceptor sessionInterceptor) {
		this.sessionInterceptor = sessionInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(
						"/css/**",
						"/js/**",
						"/images/**",
						"/favicon.ico"
				);
	}
}


