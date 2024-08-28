//package com.wfd.project.cwfm.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import com.wfd.project.cwfm.dao.ReportRequestRepository;
//import com.wfd.project.cwfm.entity.ReportRequest;
//
//@Controller
//public class ReportListController {
//
//    @Autowired
//    private ReportRequestRepository reportRequestRepository;
//
//    @PostMapping("/generateReport")
//    public String showReportList(Model model) {
//        List<ReportRequest> reportRequests = reportRequestRepository.findAll();
//        model.addAttribute("reportRequests", reportRequests);
//        return "reportlist";
//    }
//}


