package com.example.hospital;
import com.example.hospital.entities.Patient;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.security.service.AccountService;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args)
    {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //patientRepository.save(new Patient(null,"Mohamed",new Date(),false,340));
        //patientRepository.save(new Patient(null,"Hanane",new Date(),false,4321));
        //patientRepository.save(new Patient(null,"Imane",new Date(),true,334));

    }
    //@Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(javax.sql.DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder=passwordEncoder();
        return args -> {
            if (!jdbcUserDetailsManager.userExists("user11")) {
                jdbcUserDetailsManager.createUser(User.withUsername("user11")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER").build());
            }

            if (!jdbcUserDetailsManager.userExists("user22")) {
                jdbcUserDetailsManager.createUser(User.withUsername("user22")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER").build());
            }

            if (!jdbcUserDetailsManager.userExists("admin2")) {
                jdbcUserDetailsManager.createUser(User.withUsername("admin2")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER", "ADMIN").build());
            }

        };
    }
    @Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");

            if (accountService.loadUserByUsername("user1") == null)
                accountService.addNewUser("user1", "1234", "user1@gmail.com", "1234");

            if (accountService.loadUserByUsername("user2") == null)
                accountService.addNewUser("user2", "1234", "user2@gmail.com", "1234");

            if (accountService.loadUserByUsername("admin") == null)
                accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");

            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("user2", "USER");
            accountService.addRoleToUser("admin", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
        };
    }

    @Bean
    PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
