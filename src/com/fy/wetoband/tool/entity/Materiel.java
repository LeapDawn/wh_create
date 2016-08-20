package com.fy.wetoband.tool.entity;

public class Materiel {

	private String ma_id; // 物料ID
	private String ma_name; // 物料名称
	private String ma_model; // 物料型号
	private String ma_spec; // 物料规格
	
	public Materiel() {
		super();
	}
	

	public Materiel(String ma_id) {
		super();
		this.ma_id = ma_id;
	}

	public String getMa_name() {
		return ma_name;
	}

	public void setMa_name(String ma_Name) {
		this.ma_name = ma_Name;
	}

	public String getMa_id() {
		return ma_id;
	}

	public void setMa_id(String ma_id) {
		this.ma_id = ma_id;
	}

	public String getMa_model() {
		return ma_model;
	}

	public void setMa_model(String ma_model) {
		this.ma_model = ma_model != null ? ma_model : "";
	}

	public String getMa_spec() {
		return ma_spec;
	}

	public void setMa_spec(String ma_spec) {
		this.ma_spec = ma_spec != null ? ma_spec : "";
	}

	@Override
	public String toString() {
		return "Materiel [ma_id=" + ma_id + ", ma_name=" + ma_name
				+ ", ma_model=" + ma_model + ", ma_spec=" + ma_spec + "]";
	}
	
	
}
