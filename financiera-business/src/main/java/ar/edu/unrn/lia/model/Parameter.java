package ar.edu.unrn.lia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parameter")
public class Parameter extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String key;
	private String value;
	private String description;
	private String usesClass;

	public Parameter() {
	}

	@Column( name = "key_col")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column( name = "value_col")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "uses_class")
	public String getUsesClass() {
		return usesClass;
	}

	public void setUsesClass(String usesClass) {
		this.usesClass = usesClass;
	}

}
