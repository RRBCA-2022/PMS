package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.dto.ReportDTO;
import io.github.rrbca2022.pms.services.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public String report(Model model){
        ReportDTO summary = reportService.generateDailyReport();
        model.addAttribute("report", summary);
        model.addAttribute("chartData", reportService.getWeeklySalesData());// This name "report" must match the HTML
        return "report";

    }
}
