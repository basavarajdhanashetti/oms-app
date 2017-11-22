package com.bsd.oms.dto;

import java.util.List;

import com.bsd.oms.entity.StockRegister;
import com.bsd.oms.entity.StoreInwardItem;

public class StockItemForm {

	public StockItemForm() {
	}

	public StockItemForm(StoreInwardItem inItem, List<StockRegister> registers) {
		this.registers = registers;
		this.inItem = inItem;
	}

	private StoreInwardItem inItem;
	private List<StockRegister> registers;

	public List<StockRegister> getRegisters() {
		return registers;
	}

	public void setRegisters(List<StockRegister> registers) {
		this.registers = registers;
	}

	public StoreInwardItem getInItem() {
		return inItem;
	}

	public void setInItem(StoreInwardItem inItem) {
		this.inItem = inItem;
	}
}
