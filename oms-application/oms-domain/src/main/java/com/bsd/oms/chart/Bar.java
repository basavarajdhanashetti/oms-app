package com.bsd.oms.chart;

import java.util.List;
import java.util.Map;

public class Bar {

	private List<Map<String, Object>> data;
	
	private String xKey;
	
	private List<String> yKeys;
	
	private List<String> labels;

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public String getxKey() {
		return xKey;
	}

	public void setxKey(String xKey) {
		this.xKey = xKey;
	}

	public List<String> getyKeys() {
		return yKeys;
	}

	public void setyKeys(List<String> yKeys) {
		this.yKeys = yKeys;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	
}
