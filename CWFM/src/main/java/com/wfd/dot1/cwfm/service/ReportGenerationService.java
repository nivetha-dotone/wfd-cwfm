//package com.wfd.project.cwfm.service;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.wfd.project.cwfm.dao.ReportRequestRepository;
//import com.wfd.project.cwfm.entity.ReportRequest;
//
//@Service
//public class ReportGenerationService {
//
//    @Autowired(required = true)
//    private ReportRequestRepository reportRequestRepository;
//
//    @Async
//    public void processReportRequests() {
//        while (true) {
//            // Dequeue a report generation request
//            ReportRequest reportRequest = reportRequestRepository.findFirstByStatusOrderByCreatedAtAsc("waiting");
//            if (reportRequest == null) {
//                // No pending requests, sleep for some time and retry
//                try {
//                    Thread.sleep(5000); // Sleep for 5 seconds
//                } catch (InterruptedException e) {
//                    // Handle interrupted exception
//                    Thread.currentThread().interrupt();
//                }
//                continue;
//            }
//
//            // Process the report generation request
//            reportRequest.setStatus("running");
//            reportRequestRepository.save(reportRequest);
//
//            try {
//                // Generate the report (replace this with actual report generation logic)
//                generateReport(reportRequest.getParameters());
//                reportRequest.setStatus("completed");
//            } catch (Exception e) {
//                // Handle report generation failure
//                reportRequest.setStatus("failed");
//            }
//
//            reportRequestRepository.save(reportRequest);
//        }
//    }
//    private void generateReport(String parameters) {
//        // Assuming parameters contain necessary data to generate the report
//        
//        // Perform report generation logic here
//        // For example, you could create a CSV or Excel file
//        
//        // Example: Writing to a CSV file
//        try (PrintWriter writer = new PrintWriter(new FileWriter("report.csv"))) {
//            // Write header
//            writer.println("Column1,Column2,Column3");
//            
//            // Write data rows (replace this with your actual data)
//            writer.println("Data1,Data2,Data3");
//            writer.println("Data4,Data5,Data6");
//            
//            // Flush and close the writer
//            writer.flush();
//        } catch (IOException e) {
//            // Handle IOException (e.g., log error, throw exception)
//            e.printStackTrace();
//        }
//    }
//
//	/*
//	 * private void generateReport(String parameters) { // Implement report
//	 * generation logic here }
//	 */
////    public void generateReport(List<Emp> employees) {
////        // Check if the list is empty
////        if (employees == null || employees.isEmpty()) {
////            System.out.println("No data to generate report.");
////            return;
////        }
////
////        // Generate the report file (in CSV format)
////        try (PrintWriter writer = new PrintWriter(new FileWriter("report.csv"))) {
////            // Write header
////            writer.println("Name,Age,Date of Birth");
////
////            // Write data for each employee
////            for (Emp employee : employees) {
////                writer.println(employee.getFullName() + "," + employee.getEmail() + "," + employee.getDesignation());
////            }
////
////            System.out.println("Report generated successfully.");
////        } catch (IOException e) {
////            System.err.println("Error generating report: " + e.getMessage());
////        }
////    }
//}



