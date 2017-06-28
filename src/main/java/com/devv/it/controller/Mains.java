package com.devv.it.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.devv.it.POJO.DataObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mains {

	public static void main(String[] args) {

		Mains test = new Mains();
		DataObject[] dataObjList = test.convertToObject();
		/*
		 * System.out.println("Size is " + dataObjList.length);
		 * System.out.println("result " + dataObjList[78]);
		 */

		// Step 2
		/*
		 * Prepare for JSONNode object
		 */

		JsonNode node = test.prepareNode();

		// Step 3 :- convert to list
		List<DataObject> list = Arrays.asList(dataObjList);
		System.out.println("LIST  " + list);

		// Step 4 :- preparing data object for exact match
		DataObject dataObject = test.prepareExactSearch(
				"Microsoft", node);

		// Step 5 - Finally return the result of the exct match
		exactSearch(list, dataObject);

	}

	public DataObject[] convertToObject() {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {

			return objectMapper.readValue(new FileInputStream(new File(
					"data.json")), DataObject[].class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	// step 1
	public JsonNode prepareNode() {
		JsonNode node = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			node = mapper.readTree(new FileInputStream(new File("data.json")));
			// System.out.println(node);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return node;
	}

	public DataObject prepareExactSearch(String searchKey, JsonNode node) {

		String name = null;
		String type = null;
		String designedBy = null;

		List<JsonNode> namesList = node.findValues("Name");
		List<JsonNode> typeList = node.findValues("Type");
		List<JsonNode> designedByList = node.findValues("Designed by");
		System.out.println("Name List " + namesList);
		/*
		 * System.out.println("Type: " + typeList);
		 * System.out.println("Designed by Lst: " + designedByList);
		 */
		// exact name is their for searching
		for (JsonNode names : namesList) {

			if (names.asText().equalsIgnoreCase(searchKey)) {
				System.out.println("true");
				name = searchKey;
			}
		}
		// exact type is their for searching
		for (JsonNode types : typeList) {
			if (types.asText().equalsIgnoreCase(searchKey)) {
				type = searchKey;
			}
		}// exact match is their for designed by
		for (JsonNode designedBys : designedByList) {
			if (designedBys.asText().equalsIgnoreCase(searchKey)) {
				designedBy = searchKey;
			}
		}

		return new DataObject(name, type, designedBy);
	}

	public DataObject preparePartialSearch(String searchKey, JsonNode node) {

		String name = null;
		String type = null;
		String designedBy = null;

		List<JsonNode> namesList = node.findValues("Name");
		List<JsonNode> typeList = node.findValues("Type");
		List<JsonNode> designedByList = node.findValues("Designed by");
		System.out.println("Name List " + namesList);
		System.out.println("Type: " + typeList);
		System.out.println("Designed by Lst: " + designedByList);
		// PARTIAL SEARCH
		// if some part of the string is something and some string is something

		// split searchkey first and then search for individual substring in the
		// list and then set them individually

		String[] str = searchKey.split(" ");
		if (str.length > 0) {
			for (int i = 0; i < str.length; i++) {

				if (namesList.contains(str[i])) {
					name = str[i];
				}
				if (typeList.contains(str[i])) {
					type = str[i];
				}
				if (designedByList.contains(str[i])) {
					designedBy = str[i];
				}
			}
		}

		return new DataObject(name, type, designedBy);
	}

	public static String exactSearch(List<DataObject> dataObjList,
			DataObject dataObject) {

		String result = null;
		int index = 0;

		System.out.println("DATAObject is " + dataObject);

		

		// implement binary search

		if (dataObject.getName() != null) {
			
			Collections.sort(dataObjList, new Comparator<DataObject>() {

				public int compare(DataObject o1, DataObject o2) {
					// TODO Auto-generated method stub
					return o1.getName().compareTo(o2.getName());
				}
			});
			
			index = Collections.binarySearch(dataObjList, dataObject,
					new Comparator<DataObject>() {

						public int compare(DataObject o1, DataObject o2) {

							return o1.getName().compareTo(o2.getName());
						}

					});
		}
		if (dataObject.getType() != null) {
			
			Collections.sort(dataObjList, new Comparator<DataObject>() {

				public int compare(DataObject o1, DataObject o2) {
					// TODO Auto-generated method stub
					return o1.getType().compareTo(o2.getType());
				}
			});

			index = Collections.binarySearch(dataObjList, dataObject,
					new Comparator<DataObject>() {

						public int compare(DataObject o1, DataObject o2) {

							return o1.getType().compareTo(o2.getType());
						}

					});
		}
		if (dataObject.getDesignedby() != null) {
			
			Collections.sort(dataObjList, new Comparator<DataObject>() {

				public int compare(DataObject o1, DataObject o2) {
					// TODO Auto-generated method stub
					return o1.getDesignedby().compareTo(o2.getDesignedby());
				}
			});

			index = Collections.binarySearch(dataObjList, dataObject,
					new Comparator<DataObject>() {

						public int compare(DataObject o1, DataObject o2) {

							return o1.getDesignedby().compareTo(
									o2.getDesignedby());
						}

					});
		}

		System.out.println("Index is " + Math.abs(index));
		System.out.println("TYPE: "
				+ dataObjList.get(Math.abs(index)).getType());
		result = dataObjList.get(Math.abs(index)).getType();

		return result;

	}

}