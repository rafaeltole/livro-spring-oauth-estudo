package br.com.tole.configuracao.seguranca.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ConfiguracaoOauth2 {

    public static final String RESOURCE_ID = "books";

    @EnableAuthorizationServer
    public static class ConfiguracaoOAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
        }

        @Override
        public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
            clients
                .inMemory()
                    .withClient("cliente-app") //Base64 Y2xpZW50ZS1hcHA6MTIzNDU2
                    .secret("123456")
                    .authorizedGrantTypes("password") //suporte ao Resource Owner Password Credentials
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                .and()
                    .withClient("cliente-admin")
                    .secret("123abc")
                    .authorizedGrantTypes("client_credentials")
                    .scopes("read")
                    .resourceIds(RESOURCE_ID);
        // @formatter:on
        }
    }

    @EnableResourceServer
    public static class ConfiguracaoOAuth2ResourceServer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(final HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .authorizeRequests()
                    .anyRequest().authenticated().and()
                .requestMatchers()
                    .antMatchers("/api/v2/livros",
                                "/api/v2/admin/total_livros");
            // @formatter:on
        }

    }

}
