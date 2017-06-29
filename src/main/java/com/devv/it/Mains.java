package com.devv.it.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devv.it.POJO.DataObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mains {

	public static void main(String[] args) {

		Mains test = new Mains();
		DataObject[] dataObjList = test.convertToObject();
		List<DataObject> list = Arrays.asList(dataObjList);

		Map<String, List<DataObject>> nameMap = new HashMap<String, List<DataObject>>();
		Map<String, List<DataObject>> typeMap = new HashMap<String, List<DataObject>>();
		Map<String, List<DataObject>> designedByMap = new HashMap<String, List<DataObject>>();

		List<DataObject> nameList = new ArrayList<DataObject>();
		List<DataObject> typeList = new ArrayList<DataObject>();
		List<DataObject> designedByList = new ArrayList<DataObject>();

		/*
		 * System.out.println("Size is " + dataObjList.length);
		 * System.out.println("result " + dataObjList[78]);
		 */

		// Step 2
		/*
		 * Prepare for JSONNode object
		 */

		JsonNode node = test.prepareNode();

		// Step 3 :- convert to 3 Map<String,DataObject>

		for (int i = 0; i < list.size(); i++) {

			if (nameMap != null && nameMap.get(list.get(i).getName()) != null) {

				/*
				 * designedByList.add(typeMap.get(dataObjList[i].getDesignedby())
				 * .get(i));
				 */

				nameList = nameMap.get(list.get(i).getName());
				nameList.add(list.get(i));

				nameMap.put(list.get(i).getName(), nameList);

			} else {
				nameList = new ArrayList<DataObject>();
				nameList.add(list.get(i));
				nameMap.put(list.get(i).getName(), nameList);
			}

		}

		// System.out.println("NAME MAP ************  " + nameMap);

		for (int i = 0; i < list.size(); i++) {

			if (typeMap != null && typeMap.get(list.get(i).getType()) != null) {

				/*
				 * designedByList.add(typeMap.get(dataObjList[i].getDesignedby())
				 * .get(i));
				 */

				typeList = typeMap.get(list.get(i).getType());
				typeList.add(list.get(i));

				typeMap.put(list.get(i).getType(), typeList);

			} else {
				typeList = new ArrayList<DataObject>();
				typeList.add(list.get(i));
				typeMap.put(list.get(i).getType(), typeList);
			}

		}

		// System.out.println("TYPE MAP ************  " + typeMap);

		for (int i = 0; i < list.size(); i++) {

			if (designedByMap != null
					&& designedByMap.get(list.get(i).getDesignedby()) != null) {

				/*
				 * designedByList.add(typeMap.get(dataObjList[i].getDesignedby())
				 * .get(i));
				 */

				designedByList = designedByMap.get(list.get(i).getDesignedby());
				designedByList.add(list.get(i));

				designedByMap.put(list.get(i).getDesignedby(), designedByList);

			} else {
				designedByList = new ArrayList<DataObject>();
				designedByList.add(list.get(i));
				designedByMap.put(list.get(i).getDesignedby(), designedByList);
			}

		}

		// System.out.println("DESIGNED BY MAP ************  " + designedByMap);

		// Step 4 :- preparing data object for exact match
		// DataObject dataObject = test.prepareExactSearch("Scripting", node);
		/*
		 * DataObject dataObject = test.preparePartialSearch(
		 * "Microsoft Scripting", node);
		 */

		// Step 5 - Finally return the result of the exct match
		// exactSearch(nameMap, typeMap, designedByMap, dataObject);
		String searchKey = "John";
		if (searchKey.split(" ").length > 1) {

			partialSearch(nameMap, typeMap, designedByMap, searchKey);
		} else {
			partialSearch(nameMap, typeMap, designedByMap, searchKey);
		}

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
		Map<String, DataObject> map = new HashMap<String, DataObject>();

		List<JsonNode> namesList = node.findValues("Name");
		List<JsonNode> typeList = node.findValues("Type");
		List<JsonNode> designedByList = node.findValues("Designed by");
		// System.out.println("Name List " + namesList);
		/*
		 * System.out.println("Type: " + typeList);
		 * System.out.println("Designed by Lst: " + designedByList);
		 */
		// exact name is their for searching
		for (JsonNode names : namesList) {

			if (names.asText().contains(searchKey)) {
				name = searchKey;
			}
		}
		// exact type is their for searching
		for (JsonNode types : typeList) {
			if (types.asText().contains(searchKey)) {
				type = searchKey;
			}
		}// exact match is their for designed by
		for (JsonNode designedBys : designedByList) {
			if (designedBys.asText().contains(searchKey)) {
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
		/*
		 * System.out.println("Name List " + namesList);
		 * System.out.println("Type: " + typeList);
		 * System.out.println("Designed by Lst: " + designedByList);
		 */
		// PARTIAL SEARCH
		// if some part of the string is something and some string is something

		// split searchkey first and then search for individual substring in the
		// list and then set them individually

		String[] str = searchKey.split(" ");
		if (str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				for (JsonNode names : namesList) {

					if (names.asText().contains(str[i])) {
						name = searchKey;
					}
				}
				// exact type is their for searching
				for (JsonNode types : typeList) {
					if (types.asText().contains(str[i])) {
						type = searchKey;
					}
				}// exact match is their for designed by
				for (JsonNode designedBys : designedByList) {
					if (designedBys.asText().contains(str[i])) {
						designedBy = searchKey;
					}
				}
			}

		}

		return new DataObject(name, type, designedBy);
	}

	public static List<DataObject> exactSearch(
			Map<String, List<DataObject>> nameMap,
			Map<String, List<DataObject>> typeMap,
			Map<String, List<DataObject>> designedByMap, DataObject dataObject) {

		List<DataObject> result = null;

		// System.out.println("DATAObject is " + dataObject);

		if (dataObject.getName() != null) {

			// search in the name map
			System.out.println("INSIDE NAME SEARCH : " + dataObject.getName());
			result = nameMap.get(dataObject.getName());

		}
		if (dataObject.getType() != null) {

			// search in the type map
			System.out.println("INSIDE TYPE SEARCH : " + dataObject.getType());
			result = typeMap.get(dataObject.getType());
		}
		if (dataObject.getDesignedby() != null) {
			// search in the designedbyMap
			System.out.println("INSIDE DESIGNED BY SEARCH : "
					+ dataObject.getDesignedby());
			result = designedByMap.get(dataObject.getDesignedby());
		}

		System.out.println("RESULT is " + result);

		return result;

	}

	public static List<DataObject> partialSearch(
			Map<String, List<DataObject>> nameMap,
			Map<String, List<DataObject>> typeMap,
			Map<String, List<DataObject>> designedByMap, String searchKey) {

		List<DataObject> result = new ArrayList<DataObject>();
		boolean isFound = false;

		// System.out.println("DATAObject is " + dataObject);

		String[] str = searchKey.split(" ");
		if (str.length > 1) {
			for (int i = 0; i < str.length; i++) {

				for (Map.Entry<String, List<DataObject>> entry : nameMap
						.entrySet()) {

					if (entry.getKey().contains(str[i])) {

						List<DataObject> resultList = entry.getValue();
						for (DataObject obj : resultList)
							if (obj.getType().contains(str[i + 1])
									|| obj.getDesignedby().contains(str[i + 1]) || obj.getName().contains(str[i + 1])) {

								result.add(obj);
								isFound = true;
							}
					}
				}

				// If first match is type

				if (!isFound) {
					for (Map.Entry<String, List<DataObject>> entry : typeMap
							.entrySet()) {

						if (entry.getKey().contains(str[i])) {

							List<DataObject> resultList = entry.getValue();
							for (DataObject obj : resultList)
								if (obj.getName().contains(str[i + 1])
										|| obj.getDesignedby().contains(
												str[i + 1]) || obj.getType().contains(str[i + 1])) {

									result.add(obj);
									isFound = true;
								}
						}
					}
				}

				// If first match is DesignedBy

				if (!isFound) {
					for (Map.Entry<String, List<DataObject>> entry : designedByMap
							.entrySet()) {

						if (entry.getKey().contains(str[i])) {

							List<DataObject> resultList = entry.getValue();
							for (DataObject obj : resultList)
								if (obj.getName().contains(str[i + 1])
										|| obj.getType().contains(str[i + 1]) || obj.getDesignedby().contains(str[i + 1])) {

									result.add(obj);
								}
						}
					}

				}

			}
			System.out.println("RESULT is " + result);

		} else {
			for (int i = 0; i < str.length; i++) {

				for (Map.Entry<String, List<DataObject>> entry : nameMap
						.entrySet()) {

					if (entry.getKey().contains(str[i])) {

						System.out.println(entry);

					}
				}

				// If first match is type

				for (Map.Entry<String, List<DataObject>> entry : typeMap
						.entrySet()) {

					if (entry.getKey().contains(str[i])) {

						System.out.println(entry);

					}
				}

				// If first match is DesignedBy

				for (Map.Entry<String, List<DataObject>> entry : designedByMap
						.entrySet()) {

					if (entry.getKey().contains(str[i])) {

						System.out.println(entry);

					}

				}

			}

		}
		return result;
	}
}
