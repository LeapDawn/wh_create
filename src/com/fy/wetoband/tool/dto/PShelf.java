package com.fy.wetoband.tool.dto;

public class PShelf {

	private String shId; // 货架ID
	private String positionsId; // 所属仓位ID
	private String positionsName; // 所属仓位名称
	private String warehouseId; // 所属仓库ID
	private String warehouseName; // 所属仓库名称
	private String shName; // 货架名称
	private String shRemark; // 备注
	private String shDiscard; // 是否废弃,废弃/未废弃
	
	public PShelf() {
		super();
	}
	
	public PShelf(String positionsId, String shName, String shRemark) {
		super();
		this.positionsId = positionsId;
		this.shName = shName;
		this.shRemark = shRemark;
	}

	public PShelf(String shId, String positionsId, String shName,
			String shRemark) {
		super();
		this.shId = shId;
		this.positionsId = positionsId;
		this.shName = shName;
		this.shRemark = shRemark;
	}

	public PShelf(String shName, String shRemark) {
		super();
		this.shName = shName;
		this.shRemark = shRemark;
	}

	public String getShId() {
		return shId;
	}
	public void setShId(String shId) {
		this.shId = shId;
	}
	public String getPositionsId() {
		return positionsId;
	}
	public void setPositionsId(String positionsId) {
		this.positionsId = positionsId;
	}
	public String getPositionsName() {
		return positionsName;
	}
	public void setPositionsName(String positionsName) {
		this.positionsName = positionsName;
	}
	
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getShName() {
		return shName;
	}
	public void setShName(String shName) {
		this.shName = shName;
	}
	public String getShRemark() {
		return shRemark;
	}
	public void setShRemark(String shRemark) {
		this.shRemark = shRemark;
	}
	public String getShDiscard() {
		return shDiscard;
	}
	public void setShDiscard(String shDiscard) {
		this.shDiscard = shDiscard;
	}
	public void setShDiscard(Boolean shDiscard) {
		if (shDiscard) {
			this.shDiscard = "废弃";
		} else {
			this.shDiscard = "未废弃";
		}
	}
}
