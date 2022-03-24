package com.mindhub;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mindhub.models.Loan;
import com.mindhub.repositories.LoanRepository;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(LoanRepository loanRepository) {
		return (args) -> {

			Loan hipotecario = new Loan("Hipotecario", 500000.0, List.of(12, 24, 36, 48, 60));
			Loan personal = new Loan("Personal", 100000.0, List.of(6, 12, 24));
			Loan automotriz = new Loan("Automotriz", 300000.0, List.of(6, 12, 24, 36));
			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);
		};
	}
}
