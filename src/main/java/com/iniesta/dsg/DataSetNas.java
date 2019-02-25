package com.iniesta.dsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSetNas extends DataSet {

	private RandomDataSupplier rds;
	private long time;
	private int tsInc;

	public DataSetNas(InputConf iconf) {
		super(iconf);
		dataset = DataSet.DS_NAS;
		rds = RandomDataSupplier.getInstance();
		time = iconf.getTimeStart();
		tsInc = 60000 / iconf.getRate();
	}

	private Map<String, String> locations = new HashMap<>();
	private List<String> logins = new ArrayList<>();

	@Override
	public List<String> getLineSchema() {
		List<String> line = new ArrayList<>();
		String ts = genNextTs();
		String user = rds.getRandomStringFromFile("users");
		String location = getLocation(user);
		String operation = rds.getRandomPhrasesFromFile("operations");
		String payload = rds.getRandomPhrasesFromFile("lorem");
		String payload_size = String.valueOf(rds.getRandomInt(1000000));
		boolean success = rds.getRandomBoolean();

		if (userLogged(user, operation)) {
			if (isLogout(operation)) {
				logout(user);
			}
		} else {
			if (isLogin(operation)) {
				login(user, location, success);
			} else {
				success = false; // Operation not permited
			}
		}
		line = Arrays.asList(ts, user, location, operation, payload, payload_size, String.valueOf(success));
		return line;
	}

	private String getLocation(String user) {
		if (locations.containsKey(user)) {
			return locations.get(user);
		} else {
			return rds.getRandomGPSBetween(45.0, 6, 35, -3);
		}
	}

	private boolean userLogged(String user, String op) {
		return logins.contains(user);
	}

	private boolean isLogin(String operation) {
		return "login".equals(operation);
	}

	private boolean isLogout(String operation) {
		return "logout".equals(operation);
	}

	private void login(String user, String location, boolean success) {
		if (success) {
			locations.put(user, location);
			logins.add(user);
		}
	}

	private void logout(String user) {
		locations.remove(user);
		logins.remove(user);
	}

	private String genNextTs() {
		time += tsInc;
		return String.valueOf(time);
	}
	
	@Override
	public int getInc() {
		return tsInc;
	}

}
