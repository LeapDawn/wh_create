package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.entity.Materiel;
import com.fy.wetoband.tool.entity.MaterielConfig;
import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Shelf;
import com.fy.wetoband.tool.entity.Warehouse;

public class MaterielCongfigDao {

	public boolean save(Connection conn, MaterielConfig ma) throws SQLException {
		String sql = "insert into wh_config_materiel(cm_id, ma_id, cm_remark, sh_id) "
				+ "values("
				+ "'" + ma.getCmId() + "',"
				+ "'" + ma.getMa().getMa_id() + "',"
				+ "'" + (ma.getCmRemark()!=null?ma.getCmRemark():"") + "',"
				+ "'" + ma.getShelf().getShId() + "'"
				+ ");";
		PreparedStatement pt = conn.prepareStatement(sql);
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		return true;
	}
	
	public boolean update(Connection conn, MaterielConfig ma) throws SQLException {
		StringBuffer str = new StringBuffer("update wh_config_materiel m set ");
		if (ma.getCmRemark()!= null) {
			str.append("m.cm_remark = '" + ma.getCmRemark() + "',");
		}
		str.deleteCharAt(str.length() - 1);
		str.append(" where m.cm_id like '" + ma.getCmId() + "';");
		
		String sql = str.toString();
		
		PreparedStatement pt = conn.prepareStatement(sql);
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		return true;
	}
	
	public boolean delete(Connection conn, String id) throws SQLException {
		String sql = "update wh_config_materiel m set m.cm_discard='Y' where cm_id like "
				+ "'" + id + "';";
		PreparedStatement pt = conn.prepareStatement(sql);
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		return true;
	}
	
	public boolean deleteBysuper(Connection conn, String whId, String poId, String ShId) throws SQLException {
		String sql = "update wh_config_materiel m, wh_shelf s, wh_positions p set m.cm_discard='Y' "
				+ "where m.sh_id=s.shelf_id and s.po_id=p.po_id ";
		
		if (whId != null && !whId.equals("")) {
			sql += " and p.wh_id like '" + whId + "'";
		}
		if (poId != null && !poId.equals("")) {
			sql += " and s.po_id like '" + poId + "'";
		}
		if (ShId != null && !ShId.equals("")) {
			sql += " and m.sh_id like '" + ShId + "'";
		}
		
		PreparedStatement pt = conn.prepareStatement(sql);
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		return true;
	}
	
	public MaterielConfig findById(Connection conn, String id) throws SQLException {
		String sql = "select cm.ma_id, m.description as ma_name, t.description as ma_model, ms.description as ma_spec, cm.cm_remark, cm.sh_id, cm.cm_discard, "
				+ " s.shelf_description as sh_name,s.po_id, p.po_name, p.wh_id, w.wh_name "
				+ " from wh_config_materiel cm "
				+ " left join wh_shelf s on cm.sh_id = s.shelf_id "
				+ " left join wh_positions p on s.po_id = p.po_id "
				+ " left join wh_warehouse w on p.wh_id = w.wh_id "
				+ " left join bd_materials m on cm.ma_id = m.materials_id "
				+ " left join bd_spec ms on ms.spec_id = m.spec_id "
				+ " left join bd_type t on t.type_id = m.type_id "
				+ " where cm.cm_id like '" + id + "';";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		MaterielConfig cm = null;
		if (rs.next()) {
			cm = new MaterielConfig();
			Shelf sh = new Shelf();
			Positions po = new Positions();
			Warehouse wh = new Warehouse();
			Materiel ma = new Materiel();
			cm.setCmId(id);
			ma.setMa_id(rs.getString("ma_id"));
			ma.setMa_name(rs.getString("ma_name"));
			ma.setMa_model(rs.getString("ma_model"));
			ma.setMa_spec(rs.getString("ma_spec"));
			cm.setCmRemark(rs.getString("cm_remark"));
			cm.setCmDiscard(rs.getString("cm_discard"));
			sh.setShId(rs.getString("sh_id"));
			sh.setShName(rs.getString("sh_name"));
			po.setPoId(rs.getString("po_id"));
			po.setPoName(rs.getString("po_name"));
			wh.setWhId(rs.getString("wh_id"));
			wh.setWhName(rs.getString("wh_name"));
			po.setWarehouse(wh);
			sh.setPositions(po);
			cm.setShelf(sh);
			cm.setMa(ma);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return cm;
	}
	
//	public List<MaterielConfig> getAll(Connection conn, String sh_id, String po_id, String wh_id) throws SQLException {
//		String sql = "select m.ma_id, m.ma_name, m.ma_model from wh_materiel m "
//				+ " left join wh_shelf s on m.sh_id = s.sh_id "
//				+ " left join wh_positions p on s.po_id = p.po_id "
//				+ " where 1=1 ";
//		if (sh_id != null && !sh_id.equals("")) {
//			sql +=" and m.sh_id like '" + sh_id +"' ";
//		}
//		if (po_id != null && !po_id.equals("")) {
//			sql +=" and s.po_id like '" + po_id +"' ";
//		}
//		if (wh_id != null && !wh_id.equals("")) {
//			sql +=" and p.wh_id like '" + wh_id +"' ";
//		}
//		
//		PreparedStatement pt = conn.prepareStatement(sql);
//		ResultSet rs = pt.executeQuery();
//		
//		List<MaterielConfig> list = new ArrayList<MaterielConfig>();
//		MaterielConfig ma = null;
//		while (rs.next()) {
//			ma = new MaterielConfig();
//			ma.setMaId(rs.getString("ma_id"));
//			ma.setMaName(rs.getString("ma_name"));
//			ma.setMaModel(rs.getString("ma_model"));
//			list.add(ma);
//		}
//		if (rs!= null) {
//			rs.close();
//		}
//		if (pt != null) {
//			pt.close();
//		}
//		return list;
//	}
//
//	public List<MaterielConfig> getAll(Connection conn, String sh_id) throws SQLException {
//		return getAll(conn, sh_id, null, null);
//	}
//	
//	public List<MaterielConfig> getAll(Connection conn) throws SQLException {
//		return getAll(conn, null, null, null);
//	}
	
	public List<MaterielConfig> listAllMateriel(Connection conn, String maName, int page, int rows) throws SQLException {
		String sql = "select m.materials_id, m.description as ma_name, t.description as ma_model, ms.description as ma_spec "
				+ " from bd_materials m "
				+ " left join bd_spec ms on ms.spec_id = m.spec_id "
				+ " left join bd_type t on t.type_id = m.type_id where (m.isvalid=1 or m.isvalid is null) ";
		
		if (maName != null && !maName.equals("")) {
			sql += " and m.description like '%" + maName + "%' ";
		}
		
		sql += " limit " + (page-1)*rows + "," + rows;
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		
		List<MaterielConfig> list = new ArrayList<MaterielConfig>();
		MaterielConfig cm = null;
		Materiel ma = null;
		while (rs.next()) {
			cm = new MaterielConfig();
			ma = new Materiel();
			ma.setMa_id(rs.getString("materials_id"));
			ma.setMa_name(rs.getString("ma_name"));
			ma.setMa_model(rs.getString("ma_model"));
			ma.setMa_spec(rs.getString("ma_spec"));
			cm.setMa(ma);
			list.add(cm);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	
	public List<MaterielConfig> list(Connection conn, String discard, String maName, String sh_id, String po_id, String wh_id, int page, int rows) throws SQLException {
		String sql = "select cm.cm_id, cm.ma_id, m.description as ma_name, t.description as ma_model, ms.description as ma_spec, cm.cm_remark, cm.sh_id, cm.cm_discard, "
				+ " s.shelf_description as sh_name,s.po_id, p.po_name, p.wh_id, w.wh_name "
				+ " from wh_config_materiel cm "
				+ " left join wh_shelf s on cm.sh_id = s.shelf_id "
				+ " left join wh_positions p on s.po_id = p.po_id "
				+ " left join wh_warehouse w on p.wh_id = w.wh_id "
				+ " left join bd_materials m on cm.ma_id = m.materials_id "
				+ " left join bd_spec ms on ms.spec_id = m.spec_id "
				+ " left join bd_type t on t.type_id = m.type_id"
				+ " where 1=1 ";
		if (maName != null && !maName.equals("")) {
			sql += " and m.description like '%" + maName + "%' ";
		}
		if (discard != null && (discard.toUpperCase().equals("Y") || discard.toUpperCase().equals("N"))) {
			sql +=" and cm.cm_discard like '" + discard +"' ";
		}
		if (sh_id != null && !sh_id.equals("")) {
			sql +=" and cm.sh_id like '" + sh_id +"' ";
		}
		if (po_id != null && !po_id.equals("")) {
			sql +=" and s.po_id like '" + po_id +"' ";
		}
		if (wh_id != null && !wh_id.equals("")) {
			sql +=" and p.wh_id like '" + wh_id +"' ";
		}
		sql += " limit " + (page-1)*rows + "," + rows;
		
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		
		List<MaterielConfig> list = new ArrayList<MaterielConfig>();
		MaterielConfig cm = null;
		Shelf sh = null;
		Positions po = null;
		Warehouse wh = null;
		Materiel ma = null;
		while (rs.next()) {
			cm = new MaterielConfig();
			sh = new Shelf();
			po = new Positions();
			wh = new Warehouse();
			ma = new Materiel();
			cm.setCmId(rs.getString("cm_id"));
			ma.setMa_id(rs.getString("ma_id"));
			ma.setMa_name(rs.getString("ma_name"));
			ma.setMa_model(rs.getString("ma_model"));
			ma.setMa_spec(rs.getString("ma_spec"));
			cm.setCmRemark(rs.getString("cm_remark"));
			cm.setCmDiscard(rs.getString("cm_discard"));
			sh.setShId(rs.getString("sh_id"));
			sh.setShName(rs.getString("sh_name"));
			po.setPoId(rs.getString("po_id"));
			po.setPoName(rs.getString("po_name"));
			wh.setWhId(rs.getString("wh_id"));
			wh.setWhName(rs.getString("wh_name"));
			po.setWarehouse(wh);
			sh.setPositions(po);
			cm.setShelf(sh);
			cm.setMa(ma);
			list.add(cm);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	public List<MaterielConfig> listCard(Connection conn, String sh_id, int page, int rows) throws SQLException {
		return list(conn, "N", null, sh_id, null, null, page, rows);
	}
	
	public List<MaterielConfig> listCard(Connection conn, int page, int rows) throws SQLException {
		return list(conn, "N", null, null, null, null, page, rows);
	}
	
	public List<MaterielConfig> listCard(Connection conn, String maName, String sh_id, int page, int rows) throws SQLException {
		return list(conn, "N", maName, sh_id, null, null, page, rows);
	}
	
	public int count(Connection conn,String maName, String discard, String sh_id, String po_id, String wh_id) throws SQLException {
		String sql = "select count(cm.cm_id) from wh_config_materiel cm "
				+ " left join wh_shelf s on cm.sh_id = s.shelf_id "
				+ " left join wh_positions p on s.po_id = p.po_id "
				+ " left join bd_materials m on cm.ma_id = m.materials_id "
				+ " where 1=1 ";
		if (maName != null && !maName.equals("")) {
			sql += " and m.description like '%" + maName + "%' ";
		}
		if (discard != null && (discard.toUpperCase().equals("Y") || discard.toUpperCase().equals("N"))) {
			sql +=" and cm.cm_discard like '" + discard +"' ";
		}
		if (sh_id != null && !sh_id.equals("")) {
			sql +=" and cm.sh_id like '" + sh_id +"' ";
		}
		if (po_id != null && !po_id.equals("")) {
			sql +=" and s.po_id like '" + po_id +"' ";
		}
		if (wh_id != null && !wh_id.equals("")) {
			sql +=" and p.wh_id like '" + wh_id +"' ";
		}
		
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		int count = 0;
		if (rs.next()){
			count = rs.getInt(1);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return count;
	}
	
	public int countAllMateriels(Connection conn, String maName) throws SQLException {
		String sql = "select count(m.materials_id) from bd_materials m where (m.isvalid=1 or m.isvalid is null) ";
		
		if (maName != null && !maName.equals("")) {
			sql += " and m.description like '%" + maName + "%' ";
		}
		
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		int count = 0;
		if (rs.next()){
			count = rs.getInt(1);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return count;
	}

	public int countCard(Connection conn,String maName, String sh_id) throws SQLException {
		return count(conn, maName, "N", sh_id, null, null);
	}
	
	public int countCard(Connection conn) throws SQLException {
		return count(conn, null,"N", null, null, null);
	}
}
