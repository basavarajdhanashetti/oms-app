package com.bsd.oms.dashboard;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardAPI {

	@SuppressWarnings("unchecked")
	@GetMapping("/sale/product")
	public ResponseEntity<?> productWiseSale(Map<String, Object> model){
		try {
			 return new ResponseEntity(ReadWriteExcelFile.reportPieData(ReportType.PRODUCT), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/sale/year")
	public ResponseEntity<?> dateWiseSale(Map<String, Object> model){
		try {
			 return new ResponseEntity(ReadWriteExcelFile.reportBarData(ReportType.YEAR), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
