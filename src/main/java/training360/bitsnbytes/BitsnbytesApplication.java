package training360.bitsnbytes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@SpringBootApplication
@EnableWebSecurity
@Configuration
public class BitsnbytesApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(BitsnbytesApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/", "/js/**", "/api/**").permitAll()
				.antMatchers("/api/basket","/basket.html", "/api/myorders", "/myorders.html", "/logout", "/api/products").hasRole("USER")
				.antMatchers("/users.html", "api/users","/dashboard.html", "/api/dashboard", "/adminproducts.html", "/api/products","/orders.html", "/api/orders", "/api/orders/**", "/logout", "/reports.html", "/api/reports/orders", "api/reports/products", "/api/categories", "/categories.html").hasRole("ADMIN")
				.and()
				.formLogin().loginPage("/login.html").defaultSuccessUrl("/index.html")
                    .loginProcessingUrl("/login")
				    .and()
				.logout()
					.logoutSuccessUrl("/login.html");
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource, PasswordEncoder passwordEncoder) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select username,password,enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, role from users where username = ?");
	}

}


