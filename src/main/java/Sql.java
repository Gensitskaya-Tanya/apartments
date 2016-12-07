import java.sql.*;

public class Sql {

	public static void initDB(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		try {
//            st.execute("DROP TABLE IF EXISTS flats");
//			st.execute("CREATE TABLE `apartments`.`flats` (\n" +
//					"  `id` INT NOT NULL AUTO_INCREMENT,\n" +
//					"  `region` VARCHAR(45) NOT NULL,\n" +
//					"  `address` VARCHAR(45) NOT NULL,\n" +
//					"  `numberRooms` INT NOT NULL,\n" +
//					"  `square` DOUBLE NOT NULL,\n" +
//					"  `price` DOUBLE NOT NULL,\n" +
//					"  PRIMARY KEY (`id`))\n" +
//					"ENGINE = InnoDB\n" +
//					"DEFAULT CHARACTER SET = utf8;\n");
		} finally {
			st.close();
		}
	}
	public static void addApartments(Connection conn, String region, String address, int numberRooms, double square, double price) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO flats (region, address, numberRooms, square, price) VALUES(?, ?, ?, ?, ?)");
		try {
			ps.setString(1, region);
			ps.setString(2, address);
			ps.setInt(3, numberRooms);
			ps.setDouble(4, square);
			ps.setDouble(5, price);

			ps.executeUpdate();
		} finally {
			ps.close();
		}
	}

	public static void deleteFlat(Connection conn, int id) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM flats WHERE id = ?");
		try {
			ps.setInt(1, id);
			ps.executeUpdate();
		} finally {
			ps.close();
		}
	}

	public static void selectFlatByRegion(Connection conn, String region)throws SQLException{
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM flats WHERE region=?");
		ps.setString(1, region);
		printFlat(ps);
	}

	public static void selectFlatByAddress(Connection conn,String address) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM flats WHERE address=?");
		ps.setString(1, address);
		printFlat(ps);
	}

	public static void selectFlatBySquare(Connection conn, Double square) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM flats WHERE square=?");
		ps.setDouble(1, square);
		printFlat(ps);
	}

	public static void selectFlatByPrice(Connection conn, Double price) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM flats WHERE price=?");
		ps.setDouble(1, price);
		printFlat(ps);
	}

	private static void printFlat(PreparedStatement ps) throws SQLException {
		try {
			ResultSet rs = ps.executeQuery();
			try {
				ResultSetMetaData md = rs.getMetaData();
				for (int i = 1; i <= md.getColumnCount(); i++)
					System.out.print(md.getColumnName(i) + "\t\t");
				System.out.println();

				while (rs.next()) {
					for (int i = 1; i <= md.getColumnCount(); i++) {
						System.out.print(rs.getString(i) + "\t\t");
					}
					System.out.println();
				}
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
	}
}
