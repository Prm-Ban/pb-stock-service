package com.sunwell.stock.utils;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Util.java
 *
 * Created on Dec 6, 2017, 2:39:45 PM
 */


//import aegwyn.core.web.model.UserSession;
//
//
//
//
//
//import aegwyn.core.web.model.UserSessionContainer;
import io.jsonwebtoken.Claims;





import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunwell.stock.dto.StandardDTO;
import com.sunwell.stock.exception.OperationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 *
 * @author Benny
 */
@Service
public class ServiceUtil 
{    
	public static final int ERROR_INTERNAL_SERVER = 1001;
	public static final int ERROR_BAD_REQUEST = 1002;
	public static final int ERROR_NOT_PERMITTED = 1003;
	public static final int ERROR_NOT_ACTIVE = 1004;
	public static final int ERROR_TOKEN_INVALID = 1005;
	public static final int ERROR_TOKEN_EXPIRED = 1006;
	public static final int ERROR_MAIL_FAIL = 1007;
    
    @Autowired
    MessageSource messageSource;
    
    @Autowired
    HttpServletRequest request ;
    
    private Map<Integer, String> errorMap = new HashMap<>();
    
    
    public ServiceUtil() {
    		errorMap.put(StandardConstant.NO_ERROR, "{no_error}");
    		errorMap.put(ERROR_INTERNAL_SERVER, "error_internal_server");
    		errorMap.put(ERROR_NOT_ACTIVE, "error_not_active");
    		errorMap.put(ERROR_BAD_REQUEST, "error_bad_request");
    		errorMap.put(ERROR_NOT_PERMITTED, "error_not_permitted");
    		errorMap.put(ERROR_TOKEN_INVALID, "error_token_invalid");
    		errorMap.put(ERROR_TOKEN_EXPIRED, "error_token_expired");
    		errorMap.put(ERROR_MAIL_FAIL, "error_mail_fail");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_USER_GROUP, "error_cant_find_user_group");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_USER, "error_cant_find_user");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_PRODUCT, "error_cant_find_product");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_INVOICE, "error_cant_find_invoice");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_CUSTOMER, "error_cant_find_customer");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_PAYMENT, "error_cant_find_payment");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_CATEGORY, "error_cant_find_category");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_MERK, "error_cant_find_merk");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_METRIC, "error_cant_find_metric");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_SHIPMENT, "error_cant_find_shipment");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_SALES_ORDER, "error_cant_find_so");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_STOCK_RESERVATION, "error_cant_find_sr");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_DELIVERY_ORDER, "error_cant_find_do");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_SALES_RETURN, "error_cant_find_sales_return");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_CART_DETAIL, "error_cant_find_cart_detail");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_SALES_OFFICER, "error_cant_find_sales_officer");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_STOCK, "error_cant_find_stock");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_STOCK, "error_cant_find_stock");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_PRODUCT_IMAGE, "error_cant_find_product_image");
    		errorMap.put(StandardConstant.ERROR_CANT_FIND_PAYMENT_IMAGE, "error_cant_find_payment_image");
    		errorMap.put(StandardConstant.ERROR_NOT_ENOUGH_STOCK, "error_not_enough_sell_price_level");
    		errorMap.put(StandardConstant.ERROR_NO_CUSTOMER, "error_no_customer");
    		errorMap.put(StandardConstant.ERROR_NO_ITEM_IN_THE_CART, "error_no_item_in_the_cart");
    		errorMap.put(StandardConstant.ERROR_NOT_ENOUGH_STOCK, "error_not_enough_stock");
    		errorMap.put(StandardConstant.ERROR_NOT_ENOUGH_STOCK_FOR_ITEM, "error_not_enough_stock_for_item");
    		errorMap.put(StandardConstant.ERROR_USER_IS_NOT_ENABLED, "error_user_is_not_enabled");
    }
    
    public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) 
    {
        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
            	out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeToFile(byte[] _bytes, String uploadedFileLocation) 
    {
        try {
            OutputStream out = new FileOutputStream(new File(
                            uploadedFileLocation));
            int read = 0;

            out = new FileOutputStream(new File(uploadedFileLocation));
            out.write (_bytes);
            out.flush();
            out.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getErrorMessageFromCode(Integer _code) 
    {
    	return getErrorMessageFromCode(_code, null);
    }
    
    public String getErrorMessageFromCode(Integer _code, Object[] _args) 
    {
    	String key = errorMap.get(_code);
    	return messageSource.getMessage(key, (_args != null && _args.length > 0) ? _args : null, request.getLocale());
    }
    
    public  String getErrorMessageFromException(ConstraintViolationException _e) 
    {
    	String messages = "";
		for (ConstraintViolation v : _e.getConstraintViolations()) {
			String msg = v.getMessage();
			messages += msg + "\n";
		}
		messages = messages.substring(0, messages.length() - 1);
    	System.out.println("Error messages: " + messages);
		
    	return messages;
    }
    
    public void handleException(StandardDTO _retval, Exception _e) throws Exception 
    {
    	System.out.println("Error: " + _e.getMessage() + " class: " + _e.getClass().toString()
	    			+ " IS instanceof OperationException: " + (_e instanceof OperationException));
	    _e.printStackTrace();
	    	
	    if (_e instanceof OperationException) {
	    	OperationException ex = (OperationException) _e;
	    	_retval.setErrorCode(ex.getErrorCode());
	    	_retval.setErrorMessage(getErrorMessageFromCode(ex.getErrorCode()));
	    }
	    else if (_e instanceof ConstraintViolationException) {
	    	_retval.setErrorCode(StandardConstant.ERROR_CONSTRAINT);
	    	_retval.setErrorMessage(getErrorMessageFromException((ConstraintViolationException)_e));
    	}
    	else {
    		throw _e;
    	}
    }
    
    public String getErrorMessageFromException(Exception _e) {
    	System.out.println("Error: " + _e.getMessage() + " class: " + _e.getClass().toString()
    			+ " IS instanceof OperationException: " + (_e instanceof OperationException));
    _e.printStackTrace();
    	
	    if (_e instanceof OperationException) {
	    	OperationException ex = (OperationException) _e;
	    	return getErrorMessageFromCode(ex.getErrorCode());
	    }
	    else if (_e instanceof ConstraintViolationException) {
	    	return getErrorMessageFromException((ConstraintViolationException)_e);
		}
		else {
			return _e.getMessage();
		}
    }
    
    public Map<String,Object> handleException(Exception _e) throws Exception
    {    	
    	Map<String,Object> data = null;
	    _e.printStackTrace();
	    if (_e instanceof OperationException) {
    		OperationException ex = (OperationException) _e;
    		int errCode = ex.getErrorCode();
    		String errMessage = getErrorMessageFromCode(ex.getErrorCode());
    		data = returnErrorData(errCode,  errMessage);
    	}
	    else if (_e instanceof ConstraintViolationException) {
			String errMessage = getErrorMessageFromException((ConstraintViolationException)_e);
			data = returnErrorData(StandardConstant.ERROR_CONSTRAINT, errMessage);
		}
	    else if(_e instanceof MailException) {
	    	data = returnErrorData(ERROR_MAIL_FAIL);
	    }
		else {
			throw _e;
		}
	    return data;
	}
    
    public Map<String,Object> returnSuccessfulData(Object _object, int _totPages, long _totItems) 
    {
    	Map<String,Object> data = new HashMap<>();
    	data.put("status", "successful" );
    	data.put("totalPages", _totPages );
    	data.put("totalItems", _totItems);
    	data.put("data", _object);
//    	data.putAll(_addData);
    	return data;
    }
    
    public Map<String,Object> returnErrorData(int _errCode, String _msg)
    {
    	Map<String,Object> data = new HashMap<>();
    	data.put("status", "error" );
    	data.put("errorCode", _errCode);
    	data.put("errorMessage", _msg);
    	
    	return data;
    }
    
    public Map<String,Object> returnErrorData(int _errCode, Object[] _args)
    {
    	Map<String,Object> data = new HashMap<>();
    	data.put("status", "error" );
    	data.put("errorCode", _errCode);
    	data.put("errorMessage", getErrorMessageFromCode(_errCode, _args));
    	
    	return data;
    }
    
    public Map<String,Object> returnErrorData(int _errCode)
    {
    	Map<String,Object> data = new HashMap<>();
    	data.put("status", "error" );
    	data.put("errorCode", _errCode);
    	data.put("errorMessage", getErrorMessageFromCode(_errCode));
    	
    	return data;
    }
    
}
