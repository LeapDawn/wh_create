package com.fy.wetoband.tool.action;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fy.wetoband.bean.RunToolParam;
import com.fy.wetoband.tool.commons.StringUtil;
import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.dto.AjaxResult;
import com.fy.wetoband.tool.dto.PMaterielConfig;
import com.fy.wetoband.tool.dto.PPositions;
import com.fy.wetoband.tool.dto.PShelf;
import com.fy.wetoband.tool.dto.PWareHouse;
import com.fy.wetoband.tool.dto.PageModel;
import com.fy.wetoband.tool.manager.Tool;
import com.fy.wetoband.tool.service.MaterielConfigService;
import com.fy.wetoband.tool.service.PositionsService;
import com.fy.wetoband.tool.service.ShelfService;
import com.fy.wetoband.tool.service.WarehouseService;

public class WarehouseManager extends Tool {

	@Override
	public void act(HttpServletRequest request, HttpServletResponse response) {
		String toolAction = request.getParameter("toolAction");
		if (!StringUtil.checkNotNull(toolAction)){
			this.writeResult(response, new AjaxResult<Object>(false, "请求的工具Action是什么?"));
		}
		if (toolAction.equals("addWarehouse")) {
			this.addWareHouse(request, response);
		} else if (toolAction.equals("updateWarehouse")){
			this.updateWareHouse(request, response);
		} else if (toolAction.equals("deleteWarehouse")){
			this.deleteWareHouse(request, response);
		} else if (toolAction.equals("getWarehouse")){
			this.getWareHouse(request, response);
		} else if (toolAction.equals("listWarehouse")){
			this.listWareHouse(request, response);
		} else if (toolAction.equals("warehouseSelect")){
			this.wareHouseSelect(request, response);
		} else if (toolAction.equals("addPosition")){
			this.addPosition(request, response);
		} else if (toolAction.equals("updatePosition")){
			this.updatePosition(request, response);
		} else if (toolAction.equals("deletePosition")){
			this.deletePosition(request, response);
		} else if (toolAction.equals("getPosition")){
			this.getPosition(request, response);
		} else if (toolAction.equals("listPosition")){
			this.listPosition(request, response);
		} else if (toolAction.equals("positionSelect")){
			this.positionSelect(request, response);
		} else if (toolAction.equals("addShelf")){
			this.addShelf(request, response);
		} else if (toolAction.equals("updateShelf")){
			this.updateShelf(request, response);
		} else if (toolAction.equals("deleteShelf")){
			this.deleteShelf(request, response);
		} else if (toolAction.equals("getShelf")){
			this.getShelf(request, response);
		} else if (toolAction.equals("listShelf")){
			this.listShelf(request, response);
		} else if (toolAction.equals("shelfSelect")){
			this.shelfSelect(request, response);
		} else if (toolAction.equals("addMaterielConfig")){
			this.addMaterielConfig(request, response);
		} else if (toolAction.equals("addAllMaterielConfig")){
			this.addAnyMaterielConfig(request, response);
		} else if (toolAction.equals("updateMaterielConfig")){
			this.updateMaterielConfig(request, response);
		} else if (toolAction.equals("deleteMaterielConfig")) {
			this.deleteMaterielConfig(request, response);
		} else if (toolAction.equals("getMaterielConfig")) {
			this.getMaterielConfig(request, response);
		} else if (toolAction.equals("listMaterielConfig")) {
			this.listMaterielConfig(request, response);
		} else if (toolAction.equals("listMateriel")) {
			this.listMateriel(request, response);
		} else {
			this.writeResult(response, new AjaxResult<Object>(false, "请求的工具Action不存在, 请检查url"));
		}
	}

	/**
	 * 新增仓库
	 * toolAction=addWarehouse
	 * @接受前端参数(必须):whName,whType
	 * @接受前端参数(非必须):whRemark,personName,address,whTel
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void addWareHouse(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String whName = request.getParameter("whName");             
		String address = request.getParameter("address");
		String personName = request.getParameter("personName");
		String whRemark = request.getParameter("whRemark");
		String whType = request.getParameter("whType");
		String whTel = request.getParameter("whTel");
		// 数据验证
		if (!StringUtil.checkNotNull(whName)) {                      
			result = new AjaxResult<Object>(false, "仓库名不能为空");
			this.writeResult(response, result);
			return;
		}
		if (!StringUtil.checkNotNull(whType)) {
			result = new AjaxResult<Object>(false, "仓库类型不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		whTel = StringUtil.changeEncoding(whTel);
		whType = StringUtil.changeEncoding(whType);
		whName = StringUtil.changeEncoding(whName);               
		address = StringUtil.changeEncoding(address);
		personName = StringUtil.changeEncoding(personName);
		whRemark = StringUtil.changeEncoding(whRemark);
		
		// 执行业务
		PWareHouse pwh = new PWareHouse(whName, address, personName, whRemark, whType, whTel);
		Connection conn = this.getConnectionByBViewID();
		WarehouseService whservice = new WarehouseService(conn);
		boolean state = false;
		Object obj = null;
		try {
			state = whservice.addWareHouse(pwh);
			if (state) {
				obj = "新增仓库成功";
			} else {
				obj = "新增仓库失败";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}

	/**
	 * 更新仓库信息
	 * toolAction=updateWarehouse
	 * @接受前端参数(必须):whId
	 * @接受前端参数(非必须):whName,whType,whRemark,personName,address,whTel
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void updateWareHouse(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String whId = request.getParameter("whId");
		String whName = request.getParameter("whName");             
		String address = request.getParameter("address");
		String personName = request.getParameter("personName");
		String whRemark = request.getParameter("whRemark");
		String whType = request.getParameter("whType");
		String whTel = request.getParameter("whTel");
		if (!StringUtil.checkNotNull(whId)) {                      
			result = new AjaxResult<Object>(false, "待修改的仓库ID不能为空");
			this.writeResult(response, result);
		}
		
		// 数据转换
		whId = StringUtil.changeEncoding(whId);        
		whTel = StringUtil.changeEncoding(whTel);
		whType = StringUtil.changeEncoding(whType);
		whName = StringUtil.changeEncoding(whName);               
		address = StringUtil.changeEncoding(address);
		personName = StringUtil.changeEncoding(personName);
		whRemark = StringUtil.changeEncoding(whRemark);
		
		// 执行业务
		PWareHouse pwh = new PWareHouse(whName, address, personName, whRemark, whType, whTel);
		pwh.setWhId(whId);
		Connection conn = this.getConnectionByBViewID();
		WarehouseService whservice = new WarehouseService(conn);
		boolean state = false;
		Object obj = null;
		try {
			state = whservice.updateWareHouse(pwh);
			if (state) {
				obj = "更新仓库信息成功";
			} else {
				obj = "仓库信息没有被修改";
			}
		} catch (ToolException e) {
			e.printStackTrace();
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	
	/**
	 * 删除(废弃)某个仓库
	 * toolAction=deleteWarehouse
	 * @接受前端参数(必须):whId
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void deleteWareHouse(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String whId = request.getParameter("whId");
		if (!StringUtil.checkNotNull(whId)) {                      
			result = new AjaxResult<Object>(false, "待删除的仓库ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		whId = StringUtil.changeEncoding(whId);
		Connection conn = this.getConnectionByBViewID();
		WarehouseService whservice = new WarehouseService(conn);
		boolean state = false;
		Object obj = null;
		try {
			state = whservice.deleteWareHouse(whId);
			if (state) {
				obj = "删除仓库成功";
			} else {
				obj = "删除仓库失败";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	

	/**
	 * 获取指定的仓库信息
	 * toolAction=getWarehouse
	 * @接受前端参数(必须):whId,
	 * @返回前端数据:AjaxResult(Boolean, PPositions/String)
	 */
	private void getWareHouse(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String whId = request.getParameter("whId");
		if (!StringUtil.checkNotNull(whId)) {                      
			result = new AjaxResult<Object>(false, "待查询的仓库ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		whId = StringUtil.changeEncoding(whId);
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		WarehouseService whservice = new WarehouseService(conn);
		boolean state = false;
		Object obj = null;
		try {
			obj = whservice.findByID(whId);
			state = true;
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取仓库列表
	 * toolAction=listWarehouse
	 * @接受前端参数(非必须):whName仓库名称(为空则没有该查询条件),currentPage当前页码(默认1), rows每页记录数(默认10)
	 * @返回前端数据:AjaxResult(Boolean, PageModel/String)
	 */
	private void listWareHouse(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String currentPageStr = request.getParameter("currentPage");
		String rowsStr = request.getParameter("rows"); 
		String whName = request.getParameter("whName"); 
		
		// 数据转化
		int currentPage = 0;
		int rows = 0;
		if (StringUtil.checkNotNull(currentPageStr) && StringUtil.isNum(currentPageStr)){
			currentPage = Integer.valueOf(currentPageStr);
		}
		if (StringUtil.checkNotNull(rowsStr) && StringUtil.isNum(rowsStr)){
			rows = Integer.valueOf(rowsStr);
		}
		whName = StringUtil.changeEncoding(whName);
		
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		WarehouseService whservice = new WarehouseService(conn);
		boolean state = false;
		Object obj = null;	
		try {
			obj = whservice.list(whName, currentPage, rows);
			state = true;
//			this.writeResult(response, whservice.list(whName, currentPage, rows).getData());
		} catch (ToolException e) {
			e.printStackTrace();
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取仓库下拉框
	 * toolAction=warehouseSelect
	 * @返回前端数据:AjaxResult(Boolean, List/String)
	 */
	private void wareHouseSelect(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		WarehouseService whservice = new WarehouseService(conn);
		boolean state = false;
		Object obj = null;	
		try {
			obj = whservice.getWarehouse();
			state = true;
		} catch (ToolException e) {
			e.printStackTrace();
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 新增仓位
	 * toolAction=addPosition
	 * @接受前端参数(必须):warehouseId所属仓库ID,poName仓位名称
	 * @接受前端参数(非必须):poRemark备注信息
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void addPosition(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String warehouseId = request.getParameter("warehouseId");             
		String poName = request.getParameter("poName");
		String poRemark = request.getParameter("poRemark");
		// 数据验证
		if (!StringUtil.checkNotNull(warehouseId)) {                      
			result = new AjaxResult<Object>(false, "仓位所属仓库ID不能为空");
			this.writeResult(response, result);
			return;
		}		
		if (!StringUtil.checkNotNull(poName)) {                      
			result = new AjaxResult<Object>(false, "仓位名称不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		warehouseId = StringUtil.changeEncoding(warehouseId);
		poName = StringUtil.changeEncoding(poName);
		poRemark = StringUtil.changeEncoding(poRemark);
	    
		PPositions ppo = new PPositions(warehouseId, poName, poRemark);
		Connection conn = this.getConnectionByBViewID();
		PositionsService poservice = new PositionsService(conn);
		
		boolean state = false;
		Object obj = null;
		try {
			state = poservice.savePosition(ppo);
			if (state) {
				obj = "新增仓位成功";
			} else {
				obj = "新增仓位失败";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 更新仓位信息
	 * toolAction=updatePosition
	 * @接受前端参数(必须):poId 仓位ID
	 * @接受前端参数(非必须):poName, poRemark
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void updatePosition(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String poId = request.getParameter("poId");             
		String poName = request.getParameter("poName");
		String poRemark = request.getParameter("poRemark");
		// 数据验证
		if (!StringUtil.checkNotNull(poId)) {                      
			result = new AjaxResult<Object>(false, "即将修改仓位的ID不能为空");
			this.writeResult(response, result);
			return;
		}			
		
		// 数据转化
		poId = StringUtil.changeEncoding(poId);
		poName = StringUtil.changeEncoding(poName);
		poRemark = StringUtil.changeEncoding(poRemark);
		
		// 执行业务
		PPositions ppo = new PPositions(poName, poRemark);
		ppo.setPoId(poId);
		Connection conn = this.getConnectionByBViewID();
		PositionsService poservice = new PositionsService(conn);
		
		boolean state = false;
		Object obj = null;
		try {
			state = poservice.updatePosition(ppo);
			if (state) {
				obj = "更新仓位信息成功";
			} else {
				obj = "仓位信息没有更新";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 删除(废弃)某个仓位
	 * toolAction=deletePosition
	 * @接受前端参数(必须):poId
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void deletePosition(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String poId = request.getParameter("poId");
		if (!StringUtil.checkNotNull(poId)) {                      
			result = new AjaxResult<Object>(false, "待删除仓位的ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		poId = StringUtil.changeEncoding(poId);
		Connection conn = this.getConnectionByBViewID();
		PositionsService poservice = new PositionsService(conn);
		boolean state = false;
		Object obj = null;
		try {
			state = poservice.deletePosition(poId);
			if (state) {
				obj = "删除仓库成功";
			} else {
				obj = "删除仓库失败";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取指定的仓位信息
	 * toolAction=getPosition
	 * @接受前端参数(必须):poId,
	 * @返回前端数据:AjaxResult(Boolean, PPositions/String)
	 */
	private void getPosition(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String poId = request.getParameter("poId");
		if (!StringUtil.checkNotNull(poId)) {                      
			result = new AjaxResult<Object>(false, "待查询仓位的ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		poId = StringUtil.changeEncoding(poId);
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		PositionsService poservice = new PositionsService(conn);
		boolean state = false;
		Object obj = null;
		try {
			obj = poservice.findById(poId);
			state = true;
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取仓位列表
	 * toolAction=listPosition
	 * @接受前端参数(非必须):poName(仓位名称),warehouseId(仓库ID,若为空,则表示查询所有仓库的仓位), currentPage当前页码(默认1), rows每页记录数(默认10)
	 * @返回前端数据:AjaxResult(Boolean, PageModel/String)
	 */
	private void listPosition(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String poName = request.getParameter("poName"); 
		String warehouseId = request.getParameter("warehouseId");
		String currentPageStr = request.getParameter("currentPage");
		String rowsStr = request.getParameter("rows"); 
		
		// 数据转化
		warehouseId = StringUtil.changeEncoding(warehouseId);
		poName = StringUtil.changeEncoding(poName);
		int currentPage = 0;
		int rows = 0;
		if (StringUtil.checkNotNull(currentPageStr) && StringUtil.isNum(currentPageStr)){
			currentPage = Integer.valueOf(currentPageStr);
		}
		if (StringUtil.checkNotNull(rowsStr) && StringUtil.isNum(rowsStr)){
			rows = Integer.valueOf(rowsStr);
		}
		
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		PositionsService poservice = new PositionsService(conn);
		boolean state = false;
		Object obj = null;	
		try {
			obj = poservice.listByWh(poName, warehouseId, currentPage, rows);
			state = true;
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	
	/**
	 * 获取仓位下拉框
	 * toolAction=warehouseSelect
	 * @参数:whId 仓库ID
	 * @返回前端数据:AjaxResult(Boolean, List/String)
	 */
	private void positionSelect(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		
		String whId = request.getParameter("whId");
		whId = StringUtil.changeEncoding(whId);
		
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		PositionsService poservice = new PositionsService(conn);
		boolean state = false;
		Object obj = null;	
		try {
			obj = poservice.getByWh(whId);
			state = true;
		} catch (ToolException e) {
			e.printStackTrace();
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 新增货架
	 * toolAction=addShelf
	 * @接受前端参数(必须):positionsId所属仓位ID,shName
	 * @接受前端参数(非必须):shRemark
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void addShelf(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String positionsId = request.getParameter("positionsId");             
		String shName = request.getParameter("shName");
		String shRemark = request.getParameter("shRemark");
		// 数据验证
		if (!StringUtil.checkNotNull(positionsId)) {                      
			result = new AjaxResult<Object>(false, "货架所属仓位ID不能为空");
			this.writeResult(response, result);
			return;
		}		
		if (!StringUtil.checkNotNull(shName)) {                      
			result = new AjaxResult<Object>(false, "货架名称不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		positionsId = StringUtil.changeEncoding(positionsId);
		shName = StringUtil.changeEncoding(shName);
		shRemark = StringUtil.changeEncoding(shRemark);
	    
	    PShelf psh = new PShelf(positionsId, shName, shRemark);
		Connection conn = this.getConnectionByBViewID();
		ShelfService shservice = new ShelfService(conn);
		
		boolean state = false;
		Object obj = null;
		try {
			state = shservice.saveShelf(psh);
			if (state) {
				obj = "新增仓位成功";
			} else {
				obj = "新增仓位失败";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 更新货架信息
	 * toolAction=updateShelf
	 * @接受前端参数(必须):shId
	 * @接受前端参数(非必须):shName, shRemark, positionsId所属仓位ID
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void updateShelf(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String shId = request.getParameter("shId");             
		String shName = request.getParameter("shName");
		String shRemark = request.getParameter("shRemark");
		String positionsId = request.getParameter("positionsId");
		// 数据验证
		if (!StringUtil.checkNotNull(shId)) {                      
			result = new AjaxResult<Object>(false, "即将修改货架的ID不能为空");
			this.writeResult(response, result);
			return;
		}			
		
		// 数据转化
		shId = StringUtil.changeEncoding(shId);
		shName = StringUtil.changeEncoding(shName);
		shRemark = StringUtil.changeEncoding(shRemark);
		positionsId = StringUtil.changeEncoding(positionsId);
		
		// 执行业务
		PShelf psh = new PShelf(shId, positionsId, shName, shRemark);
		Connection conn = this.getConnectionByBViewID();
		ShelfService shservice = new ShelfService(conn);

		boolean state = false;
		Object obj = null;
		try {
			state = shservice.updateShelf(psh);
			if (state) {
				obj = "更新货架信息成功";
			} else {
				obj = "货架信息没有更新";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 删除(废弃)某个货架
	 * toolAction=deleteShelf
	 * @接受前端参数(必须):shId
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void deleteShelf(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String shId = request.getParameter("shId");
		if (!StringUtil.checkNotNull(shId)) {                      
			result = new AjaxResult<Object>(false, "待删除仓位的ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		shId = StringUtil.changeEncoding(shId);
		Connection conn = this.getConnectionByBViewID();
		ShelfService shservice = new ShelfService(conn);
		boolean state = false;
		Object obj = null;
		try {
			state = shservice.deleteShelf(shId);
			if (state) {
				obj = "删除货架成功";
			} else {
				obj = "删除货架失败";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取指定的某个货架信息
	 * toolAction=getShelf
	 * @接受前端参数(必须):shId,
	 * @返回前端数据:AjaxResult(Boolean, PShelf/String)
	 */
	private void getShelf(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String shId = request.getParameter("shId");
		if (!StringUtil.checkNotNull(shId)) {                      
			result = new AjaxResult<Object>(false, "待查询货架的ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		shId = StringUtil.changeEncoding(shId);
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		ShelfService shservice = new ShelfService(conn);
		boolean state = false;
		Object obj = null;
		try {
			obj = shservice.findById(shId);
			state = true;
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取货架列表
	 * toolAction=listShelf
	 * @接受前端参数(非必须):shName(货架名称),positionsId(仓位ID,若为空,则表示查询所有货架), currentPage当前页码(默认1), rows每页记录数(默认10)
	 * @返回前端数据:AjaxResult(Boolean, PageModel/String)
	 */
	private void listShelf(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String shName = request.getParameter("shName");
		String positionsId = request.getParameter("positionsId");
		String currentPageStr = request.getParameter("currentPage");
		String rowsStr = request.getParameter("rows"); 
		
		// 数据转化
		positionsId = StringUtil.changeEncoding(positionsId);
		shName = StringUtil.changeEncoding(shName);
		int currentPage = 0;
		int rows = 0;
		if (StringUtil.checkNotNull(currentPageStr) && StringUtil.isNum(currentPageStr)){
			currentPage = Integer.valueOf(currentPageStr);
		}
		if (StringUtil.checkNotNull(rowsStr) && StringUtil.isNum(rowsStr)){
			rows = Integer.valueOf(rowsStr);
		}
		
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		ShelfService shservice = new ShelfService(conn);
		boolean state = false;
		Object obj = null;	
		try {
			obj = shservice.listByPo(shName,positionsId, currentPage, rows);
			state = true;
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取货架下拉框
	 * toolAction=warehouseSelect
	 * @参数:shId 仓位ID
	 * @返回前端数据:AjaxResult(Boolean, List/String)
	 */
	private void shelfSelect(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		
		String poId = request.getParameter("poId");
		poId = StringUtil.changeEncoding(poId);
		
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		ShelfService shservice = new ShelfService(conn);
		boolean state = false;
		Object obj = null;	
		try {
			obj = shservice.getByPo(poId);
			state = true;
		} catch (ToolException e) {
			e.printStackTrace();
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 新增物料配置信息
	 * toolAction=addMaterielConfig
	 * @接受前端参数(必须):maId物料ID,shelfId所属货架ID
	 * @接受前端参数(非必须):cmRemark
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void addMaterielConfig(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String maId = request.getParameter("maId");             
		String shelfId = request.getParameter("shelfId");
		String cmRemark = request.getParameter("cmRemark");
		// 数据验证
		if (!StringUtil.checkNotNull(maId)) {                      
			result = new AjaxResult<Object>(false, "物料的ID不能为空");
			this.writeResult(response, result);
			return;
		}		
		if (!StringUtil.checkNotNull(shelfId)) {                      
			result = new AjaxResult<Object>(false, "物料所属货架的ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		maId = StringUtil.changeEncoding(maId);
		shelfId = StringUtil.changeEncoding(shelfId);
		cmRemark = StringUtil.changeEncoding(cmRemark);
	    
		// 执行业务
		PMaterielConfig pcm = new PMaterielConfig(maId, shelfId, cmRemark);
		Connection conn = this.getConnectionByBViewID();
		MaterielConfigService cmservice = new MaterielConfigService(conn);
		
		boolean state = false;
		Object obj = null;
		try {
			state = cmservice.saveMaterielConfig(pcm);
			if (state) {
				obj = "新增物料-货架配置信息成功";
			} else {
				obj = "新增物料-货架配置信息失败";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 新增多个物料配置信息(多个物料,一个货架)
	 * toolAction=addAnyMaterielConfig
	 * @接受前端参数(必须):maIds多个物料ID拼接的字符串(以英文逗号隔开),shelfId所属货架ID
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void addAnyMaterielConfig(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String maIds = request.getParameter("maIds");             
		String shelfId = request.getParameter("shelfId");
		// 数据验证
		if (!StringUtil.checkNotNull(maIds)) {                      
			result = new AjaxResult<Object>(false, "物料的ID拼接字符串不能为空");
			this.writeResult(response, result);
			return;
		}		
		if (!StringUtil.checkNotNull(shelfId)) {                      
			result = new AjaxResult<Object>(false, "物料所属货架的ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		maIds = StringUtil.changeEncoding(maIds);
		shelfId = StringUtil.changeEncoding(shelfId);
	    
		String[] maIdArray = maIds.split(",");
		
		// 执行业务
		Object obj = null;
		boolean state = false;
		PMaterielConfig pcm = null;
		Connection conn = this.getConnectionByBViewID();
		MaterielConfigService cmservice = new MaterielConfigService(conn);
		for (String maId : maIdArray) {
			pcm = new PMaterielConfig();
			pcm.setMaId(maId);
			pcm.setShelfId(shelfId);
		try {
			state = cmservice.saveMaterielConfig(pcm);
			if (state) {
				obj = "新增物料-货架配置信息成功";
			} else {
				obj = "新增物料-货架配置信息失败";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} 
	}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 更新物料配置信息
	 * toolAction=updateMaterielConfig
	 * @接受前端参数(必须):cmId物料配置记录ID
	 * @接受前端参数(非必须):cmRemark
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void updateMaterielConfig(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String cmId = request.getParameter("cmId");             
		String cmRemark = request.getParameter("cmRemark");
		// 数据验证
		if (!StringUtil.checkNotNull(cmId)) {                      
			result = new AjaxResult<Object>(false, "即将修改的配置信息记录的ID不能为空");
			this.writeResult(response, result);
			return;
		}			
		
		// 数据转化
		cmId = StringUtil.changeEncoding(cmId);
		cmRemark = StringUtil.changeEncoding(cmRemark);
		
		// 执行业务
		PMaterielConfig pcm = new PMaterielConfig(cmId, cmRemark);
		Connection conn = this.getConnectionByBViewID();
		MaterielConfigService cmservice = new MaterielConfigService(conn);

		boolean state = false;
		Object obj = null;
		try {
			state = cmservice.updateMaterielConfig(pcm);
			if (state) {
				obj = "更新物料-货架配置信息成功";
			} else {
				obj = "物料-货架配置信息没有更新";
			}
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}

	/**
	 * 删除(使失效)某条配置记录信息
	 * toolAction=deleteMaterielConfig
	 * @接受前端参数(必须):cmId
	 * @返回前端数据:AjaxResult(Boolean, String)
	 */
	private void deleteMaterielConfig(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String cmId = request.getParameter("cmId");
		if (!StringUtil.checkNotNull(cmId)) {                      
			result = new AjaxResult<Object>(false, "待删除配置记录的ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		cmId = StringUtil.changeEncoding(cmId);
		Connection conn = this.getConnectionByBViewID();
		MaterielConfigService cmService = new MaterielConfigService(conn);
		boolean state = false;
		Object obj = null;
		try {
			state = cmService.deleteMaterielConfig(cmId);
			if (state) {
				obj = "删除物料-货架配置信息成功";
			} else {
				obj = "删除物料-货架配置信息失败";
			}
		} catch (ToolException e) {
			e.printStackTrace();
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取指定的某条配置记录信息
	 * toolAction=getMaterielConfig
	 * @接受前端参数(必须):cmId
	 * @返回前端数据:AjaxResult(Boolean, PMaterielConfig/String)
	 */
	private void getMaterielConfig(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String cmId = request.getParameter("cmId");
		if (!StringUtil.checkNotNull(cmId)) {                      
			result = new AjaxResult<Object>(false, "待查询配置记录的ID不能为空");
			this.writeResult(response, result);
			return;
		}
		
		// 数据转化
		cmId = StringUtil.changeEncoding(cmId);
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		MaterielConfigService cmservice = new MaterielConfigService(conn);

		boolean state = false;
		Object obj = null;
		try {
			obj = cmservice.findById(cmId);
			state = true;
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
	}
	
	/**
	 * 获取物料配置信息列表
	 * toolAction=listMaterielConfig
	 * @接受前端参数(非必须):maName(物料名称,若为空,则表示查询所有物料), shelfId(货架ID,若为空,则表示查询所有货架), currentPage当前页码(默认1), rows每页记录数(默认10)
	 * @返回前端数据:AjaxResult(Boolean, PageModel/String)
	 */
	private void listMaterielConfig(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String shelfId = request.getParameter("shelfId");
		String maName = request.getParameter("maName");
		String currentPageStr = request.getParameter("currentPage");
		String rowsStr = request.getParameter("rows"); 
		
		// 数据转化
		shelfId = StringUtil.changeEncoding(shelfId);
		maName = StringUtil.changeEncoding(maName);
		int currentPage = 0;
		int rows = 0;
		if (StringUtil.checkNotNull(currentPageStr) && StringUtil.isNum(currentPageStr)){
			currentPage = Integer.valueOf(currentPageStr);
		}
		if (StringUtil.checkNotNull(rowsStr) && StringUtil.isNum(rowsStr)){
			rows = Integer.valueOf(rowsStr);
		}
		
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		MaterielConfigService cmservice = new MaterielConfigService(conn);
		boolean state = false;
		Object obj = null;	
		try {
			obj = cmservice.listConfig(maName, shelfId, currentPage, rows);
			state = true;
		} catch (ToolException e) {
			e.printStackTrace();
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
		
	}
	
	/**
	 * 获取物料信息列表
	 * toolAction=listMateriel
	 * @接受前端参数(非必须):maName物料名称(若为空则查询所有物料),currentPage当前页码(默认1), rows每页记录数(默认10)
	 * @返回前端数据:AjaxResult(Boolean, PageModel/String)
	 */
	private void listMateriel(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult<Object> result = null;
		// 获取数据
		String currentPageStr = request.getParameter("currentPage");
		String rowsStr = request.getParameter("rows"); 
		String maName = request.getParameter("maName"); 
		
		// 数据转化
		maName = StringUtil.changeEncoding(maName);
		int currentPage = 0;
		int rows = 0;
		if (StringUtil.checkNotNull(currentPageStr) && StringUtil.isNum(currentPageStr)){
			currentPage = Integer.valueOf(currentPageStr);
		}
		if (StringUtil.checkNotNull(rowsStr) && StringUtil.isNum(rowsStr)){
			rows = Integer.valueOf(rowsStr);
		}
		
		// 执行业务
		Connection conn = this.getConnectionByBViewID();
		MaterielConfigService cmservice = new MaterielConfigService(conn);
		boolean state = false;
		Object obj = null;	
		try {
			obj = cmservice.listAllMateriels(maName, currentPage, rows);
			state = true;
		} catch (ToolException e) {
			state = false;
			obj = e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = new AjaxResult<Object>(state, obj);
		this.writeResult(response, result);
		
	}
	
//	private void writeResult(HttpServletResponse response, List result) {
//		System.out.println("进入writeResult");
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("application/json;charset=utf-8");
//		try {
//			Writer writer = response.getWriter();
//			System.out.println("返回结果" + JSONArray.fromObject(result).toString());
//			
//			writer.write(JSONArray.fromObject(result).toString());
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
	
	/*返回结果给前端*/
	private void writeResult(HttpServletResponse response, AjaxResult result) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		try {
			Writer writer = response.getWriter();
			writer.write(JSONObject.fromObject(result).toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<Object, Object> toolMain(RunToolParam arg0) {
		return null;
	}

	@Override
	public Map<Object, Object> toolMain(HttpServletRequest arg0,
			HttpServletResponse arg1) {
		return null;
	}

}
