package com.fy.wetoband.tool.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fy.wetoband.tool.commons.IDGenerator;
import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.dao.MaterielCongfigDao;
import com.fy.wetoband.tool.dao.PositionsDao;
import com.fy.wetoband.tool.dao.ShelfDao;
import com.fy.wetoband.tool.dao.WarehouseDao;
import com.fy.wetoband.tool.dto.PWareHouse;
import com.fy.wetoband.tool.dto.PageModel;
import com.fy.wetoband.tool.entity.Warehouse;

public class WarehouseService{
	
	private WarehouseDao whdao = new WarehouseDao();
	private PositionsDao podao = new PositionsDao();
	private ShelfDao shdao = new ShelfDao();
	private MaterielCongfigDao cmdao = new MaterielCongfigDao();
	
	private Connection conn = null;

	public WarehouseService() {
		this.conn = conn;
	}
	
	public WarehouseService(Connection conn) {
		this.conn = conn;
	}
	
	private void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 新增仓库 
	 * @param pwh 仓库信息实体
	 * @return
	 * @throws ToolException
	 */
	public boolean addWareHouse(PWareHouse pwh) throws ToolException{
		Warehouse wh = new Warehouse();
		BeanUtils.copyProperties(pwh, wh);
		wh.setWhId(IDGenerator.getWhID());
		boolean success = false;
		try {
			success = whdao.save(conn, wh);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("新增仓库时发生异常");
		} catch (ToolException e) {
			e.printStackTrace();
			throw new ToolException("输入的仓库保管员未在雇员表中登记");
		}
		return success;
	}
	
	/**
	 * 更新仓库信息
	 * @param pwh  仓库信息实体
	 * @return
	 * @throws ToolException
	 */
	public boolean updateWareHouse(PWareHouse pwh) throws ToolException{
		Warehouse wh = new Warehouse();
		BeanUtils.copyProperties(pwh, wh);
		boolean success = false;
		try {
			success = whdao.update(conn, wh);
			System.out.println(wh);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("修改仓库信息时发生异常");
		} catch (ToolException e) {
			e.printStackTrace();
			throw new ToolException("输入的仓库保管员未在雇员表中登记");
		}
		return success;
	}
	
	/**
	 * 根据ID删除仓库(级联)
	 * @param whID  仓库ID
	 * @return
	 * @throws ToolException
	 */
	public boolean deleteWareHouse(String whID) throws ToolException{
		boolean success = false;
		boolean hasToolException = false;
		try {
			conn.setAutoCommit(false);
			whdao.delete(conn, whID);
			podao.deleteBySuper(conn, whID);
			shdao.deleteBySuper(conn, whID, null);
			cmdao.deleteBysuper(conn, whID, null, null);
			conn.commit();
			success = true;
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			hasToolException = true;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (hasToolException) {
			throw new ToolException("指定仓库废弃时发生异常");
		}
		
		return success;
	}
	
	/**
	 * 根据ID查询仓库
	 * @param whID  仓库ID
	 * @return 仓库信息实体
	 * @throws ToolException
	 */
	public PWareHouse findByID(String whID) throws ToolException{
 		Warehouse wh = null;
		try {
			wh = whdao.findById(conn, whID);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("获取仓库时发生异常");
		}
		if (wh == null) {
			throw new ToolException("获取不到指定仓库");
		}
		PWareHouse pwh = changeToPageModel(wh);
		return pwh;
	}
	
	/**
	 * 查询仓库信息列表
	 * @param currentPage  当前页面
	 * @param rows  每页记录数
	 * @return   页面模型
	 * @throws ToolException
	 */
	public PageModel list(String whName, int currentPage, int rows) throws ToolException{
		try {
			int total = whdao.countCard(conn, whName);
			PageModel pageModel = new PageModel(total, rows, currentPage);
			List<Warehouse> list = whdao.listCard(conn, whName ,pageModel.getCurrentPage(), pageModel.getRows());
			List<PWareHouse> plist = new ArrayList<PWareHouse>();
			for (Warehouse wh : list) {
				plist.add(changeToPageModel(wh));
			}
			pageModel.setData(plist);
			return pageModel;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("查询仓库列表时发生异常");
		}
	}
	
	/**
	 * 获取所有仓库(下拉框使用)
	 * @return   页面模型
	 * @throws ToolException
	 */
	public List<Warehouse> getWarehouse() throws ToolException{
		try {
			List<Warehouse> list = whdao.getAllCard(conn);
//			List<PWareHouse> plist = new ArrayList<PWareHouse>();
//			for (Warehouse wh : list) {
//				plist.add(changeToPageModel(wh));
//			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("获取仓库选项失败");
		}
	}
	
	private PWareHouse changeToPageModel(Warehouse wh) {
		PWareHouse pwh = new PWareHouse();
		BeanUtils.copyProperties(wh, pwh);
		return pwh;
	}
}
