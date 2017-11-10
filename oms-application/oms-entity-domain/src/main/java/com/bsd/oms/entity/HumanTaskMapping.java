package com.bsd.oms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="human_task_mapping")
public class HumanTaskMapping {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String taskName;
	
	private String navigationPath;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getNavigationPath() {
		return navigationPath;
	}

	public void setNavigationPath(String navigationPath) {
		this.navigationPath = navigationPath;
	}
	
}
