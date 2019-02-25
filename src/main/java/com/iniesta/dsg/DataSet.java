package com.iniesta.dsg;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class DataSet {

	final static Logger logger = Logger.getLogger(DataSet.class);

	public final static String DS_SHOP = "dsshop";
	public final static String DS_NAS = "dsnas";

	private String separator;
	private String output;
	private int length;
	protected String dataset;

	private InputConf iconf;

	public DataSet(InputConf iconf) {
		this.iconf = iconf;
		this.separator = iconf.getSeparator();
		this.output = iconf.getOutput();
		this.length = iconf.getLength();
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

	public void stream() {
		logger.info(String.format("Connecting to %s:%s", iconf.getHost(), iconf.getPort()));
		try (Socket socket = new Socket(InetAddress.getByName(iconf.getHost()), iconf.getPort())){			
			PrintWriter printer = new PrintWriter(socket.getOutputStream(), true);
			logger.info(String.format("Connected to %s:%s", iconf.getHost(), iconf.getPort()));
			int i = 0;
			do {
				printer.print(getLine());
				printer.flush();
				if (i % 100 == 0) {
					logger.info(String.format("Sending %s lines to stream", i));
				}
				i++;
				Thread.sleep(getInc());
			} while(true);
		} catch (IOException | InterruptedException e) {
			logger.error("Error during the streaming...", e);
		}
		
	}

	public static DataSet getFactory(InputConf iconf) {
		switch (iconf.getType()) {
		case DS_SHOP:
			return new DataSetShop(iconf);
		case DS_NAS:
			return new DataSetNas(iconf);
		default:
			return null;
		}
	}

	public int getInc() {
		return 1000;
	}

	
}
