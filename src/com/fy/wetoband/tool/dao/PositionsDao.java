package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Warehouse;

public class PositionsDao {

	public boolean save(Connection conn, Positions po) throws SQLException {
		String sql = "insert into wh_positions(po_id, po_name, po_remark, wh_id) "
				+ "values("
				+ "'" + po.getPoId() + "',"
				+ "'" + po.getPoName() + "',"
				+ "'" + (po.getPoRemark()!=null?po.getPoRemark():"") + "',"
				+ "'" + po.getWarehouse().getWhId() + "'"
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
	
	public boolean update(Connection conn, Positions po) throws SQLException {
		StringBuffer str = new StringBuffer("update wh_positions p set ");
		if (po.getPoName()!= null && !"".equals(po.getPoName())) {
			str.append("p.po_name = '" + po.getPoName() + "',");
		}
		if (po.getPoRemark()!= null) {
			str.append("p.po_remark = '" + po.getPoRemark() + "',");
		}
		str.deleteCharAt(str.length() - 1);
		str.append(" where p.po_id like '" + po.getPoId() + "';");
		
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
		String sql = "update wh_positions p set p.po_discard = 'Y' where p.po_id like "
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
	
	public boolean deleteBySuper(Connection conn, String whId) throws SQLException {
		String sql = "update wh_positions p set p.po_discard = 'Y' where p.wh_id like '" + whId + "'";
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
	
	public Positions findById(Connection conn, String id) throws SQLException {
		String sql = "select p.po_name, p.po_remark, p.po_discard, p.wh_id, w.wh_name from wh_positions p left join "
				+ " wh_warehouse w on p.wh_id = w.wh_id where p.po_id like '" + id + "';";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		Positions po = null;
		if (rs.next()) {
			po = new Positions();
			Warehouse wh = new Warehouse();
			po.setPoId(id);
			po.setPoName(rs.getString("po_name"));
			po.setPoRemark(rs.getString("po_remark"));
			po.setPoDiscard(rs.getString("po_discard"));
			wh.setWhId(rs.getString("wh_id"));
			wh.setWhName(rs.getString("wh_name"));
			po.setWarehouse(wh);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return po;
	}
	
	public List<Positions> get(Connection conn, String discard, String wh_id) throws SQLException {
		String sql = "select p.po_id, p.po_name from wh_positions p where 1=1 ";
	
		if (discard != null ){
			if (discard.toUpperCase().equals("Y")) {
				sql +=" and p.po_discard like 'Y' ";
			} else {
				sql +=" and (p.po_discard like 'N' or p.po_discard is null) ";
			}
		}
		
		if (wh_id != null && !wh_id.equals("")) {
			sql +=" and p.wh_id like '" + wh_id +"' ";
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		
		List<Positions> list = new ArrayList<Positions>();
		Positions po = null;
		while (rs.next()) {
			po = new Positions();
			po.setPoId(rs.getString("po_id"));
			po.setPoName(rs.getString("po_name"));
			list.add(po);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	public List<Positions> get(Connection conn, String wh_id) throws SQLException {
		return get(conn,"N",wh_id);
	}
	
	public List<Positions> get(Connection conn) throws SQLException {
		return get(conn,"N",null);
	}
	
	public List<Positions> list(Connection conn, String poName, String discard, String wh_id, int page, int rows) throws SQLException {
		String sql = "select p.po_id, p.po_name, p.po_remark, p.po_discard, p.wh_id, w.wh_name from wh_positions p left join "
				+ " wh_warehouse w on p.wh_id = w.wh_id where 1=1 ";
		if (poName != null && !poName.equals("")) {
			sql +=" and p.po_Name like '%" + poName +"%' ";
		}
		if (discard != null && (discard.toUpperCase().equals("Y"))) {
			sql +=" and p.po_discard like 'Y' ";
		} else {
			sql +=" and (p.po_discard like 'N' or p.po_discard is null)  ";
		}
		if (wh_id != null && !wh_id.equals("")) {
			sql +=" and p.wh_id like '" + wh_id +"' ";
		}
		sql += " limit " + (page-1)*rows + "," + rows;
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		List<Positions> list = new ArrayList<Positions>();
		Positions po = null;
		Warehouse wh = null;
		while (rs.next()) {
			po = new Positions();
			wh = new Warehouse();
			po.setPoId(rs.getString("po_id"));
			po.setPoName(rs.getString("po_name"));
			po.setPoRemark(rs.getString("po_remark"));
			po.setPoDiscard(rs.getString("po_discard"));
			wh.setWhId(rs.getString("wh_id"));
			wh.setWhName(rs.getString("wh_name"));
			po.setWarehouse(wh);
			list.add(po);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	public List<Positions> listCard(Connection conn,String poName, String wh_id, int page, int rows) throws SQLException {
		return list(conn, poName, "N", wh_id, page, rows);
	}
	
	public int count(Connection conn, String poName, String discard, String wh_id) throws SQLException {
		String sql = "select count(po_id) from wh_positions p where 1=1 ";
		if (discard != null && (discard.toUpperCase().equals("Y"))) {
			sql +=" and p.po_discard like 'Y' ";
		} else {
			sql +=" and (p.po_discard like 'N' or p.po_discard is null)  ";
		}
		if (wh_id != null && !wh_id.equals("")) {
			sql +=" and wh_id like '" + wh_id + "'";
		}
		if (poName != null && !poName.equals("")) {
			sql +=" and p.po_Name like '%" + poName +"%' ";
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

	public int countCard(Connection conn, String poName, String wh_id) throws SQLException {
		return count(conn, poName, "N", wh_id);
	}
	
	public int countCard(Connection conn) throws SQLException {
		return count(conn, null, "N", null);
	}
}
