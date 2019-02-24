package com.iniesta.dsg;

import org.apache.log4j.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Generator base class
 * 
 * @author antonio
 *
 */
public class Generator {
	
	final static Logger logger = Logger.getLogger(Generator.class);
		
	@Parameter(names= {"--length","-l"}, description="Length of the file, if generating a file")
	private int length = 1000;
	
	@Parameter(names= {"--separator", "-s"}, description="Separator of the output filename")
	private String separator = ",";
	
	@Parameter(names= {"--output", "-o"}, description="Output file name")
	private String output = "output-"+System.currentTimeMillis();
	
	@Parameter(names= {"--type", "-t"}, description="Types of generation: dsshop, dsnas")
	private String type=DataSet.DS_SHOP;
	
	@Parameter(names= {"--gentype", "-g"}, description="Generation type, file or stream")
	private String gentype = "file";
	
	@Parameter(names= {"--host", "-h"}, description="Host to stream, only when gentype is false")
	private String host = "localhost";
	
	@Parameter(names= {"--port", "-p"}, description="Port to stream, only when gentype is false")
	private int port = 5000;
	
	@Parameter(names= {"--timestart", "-b"}, description="Start time in milliseconds, default January 1st 2019")
	private long timeStart = 1546300800000L;
	
	@Parameter(names= {"--rate", "-r"}, description="Rate of the operations per minute. Default 3")
	private int rate = 3;
	
	public static void main(String[] args) {
		Generator gen = new Generator();
		JCommander.newBuilder().addObject(gen).build().parse(args);
		
		gen.run();
	}

	private void run() {
		logger.info("Starting to generate...");		
		InputConf iconf = new InputConf(length, separator, output, type, gentype, port, timeStart, rate, host);
		logger.info(String.format("Input conf: %s%n", iconf));
		DataSet ds = DataSet.getFactory(iconf);
		if("file".equals(iconf.isGentype())) {
			ds.gen();
		}else {
			ds.stream();
		}
	}
}
