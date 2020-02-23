package com.sunwell.stock.dto;

import java.util.Calendar;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sunwell.stock.model.Gudang;
import com.sunwell.stock.model.OnHandStock;


public class OnHandStockDTO extends StandardDTO
{
	private Integer idItem;
	private String itemName;
	private String warehouse;
	private Calendar expiryDate ;
	private String batchNo ;
	private String serialNo ;
	private Double qty;
	
	public OnHandStockDTO() {
		
	}
	
	public OnHandStockDTO(OnHandStock _oh) {
		setData(_oh);
	}
	
	public void setData(OnHandStock _oh) {
		if(_oh.getItemName() != null)
			itemName = _oh.getItemName();
		
		if(_oh.getWarehouse() != null)
			warehouse = _oh.getWarehouse().getName();
		
		idItem = _oh.getIdItem();		
		expiryDate = _oh.getExpiryDate();
		batchNo = _oh.getBatchNo();
		serialNo = _oh.getSerialNo();
		
		qty = _oh.getQty();
	}
	
	public OnHandStock getData() {
		if(qty == null) qty = 0.0;
		return new OnHandStock( idItem, itemName, new Gudang(warehouse), qty); 
//		return new OnHandStock( new Item(item), new Gudang(warehouse), expiryDate, batchNo, serialNo, qty); 
	}
	
	public Integer getIdItem ()
	{
		return idItem;
	}
	public void setIdItem (Integer _item)
	{
		idItem = _item;
	}
	
	public String getItemName ()
	{
		return itemName;
	}
	public void setItemName (String _item)
	{
		itemName = _item;
	}
	
	public String getWarehouse ()
	{
		return warehouse;
	}
	public void setWarehouse (String _warehouse)
	{
		warehouse = _warehouse;
	}
	public double getQty ()
	{
		return qty;
	}
	public void setQty (double _qty)
	{
		qty = _qty;
	}

	public Calendar getExpiryDate ()
	{
		return expiryDate;
	}

	public void setExpiryDate (Calendar _expiryDate)
	{
		expiryDate = _expiryDate;
	}

	public String getBatchNo ()
	{
		return batchNo;
	}

	public void setBatchNo (String _batchNo)
	{
		batchNo = _batchNo;
	}

	public String getSerialNo ()
	{
		return serialNo;
	}

	public void setSerialNo (String _no)
	{
		serialNo = _no;
	}
}
