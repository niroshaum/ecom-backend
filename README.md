# ecom-backend

Adding aws cognito implementation

Add the AWS SDK for Java and the Spring Security dependencies to your project's build file. You can do this by adding the following lines to your Gradle build file:
--
`<dependencies>
    <dependency>
        <groupId>com.amazonaws</groupId>
        <artifactId>aws-java-sdk-cognito</artifactId>
        <version>1.12.17</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
</dependencies>`


Configure your Cognito User Pool and create a new app client. Note down the User Pool ID, App Client ID, and App Client Secret.

Configure Spring Security to use Cognito as the authentication provider. You can do this by adding the following code to your Spring Security configuration file:

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Value("${cognito.pool.id}")
    private String cognitoPoolId;
    
    @Value("${cognito.client.id}")
    private String cognitoClientId;
    
    @Value("${cognito.client.secret}")
    private String cognitoClientSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(oidcUserService())
                .and()
                .and()
                .csrf().disable();
    }

    @Bean
    public ReactiveOAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        CognitoReactiveOAuth2UserService service = new CognitoReactiveOAuth2UserService(cognitoPoolId, cognitoClientId, cognitoClientSecret);
        service.setAuthoritiesMapper(authorities -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            for (String authority : authorities) {
                mappedAuthorities.add(new SimpleGrantedAuthority(authority));
            }
            return mappedAuthorities;
        });
        return service;
    }
}


This configuration enables Cognito as the authentication provider and configures Spring Security to allow access to the /api endpoint only for authenticated users. The CognitoReactiveOAuth2UserService is a custom implementation of the ReactiveOAuth2UserService interface provided by Spring Security.

Add the Cognito configuration properties to your application.properties file:

`cognito.pool.id=<your-user-pool-id>
cognito.client.id=<your-app-client-id>
cognito.client.secret=<your-app-client-secret>`
