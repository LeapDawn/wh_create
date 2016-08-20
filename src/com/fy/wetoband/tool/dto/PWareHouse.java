package com.fy.wetoband.tool.dto;

public class PWareHouse {

	private String whId; // 仓库ID
	private String whName; // 仓库名称
	private String address; // 仓库地址
	private String personName; // 仓库保管员
	private String whRemark; // 备注
	private String whDiscard; // 是否废弃,废弃/未废弃
	private String whType; // 仓库类型
	private String whTel; // 仓库联系人

	public PWareHouse() {
		super();
	}


	public PWareHouse(String whName, String address, String personName,
			String whRemark, String whType, String whTel) {
		super();
		this.whName = whName;
		this.address = address;
		this.personName = personName;
		this.whRemark = whRemark;
		this.whType = whType;
		this.whTel = whTel;
	}


	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getWhRemark() {
		return whRemark;
	}

	public void setWhRemark(String whRemark) {
		this.whRemark = whRemark;
	}

	public String getWhDiscard() {
		return whDiscard;
	}

	public void setWhDiscard(String whDiscard) {
		this.whDiscard = whDiscard;
	}

	public String getWhType() {
		return whType;
	}

	public void setWhType(String whType) {
		this.whType = whType;
	}

	public String getWhTel() {
		return whTel;
	}

	public void setWhTel(String whTel) {
		this.whTel = whTel;
	}

	@Override
	public String toString() {
		return "PWareHouse [whId=" + whId + ", whName=" + whName + ", address="
				+ address + ", personName=" + personName + ", whRemark="
				+ whRemark + ", whDiscard=" + whDiscard + ", whType=" + whType
				+ ", whTel=" + whTel + "]";
	}
}
