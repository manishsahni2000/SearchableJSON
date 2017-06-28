package com.devv.it.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devv.it.POJO.DataObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SearchableJSONController
 */
public class SearchableJSONController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SearchableJSONController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DataObject[] dataObjList = null;
		/*
		 * Step 1: convert from JSON to Java Object using Jackson library
		 */

		try {
			System.out.println("here");
			dataObjList = convertToObject();
			System.out.println("WOW here" + dataObjList.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String searchInput = request.getParameter("search_key");
		String searchtypeRadio = request.getParameter("search_type");
		System.out.println("searchtypeRadio " + searchtypeRadio);
		if (null != searchInput) {

			System.out.println("Here 1");

			if (searchtypeRadio.equalsIgnoreCase("E")) {
				/* Exact Search */
				request.setAttribute("searchParam", "E");
				System.out.println("Input to be searched for :" + searchInput);
				exactSearch(searchInput, dataObjList);
			} else if (searchtypeRadio.equalsIgnoreCase("P")) {
				/* Partial Search */
				request.setAttribute("searchParam", "P");
				System.out.println("Input to be searched for :" + searchInput);
				partialSearch(searchInput, dataObjList);
			} else {

				/* Negative search */
				request.setAttribute("searchParam", "N");
				System.out.println("Input to be searched for :" + searchInput);
				negativeSearch(searchInput, dataObjList);
			}
		}

	}

	private void negativeSearch(String searchInput, DataObject[] dataObjList) {
		// TODO Auto-generated method stub

	}

	private void partialSearch(String searchInput, DataObject[] dataObjList) {

		System.out.println(dataObjList.length);
		List list = Arrays.asList(dataObjList);
		System.out.println(list.size());

		System.out.println("Does it contains " + list.contains(searchInput));
		for (DataObject obj : dataObjList) {
			if (obj.getName().equalsIgnoreCase(searchInput)) {
				System.out.println("Result is " + obj.getType());
			}
		}

	}

	private void exactSearch(String searchInput, DataObject[] dataObjList) {
		// TODO Auto-generated method stub

	}

	private DataObject[] convertToObject() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {

			return objectMapper.readValue(new FileInputStream(new File(this
					.getServletContext().getRealPath("/data/data.json"))),
					DataObject[].class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
