package com.Gym.Star.Service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gym.Star.Entities.UrlMapping;
import com.Gym.Star.Repo.MappingRepo;

@Service
public class MappingRepoImpl {
	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int SHORT_URL_LENGTH = 6; // Length of the short URL

	@Autowired
	private MappingRepo repo;

	public String shortenUrl(String longUrl) {
		// Check if the long URL already exists in the database
		Optional<UrlMapping> existingMapping = repo.findByLongUrl(longUrl);
		if (existingMapping.isPresent()) {
			return existingMapping.get().getShortUrl(); // Return the existing mapping
		}

		// Generate a unique short URL
		String shortUrl = generateUniqueShortUrl();

		// Create a new mapping and save it to the database
		UrlMapping urlMapping = new UrlMapping();
		urlMapping.setLongUrl(longUrl);
		urlMapping.setShortUrl(shortUrl);
		repo.save(urlMapping);

		return urlMapping.getShortUrl();
	}

	public String redirectToLongUrl(String shortUrl) {
		// Retrieve the original long URL from the database
		Optional<UrlMapping> urlMapping = repo.findByShortUrl(shortUrl);
		if (urlMapping.isPresent()) {
			return urlMapping.get().getLongUrl();
		} else {
			return "URL not found"; // Handle when the short URL does not exist
		}
	}

	private String generateUniqueShortUrl() {
		String generatedUrl;
		do {
			generatedUrl = generateRandomShortUrl();
		} while (repo.existsByShortUrl(generatedUrl));
		return generatedUrl;
	}

	private String generateRandomShortUrl() {
		StringBuilder shortUrl = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < SHORT_URL_LENGTH; i++) {
			char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
			shortUrl.append(randomChar);
		}
		return shortUrl.toString();
	}

}
