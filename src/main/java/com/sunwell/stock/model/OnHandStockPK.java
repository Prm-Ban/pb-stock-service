/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * SalesItemPK.java
 *
 * Created on Apr 17, 2015, 11:50:33 AM
 */

package com.sunwell.stock.model;

import java.io.Serializable;

/**
 *
 * @author Benny
 */

public class OnHandStockPK implements Serializable
{
    private int idItem ;
    private int warehouse ;
    
    public OnHandStockPK() {
    	
    }
    
    public OnHandStockPK(int _item, int _warehouse) {
    	idItem = _item;
    	warehouse = _warehouse;
    }
    
    public int getIdItem ()
    {
        return idItem;
    }

    public void setIdItem (int _itemid)
    {
        this.idItem = _itemid;
    }

    public int getWarehouse ()
    {
        return warehouse;
    }

    public void setWarehouse (int _w )
    {
        this.warehouse = _w;
    }
    
    @Override
    public int hashCode ()
    {
        int hash = 11;
        hash += idItem ;
        hash += warehouse ;
        return hash;
    }

    @Override
    public boolean equals (Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OnHandStockPK)) {
            return false;
        }
        OnHandStockPK other = (OnHandStockPK) object;
        
        if (this.idItem != other.idItem)
            return false;
        if (warehouse != other.warehouse)
            return false;
        return true;
    }
}