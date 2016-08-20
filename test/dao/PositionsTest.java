package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.geometry.Pos;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fy.wetoband.tool.dao.PositionsDao;
import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Warehouse;

public class PositionsTest {
	private static Connection conn = null;
	PositionsDao podao = new PositionsDao();

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
		po.setPoId(new SimpleDateFormat("MMYYDDHHmmss").format(date));
		po.setPoName("测试保存仓区" + new SimpleDateFormat("ss").format(date));
		// po.setPoRemark("测试保存仓区");
		Warehouse wh = new Warehouse();
		wh.setWhId("1608224154057");
		po.setWarehouse(wh);
		try {
			boolean save = podao.save(conn, po);
			Assert.assertEquals(true, save);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate() {
		Date date = new Date();
		Positions po = new Positions();
		po.setPoId("0816224162145");
		po.setPoName("测试修改仓区" + new SimpleDateFormat("ss").format(date));
		po.setPoRemark("");
		Warehouse wh = new Warehouse();
		wh.setWhId("1608224154057");
		po.setWarehouse(wh);
		try {
			boolean save = podao.update(conn, po);
			Assert.assertEquals(true, save);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
		try {
			boolean save = podao.delete(conn, "2");
			Assert.assertEquals(true, save);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testFindById() {
		try {
			Positions po = podao.findById(conn, "1608224162003");
			System.out.println(po);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAll() {
		try {
			List<Positions> all = podao.get(conn, "Y", null);
			List<Positions> all2 = podao.get(conn, "Y", "1");
			List<Positions> allCard = podao.get(conn, "1608224154057");
			List<Positions> allCard2 = podao.get(conn);
			System.out.println("全部已删除:");
			for (Positions positions : all) {
				System.out.println(positions);
			}
			System.out.println("'1'已删除:");
			for (Positions positions : all2) {
				System.out.println(positions);
			}
			System.out.println("'1608224154057'未删除:");
			for (Positions positions : allCard) {
				System.out.println(positions);
			}
			System.out.println("全部未删除:");
			for (Positions positions : allCard2) {
				System.out.println(positions);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testList() {
		try {
			List<Positions> list = podao.list(conn, null, "Y", "1", 1, 1);
			List<Positions> list2 = podao.list(conn, null,"Y", null, 1, 1);
			List<Positions> list3 = podao.list(conn,"仓位", "N", null, 1, 3);
			List<Positions> listCard = podao.listCard(conn,null, "1608224154329", 1,
					3);

			System.out.println("'1'已删除:");
			for (Positions positions : list) {
				System.out.println(positions);
			}
			System.out.println("已删除:");
			for (Positions positions : list2) {
				System.out.println(positions);
			}
			System.out.println("未删除:");
			for (Positions positions : list3) {
				System.out.println(positions);
			}
			System.out.println("'1608224154329'未删除:");
			for (Positions positions : listCard) {
				System.out.println(positions);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCount() {
		try {
			System.out.println("已删除:" + podao.count(conn, "Y", null));
			System.out.println("1已删除:" + podao.count(conn, "Y", "1"));
			System.out.println("1608224154329未删除:" + podao.countCard(conn, "1608224154329"));
			System.out.println("未删除:" + podao.countCard(conn));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
