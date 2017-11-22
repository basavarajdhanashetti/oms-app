package com.bsd.oms.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="report_views")
public class ReportView {

	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	private String displayName;
	
	private String role;
	
	@OneToMany(mappedBy="idReportView")
	private List<ReportViewColumn> viewColumns;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<ReportViewColumn> getViewColumns() {
		return viewColumns;
	}

	public void setViewColumns(List<ReportViewColumn> viewColumns) {
		this.viewColumns = viewColumns;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
