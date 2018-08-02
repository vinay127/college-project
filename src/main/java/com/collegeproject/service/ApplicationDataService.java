package com.collegeproject.service;

import java.util.List;

import com.collegeproject.model.ApplicationData;

public interface ApplicationDataService {

	public ApplicationData getByID(long applicationData);

	public ApplicationData getByKey(String key);

	public List<ApplicationData> getAll();

	public ApplicationData save(ApplicationData applicationData);

	public ApplicationData update(ApplicationData applicationData);

	public void updateXml();

	public void updateDB();
}
