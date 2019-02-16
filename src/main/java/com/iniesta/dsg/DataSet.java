package com.iniesta.dsg;

import java.io.FileWriter;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class DataSet {

	final static Logger logger = Logger.getLogger(DataSet.class);

	public final static String STREAM = "stream";
	public final static String DS_SHOP = "ds-shop";
	public final static String DS_NAS = "ds-nas";

	private String separator;
	private String output;
	private int length;
	protected String dataset;

	public DataSet(String separator, String output, int length) {
		this.separator = separator;
		this.output = output;
		this.length = length;
	}

	public abstract List<String> getLineSchema();

	private String getLine() {
		String line = "";
		List<String> schema = getLineSchema();
		for (int i = 0; i < schema.size(); i++) {
			line += schema.get(i);
			if (i < schema.size() - 1) {
				line += separator;
			}
		}
		line += "\n";
		return line;
	}

	public void gen() {
		logger.info(String.format("Generating %s file with dataset %s of %s lines %n", output, dataset, length));
		try (FileWriter fw = new FileWriter(output)) {
			for (int i = 0; i < length; i++) {
				fw.write(getLine());
				if (i % 10000 == 0) {
					fw.flush();
					logger.info(String.format("Saving %s of %s lines to file", i, length));
				}
			}
			fw.flush();
			logger.info(String.format("File %s generated", output));
		} catch (Exception e) {
			logger.error("Error during the generation of " + output, e);
		}
	}


	public static DataSet getFactory(String type, String separator, String output, int length) {
		switch (type) {
		case STREAM:
			return null;
		case DS_SHOP:
			return new DataSetShop(separator, output, length);
		case DS_NAS:
			return null;
		default:
			return null;
		}
	}
}
