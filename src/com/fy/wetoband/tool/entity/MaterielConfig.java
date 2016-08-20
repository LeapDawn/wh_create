package com.fy.wetoband.tool.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WhConfigMateriel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "wh_config_materiel", catalog = "db_xinzegu")
public class MaterielConfig implements java.io.Serializable {

	// Fields

	private String cmId;    // 配置记录ID
	private Materiel ma;    // 物料实体
	private Shelf Shelf;    // 所属货架
	private String cmRemark;  // 备注信息
	private String cmDiscard;  // 记录是否失效,Y失效,N生效
	
	public MaterielConfig() {
	}

	public MaterielConfig(String cmId, Materiel ma,
			com.fy.wetoband.tool.entity.Shelf shelf, String cmRemark,
			String cmDiscard) {
		super();
		this.cmId = cmId;
		this.ma = ma;
		Shelf = shelf;
		this.cmRemark = cmRemark;
		this.cmDiscard = cmDiscard;
	}

	public String getCmId() {
		return cmId;
	}

	public void setCmId(String cmId) {
		this.cmId = cmId;
	}

	public Materiel getMa() {
		return ma;
	}

	public void setMa(Materiel ma) {
		this.ma = ma;
	}

	public String getCmRemark() {
		return cmRemark;
	}

	public void setCmRemark(String cmRemark) {
		this.cmRemark = cmRemark;
	}

	public Shelf getShelf() {
		return Shelf;
	}

	public void setShelf(Shelf shelf) {
		Shelf = shelf;
	}

	public String getCmDiscard() {
		return cmDiscard;
	}

	public void setCmDiscard(String cmDiscard) {
		this.cmDiscard = cmDiscard;
	}

	@Override
	public String toString() {
		return "MaterielConfig [cmId=" + cmId + ", ma=" + ma + ", Shelf="
				+ Shelf + ", cmRemark=" + cmRemark + ", cmDiscard=" + cmDiscard
				+ "]";
	}
}