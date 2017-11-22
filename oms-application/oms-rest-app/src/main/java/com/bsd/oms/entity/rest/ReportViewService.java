package com.bsd.oms.entity.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.entity.ReportView;
import com.bsd.oms.entity.ReportViewColumn;
import com.bsd.oms.repo.ReportViewColumnRepository;
import com.bsd.oms.repo.ReportViewRepository;

@RestController()
@RequestMapping(path = "/reportviews")
public class ReportViewService {

	private static Logger LOG = LoggerFactory.getLogger(ReportViewService.class);

	@Autowired
	private ReportViewRepository reportViewRepo;

	@Autowired
	private ReportViewColumnRepository reportViewColumnRepo;

	/**
	 * 
	 * @param reportView
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<ReportView>> getReportViews() {
		LOG.debug("Get all reportView");

		List<ReportView> lst = new ArrayList<ReportView>();
		for (ReportView reportView : reportViewRepo.findAll()) {
			lst.add(reportView);
		}
		return ResponseEntity.ok(lst);
	}

	/**
	 * 
	 * @param reportView
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createReport(@RequestBody ReportView reportView) {
		LOG.debug("Create new reportView with (" + reportView.toString() + " )");
		System.out.println("Create new reportView with (" + reportView.toString() + " )");
		ReportView reportViewTmp = reportViewRepo.save(reportView);

		for (ReportViewColumn viewColumn : reportView.getViewColumns()) {
			viewColumn.setIdReportView(reportViewTmp.getId());
			reportViewColumnRepo.save(viewColumn);
		}

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
		ReportView reportViewTmp = reportViewRepo.save(reportView);

		for (ReportViewColumn viewColumn : reportView.getViewColumns()) {
			if (viewColumn.getIdReportView() == 0) {
				reportViewColumnRepo.delete(viewColumn.getId());
			} else {
				viewColumn.setIdReportView(reportViewTmp.getId());
				reportViewColumnRepo.save(viewColumn);
			}
		}

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
	@DeleteMapping(path = "/{reportViewId}")
	public ResponseEntity<?> deleteReportView(@PathVariable long reportViewId) {
		LOG.debug("delete reportView for id " + reportViewId);

		reportViewRepo.delete(reportViewId);

		return ResponseEntity.noContent().build();
	}

}
