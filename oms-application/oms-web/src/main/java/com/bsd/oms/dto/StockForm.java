package com.bsd.oms.dto;

import java.util.List;

public class StockForm {

	public StockForm() {
	}

	public StockForm(List<StockItemForm> stockItems) {
		this.setStockItems(stockItems);
	}

	public List<StockItemForm> getStockItems() {
		return stockItems;
	}

	public void setStockItems(List<StockItemForm> stockItems) {
		this.stockItems = stockItems;
	}

	private List<StockItemForm> stockItems;
	
}
