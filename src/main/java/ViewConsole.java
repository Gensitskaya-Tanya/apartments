import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewConsole {
	private Connection conn;

	public void startApartments() {
		Scanner sc = new Scanner(System.in);
		try {
			try {
				conn = new DatabaseConnection().connection();
//                Sql.initDB(conn);
				while (true) {
					System.out.println("1: add flat");
					System.out.println("2: delete flat");
					System.out.println("3: view flat by parameter");
					System.out.print("-> ");
					String s = sc.nextLine();
					switch (s) {
						case "1":
							addApartmentsView(sc);
							break;
						case "2":
							deleteFlatView(sc);
							break;
						case "3":
							viewFlatByParameter(sc);
							break;
						default:
							return;
					}
				}
			} finally {
				sc.close();
				if (conn != null) conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return;
		}
	}

	private void addApartmentsView(Scanner sc) throws SQLException {
		System.out.print("Enter region: ");
		String region = sc.nextLine();
		System.out.print("Enter address: ");
		String address = sc.nextLine();
		System.out.print("Enter number of rooms: ");
		String numberOfrooms = sc.nextLine();
		int numberRooms = Integer.parseInt(numberOfrooms);
		System.out.print("Enter square: ");
		String squar = sc.nextLine();
		double square = Double.parseDouble(squar);
		System.out.print("Enter price: ");
		String priceApart = sc.nextLine();
		double price = Double.parseDouble(priceApart);
		Sql.addApartments(conn, region, address, numberRooms, square, price);
	}

	private void deleteFlatView(Scanner sc) throws SQLException {
		System.out.print("Enter id flat: ");
		String idFlat = sc.nextLine();
		int id = Integer.parseInt(idFlat);
		Sql.deleteFlat(conn, id);
	}

	private void viewFlatByParameter(Scanner sc) throws SQLException {
		System.out.println("select parameter: ");
		while (true) {
			System.out.println("1: view flat by region");
			System.out.println("2: view flat by address");
			System.out.println("3: view flat by square");
			System.out.println("4: view flat by price");
			System.out.print("-> ");
			String s = sc.nextLine();
			switch (s) {
				case "1":
					selectFlatByRegionView(sc);
					break;
				case "2":
					selectFlatByAddressView(sc);
					break;
				case "3":
					selectFlatBySquareView(sc);
					break;
				case "4":
					selectFlatByPriceView(sc);
					break;
				default:
					return;
			}
		}
	}

	private void selectFlatByRegionView(Scanner sc) throws SQLException {
		System.out.println("enter region:");
		String region = sc.nextLine();
		Sql.selectFlatByRegion(conn, region);
	}

	private void selectFlatByAddressView(Scanner sc) throws SQLException {
		System.out.println("enter address:");
		String address = sc.nextLine();
		Sql.selectFlatByAddress(conn, address);
	}

	private void selectFlatBySquareView(Scanner sc) throws SQLException {
		System.out.println("enter square:");
		String squareApart = sc.nextLine();
		Double square = Double.parseDouble(squareApart);
		Sql.selectFlatBySquare(conn, square);
	}

	private void selectFlatByPriceView(Scanner sc) throws SQLException {
		System.out.println("enter price:");
		String priceApart = sc.nextLine();
		Double price = Double.parseDouble(priceApart);
		Sql.selectFlatByPrice(conn, price);
	}

}
