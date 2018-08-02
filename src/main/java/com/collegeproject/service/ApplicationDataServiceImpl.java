package com.collegeproject.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.collegeproject.codetype.NotificationInfo;
import com.collegeproject.core.TransactionInfo;
import com.collegeproject.model.ApplicationData;
import com.collegeproject.repository.ApplicationDataRepository;

@Service
@Transactional
public class ApplicationDataServiceImpl implements ApplicationDataService {

	@Autowired
	ApplicationDataRepository applicationDataRepository;
	@Autowired
	ApplicationDataUtil applicationDataUtilImpl;	

	@Autowired
	TransactionInfo transactionInfo;

	@Override
	public ApplicationData getByID(long applicationDataID) {
		return applicationDataRepository.findByApplicationdataid(applicationDataID);
	}

	@Override
	public ApplicationData getByKey(String key) {
		return applicationDataRepository.findByObsKey(key);
	}

	@Override
	public List<ApplicationData> getAll() {
		return applicationDataRepository.findAll();
	}

	@Override
	public ApplicationData save(ApplicationData applicationData) {

		ApplicationData existingData = applicationDataRepository.findByObsKey(applicationData.getObsKey());
		if (null != existingData && applicationData.getApplicationdataid() == 0) {
			List<Object> args = new ArrayList<Object>();
			args.add(applicationData.getObsKey());
			transactionInfo.generateException("appdata_key_exists",args, NotificationInfo.ERROR,
					Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		applicationData = applicationDataRepository.save(applicationData);

		// Writing data to xml
		applicationDataUtilImpl.writeDataToXml(applicationData);
		return applicationData;
	}

	@Override
	public ApplicationData update(ApplicationData applicationData) {
		ApplicationData oldApplicationData = getByKey(applicationData.getObsKey());
		applicationData.setApplicationdataid(oldApplicationData.getApplicationdataid());
		// applicationDataRepository.delete(oldApplicationData);
		return save(applicationData);
	}

	@Override
	public void updateXml() {
		ApplicationDataUtilImpl applicationDataUtilImpl = new ApplicationDataUtilImpl();
		List<ApplicationData> applicationDataList = applicationDataRepository.findAll();
		applicationDataUtilImpl.writeAllToXml(applicationDataList);
	}

	@Override
	public void updateDB() {
		ApplicationDataUtilImpl applicationDataUtilImpl = new ApplicationDataUtilImpl();
		List<ApplicationData> applicationDataList = applicationDataRepository.findAll();
		List<ApplicationData> applicationDataListNew = applicationDataUtilImpl.fetchFromXml(applicationDataList);
		for (ApplicationData applicationData : applicationDataListNew) {
			applicationDataRepository.save(applicationData);
		}
	}

}