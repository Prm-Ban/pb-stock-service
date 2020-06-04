/*
 * @File : Gudang.java	@Date : Jan 5, 2007, 12:07:04 AM
 *
 * Copyright 2007 Latifolia Technologies, PT. All Rights Reserved.
 * LATIFOLIA PROPRIETARY/CONFIDENTIAL.
 */
package com.sunwell.stock.model;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name="gudang")
@NamedQueries ({
    @NamedQuery (name="Gudang.findDefaultWarehouse", query="SELECT g FROM Gudang g WHERE g.systemId=1")
})
public class Gudang 
{
    /** PRIMARY KEY ; auto-increment */
    @Id
    @SequenceGenerator (name = "gudang_id_gudang_seq", sequenceName = "gudang_id_gudang_seq", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "gudang_id_gudang_seq" )
    @Column(name = "id_gudang")
    private int systemId;
    
	@NotNull(message="{error_no_name}")
    @Column(name = "name", unique = true)
    private String name;      // UNIQUE
    
    @Column(name = "addr")
    private String address;
    
    @Column(name = "memo")
    private String memo;
    
    @Column(name = "isactive")
    private Boolean isActive;
    
    /**
     * Menandakan apakah gudang sbg pilihan default saat input data.
     *
     * True = default False = not default
     */
    @Column(name = "\"default\"")
    private boolean m_default;

    /**
     * @roseuid 45C7F9470198
     */
    public Gudang() 
    {
        address = "";
        memo = "";
    }
    
    public Gudang(int _id) {
    	systemId = _id;
    }
    
    public Gudang(String _name) {
    	name = _name;
    }

    public Gudang(String _name, String _addr, String _memo, boolean _isDefault)
    {
        name = _name;
        address = _addr;
        memo = _memo;
        m_default = _isDefault;
    }

    public int getSystemId() 
    {
        return systemId;
    }

    public void setSystemId(int m_id_gudang) 
    {
        this.systemId = m_id_gudang;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String m_name)
    {
        this.name = m_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String m_addr) {
        this.address = m_addr;
    }

    public String getDesc() {
        return memo;
    }

    public void setDesc(String _memo) {
        this.memo = _memo;
    }

    public boolean isDefault() {
        return m_default;
    }

    public void setDefault(boolean m_default) {
        this.m_default = m_default;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean _isActive) {
        this.isActive = _isActive;
    }

    @Override
    public String toString ()
    {
        return name;
    }
    
    @Override
    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        if (getClass () != obj.getClass ())
            return false;
        
        final Gudang other = (Gudang) obj;
        if (this.systemId != other.systemId)
            return false;
        
        return true;
    }

    @Override
    public int hashCode ()
    {
        return systemId;
    }

}
