package com.fy.wetoband.tool.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fy.wetoband.tool.commons.IDGenerator;
import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.dao.MaterielCongfigDao;
import com.fy.wetoband.tool.dto.PMaterielConfig;
import com.fy.wetoband.tool.dto.PageModel;
import com.fy.wetoband.tool.entity.Materiel;
import com.fy.wetoband.tool.entity.MaterielConfig;
import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Shelf;
import com.fy.wetoband.tool.entity.Warehouse;

public class MaterielConfigService {
	
	private MaterielCongfigDao cmdao = new MaterielCongfigDao();
	private Connection conn = null;

	public MaterielConfigService (Connection conn) {
		this.conn = conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * 新增物料-货架配置信息
	 * @param pcm 配置信息实体
	 * @return
	 */
	public boolean saveMaterielConfig(PMaterielConfig pcm) throws ToolException{
		MaterielConfig cm = changeToPOJO(pcm);
		cm.setCmId(IDGenerator.getCmID());
		try {
			return cmdao.save(conn, cm);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("保存货架配置信息时发生异常");
		}
	}
	
	/**
	 * 更新配置信息
	 * @param pcm
	 * @return
	 * @throws ToolException
	 */
	public boolean updateMaterielConfig(PMaterielConfig pcm) throws ToolException{
		MaterielConfig cm = changeToPOJO(pcm);
		try {
			return cmdao.update(conn, cm);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("修改货架配置信息时发生异常");
		}
	}
	
	/**
	 * 删除配置信息(失效)
	 * @param cmID
	 * @return
	 * @throws ToolException
	 */
	public boolean deleteMaterielConfig(String cmID) throws ToolException{
		try {
			return cmdao.delete(conn, cmID);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("指定货架配置信息失效时发生异常");
		}
	}
	
	/**
	 * 根据ID获取指定配置信息
	 * @param cmID
	 * @return
	 * @throws ToolException
	 */
	public PMaterielConfig findById(String cmID) throws ToolException {
		MaterielConfig cm = null;
		try {
			cm = cmdao.findById(conn, cmID);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("获取指定配置信息时发生异常");
		}
		if (cm == null) {
			throw new ToolException("获取指定配置信息失败");
		}
		return changeToPageModel(cm);
	}
	
	
	/**
	 * 查询有效物品信息配置列表
	 * @param shID  指定货架ID
	 * @param maName 物料名称
	 * @param currentPage  当前页码
	 * @param rows   每页记录数
	 * @return    页面模型
	 * @throws ToolException
	 */
	public PageModel listConfig(String maName, String shID, int currentPage, int rows) throws ToolException {
		try {
			int total = cmdao.countCard(conn, shID);
			PageModel pageModel = new PageModel(total, rows, currentPage);
			List<MaterielConfig> list = cmdao.listCard(conn, shID, pageModel.getCurrentPage(), pageModel.getRows());
			List<PMaterielConfig> plist = new ArrayList<PMaterielConfig>();
			for (MaterielConfig cm : list) {
				plist.add(changeToPageModel(cm));
			}
			pageModel.setData(plist);
			return pageModel;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("获取指定仓库下的物料配置信息列表失败");
		}
		
	}
	
//	/**
//	 * 获取有效物品信息配置列表
//	 * @author zqh
//	 * @param currentPage
//	 * @param rows
//	 * @return
//	 * @throws ToolException
//	 */
//	public PageModel list(int currentPage, int rows) throws ToolException {
//		try {
//			int total = cmdao.countCard(conn);
//			PageModel pageModel = new PageModel(total, rows, currentPage);
//			List<MaterielConfig> list = cmdao.listCard(conn, pageModel.getCurrentPage(), pageModel.getRows());
//			List<PMaterielConfig> plist = new ArrayList<PMaterielConfig>();
//			for (MaterielConfig cm : list) {
//				plist.add(changeToPageModel(cm));
//			}
//			pageModel.setData(plist);
//			return pageModel;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new ToolException("获取物料配置信息列表失败");
//		}
//	}
	
	/**
	 * 获取物料信息列表
	 * @param currentPage
	 * @param rows
	 * @return
	 * @throws ToolException
	 */
	public PageModel listAllMateriels(String maName, int currentPage, int rows) throws ToolException {
		try {
			int total = cmdao.countAllMateriels(conn);
			PageModel pageModel = new PageModel(total, rows, currentPage);
			List<MaterielConfig> list = cmdao.listAllMateriel(conn, maName, pageModel.getCurrentPage(), pageModel.getRows());
			List<PMaterielConfig> plist = new ArrayList<PMaterielConfig>();
			for (MaterielConfig cm : list) {
				plist.add(changeToPageModel(cm));
			}
			pageModel.setData(plist);
			return pageModel;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("获取物料信息列表失败");
		}
	}
	
	private MaterielConfig changeToPOJO(PMaterielConfig pcm) {
		MaterielConfig cm = new MaterielConfig();
		BeanUtils.copyProperties(pcm, cm);
		cm.setMa(new Materiel(pcm.getMaId()));
		Shelf sh = new Shelf();
		sh.setShId(pcm.getShelfId());
		cm.setShelf(sh);
		return cm;
	}
	
	private PMaterielConfig changeToPageModel(MaterielConfig cm){
		PMaterielConfig pcm = new PMaterielConfig();
		BeanUtils.copyProperties(cm, pcm);
		pcm.setMaId(cm.getMa().getMa_id());
		pcm.setMaName(cm.getMa().getMa_name());
		pcm.setMaModel(cm.getMa().getMa_model());
		pcm.setMaSpec(cm.getMa().getMa_spec());
		Shelf sh = cm.getShelf();
		if (sh != null) {
			pcm.setShelfId(sh.getShId());
			pcm.setShelfName(sh.getShName());
			Positions po = sh.getPositions();
			if (po != null) {
				pcm.setPositionsId(po.getPoId());
				pcm.setPositionsName(po.getPoName());
			}
			Warehouse wh = po.getWarehouse();
				if (wh != null) {
					pcm.setWarehouseId(wh.getWhId());
					pcm.setWarehouseName(wh.getWhName());
				}
		}
		return pcm;
	}
}
