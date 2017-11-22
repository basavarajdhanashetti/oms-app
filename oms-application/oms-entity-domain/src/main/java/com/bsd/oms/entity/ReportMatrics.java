package com.bsd.oms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="report_matrics")
public class ReportMatrics {

	@Id
	@GeneratedValue
	private long id;
	
	private long idReport;
	
	@Column(name = "matric")
	private long idMatric;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="matric", insertable= false, updatable=false)
	private ReportViewColumn matric;
	
	@Enumerated(EnumType.STRING)
	private OperationType operation;
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public long getIdReport() {
		return idReport;
	}



	public void setIdReport(long idReport) {
		this.idReport = idReport;
	}



	public ReportViewColumn getMatric() {
		return matric;
	}



	public void setMatric(ReportViewColumn matric) {
		this.matric = matric;
	}



	public OperationType getOperation() {
		return operation;
	}



	public void setOperation(OperationType operation) {
		this.operation = operation;
	}


	public long getIdMatric() {
		return idMatric;
	}



	public void setIdMatric(long idMatric) {
		this.idMatric = idMatric;
	}


	public enum OperationType{
		COUNT, SUM, AVG;
	}
}
