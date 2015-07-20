package com.evive.formprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetRectangles {
	private static final Logger LOG = LoggerFactory.getLogger(GetRectangles.class);
	private final String propFile = "src/main/resources/data.properties";
	Properties properties;

	public void assignLabels() {
		final List<Integer> numSquares;
		final List<String> squareNames;
		List<String> predictedLabels = new ArrayList<String>();


		try(FileInputStream fileInput = new FileInputStream(new File(propFile))) {
			properties = new Properties();
			properties.load(fileInput);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		numSquares = new ArrayList<Integer>();

		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_1")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_2")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_3")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_4")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_5")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_6")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_7")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_8")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_9")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_10")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_11")));
		numSquares.add(Integer.valueOf(properties.getProperty("FIELDS_IN_INPUT_12")));


		squareNames = new ArrayList<>();

		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_1"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_2"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_3"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_4"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_5"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_6"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_7"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_8"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_9"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_10"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_11"));
		squareNames.add(properties.getProperty("NAME_OF_INPUT_FEILDS_12"));

		try(BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("src/main/resources/input.txt"))) {
			predictedLabels = bufferedReader.lines()
					.collect(Collectors.toList());

		}catch(final IOException e) {
			LOG.error(e.getMessage(),e);
		}


		StringBuilder stringBuilder;
		int index = 0;
		for(final String sqName : squareNames) {
			stringBuilder = new StringBuilder();
			for(int i = 0; i<numSquares.get(squareNames.indexOf(sqName));i++) {
				stringBuilder.append(predictedLabels.get(index));
				index++;
			}
			LOG.info("{} : {}", sqName, stringBuilder.toString());

		}

	}

	public static void main(final String[] args) {
		final GetRectangles gr = new GetRectangles();
		gr.assignLabels();
	}

}
