package com.collegeproject.service;

import java.util.List;

import org.w3c.dom.NodeList;

import com.collegeproject.model.ApplicationData;

public interface ApplicationDataUtil {

	public String fetchXmlPath();
	
	public boolean checkNodeDuplicates(NodeList nodes,String key);
	
	public boolean checkDBDuplicates(List<ApplicationData> applicationDataList,String key);
	
	public List<ApplicationData> fetchFromXml(List<ApplicationData> applicationDataList);
	
	public void writeDataToXml(ApplicationData applicationData);
	
	public void writeAllToXml(List<ApplicationData> applicationDataList);
}
