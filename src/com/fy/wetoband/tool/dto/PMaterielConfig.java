package com.fy.wetoband.tool.dto;


public class PMaterielConfig {
	
	private String cmId;    // 物料配置信息ID
	private String maId;    // 物料ID
	private String maName;  // 物料名称
	private String cmRemark;  // 记录备注
	private String cmDiscard;  // 记录是否失效,Y失效,N生效
	private String shelfId;  // 所属货架ID
	private String shelfName;  // 所属货架名称
	private String positionsId; // 所属仓位ID
	private String positionsName; // 所属仓位名称
	private String warehouseId; // 所属仓库ID
	private String warehouseName; // 所属仓库名称
	private String maModel;  // 物料型号
	private String maSpec;   // 物料规格

	public PMaterielConfig() {
		super();
	}
	
	public PMaterielConfig(String cmId, String cmRemark) {
		super();
		this.cmId = cmId;
		this.cmRemark = cmRemark;
	}

	public PMaterielConfig(String maId, String shelfId, String cmRemark) {
		super();
		this.maId = maId;
		this.cmRemark = cmRemark;
		this.shelfId = shelfId;
	}


	public String getMaId() {
		return maId;
	}
	public void setMaId(String maId) {
		this.maId = maId;
	}
	public String getShelfId() {
		return shelfId;
	}
	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}
	public String getShelfName() {
		return shelfName;
	}
	public void setShelfName(String shelfName) {
		this.shelfName = shelfName;
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
	public String getMaName() {
		return maName;
	}
	public void setMaName(String maName) {
		this.maName = maName;
	}
	public String getMaModel() {
		return maModel;
	}
	public void setMaModel(String maModel) {
		this.maModel = maModel;
	}
	public String getCmId() {
		return cmId;
	}
	public void setCmId(String cmId) {
		this.cmId = cmId;
	}
	public String getCmRemark() {
		return cmRemark;
	}
	public void setCmRemark(String cmRemark) {
		this.cmRemark = cmRemark;
	}
	public String getCmDiscard() {
		return cmDiscard;
	}
	public void setCmDiscard(String cmDiscard) {
		if (cmDiscard != null && "Y".equals(cmDiscard)) {
			this.cmDiscard = "废弃";
		} else {
			this.cmDiscard = "未废弃";
		}
	}
	public String getMaSpec() {
		return maSpec;
	}
	public void setMaSpec(String maSpec) {
		this.maSpec = maSpec;
	}
}
