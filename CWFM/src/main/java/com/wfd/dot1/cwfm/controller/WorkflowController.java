package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/workflow")
public class WorkflowController {
	private static final Logger log = LoggerFactory.getLogger(WorkflowController.class.getName());
	
	@Autowired
	WorkmenService workmenService;
	@GetMapping("/list")
	public String getAllModules(HttpServletRequest request, HttpServletResponse response) {
		//Get All GeneralMaster
				List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

				// Grouping the CmsGeneralMaster objects by gmType
				Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
				        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

				// Define the types and their corresponding request attribute names
				Map<String, String> attributeMapping = Map.of(
				      				        "MODULE","Modules"
				);

				// Iterate over the attribute mappings and set the request attributes dynamically
				attributeMapping.forEach((type, attributeName) -> {
				    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
				    request.setAttribute(attributeName, gmList1);
				});

	
		return "workflow/list";
	}
}
