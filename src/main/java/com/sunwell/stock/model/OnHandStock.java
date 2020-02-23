/*
 * OnHandStock.java
 *
 * Created on September 17, 2008, 10:30 PM
 */
package com.sunwell.stock.model;

import java.io.Serializable;


import java.math.BigDecimal;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Atribut m_expiry_date dalam table database disimpan dalam tipe varchar(10) 
 * bukan date, sebab atribut ini menjadi bagian dari primary key sehingga tidak 
 * boleh null, meskipun secara programming nilainya adalah null. Untuk itu, 
 * jika tgl expire di progr adalah null, maka di database disimpan sebagai 
 * string kosong.
 * 
 * @author Irfin A 
 * @author Benny ( since versi 1.5 )
 * 
 * @version 1.0 - Sep 17 2008 ; versi awal 
 * @version 1.5 - Des 2 2014 ; tambahan atribut no batch dan no seri
 * @version 1.5.1 - Apr 27 2015 ; penyesuaian dengan jpa
 */
@Entity
@IdClass(OnHandStockPK.class)
@Table(name="onhandstock")
//@NamedQueries({
//    @NamedQuery(
//    		name = "OnHandStock.getQtyByItem", query = "SELECT SUM(o.qty) FROM OnHandStock o WHERE o.item = :item  ")   
//    , @NamedQuery(name = "OnHandStock.getQtyByItemAndWarehouse", 
//    				query = "SELECT COALESCE(SUM(o.qty),0) FROM OnHandStock o WHERE o.item = :item AND o.warehouse = :warehouse")
//    , @NamedQuery(name = "OnHandStock.getQtyByItemAndWarehouseId", 
//			query = "SELECT COALESCE(SUM(o.qty),0) FROM OnHandStock o WHERE o.item.systemId = :itemId AND o.warehouse.systemId = :warehouseId")})
public class OnHandStock implements Serializable
{
	@NotNull(message="{error_no_item}")
    @Id
    private int idItem ;
	
	@Column(name="item_name")
	private String itemName;
	
	@NotNull(message="{error_no_warehouse}")
    @Id
    @ManyToOne
    @JoinColumn(name="id_gudang")
    private Gudang warehouse = null;
	
//	@NotNull(message="{error_no_batchno}")
//    @Id
    @Column(name="batch_no")
    private String batchNo = "" ;

//	@NotNull(message="{error_no_serialno}")
//    @Id
    @Column(name="serialno")
    private String serialNo = "" ;
	
//	@NotNull(message="{error_no_expiry_date}")
//	@Id
    @Column(name="str_expiry_date")
    private String strExpiryDate = "" ;
	
//	@NotNull(message="{error_no_expiry_date}")
//    @Transient
    @Column(name="expirydate")
    private Calendar expiryDate = null;
      
//    
//    @Id
//    @Column(name="source_ref_type")
//    private int m_source_ref_type = IOnHandStockSourceRefConstants.REF_UNKNOWN;
//    
//    @Id
//    @Column(name="source_ref_id")
//    private int m_source_ref_id = 0;
    
    @Column(name="qty")
    private double qty = 0;
    
    
    public OnHandStock ()
    {
    	setExpiryDate(null);
    }
    
    public OnHandStock (int _item, Gudang _gudang, double _qty)
    {
    	idItem = _item;
    	warehouse = _gudang;
    	qty = _qty;
    }
    
    public OnHandStock (int _item, String _name, Gudang _gudang, double _qty)
    {
    	idItem = _item;
    	itemName = _name;
    	warehouse = _gudang;
    	qty = _qty;
    }
    
//    public OnHandStock (Item _item, Gudang _gudang, Calendar _expDate, String _serialNo, String _batchNo, double _qty)
//    {
//    	item = _item;
//    	warehouse = _gudang;
//		setExpiryDate(_expDate);
//    	setSerialNo(_serialNo);
//    	setBatchNo(_batchNo);
//    	qty = _qty;
//    }
    
    public int getIdItem () { return idItem; }

    public void setIdItem (int _item)
    {
        this.idItem = _item;
    }

    public Gudang getWarehouse () { return warehouse; }

    public void setWarehouse (Gudang m_warehouse)
    {
        this.warehouse = m_warehouse;
    }

    public double getQty () { return qty; }

    public void setQty (double m_qty)
    {
        this.qty = m_qty;
    }

    public Calendar getExpiryDate () { 
//    	if(expiryDate == null) {
//	    	if(strExpiryDate.length() > 0)
//	    	{
//	    		try {
//		    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-DD-mm");
//		    		expiryDate = Calendar.getInstance();
//		    		expiryDate.setTime(sdf.parse(strExpiryDate));
//	    		}
//	    		catch(ParseException e) {
//	    			e.printStackTrace();
//	        	}
//	    	}
//    	}
    	
    	return expiryDate;
    }

    public void setExpiryDate (Calendar _expiry_date)
    {
    	expiryDate = _expiry_date;
    	
    	if(expiryDate != null ) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	setStrExpiryDate(sdf.format(expiryDate.getTime()));
    	}
    	else {
    		setStrExpiryDate("");
    	}
    }
//    
//    
    public String getBatchNo () { return batchNo; }

    public void setBatchNo (String _batchNo)
    {
    	if(_batchNo == null)
    		batchNo = "";
    	else
    		batchNo = _batchNo;
    }
//    
    public String getSerialNo () { return serialNo; }

    public void setSerialNo (String _serialNo)
    {
    	if(_serialNo == null)
    		serialNo = "";
    	else
    		serialNo = _serialNo;
    }

	public String getStrExpiryDate ()
	{
		return strExpiryDate;
	}

	public void setStrExpiryDate (String _strExpiryDate)
	{
		if(_strExpiryDate == null)
			strExpiryDate = "";
		else
			strExpiryDate = _strExpiryDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}    
}
