package com.sunwell.stock.utils;

import java.util.LinkedList;
import java.util.List;

public class Filters
{
	public static final String ISSUE_DATE = "issueDate";
	
	public static final String COMPARISON_EQUAL = "=";
	public static final String COMPARISON_NOT = "!=";
	public static final String COMPARISON_LIKE = "%";
	public static final String COMPARISON_GREATER_THAN = ">";
	public static final String COMPARISON_EQUAL_GREATER_THAN = ">=";
	public static final String COMPARISON_LESS_THAN = "<";
	public static final String COMPARISON_EQUAL_LESS_THAN = "<=";
	
	private List<Filter> filters;
	
	public List<Filter> getFilters ()
	{
		return filters;
	}

	public void setFilters (List<Filter> _filters)
	{
		filters = _filters;
	}
	
	public void addFilter(Filter _f) {
		if (filters == null)
			filters = new LinkedList<>();
		
		filters.add(_f);
	}
	
	public static class Filter {
		private String key;
		private String comparison;
		private String value;
		
		public Filter() {
			
		}
		
		public Filter(String _key, String _comparison, String _value) {
			key = _key;
			comparison = _comparison;
			value = _value;
		}
		
		public String getKey ()
		{
			return key;
		}
		public void setKey (String _key)
		{
			key = _key;
		}
		public String getComparison ()
		{
			return comparison;
		}
		public void setComparison (String _comparison)
		{
			comparison = _comparison;
		}
		public String getValue ()
		{
			return value;
		}
		public void setValue (String _value)
		{
			value = _value;
		}
		
//		private Object convertToType(String _s, Class _c) {
//			if(_c.getName().equals(""))
//		}
	}	
	
	public static class Unknown {
		private String value;
		
		public Unknown(String _value) {
			value = _value;
		}

		public String getValue ()
		{
			return value;
		}

		public void setValue (String _value)
		{
			value = _value;
		}
		
		@Override
		public String toString() {
			return value.toString();
		}
	}
	
	public static class Nested {
		private String value;
		
		public Nested(String _value) {
			value = _value;
		}

		public String getValue ()
		{
			return value;
		}

		public void setValue (String _value)
		{
			value = _value;
		}
		
		@Override
		public String toString() {
			return value.toString();
		}
	}
}
