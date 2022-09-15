package com.example.demo;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.Role;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootApplication
public class SareetaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SareetaApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner initDB(UserService userService, ItemRepository repository){
		return  args -> {
			userService.saveRole(new Role(1L, "ROLE_USER"));
			userService.saveRole(new Role(2L, "ROLE_ADMIN"));
			userService.saveRole(new Role(3L, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new AppUser(null, "Kris Cer", "kris1", "1223", new ArrayList<>(), null));
			userService.saveUser(new AppUser(null, "Anna Holmes", "anna", "1223", new ArrayList<>(), null));
			userService.saveUser(new AppUser(null, "John West", "john1", "1223", new ArrayList<>(), null));
			userService.saveUser(new AppUser(null, "Ali Johannes", "ali1", "1223", new ArrayList<>(), null));

			userService.addRoleToUser("kris1", "ROLE_ADMIN");
			userService.addRoleToUser("anna", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("john1", "ROLE_USER");
			userService.addRoleToUser("ali1", "ROLE_USER");

			repository.save(new Item(1L, "Round Widget",  BigDecimal.valueOf(2.99),"A widget that is round"));
			repository.save(new Item(2L, "Square Widget",  BigDecimal.valueOf(1.99),"A widget that is square"));


		};
	}
}
