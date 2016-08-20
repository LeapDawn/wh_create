//package dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import junit.framework.Assert;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.fy.wetoband.tool.dao.WarehouseDao;
//import com.fy.wetoband.tool.entity.Warehouse;
//
//public class WarehouseTest {
//	
//	private static Connection conn = null;
//	private static WarehouseDao whdao = new WarehouseDao();
//	@Before
//	public void before() {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db_xinzegu?useUnicode=true&amp;characterEncoding=utf-8", "root", "root");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testSave() {
//		
//		Date date = new Date();
//		Warehouse wh = new Warehouse();
//		wh.setWhId(new SimpleDateFormat("YYMMDDHHmmss").format(date));
//		wh.setWhName("测试保存" + new SimpleDateFormat("ss").format(date));
//		wh.setAddress("测试保存...54Aa");
//		wh.setPersonName("janvier陈");
//		wh.setNumber((short) 3);
//		wh.setWhRemark("测试保存");
//		try {
//			boolean save = whdao.save(conn, wh);
//			Assert.assertEquals(true, save);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testUpdate() {
//		Date date = new Date();
//		Warehouse wh = new Warehouse();
//		wh.setWhId("1608224153956");
//		wh.setWhName("测试修改" + new SimpleDateFormat("ss").format(date));
////		wh.setAddress("测试修改...45Aa");
////		wh.setPersonName("janvier陈");
//		wh.setNumber((short) 5);
//		wh.setWhRemark("测试修改54153");
//		try {
//			boolean save = whdao.update(conn, wh);
//			Assert.assertEquals(true, save);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testdelete() {
//		
//		Date date = new Date();
//		Warehouse wh = new Warehouse();
//		wh.setWhId("1");
//		try {
//			boolean save = whdao.delete(conn, wh.getWhId());
//			Assert.assertEquals(true, save);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testfindById() {
//		Warehouse wh = null;
//		try {
//		    wh = whdao.findById(conn, "1608224153956");
//			System.out.println(wh);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testgetAll() {
//		try {
//		    List<Warehouse> dis = whdao.getAll(conn, "Y");
//		    List<Warehouse> no_dis = whdao.getAll(conn, "N");
//		    List<Warehouse> no_dis2 = whdao.getAllCard(conn);
//		    System.out.println("已删除:");
//		    for (Warehouse warehouse : dis) {
//				System.out.println(warehouse);
//			}
//		    System.out.println("未删除:");
//		    for (Warehouse warehouse : no_dis) {
//				System.out.println(warehouse);
//			}
//		    System.out.println("未删除2:");
//		    for (Warehouse warehouse : no_dis2) {
//				System.out.println(warehouse);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testlist() {
//		try {
//		    List<Warehouse> dis = whdao.list(conn, "Y", 1, 2);
//		    List<Warehouse> no_dis = whdao.list(conn, "N", 1, 2);
//		    List<Warehouse> no_dis2 = whdao.listCard(conn, 2, 2);
//		    System.out.println("已删除:");
//		    for (Warehouse warehouse : dis) {
//				System.out.println(warehouse);
//			}
//		    System.out.println("未删除:");
//		    for (Warehouse warehouse : no_dis) {
//				System.out.println(warehouse);
//			}
//		    System.out.println("未删除2:");
//		    for (Warehouse warehouse : no_dis2) {
//				System.out.println(warehouse);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testCount() {
//		try {
//			
//			System.out.println("已删除:" + whdao.count(conn, "Y"));
//			System.out.println("未删除:" + whdao.count(conn, "N"));
//			System.out.println("未删除:" + whdao.countCard(conn));
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@After
//	public void after() {
//		if (conn != null){
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
