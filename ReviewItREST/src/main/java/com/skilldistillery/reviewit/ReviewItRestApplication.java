package com.skilldistillery.reviewit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReviewItRestApplication {
	// this banner looks incorrect here but its fine in the console
	// made with the figlet cli tool
	private static final String textBanner = """
    ____            _               ___ _   ____  _____ ____ _____ _
   |  _ \\ _____   _(_) _____      _|_ _| |_|  _ \\| ____/ ___|_   _| |
   | |_) / _ \\ \\ / / |/ _ \\ \\ /\\ / /| || __| |_) |  _| \\___ \\ | | | |
   |  _ <  __/\\ V /| |  __/\\ V  V / | || |_|  _ <| |___ ___) || | |_|
   |_| \\_\\___| \\_/ |_|\\___| \\_/\\_/ |___|\\__|_| \\_\\_____|____/ |_| (_)
							  (v1.0.1)""";

	public static void main(String[] args) {
		SpringApplication.run(ReviewItRestApplication.class, args);
		System.out.println(textBanner);
	}

}
