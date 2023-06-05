package com.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class ReservationApplication {



	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(ReservationApplication.class, args);

	}

}
