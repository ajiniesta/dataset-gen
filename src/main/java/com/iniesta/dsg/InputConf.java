package com.iniesta.dsg;

public class InputConf {

	private int length;
	private String separator;
	private String output;
	private String type;
	private String gentype;
	private int port;
	private long timeStart;
	private int rate;
	private String host;

	public InputConf() {
	}

	public InputConf(int length, String separator, String output, String type, String gentype, int port,
			long timeStart, int rate, String host) {
		super();
		this.length = length;
		this.separator = separator;
		this.output = output;
		this.type = type;
		this.gentype = gentype;
		this.port = port;
		this.timeStart = timeStart;
		this.rate = rate;
		this.host = host;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String isGentype() {
		return gentype;
	}

	public void setGentype(String gentype) {
		this.gentype = gentype;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "InputConf [length=" + length + ", separator=" + separator + ", output=" + output + ", type=" + type
				+ ", gentype=" + gentype + ", port=" + port + ", timeStart=" + timeStart + ", rate=" + rate + ", host="
				+ host + "]";
	}

}
