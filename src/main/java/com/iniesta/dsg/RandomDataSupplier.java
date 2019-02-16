package com.iniesta.dsg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

public class RandomDataSupplier {

	final static Logger logger = Logger.getLogger(RandomDataSupplier.class);
	
	private Random random;

	private Map<String, List<String>> cacheFiles = new HashMap<>();
	
	private static RandomDataSupplier instance = null;
	
	private long idraw = 1L;
	
	private RandomDataSupplier() {
		random = new Random();
	}
	
	public static synchronized RandomDataSupplier getInstance() {
		if(instance==null) {
			instance = new RandomDataSupplier();
		}
		return instance;
	}
	
	/**
	 * Return an int from 0 to top
	 * @param top
	 * @return
	 */
	public int getRandomInt(int top) {
		return random.nextInt(top);
	}
	
	public double getRandomDouble(int top) {
		return random.nextDouble()*top+1;
	}
		
	public String getRandomString(List<String> input) {
		return input.get(getRandomInt(input.size()));
	}
	
	private List<String> extractNamesRaw(String file){
		List<String> names = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file)))){
			String line = null;
			while((line = br.readLine()) != null) {
				String[] split = line.split("\\s+");
				for (String chunk : split) {
					names.add(chunk.trim());
				}
			}
		} catch (IOException e) {
			logger.error("Error during the read of the file " + file, e);
		}
		return names;
	}
	
	private List<String> extractPhrasesRaw(String file){
		List<String> names = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file)))){
			String line = null;
			while((line = br.readLine()) != null) {
				names.add(line.trim());
			}
		} catch (IOException e) {
			logger.error("Error during the read of the file " + file, e);
		}
		return names;
	}
	
	private List<String> extractNames(String file, boolean chunks){
		List<String> cached = cacheFiles.get(file);
		if(cached==null) {
			if(chunks) {
				cached = extractNamesRaw(file);
			}else {
				cached = extractPhrasesRaw(file);
			}
			cacheFiles.put(file, cached);			
		}
		return cached;
	}
	
	public String getRandomStringFromFile(String file) {
		List<String> names = extractNames(file, true);
		return getRandomString(names);
	}
	
	public String getRandomPhrasesFromFile(String file) {
		List<String> names = extractNames(file, false);
		return getRandomString(names);
	}

	public String getID(String prefix, int fixedLengthNum) {
		String idVal = Long.toString(idraw++);
		int fillingZeroes = fixedLengthNum - idVal.length();
		String id = prefix;
		for (int i = 0; i < fillingZeroes; i++) {
			id += "0";
		}
		id += idVal;
		return id;
	}

	public String getRandomDouble(int top, int decimals) {
		String pattern = "#.";
		for (int i = 0; i < decimals; i++) {
			pattern += "#";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(getRandomDouble(top));
	}
}
