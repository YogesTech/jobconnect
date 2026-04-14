package com.yogesh.jobconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobconnectApplication {

	public static void main(String[] args) {
		try {
			java.net.InetAddress addr = java.net.InetAddress
					.getByName("jobconnect-mysql-jobconnect-db.d.aivencloud.com");
			System.out.println("✅ DNS lookup success: " + addr.getHostAddress());
		} catch (Exception e) {
			System.out.println("❌ DNS lookup failed: " + e.getMessage());
		}

		SpringApplication.run(JobconnectApplication.class, args);
	}

}
