package com.itstep.entities;

public class AllProbesResult {
	private int probeRel;
	private int probeId;
	private String probeApp;
	private String probeType;
	private String probeSourceDevName;
	private int probeSourceDevPort;
	private String probeDestDevName;
	private int probeDestDevPort;
	
	public AllProbesResult() {}

	public int getProbeRel() {
		return probeRel;
	}

	public void setProbeRel(int probeRel) {
		this.probeRel = probeRel;
	}

	public int getProbeId() {
		return probeId;
	}

	public void setProbeId(int probeId) {
		this.probeId = probeId;
	}

	public String getProbeApp() {
		return probeApp;
	}

	public void setProbeApp(String probeApp) {
		this.probeApp = probeApp;
	}

	public String getProbeType() {
		return probeType;
	}

	public void setProbeType(String probeType) {
		this.probeType = probeType;
	}

	public String getProbeSourceDevName() {
		return probeSourceDevName;
	}

	public void setProbeSourceDevName(String probeSourceDevName) {
		this.probeSourceDevName = probeSourceDevName;
	}

	public int getProbeSourceDevPort() {
		return probeSourceDevPort;
	}

	public void setProbeSourceDevPort(int probeSourceDevPort) {
		this.probeSourceDevPort = probeSourceDevPort;
	}

	public String getProbeDestDevName() {
		return probeDestDevName;
	}

	public void setProbeDestDevName(String probeDestDevName) {
		this.probeDestDevName = probeDestDevName;
	}

	public int getProbeDestDevPort() {
		return probeDestDevPort;
	}

	public void setProbeDestDevPort(int probeDestDevPort) {
		this.probeDestDevPort = probeDestDevPort;
	}
	
}
