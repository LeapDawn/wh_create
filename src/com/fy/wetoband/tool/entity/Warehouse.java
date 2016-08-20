package com.fy.wetoband.tool.entity;

public class Warehouse implements java.io.Serializable {

	private String whId; // 仓库ID
	private String whName; // 仓库名称
	private String address; // 仓库地址,允许为空
	private String personName; // 仓库保管员 ,允许为空
	private String whRemark; // 备注,允许为空
	private String whDiscard; // 是否废弃,true/false
	private String whType; // 仓库类型
	private String whTel; // 仓库联系人号码 ,允许为空

	public Warehouse() {
		super();
	}

	public Warehouse(String whId) {
		super();
		this.whId = whId;
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

	public void setWhDiscard(Boolean whDiscard) {
		if (whDiscard) {
			this.whDiscard  = "废弃";
		}
		else  {
			this.whDiscard  = "未废弃";
		}
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
		return "Warehouse [whId=" + whId + ", whName=" + whName + ", address="
				+ address + ", personName=" + personName + ", whRemark="
				+ whRemark + ", whDiscard=" + whDiscard + ", whType=" + whType
				+ ", whTel=" + whTel + "]";
	}
}