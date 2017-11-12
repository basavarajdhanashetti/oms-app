package com.bsd.oms.entity.rest;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.chart.Bar;
import com.bsd.oms.entity.Report;
import com.bsd.oms.entity.Report.Duration;
import com.bsd.oms.entity.ReportMatrics;
import com.bsd.oms.entity.ReportView;
import com.bsd.oms.repo.CustomQueryExecutor;
import com.bsd.oms.repo.ReportMatricsRepository;
import com.bsd.oms.repo.ReportRepository;
import com.bsd.oms.repo.ReportViewRepository;
import com.bsd.oms.utils.OMSDateUtil;

@RestController
public class ReportService {

	private static Logger LOG = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private ReportRepository reportRepo;

	@Autowired
	private ReportMatricsRepository reportMatricsRepo;

	@Autowired
	private ReportViewRepository viewRepo;

	@Autowired
	private CustomQueryExecutor queryExecutor;

	/**
	 * 
	 * @param report
	 * @return
	 */
	@PostMapping(path = "/users/{userId}/reports", consumes = "application/json")
	public ResponseEntity<?> createReport(@RequestBody Report report, @PathVariable String userId) {
		LOG.debug("Create new report with (" + report.toString() + " )");
		System.out.println("Create new report with (" + report.toString() + " )");
		report.setUserId(userId);
		report = reportRepo.save(report);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(report.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param report
	 * @return
	 */
	@GetMapping(path = "/users/{userId}/reports")
	public ResponseEntity<List<Report>> getAllReport(@PathVariable String userId) {
		LOG.debug("Get All report ");
		System.out.println("Get All report ");
		List<Report> lst = new ArrayList<Report>();
		for (Report report : reportRepo.getByUserId(userId)) {
			lst.add(report);
		}
		return ResponseEntity.ok(lst);
	}

	/**
	 * 
	 * @param report
	 * @return
	 */
	@PutMapping(path = "/reports/{reportId}", consumes = "application/json")
	public ResponseEntity<Report> updateReport(@PathVariable long reportId, @PathVariable String userId, @RequestBody Report report) {
		LOG.debug("Update report with (" + report.toString() + " )");

		report.setId(reportId);
		report.setUserId(userId);
		report = reportRepo.save(report);

		return ResponseEntity.ok(report);
	}

	/**
	 * 
	 * @param report
	 * @return
	 */
	@GetMapping(path = "/reports/{reportId}")
	public ResponseEntity<Report> getReport(@PathVariable long reportId) {
		LOG.debug("Get report for id " + reportId);

		Report report = reportRepo.findOne(reportId);

		return ResponseEntity.ok(report);
	}

	/**
	 * 
	 * @param report
	 * @return
	 */
	@DeleteMapping(path = "/reports/{reportId}")
	public ResponseEntity<?> deleteReport(@PathVariable long reportId) {
		LOG.debug("delete report for id " + reportId);

		reportRepo.delete(reportId);

		return ResponseEntity.noContent().build();
	}

	/**
	 * 
	 * @param report
	 * @return
	 */
	@GetMapping(path = "/reports/{reportId}/matrics")
	public ResponseEntity<List<ReportMatrics>> getAllReportMatrics(@PathVariable long reportId) {
		LOG.debug("Get All report matrics ");
		return ResponseEntity.ok(reportMatricsRepo.getByIdReport(reportId));
	}

	/**
	 * 
	 * @param report
	 * @return
	 * @throws SQLException
	 */
	@GetMapping(path = "/reports/{reportId}/content")
	public ResponseEntity<Bar> getReportContent(@PathVariable long reportId) throws SQLException {
		LOG.debug("Get All report matrics ");

		Report report = reportRepo.findOne(reportId);

		String sqlQuery = buildSQLQuery(report);

		List<Map<String, Object>> result = queryExecutor.getResult(sqlQuery, report);

		List<String> yKeys = new ArrayList<String>();
		List<String> labels = new ArrayList<String>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> row : result) {

			Map<String, Object> data = new HashMap<String, Object>();
			
			data.put(report.getXDimensionName().getColumnName(), row.get(report.getXDimensionName().getColumnName()));
			
			for (ReportMatrics matrics : report.getMatrices()) {
				data.put(matrics.getMatric().getColumnName(), row.get(matrics.getMatric().getColumnName()));

				if (!labels.contains(matrics.getMatric().getDisplayName())) {
					labels.add(matrics.getMatric().getDisplayName());
					yKeys.add(matrics.getMatric().getColumnName());
				}
			}
			dataList.add(data);
		}

		Bar bar = new Bar();
		bar.setData(dataList);
		bar.setLabels(labels);
		bar.setxKey(report.getXDimensionName().getColumnName());
		bar.setyKeys(yKeys);

		return ResponseEntity.ok(bar);
	}

	/**
	 * 
	 * @param report
	 * @return
	 */
	private String buildSQLQuery(Report report) {

		StringBuilder qry = new StringBuilder("select ");

		ReportView rpView = report.getView();

		if (report.getDuration() == Duration.MONTH || report.getDuration() == Duration.QUATER || report.getDuration() == Duration.HALFYEAR
				|| report.getDuration() == Duration.YEAR) {
			qry.append(" month(").append(report.getXDimensionName().getColumnName()).append("),");
		} else {
			qry.append(" week(").append(report.getXDimensionName().getColumnName()).append("),");
		}

		for (int i = 0; i < report.getMatrices().size();) {

			ReportMatrics matric = report.getMatrices().get(i);
			i++;

			switch (matric.getOperation()) {

			case COUNT:
				qry.append(" count(").append(matric.getMatric().getColumnName()).append(")");
				break;
			case SUM:
				qry.append(" sum(").append(matric.getMatric().getColumnName()).append(")");
				break;
			case AVG:
				qry.append(" avg(").append(matric.getMatric().getColumnName()).append(")");
				break;

			}

			if (i < report.getMatrices().size()) {
				qry.append(",");
			}
		}

		qry.append(" from ").append(report.getView().getName());

		addDateCriteria(report, qry);

		if (report.getDuration() == Duration.MONTH || report.getDuration() == Duration.QUATER || report.getDuration() == Duration.HALFYEAR
				|| report.getDuration() == Duration.YEAR) {
			qry.append("  group by  month(").append(report.getXDimensionName().getColumnName()).append(") ");
		} else {
			qry.append("  group by  week(").append(report.getXDimensionName().getColumnName()).append(") ");
		}
		return qry.toString();
	}

	/**
	 * 
	 * @param report
	 * @param qry
	 */
	private void addDateCriteria(Report report, StringBuilder qry) {
		Calendar currentDte = Calendar.getInstance();

		Calendar fromAgo = Calendar.getInstance();
		switch (report.getDuration()) {
		case WEEK:
			fromAgo.add(Calendar.DAY_OF_MONTH, -7);
			break;
		case MONTH:
			fromAgo.add(Calendar.MONTH, -1);
			break;
		case QUATER:
			fromAgo.add(Calendar.MONTH, -3);
			break;
		case HALFYEAR:
			fromAgo.add(Calendar.MONTH, -6);
			break;
		case YEAR:
			fromAgo.add(Calendar.MONTH, -12);
			break;
		}

		qry.append(" where ").append(report.getDateCriteriaColumn().getColumnName()).append(" between '")
				.append(OMSDateUtil.getDBDateTime(fromAgo.getTime())).append("' and '")
				.append(OMSDateUtil.getDBDateTime(currentDte.getTime())).append("'");
	}
}
