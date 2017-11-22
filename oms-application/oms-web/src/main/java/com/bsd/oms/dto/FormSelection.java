package com.bsd.oms.dto;

public class FormSelection {

	private long id;
	
	private long[] selectedIds;
	
	private String comments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(long[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
