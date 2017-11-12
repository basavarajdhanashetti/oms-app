package com.bsd.oms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "report_view_columns")
public class ReportViewColumn {

	@Id
	@GeneratedValue
	private long id;
	
	private String columnName;
	
	private String displayName;
	
	private long idReportView;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public long getIdReportView() {
		return idReportView;
	}

	public void setIdReportView(long idReportView) {
		this.idReportView = idReportView;
	}
	
}
