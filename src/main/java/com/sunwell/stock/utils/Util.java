/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Util.java
 *
 * Created on Nov 8, 2017, 3:38:25 PM
 */

package com.sunwell.stock.utils;

//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Benny
 */

public class Util 
{
	
	public static final String TYPE_CALENDAR = "Calendar";
	public static final String TYPE_STRING = "String";
	public static final String TYPE_BL = "boolean";
	public static final String TYPE_BOOLEAN = "Boolean";
	public static final String TYPE_INT = "int";
	public static final String TYPE_INTEGER = "Integer";
	public static final String TYPE_DB = "double";
	public static final String TYPE_DOUBLE = "Double";
	public static final String TYPE_UNKNOWN = "Unknown";
	public static final String TYPE_NESTED = "Nested";
	
//    public static Map<String, Object> objToMap(Object _obj) {
//        ObjectMapper oMapper = new ObjectMapper();
//        oMapper.setSerializationInclusion (JsonInclude.Include.NON_NULL);
//        Map<String, Object> map = oMapper.convertValue(_obj, Map.class);
//        System.out.println(map);
//        return map;
//    }
    
    public static String getHomePath() {
        String homePath = System.getProperty("user.home") + "/"; 
        return homePath;
    }
    
    public static void writeToFile(byte[] _bytes, String uploadedFileLocation) {

        try(OutputStream out = new FileOutputStream(new File(uploadedFileLocation))) {
            int read = 0;

            out.write (_bytes);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static <T> String findType(Class<T> _clazz, String _key) throws Exception {
		System.out.println("findType called");
		String[] keys = null;
		Class c = _clazz;
		if(_key.contains("_")) {
			keys = _key.split("_");
			System.out.println("LOOKING FOR NESTED TYPE, size: " + keys.length);
//			for(int i = 0 ; i < keys.length ; i++) {
//				System.out.println("K at " + i + " " + keys[i]);
//			}
		}
		else {
			System.out.println("LOOKING FOR TYPE");
			keys = new String[1];
			keys[0] = _key;
		}
		
		for(String k : keys) {
//			System.out.println("K: " + k);
			String key = k.substring(0, 1).toUpperCase() + k.substring(1);
			key = "get" + key;
			System.out.println("KEY METHOD: " + key);
			Method method = c.getMethod(key);
			c = method.getReturnType();
			System.out.println("CLASS NAME: " + c.getName());
		}
		
		String name = c.getName();
		String[] names = name.split("\\.");
		name = names[names.length -1];
		System.out.println("Field is: " + name);
		return name;
	}
    
    public static String getRandomString(int _length) {
		  
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(_length);
	    for (int i = 0; i < _length; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	    System.out.println(generatedString);
	    return generatedString;
	}
    
    public static Map<String,Object> returnSuccessfulData(Object _object, int _totPages, long _totItems) 
    {
    	Map<String,Object> data = new HashMap<>();
    	data.put("status", "successful" );
    	data.put("totalPages", _totPages );
    	data.put("totalItems", _totItems);
    	data.put("data", _object);
//    	data.putAll(_addData);
    	return data;
    }
    
    public static Map<String,Object> returnErrorData(int _errCode, String _msg)
    {
    	Map<String,Object> data = new HashMap<>();
    	data.put("status", "error" );
    	data.put("errorCode", _errCode);
    	data.put("errorMessage", _msg);
    	
    	return data;
    }
    
//    public Map<String,Object> returnErrorData(int _errCode, Object[] _args)
//    {
//    	Map<String,Object> data = new HashMap<>();
//    	data.put("status", "error" );
//    	data.put("errorCode", _errCode);
//    	data.put("errorMessage", getErrorMessageFromCode(_errCode, _args));
//    	
//    	return data;
//    }
//    
//    public static Map<String,Object> returnErrorData(int _errCode)
//    {
//    	Map<String,Object> data = new HashMap<>();
//    	data.put("status", "error" );
//    	data.put("errorCode", _errCode);
//    	data.put("errorMessage", getErrorMessageFromCode(_errCode));
//    	
//    	return data;
//    }
    
//    public static <T> String findType(Class<T> _clazz, String _key) throws Exception {
//		System.out.println("findType called");
//		String key = _key.substring(0, 1).toUpperCase() + _key.substring(1);
//		key = "get" + key;
//		Method k = _clazz.getMethod(key);
//		Class c = k.getReturnType();
//		String name = c.getName();
//		String[] names = name.split("\\.");
//		name = names[names.length -1];
//		System.out.println("Field is: " + name);
//		return name;
//	}
	
//	public static Predicate composePredicate(CriteriaBuilder _cb, Root _root, Filter _f, String _type, String _comp) {
//		switch(_type) {
//			case "Calendar" :
//				switch(_comp) {
//					case Filters.COMPARISON_GREATER_THAN:
//						return _cb.greaterThan(_root.<Calendar>get(_f.getKey()), (Calendar)_f.getValue());
//				}
//			case "Integer" :
//			case "int" :
//				System.out.println("Integer called");
//				switch(_comp) {
//					case Filters.COMPARISON_GREATER_THAN:
//						return _cb.greaterThan(_root.<Integer>get(_f.getKey()), (Integer)_f.getValue());
//					case Filters.COMPARISON_EQUAL:
//						return _cb.equal(_root.<Integer>get(_f.getKey()), (Integer)_f.getValue());
//				}
//				
//		}
//		
//		return null;
//	}
	
//	public static <T> Predicate convertFilterToPredicate(Filters _filters, Root<T> _root, CriteriaBuilder _cb, Class<T> _clazz) throws Exception {
//		if(_filters == null ||  _filters.getFilters() == null || _filters.getFilters().size() <= 0)
//			return null;
//		
//		Predicate retval = null;
//		List<Predicate> predicates = new LinkedList<>();
//		for(Filter f : _filters.getFilters()) {
//			String type = Util.findType(_clazz, f.getKey());
//			switch(f.getComparison()) {
//				case Filters.COMPARISON_EQUAL : 
//					switch(type) {
//						case Util.TYPE_CALENDAR :
//							retval = _cb.equal(_root.<Calendar>get(f.getKey()), (Calendar)f.getValue());
//							break;
//						case Util.TYPE_INTEGER:
//						case Util.TYPE_INT:
//							retval = _cb.equal(_root.<Integer>get(f.getKey()), (Integer)f.getValue());
//							break;
//						case Util.TYPE_DB:
//						case Util.TYPE_DOUBLE:
//							retval = _cb.equal(_root.<Double>get(f.getKey()), (Double)f.getValue());
//							break;
//					}
//					break;
//				case Filters.COMPARISON_GREATER_THAN : 
//					switch(type) {
//						case Util.TYPE_CALENDAR :
//							retval = _cb.greaterThan(_root.<Calendar>get(f.getKey()), (Calendar)f.getValue());
//							break;
//						case Util.TYPE_INTEGER:
//						case Util.TYPE_INT:
//							retval = _cb.greaterThan(_root.<Integer>get(f.getKey()), (Integer)f.getValue());
//							break;
//						case Util.TYPE_DB:
//						case Util.TYPE_DOUBLE:
//							retval = _cb.greaterThan(_root.<Double>get(f.getKey()), (Double)f.getValue());
//							break;
//					}
//					break;
//				case Filters.COMPARISON_LESS_THAN : 
//					switch(type) {
//						case Util.TYPE_CALENDAR :
//							retval = _cb.lessThan(_root.<Calendar>get(f.getKey()), (Calendar)f.getValue());
//							break;
//						case Util.TYPE_INTEGER:
//						case Util.TYPE_INT:
//							retval = _cb.lessThan(_root.<Integer>get(f.getKey()), (Integer)f.getValue());
//							break;
//						case Util.TYPE_DB:
//						case Util.TYPE_DOUBLE:
//							retval = _cb.lessThan(_root.<Double>get(f.getKey()), (Double)f.getValue());
//							break;
//					}
//					break;
//				case Filters.COMPARISON_EQUAL_GREATER_THAN : 
//					switch(type) {
//						case Util.TYPE_CALENDAR :
//							retval = _cb.greaterThanOrEqualTo(_root.<Calendar>get(f.getKey()), (Calendar)f.getValue());
//							break;
//						case Util.TYPE_INTEGER:
//						case Util.TYPE_INT:
//							retval = _cb.greaterThanOrEqualTo(_root.<Integer>get(f.getKey()), (Integer)f.getValue());
//							break;
//						case Util.TYPE_DB:
//						case Util.TYPE_DOUBLE:
//							retval = _cb.greaterThanOrEqualTo(_root.<Double>get(f.getKey()), (Double)f.getValue());
//							break;
//					}
//					break;
//				case Filters.COMPARISON_LIKE : 
//					switch(type) {
//						case Util.TYPE_STRING :
//							retval = _cb.like(_root.<String>get(f.getKey()), (String)f.getValue());
//							break;
//						default:
//							throw new Exception("Error, invalid type: " + type + " for like comparison");
//					}
//					break;
//			}
//			predicates.add(retval);
//		}
//		
//		if(predicates.size() > 1) {
//			retval = _cb.and(predicates.toArray(new Predicate[0]));
//		}
//		
//		return retval;
//	}
	
//	public static String findFieldN
    
    
}
