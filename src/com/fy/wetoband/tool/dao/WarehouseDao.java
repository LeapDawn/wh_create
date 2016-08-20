package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.entity.Warehouse;

public class WarehouseDao {
	
	/**
	 * 新增仓库
	 * @param conn
	 * @param wh
	 * @return
	 * @throws SQLException
	 * @throws ToolException 
	 */
	public boolean save(Connection conn, Warehouse wh) throws SQLException, ToolException {
		try {
			wh.setPersonName(getKeeper(conn, wh.getPersonName()));
		} catch (ToolException e) {
			throw e;
		}
		String sql = "insert into wh_warehouse(wh_id, wh_name, wh_addr, keeper, wh_type, wh_remark, wh_tel) "
				+ "values("
				+ "'" + wh.getWhId() + "',"
				+ "'" + wh.getWhName() + "',"
				+ "'" + wh.getAddress() + "',"
				+ (wh.getPersonName()!=null ? "'" + wh.getPersonName() + "'," : "null,")
//				+ "'" + (wh.getWhName()!=null?wh.getWhName():"") + "',"
				+ "'" + wh.getWhType() + "',"
				+ "'" + (wh.getWhRemark()!=null?wh.getWhRemark():"") + "',"
				+ "'" + (wh.getWhTel()!=null?wh.getWhTel():"") + "'"
				+ ");";
		System.out.println(sql);
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
	
	/**
	 * 修改仓库
	 * @param conn
	 * @param wh
	 * @return
	 * @throws SQLException
	 * @throws ToolException 
	 */
	public boolean update(Connection conn, Warehouse wh) throws SQLException, ToolException {
		try {
			wh.setPersonName(getKeeper(conn, wh.getPersonName()));
		} catch (ToolException e) {
			throw e;
		}
		StringBuffer str = new StringBuffer("update wh_warehouse w set ");
		if (wh.getWhName()!= null && !"".equals(wh.getWhName())) {
			str.append("w.wh_name = '" + wh.getWhName() + "',");
		}
		if (wh.getPersonName()!= null) {
			if ("".equals(wh.getPersonName())) {
				str.append("w.keeper = null,");
			} else {
				str.append("w.keeper = '" + wh.getPersonName() + "',");
			}
		}
		if (wh.getWhRemark()!= null) {
			str.append("w.wh_remark = '" + wh.getWhRemark() + "',");
		}
		if (wh.getWhTel()!= null) {
			str.append("w.wh_tel = '" + wh.getWhTel() + "',");
		}
		if (wh.getWhType()!= null && !"".equals(wh.getWhType())) {
			str.append("w.wh_type = '" + wh.getWhType() + "',");
		}
		if (wh.getAddress()!= null) {
			str.append("w.wh_addr = '" + wh.getAddress() + "',");
		}
		
		str.deleteCharAt(str.length() - 1);
		str.append(" where w.wh_id like '" + wh.getWhId() + "';");
		
		String sql = str.toString();
		
		System.out.println(sql);
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
	
	/**
	 * 删除仓库(修改删除标志)
	 * @author zqh
	 * @param conn
	 * @param id
	 * @throws SQLException
	 */
	public boolean delete(Connection conn, String id) throws SQLException {
		String sql = "update wh_warehouse w set w.wh_discard = 1 where w.wh_id like "
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
	
	/**
	 * 查询单个仓库
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Warehouse findById(Connection conn, String id) throws SQLException {
		String sql = "select w.wh_name, w.wh_addr as address, e.employee_name as personName, w.wh_remark, w.wh_discard,"
				+ " w.wh_type, w.wh_tel from wh_warehouse w "
				+ " left join hr_employee e on w.keeper = e.employee_no"
				+ " where w.wh_id like '" + id + "';";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		Warehouse wh = null;
		if (rs.next()) {
			wh = new Warehouse();
			wh.setWhId(id);
			wh.setWhName(rs.getString("wh_name"));
			wh.setAddress(rs.getString("address"));
			wh.setPersonName(rs.getString("personName"));
			wh.setWhRemark(rs.getString("wh_remark"));
			wh.setWhDiscard(rs.getBoolean("wh_discard"));;
			wh.setWhType(rs.getString("wh_type"));
			wh.setWhTel(rs.getString("wh_tel"));
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return wh;
	}
	
//	/**
//	 * 获取所有仓库
//	 * @author
//	 * @param conn
//	 * @param discard
//	 * @return
//	 * @throws SQLException
//	 */
//	public List<Warehouse> getAll(Connection conn, String discard) throws SQLException {
//		String sql = "select w.wh_id, w.wh_name, w.wh_addr as address, e.employee_name as personName, w.wh_remark,"
//				+ " w.wh_discard,"
//				+ " w.wh_type, w.wh_tel from wh_warehouse w "
//				+ " left join hr_employee e on w.keeper = e.employee_no ";
//		if (discard != null ){
//			if (discard.toUpperCase().equals("Y")) {
//				sql +=" where wh_discard = 1 ";
//			} else {
//				sql +=" where wh_discard != 1 ";
//			}
//		}
//		
//		PreparedStatement pt = conn.prepareStatement(sql);
//		ResultSet rs = pt.executeQuery();
//		List<Warehouse> list = new ArrayList<Warehouse>();
//		Warehouse wh = null;
//		while (rs.next()) {
//			wh = new Warehouse();
//			wh.setWhId(rs.getString("wh_id"));
//			wh.setWhName(rs.getString("wh_name"));
//			list.add(wh);
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
//	public List<Warehouse> getAllCard(Connection conn) throws SQLException {
//		return getAll(conn, "N");
//	}
	
	/**
	 * 查询仓库
	 * @param conn
	 * @param id
	 * @param discard
	 * @return
	 * @throws SQLException
	 */
	public List<Warehouse> list(Connection conn, String discard, int page, int rows) throws SQLException {
		String sql = "select w.wh_id, w.wh_name, w.wh_addr as address, e.employee_name as personName, w.wh_remark,"
				+ " w.wh_discard,"
				+ " w.wh_type, w.wh_tel from wh_warehouse w "
				+ " left join hr_employee e on w.keeper = e.employee_no ";
		if (discard != null ){
			if (discard.toUpperCase().equals("Y")) {
				sql +=" where wh_discard = 1 ";
			} else {
				sql +=" where wh_discard = 0 or wh_discard is null ";
			}
		}
		sql += " limit " + (page-1)*rows + "," + rows;
		System.out.println(sql);
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		List<Warehouse> list = new ArrayList<Warehouse>();
		Warehouse wh = null;
		while (rs.next()) {
			wh = new Warehouse();
			wh.setWhId(rs.getString("wh_id"));
			wh.setWhName(rs.getString("wh_name"));
			wh.setAddress(rs.getString("address"));
			wh.setPersonName(rs.getString("personName"));
			wh.setWhRemark(rs.getString("wh_remark"));
			wh.setWhDiscard(rs.getBoolean("wh_discard"));;
			wh.setWhType(rs.getString("wh_type"));
			wh.setWhTel(rs.getString("wh_tel"));
			list.add(wh);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	public List<Warehouse> listCard(Connection conn, int page, int rows) throws SQLException {
		return list(conn, "N", page, rows);
	}
	
	/**
	 * 查询仓库数量
	 * @author
	 * @return
	 * @throws SQLException 
	 */
	public int count(Connection conn,String discard) throws SQLException {
		String sql = "select count(wh_id) from wh_warehouse ";
		if (discard != null ){
			if (discard.toUpperCase().equals("Y")) {
				sql +=" where wh_discard = 1 ";
			} else {
				sql +=" where wh_discard = 0 or wh_discard is null ";
			}
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
	
	public int countCard(Connection conn) throws SQLException {
		return count(conn, "N");
	}
	
	// 获取仓库保管员的ID
	private String getKeeper(Connection conn, String name) throws ToolException, SQLException {
		if (name == null || name.equals("")) {
			return null;
		}
		String sql = "select employee_no from hr_employee where employee_name like '" + name + "';";
		System.out.println(sql);
		PreparedStatement pt;
		String result = null;
		pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		if (rs.next()) {
			result = rs.getString("employee_no");
			System.out.println("result : " +result);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		if (result == null) {
			throw new ToolException("");
		}
		return result;
	}
	
}
