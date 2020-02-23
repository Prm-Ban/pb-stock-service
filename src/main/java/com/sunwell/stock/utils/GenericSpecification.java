package com.sunwell.stock.utils;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sunwell.stock.utils.Filters.Filter;

public class GenericSpecification<T> implements Specification<T> {
	protected Filters filters;
	protected Class<T> clazz;
	protected Predicate predicate;
	protected List<String> blackListedAttributes = defaultBlackListedAttributes;

	private static final List<String> defaultBlackListedAttributes = Arrays.asList("sysCreator", "sysUpdater",
			"sysCreateDate", "sysLastUpdateDate");

	public GenericSpecification(Filters _filters, Class<T> _class) {
		filters = _filters;
		clazz = _class;
	}

	@Override
	public Predicate toPredicate(Root<T> _root, CriteriaQuery<?> _cq, CriteriaBuilder _cb) {
		try {
			if (filters == null || filters.getFilters() == null || filters.getFilters().size() <= 0)
				return null;

			return convertFiltersToPredicate(_root, _cq, _cb);
		} catch (Exception _e) {
			_e.printStackTrace();
			throw new RuntimeException(_e);
		}
	}

	protected Predicate convertFiltersToPredicate(Root<T> _root, CriteriaQuery<?> _cq, CriteriaBuilder _cb)
			throws Exception {
		if (filters == null || filters.getFilters() == null || filters.getFilters().size() <= 0)
			return null;

		Predicate retval = null;
		List<Predicate> predicates = new LinkedList<>();
		for (Filter f : filters.getFilters()) {
			retval = convertFilterToPredicate(f, _root, _cq, _cb);
			if (retval != null)
				predicates.add(retval);
		}

		if (predicates.size() > 1) {
			retval = _cb.and(predicates.toArray(new Predicate[0]));
		}

		return retval;
	}

	private Path<EntityManager> messyMethod() {
		return null;
	}

	@SuppressWarnings("unchecked")
	protected Predicate convertFilterToPredicate(Filter _f, Path _root, CriteriaQuery<?> _cq, CriteriaBuilder _cb)
			throws Exception {
		String type = null;
		Path path = _root;
		String key = _f.getKey();
		String value = _f.getValue();

		System.out.println("KEY: " + key + " value: " + value);

		type = Util.findType(clazz, key);
		System.out.println("TYPE: " + type);

		if (_f.getKey().contains("_")) {
			System.out.println("LOOKING FOR NESTED ATTRIBUTES");
			String[] nestedAttributes = _f.getKey().split("_");
			if (nestedAttributes.length < 2)
				throw new Exception("Unknown filter: " + _f.getKey());
			for (int i = 0; i < nestedAttributes.length - 1; i++) {
				System.out.println("NESTED: " + i + " NAME: " + nestedAttributes[i]);
				path = path.get(nestedAttributes[i]);
			}
			key = nestedAttributes[nestedAttributes.length - 1];
		}

		switch (_f.getComparison()) {
		case Filters.COMPARISON_EQUAL:
			Object retval;
			System.out.println(" IS EQ: " + Util.TYPE_STRING.equals(type));
			switch (type) {
			case Util.TYPE_CALENDAR:
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				retval = Calendar.getInstance();
				((Calendar) retval).setTime(sdf.parse((String) value));
				break;
			case Util.TYPE_BL:
			case Util.TYPE_BOOLEAN:
				retval = Boolean.parseBoolean(value);
				break;
			case Util.TYPE_INTEGER:
			case Util.TYPE_INT:
				retval = new Integer(value);
				break;
			case Util.TYPE_DB:
			case Util.TYPE_DOUBLE:
				retval = new Double(value);
				break;
			case Util.TYPE_STRING:
				retval = value;
				break;
			default:
				throw new Exception("Error, invalid type for equality comparison: " + type);
			}
			return _cb.equal(path.get(key), retval);

		case Filters.COMPARISON_GREATER_THAN:
			switch (type) {
			case Util.TYPE_CALENDAR: {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Calendar val = Calendar.getInstance();
				val.setTime(sdf.parse((String) value));
				return _cb.greaterThan(path.get(key), val);
			}
			case Util.TYPE_INTEGER:
			case Util.TYPE_INT: {
				Integer val = new Integer(value);
				return _cb.greaterThan(path.get(key), val);
			}
			case Util.TYPE_DB:
			case Util.TYPE_DOUBLE: {
				Double val = new Double(value);
				return _cb.greaterThan(path.get(key), val);
			}
			default:
				throw new Exception("Error, invalid type for greter than comparison: " + type);
			}
		case Filters.COMPARISON_LESS_THAN:
			switch (type) {
			case Util.TYPE_CALENDAR: {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Calendar val = Calendar.getInstance();
				val.setTime(sdf.parse((String) value));
				return _cb.lessThan(path.get(key), val);
			}
			case Util.TYPE_INTEGER:
			case Util.TYPE_INT: {
				Integer val = new Integer(value);
				return _cb.lessThan(path.get(key), val);
			}
			case Util.TYPE_DB:
			case Util.TYPE_DOUBLE: {
				Double val = new Double(value);
				return _cb.lessThan(path.get(key), val);
			}
			default:
				throw new Exception("Error, invalid type for less than comparison: " + type);
			}
		case Filters.COMPARISON_EQUAL_GREATER_THAN:
			switch (type) {
			case Util.TYPE_CALENDAR: {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Calendar val = Calendar.getInstance();
				val.setTime(sdf.parse((String) value));
				return _cb.greaterThanOrEqualTo(path.get(key), val);
			}
			case Util.TYPE_INTEGER:
			case Util.TYPE_INT: {
				Integer val = new Integer(value);
				return _cb.greaterThanOrEqualTo(path.get(key), val);
			}
			case Util.TYPE_DB:
			case Util.TYPE_DOUBLE: {
				Double val = new Double(value);
				return _cb.greaterThanOrEqualTo(path.get(key), val);
			}
			default:
				throw new Exception("Error, invalid type for greter than or equal to comparison: " + type);
			}
		case Filters.COMPARISON_LIKE:
			switch (type) {
			case Util.TYPE_STRING:
				return _cb.like(path.<String>get(key), "%" + (String) value + "%");
			default:
				throw new Exception("Error, invalid type: " + type + " for like comparison");
			}
		default:
			throw new Exception("Error, no comparison operator found for operator: " + _f.getComparison());
		}

	}

	protected Predicate convertUnknownFilterToPredicate(Filter _f, Path _root, CriteriaQuery<?> _cq,
			CriteriaBuilder _cb) throws Exception {
		throw new Exception("Unknown filter: " + _f.getKey());
	}
}
