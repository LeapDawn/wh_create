package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fy.wetoband.tool.commons.StringUtil;
import com.fy.wetoband.tool.dao.MaterielCongfigDao;
import com.fy.wetoband.tool.entity.Materiel;
import com.fy.wetoband.tool.entity.MaterielConfig;
import com.fy.wetoband.tool.entity.Positions;
import com.fy.wetoband.tool.entity.Shelf;

public class MaterielTest {
	private static Connection conn = null;
	private static MaterielCongfigDao madao = new MaterielCongfigDao();

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
		Shelf sh = new Shelf();
		MaterielConfig cm = new MaterielConfig();
		cm.setCmId(new SimpleDateFormat("HHMMDDYYmmss").format(date));
		Materiel ma = new Materiel();
		ma.setMa_id("BM00030");
		cm.setCmRemark("CM_TEST_SAVE");
		cm.setMa(ma);
		sh.setShId("0822416170928");
		cm.setShelf(sh);
		boolean b = false;
		try {
			b = madao.save(conn, cm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(true, b);
	}

	@Test
	public void testUpdate() {
		Date date = new Date();
		MaterielConfig cm = new MaterielConfig();
		cm.setCmId("1708225160531");
		cm.setCmRemark("CM_TEST_UPDATE");
		boolean b = false;
		try {
			b = madao.update(conn, cm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(true, b);
	}

	@Test
	public void testDelete() {
		boolean b = false;
		try {
			b = madao.delete(conn, "1808224161303");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(true, b);
	}

	@Test
	public void testFindByID() {
		
		try {
			MaterielConfig findById = madao.findById(conn, "1708225160531");
			System.out.println(findById);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
//	public void testGetAll() {
//		try {
//			List<MaterielConfig> all = madao
//					.getAll(conn, null, null, "1608224154057");
//			List<MaterielConfig> all2 = madao.getAll(conn, null, "0816224162145",
//					null);
//			List<MaterielConfig> all3 = madao.getAll(conn, "0822416170905");
//			List<MaterielConfig> all4 = madao.getAll(conn);
//			System.out.println("仓库1608224154057:");
//			for (MaterielConfig shelf : all) {
//				System.out.println(shelf);
//			}
//			System.out.println("仓位0816224162145:");
//			for (MaterielConfig shelf : all2) {
//				System.out.println(shelf);
//			}
//			System.out.println("货架0822416170905:");
//			for (MaterielConfig shelf : all3) {
//				System.out.println(shelf);
//			}
//			System.out.println("所有:");
//			for (MaterielConfig shelf : all4) {
//				System.out.println(shelf);
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testList() {
		try {
			List<MaterielConfig> list = madao.list(conn,"N","块",null, null, null, 1, 10);
			List<MaterielConfig> list2 = madao.listCard(conn, "0822416170905", 1, 10);
			List<MaterielConfig> list3 = madao.listCard(conn, 3, 1);

			System.out.println("仓库1608224154057:");
			for (MaterielConfig shelf :list) {
				System.out.println(shelf);
			}
			System.out.println("货架0822416170905:");
			for (MaterielConfig shelf : list2) {
				System.out.println(shelf);
			}
			System.out.println("所有:");
			for (MaterielConfig shelf : list3) {
				System.out.println(shelf);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testListAllMateriels() {
//		String sql = "select m.materials_id, m.description as ma_name, mm.description as ma_model, ms.description as ma_spec"
//				+ " from bd_materials m "
//				+ " left join bd_spec ms on ms.spec_id = m.spec_id "
//				+ " left join bd_model mm on mm.model_id = m.model_id ";
//		System.out.println(sql);
		try {
			List<MaterielConfig> list = madao.listAllMateriel(conn, "块", 3 , 4);
			for (MaterielConfig shelf :list) {
				System.out.println(shelf);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCount() {
		try {
			System.out.println("1608224154057仓库:"+madao.count(conn,"Y",null, null, "1608224154057"));
			System.out.println("0816224162145仓位:"+madao.count(conn,"N",null,"0816224162145",null));
			System.out.println("0822416170905货架:"+madao.countCard(conn, "0822416170905"));
			System.out.println("所有                               :"+madao.countCard(conn));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCountAllMateriels() {
		try {
			System.out.println(madao.countAllMateriels(conn));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
