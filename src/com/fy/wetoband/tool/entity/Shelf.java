package com.fy.wetoband.tool.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * WhShelf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "wh_shelf", catalog = "db_xinzegu")
public class Shelf implements java.io.Serializable {

	// Fields

	private String shId;  // 货架ID
	private Positions positions;  // 所属仓位
	private String shName;  // 货架名称
	private String shRemark;  // 备注
	private Boolean shelfDiscard;  // 是否废弃,Y/N
	// 物料集合
	private Set<MaterielConfig> materiels = new HashSet<MaterielConfig>(0);

	// Constructors

	/** default constructor */
	public Shelf() {
	}

	/** minimal constructor */
	public Shelf(String shId, Positions whPositions) {
		this.shId = shId;
		this.positions = whPositions;
	}

	// Property accessors
	@Id
	@Column(name = "sh_id", unique = true, nullable = false, length = 20)
	public String getShId() {
		return this.shId;
	}

	public void setShId(String shId) {
		this.shId = shId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "po_id", nullable = false)
	public Positions getPositions() {
		return this.positions;
	}

	public void setPositions(Positions whPositions) {
		this.positions = whPositions;
	}

	@Column(name = "sh_name", length = 100)
	public String getShName() {
		return this.shName;
	}

	public void setShName(String shName) {
		this.shName = shName;
	}

	@Column(name = "sh_remark")
	public String getShRemark() {
		return this.shRemark;
	}

	public void setShRemark(String shRemark) {
		this.shRemark = shRemark;
	}

	public Boolean getShelfDiscard() {
		return this.shelfDiscard;
	}

	public void setShelfDiscard(Boolean shDiscard) {
		this.shelfDiscard = shDiscard;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "whShelf")
	public Set<MaterielConfig> getMateriels() {
		return this.materiels;
	}

	public void setMateriels(Set<MaterielConfig> whMateriels) {
		this.materiels = whMateriels;
	}

	@Override
	public String toString() {
		return "Shelf [shId=" + shId + ", positions=" + positions + ", shName="
				+ shName + ", shRemark=" + shRemark + ", shDiscard="
				+ shelfDiscard + ", materiels=" + materiels + "]";
	}

}