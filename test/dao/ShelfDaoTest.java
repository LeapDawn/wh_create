package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fy.wetoband.tool.commons.StringUtil;
import com.fy.wetoband.tool.dao.ShelfDao;
import com.fy.wetoband.tool.dto.AjaxResult;
import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Shelf;
import com.fy.wetoband.tool.entity.Warehouse;

public class ShelfDaoTest {
	private static Connection conn = null;
	private static ShelfDao shdao = new ShelfDao();

	@Before
	public void before() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://127.0.0.1:3306/db_xinzegu?useUnicode=true&amp;characterEncoding=utf-8",
							"root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@After
	public void after() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testSave() {
		Date date = new Date();
		Positions po = new Positions();
		Shelf sh = new Shelf();
		sh.setShId(new SimpleDateFormat("MMDDYYHHmmss").format(date));
		sh.setShName("级条货架" + new SimpleDateFormat("ss").format(date));
		sh.setShRemark("测试保存");
		po.setPoId("1608224162003");
		sh.setPositions(po);
		boolean save = false;
		try {
			save = shdao.save(conn, sh);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(true, save);
	}

	@Test
	public void testUpdate() {
		Date date = new Date();
		Shelf sh = new Shelf();
		sh.setShId("0822416171008");
		sh.setShName("测试修改货架" + new SimpleDateFormat("ss").format(date));
		sh.setShRemark("");
		boolean save = false;
		try {
			save = shdao.update(conn, sh);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(true, save);
	}

	@Test
	public void testDelete() {
		boolean save = false;
		try {
			save = shdao.delete(conn, "0822416171017");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(true, save);
	}

	@Test
	public void testFindById() {
		try {
			Shelf findById = shdao.findById(conn, "0822416171017");
			System.out.println(findById);
			Shelf findById2 = shdao.findById(conn, "0822416170928");
			System.out.println(findById2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
//	public void testGetAll() {
//		try {
//			List<Shelf> all = shdao.getAll(conn, "Y", null, null);
//			List<Shelf> allCard = shdao.getAllCard(conn, null, "1608224154329");
//			List<Shelf> allCard2 = shdao.getAllCard(conn, "0816224162145");
//			List<Shelf> allCard3 = shdao.getAllCard(conn);
//			System.out.println("已删除:");
//			for (Shelf shelf : all) {
//				System.out.println(shelf);
//			}
//			System.out.println("1608224154329仓库未删除:");
//			 for (Shelf shelf : allCard) {
//				System.out.println(shelf);
//			}System.out.println("0816224162145货架删除:");
//			 for (Shelf shelf : allCard2) {
//				System.out.println(shelf);
//			}System.out.println("未删除:");
//			 for (Shelf shelf : allCard3) {
//				System.out.println(shelf);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testList() {
		try {
			List<Shelf> list = shdao.list(conn, "Y", null, null, 1, 2);
			List<Shelf> listCard = shdao.listCard(conn, null, "1608224154057", 2, 1);
			List<Shelf> listCard2 = shdao.listCard(conn, "0816224162145", 1, 2);
			List<Shelf> listCard3 = shdao.listCard(conn, 2, 2);
			
			System.out.println("已删除:");
			for (Shelf shelf : list) {
				System.out.println(shelf);
			}
			System.out.println("1608224154329仓库未删除:");
			 for (Shelf shelf : listCard) {
				System.out.println(shelf);
			}System.out.println("0816224162145货架删除:");
			 for (Shelf shelf : listCard2) {
				System.out.println(shelf);
			}System.out.println("未删除:");
			 for (Shelf shelf : listCard3) {
				System.out.println(shelf);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCount() {
//		AjaxResult<Object> result = new AjaxResult<Object>(false, "仓库名不能为空");
//		System.out.println(JSONObject.fromObject(result).toString());
//		Map map = new HashMap<String, Object>();
//		map.add("state", result.get)
		
//		AjaxResult<String> result2 = new AjaxResult<String>(false, "仓库名2不能为空");
//		System.out.println(JSONObject.fromObject(result2).toString());
		
		try {
			System.out.println("所有:"+shdao.count(conn, null, null, null));
			System.out.println("已删除:"+shdao.count(conn, "Y", null, null));
			System.out.println("货架...::"+shdao.countCard(conn, "0816224162145"));
			System.out.println("所有未删除:"+shdao.countCard(conn));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
