package io.github.rrbca2022.pms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

	@RequestMapping("/medicine")
	public String medicine () {
		return "medicine";
	}

	@RequestMapping("/category")
	public String category () {
		return "category";
	}
	@RequestMapping("/purchase")
	public String purchase () {
		return "purchase";
	}
	@RequestMapping("/sales")
	public String sales () {
		return "sales";
	}
	@RequestMapping("/user")
	public String user () {
		return "user";
	}
	@RequestMapping("/supplier")
	public String supplier(){
		return "supplier";
	}
}
