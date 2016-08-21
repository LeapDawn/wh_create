package com.fy.wetoband.tool.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fy.wetoband.tool.commons.IDGenerator;
import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.dao.ShelfDao;
import com.fy.wetoband.tool.dto.PShelf;
import com.fy.wetoband.tool.dto.PageModel;
import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Shelf;

public class ShelfService {
	
	private ShelfDao shdao = new ShelfDao();
	private Connection conn = null;

	public ShelfService(Connection conn) {
		this.conn = conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * 新增货架
	 * @param psh  货架信息实体
	 * @return
	 * @throws ToolException
	 */
	public boolean saveShelf(PShelf psh)  throws ToolException{
        Positions po = new Positions();
        po.setPoId(psh.getPositionsId());
        Shelf sh = new Shelf();
        BeanUtils.copyProperties(psh, sh);
        sh.setShId(IDGenerator.getShId());
        sh.setPositions(po);
        
        try {
			return shdao.save(conn, sh);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("新增货架时发生异常");
		}
	}
	
	/**
	 * 更新货架信息
	 * @param psh 货架信息实体
	 * @return
	 * @throws ToolException
	 */
	public boolean updateShelf(PShelf psh) throws ToolException {
		Shelf sh = new Shelf();
		BeanUtils.copyProperties(psh, sh);
		Positions po = new Positions();
	    po.setPoId(psh.getPositionsId());
	    sh.setPositions(po);
	    try {
			return shdao.update(conn, sh);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("修改货架信息时发生异常");
		}
	}
	
	/**
	 * 根据ID删除货架
	 * @param shID
	 * @return
	 * @throws ToolException
	 */
	public boolean deleteShelf(String shID) throws ToolException {
		try {
			return shdao.delete(conn, shID);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("删除货架时发生异常");
		}
	}
	
	/**
	 * 根据ID查询货架信息
	 * @param shID
	 * @return  货架信息实体
	 * @throws ToolException
	 */
	public PShelf findById(String shID) throws ToolException {
		Shelf sh = null;
		try {
			sh = shdao.findById(conn, shID);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("获取货架信息时发生异常");
		}
		if (sh == null) {
			throw new ToolException("获取货架信息失败");
		}
		
		return changeToPageModel(sh);
	}
	
	/**
	 * 获取指定仓位下的货架列表
	 * @author zqh
	 * @param poID
	 * @param currentPage
	 * @param rows
	 * @return
	 * @throws ToolException
	 */
	public PageModel listByPo(String shName, String poID, int currentPage, int rows) throws ToolException {
		
		try {
			int total = shdao.countCard(conn, shName, poID);
			PageModel pageModel = new PageModel(total, rows, currentPage);
			List<Shelf> list = shdao.listCard(conn, shName, poID, pageModel.getCurrentPage(), pageModel.getRows());
			List<PShelf> plist = new ArrayList<PShelf>();
			for (Shelf shelf : list) {
				plist.add(changeToPageModel(shelf));
			}
			pageModel.setData(plist);
			return pageModel;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("查询指定仓位下的货架列表时发生异常");
		}
	}
	
	/**
	 * 获取货架(下拉框)
	 * @param poID 仓位ID
	 * @return
	 * @throws ToolException
	 */
	public List<Shelf> getByPo(String poID) throws ToolException {
		
		try {
			List<Shelf> list = shdao.getByPo(conn, poID);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("查询货架选项失败");
		}
	}
	
	private PShelf changeToPageModel(Shelf sh){
		PShelf psh = new PShelf();
		BeanUtils.copyProperties(sh, psh);
		psh.setShDiscard(sh.getShelfDiscard());
		psh.setPositionsId(sh.getPositions().getPoId());
		psh.setPositionsName(sh.getPositions().getPoName());
		psh.setWarehouseId(sh.getPositions().getWarehouse().getWhId());
		psh.setWarehouseName(sh.getPositions().getWarehouse().getWhName());
		return psh;
	}
}	
