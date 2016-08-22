package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Shelf;
import com.fy.wetoband.tool.entity.Warehouse;

public class ShelfDao {
	
	public boolean save(Connection conn, Shelf sh) throws SQLException {
		String sql = "insert into wh_shelf(shelf_id, shelf_description, shelf_remark, po_id) "
				+ "values("
				+ "'" + sh.getShId() + "',"
				+ "'" + sh.getShName() + "',"
				+ "'" + (sh.getShRemark()!=null?sh.getShRemark():"") + "',"
				+ "'" + sh.getPositions().getPoId() + "'"
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
	
	public boolean update(Connection conn, Shelf sh) throws SQLException {
		StringBuffer str = new StringBuffer("update wh_shelf s set ");
		if (sh.getShName()!= null && !"".equals(sh.getShName())) {
			str.append("s.shelf_description = '" + sh.getShName() + "',");
		}
		if (sh.getShRemark()!= null) {
			str.append("s.shelf_remark = '" + sh.getShRemark() + "',");
		}
		if (sh.getPositions() != null && sh.getPositions().getPoId() != null && !"".equals(sh.getPositions().getPoId())) {
			str.append("s.po_id = '" + sh.getPositions().getPoId() + "',");
		}
		str.deleteCharAt(str.length() - 1);
		str.append(" where s.shelf_id like '" + sh.getShId() + "';");
		
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
		String sql = "update wh_shelf s set s.sh_discard = 1 where s.shelf_id like "
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
	
	public boolean deleteBySuper(Connection conn, String whId, String poId) throws SQLException {
		String sql = "update wh_shelf s, wh_positions p set s.sh_discard = 1 where s.po_id=p.po_id ";
		if (whId != null && !whId.equals("")) {
			sql += " and p.wh_id like '" + whId + "'";
		}
		if (poId != null && !poId.equals("")) {
			sql += " and s.po_id like '" + poId + "'";
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
	
	public Shelf findById(Connection conn, String id) throws SQLException {
		String sql = "select s.shelf_description as sh_name, s.shelf_remark as sh_remark, s.sh_discard, s.po_id, p.po_name, p.wh_id, w.wh_name "
				+ " from wh_shelf s "
				+ " left join wh_positions p on s.po_id = p.po_id "
				+ " left join wh_warehouse w on p.wh_id = w.wh_id "
				+ " where s.shelf_id like '" + id + "';";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		Shelf sh = null;
		if (rs.next()) {
			sh = new Shelf();
			Positions po = new Positions();
			Warehouse wh = new Warehouse();
			sh.setShId(id);
			sh.setShName(rs.getString("sh_name"));
			sh.setShRemark(rs.getString("sh_remark"));
			sh.setShelfDiscard(rs.getBoolean("sh_discard"));
			po.setPoId(rs.getString("po_id"));
			po.setPoName(rs.getString("po_name"));
			wh.setWhId(rs.getString("wh_id"));
			wh.setWhName(rs.getString("wh_name"));
			po.setWarehouse(wh);
			sh.setPositions(po);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return sh;
	}
	
	/**
	 * 获取所有货架的ID与名称(可指定某个仓库/仓位)
	 * @param conn
	 * @param discard
	 * @param po_id
	 * @param wh_id
	 * @return
	 * @throws SQLException
	 */
	public List<Shelf> get(Connection conn, String discard, String po_id) throws SQLException {
		String sql = "select s.shelf_id as sh_id, s.shelf_description as sh_name from wh_shelf s where 1=1 ";
		if (discard != null ){
			if (discard.toUpperCase().equals("Y")) {
				sql +=" and s.sh_discard = 1 ";
			} else {
				sql +=" and (s.sh_discard = 0 or s.sh_discard is null) ";
			}
		}
		if (po_id != null && !po_id.equals("")) {
			sql +=" and s.po_id like '" + po_id +"' ";
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		
		List<Shelf> list = new ArrayList<Shelf>();
		Shelf sh = null;
		while (rs.next()) {
			sh = new Shelf();
			sh.setShId(rs.getString("sh_id"));
			sh.setShName(rs.getString("sh_name"));
			list.add(sh);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	public List<Shelf> getByPo(Connection conn, String po_id) throws SQLException {
		return get(conn, "N", po_id);
	}
	
	
	public List<Shelf> getAllCard(Connection conn) throws SQLException {
		return get(conn, "N", null);
	}
	
	public List<Shelf> list(Connection conn, String shName, String discard, String po_id, String wh_id, int page, int rows) throws SQLException {
		String sql = "select s.shelf_id as sh_id, s.shelf_description as sh_name, s.shelf_remark as sh_remark, s.sh_discard, s.po_id, p.po_name, p.wh_id, w.wh_name "
				+ " from wh_shelf s "
				+ " left join wh_positions p on s.po_id = p.po_id "
				+ " left join wh_warehouse w on p.wh_id = w.wh_id "
				+ " where 1=1 ";
		if (discard != null ){
			if (discard.toUpperCase().equals("Y")) {
				sql +=" and s.sh_discard = 1 ";
			} else {
				sql +=" and (s.sh_discard = 0 or s.sh_discard is null) ";
			}
		}
		if (shName != null && !shName.equals("")) {
			sql +=" and s.shelf_description like '%" + shName +"%' ";
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
		
		List<Shelf> list = new ArrayList<Shelf>();
		Shelf sh = null;
		Positions po = null;
		Warehouse wh = null;
		while (rs.next()) {
			sh = new Shelf();
			po = new Positions();
			wh = new Warehouse();
			sh.setShId(rs.getString("sh_id"));
			sh.setShName(rs.getString("sh_name"));
			sh.setShRemark(rs.getString("sh_remark"));
			sh.setShelfDiscard(rs.getBoolean("sh_discard"));
			po.setPoId(rs.getString("po_id"));
			po.setPoName(rs.getString("po_name"));
			wh.setWhId(rs.getString("wh_id"));
			wh.setWhName(rs.getString("wh_name"));
			po.setWarehouse(wh);
			sh.setPositions(po);
			list.add(sh);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	public List<Shelf> listCard(Connection conn, String shName,String po_id, String wh_id, int page, int rows) throws SQLException {
		return list(conn,shName, "N", po_id, wh_id, page, rows);
	}
	
	public List<Shelf> listCard(Connection conn,String shName, String po_id, int page, int rows) throws SQLException {
		return list(conn, shName, "N", po_id, null, page, rows);
	}
	
//	public List<Shelf> listCard(Connection conn, int page, int rows) throws SQLException {
//		return list(conn, "N", null, null, page, rows);
//	}
	
	public int count(Connection conn, String shName, String discard, String po_id, String wh_id) throws SQLException {
		String sql = "select count(s.shelf_id) from wh_shelf s "
				+ " left join wh_positions p on s.po_id = p.po_id "
				+ " where 1=1 ";
		if (discard != null ){
			if (discard.toUpperCase().equals("Y")) {
				sql +=" and s.sh_discard = 1 ";
			} else {
				sql +=" and (s.sh_discard = 0 or s.sh_discard is null) ";
			}
		}
		if (po_id != null && !po_id.equals("")) {
			sql +=" and s.po_id like '" + po_id + "'";
		}
		if (wh_id != null && !wh_id.equals("")) {
			sql +=" and p.wh_id like '" + wh_id + "'";
		}
		if (shName != null && !shName.equals("")) {
			sql +=" and s.shelf_description like '%" + shName +"%' ";
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

	public int countCard(Connection conn, String shName, String po_id) throws SQLException {
		return count(conn, shName, "N", po_id, null);
	}
	
	public int countCard(Connection conn) throws SQLException {
		return count(conn, null, "N", null, null);
	}
}
