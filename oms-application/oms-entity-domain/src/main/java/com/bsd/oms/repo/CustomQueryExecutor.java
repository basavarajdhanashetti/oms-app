package com.bsd.oms.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.bsd.oms.entity.Report;
import com.bsd.oms.entity.ReportMatrics;
import com.bsd.oms.entity.ReportViewColumn;

@Component
public class CustomQueryExecutor {

	@Autowired
	private DataSource dataSource;

	public List<Map<String, Object>> getResult(String sqlQuery, Report report) throws SQLException {

		List<ReportViewColumn> headers = new ArrayList<ReportViewColumn>();
		headers.add(report.getXDimensionName());
		
		for(ReportMatrics matrics: report.getMatrices()){
			headers.add(matrics.getMatric());
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.query(sqlQuery, new ResultSetExtractor<List<Map<String, Object>>>() {

			@Override
			public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

				while (rs.next()) {

					Map<String, Object> map = new HashMap<String, Object>();
					for (int j = 0; j < headers.size(); j++) {
						ReportViewColumn vwColumn = headers.get(j);
						map.put(vwColumn.getColumnName(), rs.getObject(j+1));
					}
					data.add(map);

				}

				return data;
			}

		});

	}

}
