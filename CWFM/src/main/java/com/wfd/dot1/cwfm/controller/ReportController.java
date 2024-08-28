////package com.wfd.project.cwfm.controller;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.RequestParam;
////import org.springframework.web.bind.annotation.ResponseBody;
////
////import com.wfd.project.cwfm.dao.ReportRequestRepository;
////import com.wfd.project.cwfm.entity.ReportRequest;
////
////@Controller
////public class ReportController {
////
////	@Autowired(required = true)
////    private ReportRequestRepository reportRequestRepository;
////
////    @PostMapping("/generate-report")
////    @ResponseBody
////    public void generateReport(@RequestParam String parameters) {
////        // Enqueue the report generation request
////        ReportRequest reportRequest = new ReportRequest();
////        reportRequest.setParameters(parameters);
////        reportRequest.setStatus("waiting");
////        reportRequestRepository.save(reportRequest);
////    }
////}
////
//package com.wfd.dot1.cwfm.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.wfd.dot1.cwfm.service.ReportService;
//@Controller
//@RequestMapping("/reports")
//public class ReportController {
//
//    @Autowired
//    private ReportService reportService;
//
//    // Controller methods to handle report requests and status tracking
//
//
//
//}
//
//
//
//
