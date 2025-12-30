package io.github.rrbca2022.pms.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
	// GET endpoint
	@GetMapping("/hello")
	public String hello() {
		return "Hello, Spring Boot!";
	}

	@GetMapping("/greet/{name}")
	public String greet(@PathVariable String name) {
		return "Hello, " + name + "!";
	}

	// POST endpoint
	@PostMapping("/echo")
	public String echo(@RequestBody String message) {
		return "You said: " + message;
	}
}
