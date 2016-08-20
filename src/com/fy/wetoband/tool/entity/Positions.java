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
 * WhPositions entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "wh_positions", catalog = "db_xinzegu")
public class Positions implements java.io.Serializable {

	// Fields

	private String poId;   // 仓位ID
	private Warehouse warehouse;  // 所属仓库
	private String poName;  // 仓位名称
	private String poRemark;  // 备注
	private String poDiscard;  // 是否废弃,Y/N
	// 货架集合
	private Set<Shelf> shelfs = new HashSet<Shelf>(0);

	// Constructors

	/** default constructor */
	public Positions() {
	}

	/** minimal constructor */
	public Positions(String poId, Warehouse whWarehouse) {
		this.poId = poId;
		this.warehouse = whWarehouse;
	}

	/** full constructor */
	public Positions(String poId, Warehouse whWarehouse, String poName,
			String poRemark, String poDiscard, Set<Shelf> whShelfs) {
		this.poId = poId;
		this.warehouse = whWarehouse;
		this.poName = poName;
		this.poRemark = poRemark;
		this.poDiscard = poDiscard;
		this.shelfs = whShelfs;
	}

	// Property accessors
	@Id
	@Column(name = "po_id", unique = true, nullable = false, length = 20)
	public String getPoId() {
		return this.poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wh_id", nullable = false)
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse whWarehouse) {
		this.warehouse = whWarehouse;
	}

	@Column(name = "po_name", length = 100)
	public String getPoName() {
		return this.poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
	}

	@Column(name = "po_remark")
	public String getPoRemark() {
		return this.poRemark;
	}

	public void setPoRemark(String poRemark) {
		this.poRemark = poRemark;
	}

	@Column(name = "po_discard", length = 1)
	public String getPoDiscard() {
		return this.poDiscard;
	}

	public void setPoDiscard(String poDiscard) {
		this.poDiscard = poDiscard;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "whPositions")
	public Set<Shelf> getShelfs() {
		return this.shelfs;
	}

	public void setShelfs(Set<Shelf> whShelfs) {
		this.shelfs = whShelfs;
	}

	@Override
	public String toString() {
		return "Positions [poId=" + poId + ", warehouse=" + warehouse
				+ ", poName=" + poName + ", poRemark=" + poRemark
				+ ", poDiscard=" + poDiscard + ", shelfs=" + shelfs + "]";
	}

	
	
}