package com.Gym.Star.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Gym.Star.Entities.UrlMapping;
import com.Gym.Star.Service.MappingRepoImpl;

@Controller
@RequestMapping("")
public class MyController {

	@Autowired
	private MappingRepoImpl impl;
	
	@GetMapping("/api")
	public String home() {
		return "index";
	}

	@PostMapping("/shorten")
	public String shortenUrl(@RequestParam("longUrl") String longUrl,Model model) {
	    System.out.println(longUrl);
	    model.addAttribute("sortUrl","http://localhost:9191/"+impl.shortenUrl(longUrl));
	    return "index";
	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
		 String longUrl = impl.redirectToLongUrl(shortUrl);
		 if (longUrl != null) {
	            // Perform a 302 (temporary) redirection to the original long URL
	            HttpHeaders headers = new HttpHeaders();
	            headers.add("Location", longUrl);
	            return new ResponseEntity<>(headers, HttpStatus.FOUND);
	        } else {
	            // Handle the case where the short URL doesn't exist in your system
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	
	}
}
