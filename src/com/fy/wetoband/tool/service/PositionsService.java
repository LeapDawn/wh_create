package com.fy.wetoband.tool.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fy.wetoband.tool.commons.IDGenerator;
import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.dao.PositionsDao;
import com.fy.wetoband.tool.dao.WarehouseDao;
import com.fy.wetoband.tool.dto.PPositions;
import com.fy.wetoband.tool.dto.PageModel;
import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Warehouse;

public class PositionsService {

	private WarehouseDao whdao = new WarehouseDao();
	private PositionsDao podao = new PositionsDao();
	private Connection conn = null;

	public PositionsService(Connection conn) {
		this.conn = conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 配置新的仓位
	 * 获取所属仓库,若仓库的仓位未满,执行添加操作
	 * @param ppo  仓位信息实体
	 * @return     保存结果
	 * @throws ToolException
	 */
	public boolean savePosition(PPositions ppo) throws ToolException {
		String whID = ppo.getWarehouseId();
		// 添加仓位
		Positions po = new Positions();
		BeanUtils.copyProperties(ppo, po);
		po.setPoId(IDGenerator.getPoID());
		po.setWarehouse(new Warehouse(whID));
		try {
			return podao.save(conn, po);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("保存仓位时发生异常");
		}
	}

	
	/**
	 * 更新仓位信息
	 * @param ppo  仓位信息实体
 	 * @return
	 * @throws ToolException
	 */
	public boolean updatePosition(PPositions ppo) throws ToolException{
		Positions po = new Positions();
		BeanUtils.copyProperties(ppo, po);
		try {
			return podao.update(conn, po);
		} catch (SQLException e) {
			throw new ToolException("修改仓位时发生异常");
		}
	}
	
	/**
	 * 废弃仓位
	 * @param poID  仓位ID
	 * @return
	 * @throws ToolException
	 */
	public boolean deletePosition(String poID) throws ToolException{
		try {
			return podao.delete(conn, poID);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("删除仓位时发生异常");
		}
	}
	
	/**
	 * 根据ID查询仓位
	 * @param poID 仓位ID
	 * @return
	 * @throws ToolException
	 */
	public PPositions findById(String poID) throws ToolException{
		Positions po = null;
		try {
			po = podao.findById(conn, poID);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("获取仓位时发生异常");
		}
		if (po == null) {
			throw new ToolException("获取不到指定仓位");
		}
		PPositions ppo = changeToPageModel(po);
		return ppo;
	}
	
	
	/**
	 * 查询指定仓库下的仓位列表
	 * @param WhID 仓库ID
	 * @param currentPage  当前页数
	 * @param rows  每页记录数
	 * @return  页面模型
	 */
	public PageModel listByWh(String poName, String whID, int currentPage, int rows) throws ToolException{
		try {
			int total = podao.countCard(conn, whID);
			PageModel pageModel = new PageModel(total, rows, currentPage);
			List<Positions> list = podao.listCard(conn, poName, whID, pageModel.getCurrentPage(), pageModel.getRows());
			List<PPositions> plist = new ArrayList<PPositions>();
			for (Positions po : list) {
				plist.add(changeToPageModel(po));
			}
			pageModel.setData(plist);
			return pageModel;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("查询指定仓库下的仓位列表时发生异常");
		}
	}
	
	/**
	 * 获取仓位(下拉框)
	 * @param whID(仓库ID)
	 * @return
	 * @throws ToolException
	 */
	public List<Positions> getByWh(String whID) throws ToolException{
		try {
			List<Positions> list = podao.get(conn, whID);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("查询仓位选项失败");
		}
	}
	
	private PPositions changeToPageModel(Positions po){
		PPositions ppo = new PPositions();
		BeanUtils.copyProperties(po, ppo);
		ppo.setWarehouseId(po.getWarehouse().getWhId());
		ppo.setWarehouseName(po.getWarehouse().getWhName());
		return ppo;
	}
}
