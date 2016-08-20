package com.fy.wetoband.tool.dto;


public class PPositions {
	
	private String poId;   // 仓位ID
	private String warehouseId;  // 所属仓库ID
	private String warehouseName;  // 所属仓库名称
	private String poName;  // 仓位名称
	private String poRemark;  // 备注
	private String poDiscard;  // 是否废弃,废弃/未废弃
	
	public PPositions() {
		super();
	}
	
	public PPositions(String warehouseId, String poName, String poRemark) {
		super();
		this.warehouseId = warehouseId;
		this.poName = poName;
		this.poRemark = poRemark;
	}

	public PPositions(String poName, String poRemark) {
		super();
		this.poName = poName;
		this.poRemark = poRemark;
	}

	public String getPoId() {
		return poId;
	}
	public void setPoId(String poId) {
		this.poId = poId;
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
	public String getPoName() {
		return poName;
	}
	public void setPoName(String poName) {
		this.poName = poName;
	}
	public String getPoRemark() {
		return poRemark;
	}
	public void setPoRemark(String poRemark) {
		this.poRemark = poRemark;
	}
	public String getPoDiscard() {
		return poDiscard;
	}
	public void setPoDiscard(String poDiscard) {
		if (poDiscard != null && "Y".equals(poDiscard)) {
			this.poDiscard  = "废弃";
		}
		else  {
			this.poDiscard  = "未废弃";
		}
	}
}	
