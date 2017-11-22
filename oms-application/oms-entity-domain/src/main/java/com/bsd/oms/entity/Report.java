package com.bsd.oms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="reports")
public class Report {

	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	@Column(name="id_view")
	private long idView;
	
	@ManyToOne
	@JoinColumn(name="id_view", updatable=false, insertable=false)
	private ReportView view;
	
	private String userId;
	
	@Enumerated(EnumType.STRING)
	private ChartType chartType;
	
	@ManyToOne
	@JoinColumn(name="x_dimension_name")
	private ReportViewColumn xDimensionName;
	
	@Enumerated(EnumType.STRING)
	private Duration duration;
	
	@ManyToOne()
	@JoinColumn(name="date_criteria_column")
	private ReportViewColumn dateCriteriaColumn;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="idReport")
	private List<ReportMatrics> matrices;
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}

	public ReportViewColumn getXDimensionName() {
		return xDimensionName;
	}

	public void setXDimensionName(ReportViewColumn xDimensionName) {
		this.xDimensionName = xDimensionName;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public List<ReportMatrics> getMatrices() {
		return matrices;
	}

	public void setMatrices(List<ReportMatrics> matrices) {
		this.matrices = matrices;
	}

	public ReportViewColumn getDateCriteriaColumn() {
		return dateCriteriaColumn;
	}

	public void setDateCriteriaColumn(ReportViewColumn dateCriteriaColumn) {
		this.dateCriteriaColumn = dateCriteriaColumn;
	}

	public ReportView getView() {
		return view;
	}

	public void setView(ReportView view) {
		this.view = view;
	}

	public long getIdView() {
		return idView;
	}

	public void setIdView(long idView) {
		this.idView = idView;
	}

	public enum ChartType {
		BarChart, AreaChart, DonutChart;
		
		public String toString(){
			return this.name();
		}
	}
	
	public enum Duration {
		WEEK, MONTH, QUATER, HALFYEAR, YEAR;
		public String toString(){
			return this.name();
		}
	}
}
