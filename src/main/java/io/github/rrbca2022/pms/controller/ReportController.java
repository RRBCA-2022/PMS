package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.dto.ReportDTO;
import io.github.rrbca2022.pms.services.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public String report(Model model){
        ReportDTO summary = reportService.generateDailyReport();
        model.addAttribute("report", summary);
        model.addAttribute("chartData", reportService.getTrendData("daily"));// This name "report" must match the HTML
        return "report";

    }
    @GetMapping("/trend")
    @ResponseBody
    public Map<String, Object> getTrend(@RequestParam(defaultValue = "daily")  String period){
        return reportService.getTrendData(period);
    }
}
