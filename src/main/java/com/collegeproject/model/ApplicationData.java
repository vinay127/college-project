package com.collegeproject.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "applicationdata")
public class ApplicationData {

	@Id
	@GeneratedValue
	private long applicationdataid;

	@Column(unique = true)
	private String obsKey;

	@Column
	private String obsValue;

	public long getApplicationdataid() {
		return applicationdataid;
	}

	public void setApplicationdataid(long applicationdataid) {
		this.applicationdataid = applicationdataid;
	}

	public String getObsKey() {
		return obsKey;
	}

	public void setObsKey(String obsKey) {
		this.obsKey = obsKey;
	}

	public String getObsValue() {
		return obsValue;
	}

	public void setObsValue(String obsValue) {
		this.obsValue = obsValue;
	}

}