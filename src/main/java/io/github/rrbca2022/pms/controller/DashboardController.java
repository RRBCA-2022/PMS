package io.github.rrbca2022.pms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@RequestMapping("/")
	public String dashboard() {
		return "dashboard";
	}

	@RequestMapping("/medicine")
	public String medicine () {
		return "medicine";
	}

}
