package dev.jeffersonfreitas.caixaki.utils;

import java.io.Serializable;
import java.util.Comparator;

public class SearchFieldObject implements Serializable, Comparator<SearchFieldObject> {
	private static final long serialVersionUID = 1L;

	private String description;
	private String databaseField;
	private Object classType;
	private Integer mainField;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDatabaseField() {
		return databaseField;
	}

	public void setDatabaseField(String databaseField) {
		this.databaseField = databaseField;
	}

	public Object getClassType() {
		return classType;
	}

	public void setClassType(Object classType) {
		this.classType = classType;
	}

	public Integer getMainField() {
		return mainField;
	}

	public void setMainField(Integer mainField) {
		this.mainField = mainField;
	}

	@Override
	public String toString() {
		return getDescription();
	}

	@Override
	public int compare(SearchFieldObject o1, SearchFieldObject o2) {
		return ((SearchFieldObject) o1).getMainField().compareTo(((SearchFieldObject) o2).getMainField());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((databaseField == null) ? 0 : databaseField.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchFieldObject other = (SearchFieldObject) obj;
		if (databaseField == null) {
			if (other.databaseField != null)
				return false;
		} else if (!databaseField.equals(other.databaseField))
			return false;
		return true;
	}

}
