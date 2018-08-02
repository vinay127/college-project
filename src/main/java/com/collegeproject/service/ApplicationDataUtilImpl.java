package com.collegeproject.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.collegeproject.codetype.ApplicationDataXMLTag;
import com.collegeproject.model.ApplicationData;

@Service
@Transactional
public class ApplicationDataUtilImpl implements ApplicationDataUtil {
	@Autowired
	ApplicationDataService applicationDataService;
	private static final Logger logger = LoggerFactory.getLogger(ApplicationDataUtilImpl.class);
	String key;
	File xmlFile = new File(this.fetchXmlPath());
	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	Document doc;

	@Override
	public String fetchXmlPath() {
		logger.info("Entering fetchXmlPath()");
		String xmlPath = "./src/main/resources/applicationData.xml";
		return xmlPath;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void writeDataToXml(ApplicationData applicationData) {
		logger.info("Entering writeDataToXml() method");
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);

			// assigning node names

			String tElement = ApplicationDataXMLTag.element.toString();

			NodeList eElements = doc.getElementsByTagName(tElement);
			for (int i = 0; i < eElements.getLength(); i++) {
				// Node key = eElements.item(i).getChildNodes().item(0);
				// Node oldValue = eElements.item(i).getChildNodes().item(1);
				Node key = null;
				Node oldValue = null;
				NodeList nodes = eElements.item(i).getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					if (nodes.item(j).getNodeType() != 3) {
						if (nodes.item(j).getNodeName().equals(ApplicationDataXMLTag.key.toString())) {
							key = nodes.item(j);
						} else if (nodes.item(j).getNodeName().equals(ApplicationDataXMLTag.value.toString())) {
							oldValue = nodes.item(j);
						}
					}

				}

				if (key.getTextContent().equals(applicationData.getObsKey())) {

					if (oldValue.equals(applicationData.getObsValue())) {
						return;
					} else {
						oldValue.setTextContent(applicationData.getObsValue());
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(new File(fetchXmlPath()));
						transformer.transform(source, result);
						return;
					}
				}

			}

			Node appData = doc.getElementsByTagName(ApplicationDataXMLTag.applicationdata.toString()).item(0);
			Element eElement = doc.createElement(tElement);
			Element eKey = doc.createElement(ApplicationDataXMLTag.key.toString());
			eKey.setTextContent(applicationData.getObsKey());
			Element eValue = doc.createElement(ApplicationDataXMLTag.value.toString());
			eValue.setTextContent(applicationData.getObsValue());

			// appending elements
			appData.appendChild(eElement);
			eElement.appendChild(eKey);
			eElement.appendChild(eValue);

			// write the content into XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fetchXmlPath()));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ApplicationData> fetchFromXml(List<ApplicationData> applicationDataList) {
		logger.info("Entering fetchFromXml() method");
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			ApplicationData applicationData;
			List<ApplicationData> applicationDataListNew = new ArrayList<ApplicationData>();
			NodeList keyList = doc.getElementsByTagName("key");
			NodeList valueList = doc.getElementsByTagName("value");

			for (int temp = 0; temp < keyList.getLength(); temp++) {
				if (!this.checkDBDuplicates(applicationDataList, keyList.item(temp).getTextContent())) {
					applicationData = new ApplicationData();
					applicationData.setObsKey(keyList.item(temp).getTextContent());
					applicationData.setObsValue(valueList.item(temp).getTextContent());
					applicationDataListNew.add(applicationData);
					logger.info(
							"Data added to DB " + applicationData.getObsKey() + " " + applicationData.getObsValue());
				} else {
					logger.info("Data not added to DB");
				}
			}
			return applicationDataListNew;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void writeAllToXml(List<ApplicationData> applicationDataList) {
		logger.info("Entering writeAllToXml() method");
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			NodeList keyList = doc.getElementsByTagName("key");
			for (int temp = 0; temp < keyList.getLength(); temp++) {
				if (!this.checkNodeDuplicates(keyList, applicationDataList.get(temp).getObsKey())) {
					this.writeDataToXml(applicationDataList.get(temp));
					logger.info("Data added to XML");
				} else {
					logger.info("Data not added to XML");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkNodeDuplicates(NodeList nodes, String key) {
		logger.info("checking Node duplicates for key " + key);
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getTextContent().equalsIgnoreCase(key)) {
				return true;
			}
		}
		logger.info("No Duplicates");
		return false;
	}

	@Override
	public boolean checkDBDuplicates(List<ApplicationData> applicationDataList, String key) {
		logger.info("checking DB duplicates for key" + key);
		for (int i = 0; i < applicationDataList.size(); i++) {
			if (applicationDataList.get(i).getObsKey().equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}
}