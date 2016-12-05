import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/apartments";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "root";

    static Connection conn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            try {
                // create connection
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                initDB();

                while (true) {
                    System.out.println("1: add flat");
                    System.out.println("2: delete flat");
                    System.out.println("3: view flat by parameter");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addApartments(sc);
                            break;
                        case "2":
                            deleteFlat(sc);
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

    private static void initDB() throws SQLException {
        Statement st = conn.createStatement();
        try {
//            st.execute("DROP TABLE IF EXISTS flats");
//            st.execute("CREATE TABLE `apartments`.`flats` (\n" +
//                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
//                    "  `region` VARCHAR(45) NOT NULL,\n" +
//                    "  `address` VARCHAR(45) NOT NULL,\n" +
//                    "  `numberRooms` INT NOT NULL,\n" +
//                    "  `square` DOUBLE NOT NULL,\n" +
//                    "  `price` DOUBLE NOT NULL,\n" +
//                    "  PRIMARY KEY (`id`))\n" +
//                    "ENGINE = InnoDB\n" +
//                    "DEFAULT CHARACTER SET = utf8;\n");
        } finally {
            st.close();
        }
    }

    private static void addApartments(Scanner sc) throws SQLException {
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

    private static void deleteFlat(Scanner sc) throws SQLException {
        System.out.print("Enter id flat: ");
        String idFlat = sc.nextLine();
        int id = Integer.parseInt(idFlat);

        PreparedStatement ps = conn.prepareStatement("DELETE FROM flats WHERE id = ?");
        try {
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }


    private static void viewFlatByParameter(Scanner sc) throws SQLException {
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
                    selectFlatByRegion(sc);
                    break;
                case "2":
                    selectFlatByAddress(sc);
                    break;
                case "3":
                    selectFlatBySquare(sc);
                    break;
                case "4":
                    selectFlatByPrice(sc);
                    break;
                default:
                    return;
            }
        }
    }


    private static void selectFlatByRegion(Scanner sc) throws SQLException {
        System.out.println("enter region:");
        String region = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM flats WHERE region=?");
        ps.setString(1, region);
        printFlat(ps);
    }

    private static void selectFlatByAddress(Scanner sc) throws SQLException {
        System.out.println("enter address:");
        String address = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM flats WHERE address=?");
        ps.setString(1, address);
        printFlat(ps);
    }

    private static void selectFlatBySquare(Scanner sc) throws SQLException {
        System.out.println("enter square:");
        String squareApart = sc.nextLine();
        Double square = Double.parseDouble(squareApart);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM flats WHERE square=?");
        ps.setDouble(1, square);
        printFlat(ps);
    }

    private static void selectFlatByPrice(Scanner sc) throws SQLException {
        System.out.println("enter price:");
        String priceApart = sc.nextLine();
        Double price = Double.parseDouble(priceApart);
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
