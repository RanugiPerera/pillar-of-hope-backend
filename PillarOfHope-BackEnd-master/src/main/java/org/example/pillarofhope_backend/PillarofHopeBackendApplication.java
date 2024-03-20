package org.example.pillarofhope_backend;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class PillarofHopeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PillarofHopeBackendApplication.class, args);

	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
