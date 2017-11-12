package com.bsd.oms.entity.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.entity.ReportView;
import com.bsd.oms.repo.ReportViewRepository;


@RestController("/views")
public class ReportViewService {

	private static Logger LOG = LoggerFactory.getLogger(ReportViewService.class);

	@Autowired
	private ReportViewRepository reportViewRepo;
	
	/**
	 * 
	 * @param reportView
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createReport(@RequestBody ReportView reportView) {
		LOG.debug("Create new reportView with (" + reportView.toString() + " )");
		System.out.println("Create new reportView with (" + reportView.toString() + " )");
		reportView = reportViewRepo.save(reportView);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(reportView.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param reportView
	 * @return
	 */
	@PutMapping(path = "/{reportViewId}", consumes = "application/json")
	public ResponseEntity<ReportView> updateReportView(@PathVariable long reportViewId, @RequestBody ReportView reportView) {
		LOG.debug("Update reportView with (" + reportView.toString() + " )");

		reportView.setId(reportViewId);
		reportView = reportViewRepo.save(reportView);

		return ResponseEntity.ok(reportView);
	}

	/**
	 * 
	 * @param reportView
	 * @return
	 */
	@GetMapping(path = "/{reportViewId}")
	public ResponseEntity<ReportView> getReportView(@PathVariable long reportViewId) {
		LOG.debug("Get reportView for id " + reportViewId);

		ReportView reportView = reportViewRepo.findOne(reportViewId);

		return ResponseEntity.ok(reportView);
	}
	
	/**
	 * 
	 * @param reportView
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<ReportView> getReportViewByName(@RequestParam String viewName) {
		LOG.debug("Get reportView for name " + viewName);

		ReportView reportView = reportViewRepo.getByName(viewName);

		return ResponseEntity.ok(reportView);
	}

	/**
	 * 
	 * @param reportView
	 * @return
	 */
	@DeleteMapping(path = "/{reportViewId}")
	public ResponseEntity<?> deleteReportView(@PathVariable long reportViewId) {
		LOG.debug("delete reportView for id " + reportViewId);

		reportViewRepo.delete(reportViewId);

		return ResponseEntity.noContent().build();
	}


}
