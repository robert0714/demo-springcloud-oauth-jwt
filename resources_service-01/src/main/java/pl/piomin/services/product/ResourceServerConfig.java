package pl.piomin.services.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${security.oauth2.authorization.jwt.key-value}")
    private String jwtKeyValue;
//	@Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//  @Override
//  public void configure(ResourceServerSecurityConfigurer config) {
//      config.tokenServices(tokenServices());
//  }
//  @Bean
//  @Primary
//  public DefaultTokenServices tokenServices() {
//      DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//      defaultTokenServices.setTokenStore(tokenStore());
//      return defaultTokenServices;
//  }
	@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtKeyValue);
        return converter;
    }
 

//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.requestMatcher(new OrRequestMatcher(
//				new RegexRequestMatcher("/^/.*$",HttpMethod.GET.toString()), 
////				new AntPathRequestMatcher("/getPrinciple")
//				new RegexRequestMatcher("/getPrinciple",HttpMethod.GET.toString())
//			)).authorizeRequests().anyRequest().authenticated();
//		
//		// 配置哪些請求需要驗證
////		http.authorizeRequests().antMatchers("/getPrinciple").permitAll()
////		.anyRequest().authenticated();
//
//	}
	


}
