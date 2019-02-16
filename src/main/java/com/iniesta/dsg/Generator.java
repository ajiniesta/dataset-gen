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
	private String output = "output";
	
	@Parameter(names= {"--type", "-t"}, description="Types of generation: stream, ds-shop, ds-nas")
	private String type=DataSet.DS_SHOP;
	
	public static void main(String[] args) {
		Generator gen = new Generator();
		JCommander.newBuilder().addObject(gen).build().parse(args);
		
		gen.run();
	}

	private void run() {
		logger.info("Starting to generate...");
		logger.info(String.format("The length of the file will be: %d%n", length));
		DataSet ds = DataSet.getFactory(type, separator, output, length);
		ds.gen();
	}
}
