package com.Gym.Star.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gym.Star.Entities.UrlMapping;

public interface MappingRepo extends JpaRepository<UrlMapping, Integer> {
	Optional<UrlMapping> findByShortUrl(String shortUrl);

	boolean existsByShortUrl(String shortUrl);

	Optional<UrlMapping> findByLongUrl(String longUrl);
}
