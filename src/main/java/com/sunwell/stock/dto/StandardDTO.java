package com.sunwell.stock.dto;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * StandardDTO.java
 *
 * Created on Jul 20, 2017, 11:11:14 AM
 */


import java.util.HashMap;
import java.util.Map;

//import javax.ws.rs.QueryParam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Benny
 */
//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class StandardDTO 
{
    private static Map<Integer, String> errorMap = new HashMap<>();
    
    private Integer errorCode ;
    private String sessionString;
    private String token;
    private String errorMessage;
    private String companyName;
    private Long totalItems;
    private Integer totalPages;
    private Integer currentPage;
    
    public static String getErrorMessageKey(Integer _code) {
    		return errorMap.get(_code);
    }
    
    /**
     * @return the session
     */
    public String getSessionString ()
    {
        return sessionString;
    }

    /**
     * @param session the session to set
     */
    public void setSessionString (String session)
    {
        this.sessionString = session;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage ()
    {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage (String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    
    /**
     * @return the companyName
     */
    public String getCompanyName ()
    {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName (String companyName)
    {
        this.companyName = companyName;
    }
    
    /**
     * @return the errorCode
     */
    public Integer getErrorCode ()
    {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode (Integer errorCode)
    {
        this.errorCode = errorCode;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPages(Integer currentPage) {
		this.currentPage = currentPage;
	}   
}
