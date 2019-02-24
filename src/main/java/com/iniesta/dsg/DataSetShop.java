package com.iniesta.dsg;

import java.util.Arrays;
import java.util.List;

public class DataSetShop extends DataSet {

	private RandomDataSupplier rds;

	public DataSetShop(InputConf iconf) {
		super(iconf);
		dataset = DataSet.DS_SHOP;
		rds = RandomDataSupplier.getInstance();
	}

	@Override
	public List<String> getLineSchema() {
		return Arrays.asList( //
				rds.getID("ds", 10), //
				rds.getRandomStringFromFile("productos"), //
				rds.getRandomPhrasesFromFile("lorem"), //
				rds.getRandomStringFromFile("trademarks"), //
				rds.getRandomDouble(1000, 2));//
	}

}
