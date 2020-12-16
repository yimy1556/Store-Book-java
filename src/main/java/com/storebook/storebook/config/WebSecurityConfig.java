package com.storebook.storebook.config;
import com.storebook.storebook.entity.Admin;
import com.storebook.storebook.entity.Customer;
import com.storebook.storebook.service.AdminService;
import com.storebook.storebook.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    CustomerService customerService;

    @Autowired
    AdminService adminService;

    private User newUser(String email, String password, String... roles){
        return new User(email, password, AuthorityUtils.createAuthorityList(roles));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public  void  init(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(inputName -> {
            Admin admin = adminService.findByEmail(inputName);
            Customer customer = customerService.findByEmail(inputName);

            if (admin == null && customer == null)
                throw new UsernameNotFoundException("Unknown: " + inputName);

            if (admin != null)
                return newUser(admin.getEmail(), admin.getPassword(), "ADMIN");

            return  newUser(customer.getEmail(), customer.getPassword(),"CUSTOMER");
        }).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/authors", "/api/authors/**/books").hasRole("SUPERADMIN")
                .antMatchers(HttpMethod.POST, "/api/stores", "/api/stores/**/books/**").hasAnyAuthority("SUPERADMIN", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/stores/**/books/**").hasAnyAuthority("SUPERADMIN", "ADMIN")
                .antMatchers("/api/customers/**/books", "/api/customers/**").hasAnyAuthority("CUSTOMER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        httpSecurity.logout().logoutUrl("/api/logout");

        httpSecurity.csrf().disable();
    }
}
