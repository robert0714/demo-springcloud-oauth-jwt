package pl.piomin.services.product;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableResourceServer
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)//開啟方法級別的保護 
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(new OrRequestMatcher(
				new RegexRequestMatcher("/^/.*$",HttpMethod.GET.toString()), 
//				new AntPathRequestMatcher("/getPrinciple")
				new RegexRequestMatcher("/getPrinciple",HttpMethod.GET.toString())
			)).authorizeRequests().anyRequest().authenticated();
		
		// 配置哪些請求需要驗證
//		http.authorizeRequests().antMatchers("/getPrinciple").permitAll()
//		.anyRequest().authenticated();

	}

}
