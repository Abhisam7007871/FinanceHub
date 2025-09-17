package com.project_ledger.project_ledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.project_ledger.project_ledger.repository")
@EntityScan(basePackages = "com.project_ledger.project_ledger.entity")
public class ProjectLedgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectLedgerApplication.class, args);
	}

}