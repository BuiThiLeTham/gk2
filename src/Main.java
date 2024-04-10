import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static final String JDBC_URL = "jdbc:mysql://localhost:3306/thigk2";
    static final String USERNAME = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        try {

            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");


            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Hiển thị tất cả nhân viên");
                System.out.println("2. Thêm thành viên");
                System.out.println("3. Sửa thành viên");
                System.out.println("4. Hiển thị 1 nhân viên");
                System.out.println("5. Xóa 1 nhân viên");
                System.out.println("6. Số lượng thành viên");
                System.out.println("7. Thoát");
                System.out.println("8. Nhập file ");
                System.out.println("9. Xuất file");

                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        displayAllEmployees(connection);
                        break;
                    case 2:
                        addEmployee(connection);
                        break;
                    case 3:
                        updateEmployee(connection);
                        break;
                    case 4:
                        displayEmployee(connection);
                        break;
                    case 5:
                        deleteEmployee(connection);
                        break;
                    case 6:
                        int employeeCount = countEmployees(connection);
                        System.out.println("Tong so nhan vien la: " + employeeCount);
                        break;
                    case 7:
                        // Đóng kết nối và thoát khỏi chương trình
                        connection.close();
                        System.out.println("Dong ket noi.");
                        System.exit(0);
                    case 8:
                        importEmployeesFromFile(connection);
                        System.out.println("hoan thanh!");
                    case 9:
                        exportEmployeesToFile(connection);
                        System.out.println("Hoan thanh!");
                    default:
                        System.out.println("Chon lai!");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void displayAllEmployees(Connection connection) throws SQLException {
        String sql = "SELECT * FROM employees";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getString("ID"));
            System.out.println("Full Name: " + resultSet.getString("full_name"));
            System.out.println("Birth Day: " + resultSet.getString("birth_day"));
            System.out.println("Phone: " + resultSet.getString("phone"));
            System.out.println("Email: " + resultSet.getString("email"));
            System.out.println("Employee Type: " + resultSet.getString("employee_type"));
            if (resultSet.getString("employee_type").equals("Experience")) {
                System.out.println("Experience in Year: " + resultSet.getInt("exp_in_year"));
                System.out.println("Professional Skill: " + resultSet.getString("pro_skill"));
            } else if (resultSet.getString("employee_type").equals("Fresher")) {
                System.out.println("Graduation Date: " + resultSet.getString("graduation_date"));
                System.out.println("Graduation Rank: " + resultSet.getString("graduation_rank"));
                System.out.println("Education: " + resultSet.getString("education"));
            } else if (resultSet.getString("employee_type").equals("Intern")) {
                System.out.println("Majors: " + resultSet.getString("majors"));
                System.out.println("Semester: " + resultSet.getString("semester"));
                System.out.println("University Name: " + resultSet.getString("university_name"));
            }
            System.out.println("------------------------");
        }
        resultSet.close();
        statement.close();
    }


    static void addEmployee(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee ID:");
        String ID = scanner.nextLine();
        System.out.println("Enter employee full name:");
        String fullName = scanner.nextLine();
        System.out.println("Enter employee birth day (YYYY-MM-DD):");
        String birthDay = scanner.nextLine();
        System.out.println("Enter employee phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter employee email:");
        String email = scanner.nextLine();
        System.out.println("Enter employee type (Experience, Fresher, or Intern):");
        String employeeType = scanner.nextLine();
        String sql = "INSERT INTO employees (ID, full_name, birth_day, phone, email, employee_type";
        StringBuilder valuesBuilder = new StringBuilder("VALUES (?, ?, ?, ?, ?, ?");

        switch (employeeType.toLowerCase()) {
            case "experience":
                System.out.println("Enter years of experience:");
                int expInYear = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                System.out.println("Enter professional skill:");
                String proSkill = scanner.nextLine();
                sql += ", exp_in_year, pro_skill)";
                valuesBuilder.append(", ?, ?)");
                break;
            case "fresher":
                System.out.println("Enter graduation date (YYYY-MM-DD):");
                String graduationDate = scanner.nextLine();
                System.out.println("Enter graduation rank:");
                String graduationRank = scanner.nextLine();
                System.out.println("Enter education:");
                String education = scanner.nextLine();
                sql += ", graduation_date, graduation_rank, education)";
                valuesBuilder.append(", ?, ?, ?)");
                break;
            case "intern":
                System.out.println("Enter majors:");
                String majors = scanner.nextLine();
                System.out.println("Enter semester:");
                String semester = scanner.nextLine();
                System.out.println("Enter university name:");
                String universityName = scanner.nextLine();
                sql += ", majors, semester, university_name)";
                valuesBuilder.append(", ?, ?, ?)");
                break;
            default:
                System.out.println("Invalid employee type.");
                return;
        }

        sql += valuesBuilder.toString();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, ID);
        statement.setString(2, fullName);
        statement.setString(3, birthDay);
        statement.setString(4, phone);
        statement.setString(5, email);
        statement.setString(6, employeeType);

        int parameterIndex = 7;
        switch (employeeType.toLowerCase()) {
            case "experience":
                int expInYear = 0;
                statement.setInt(parameterIndex++, expInYear);
                String proSkill = null;
                statement.setString(parameterIndex, proSkill);
                break;
            case "fresher":
                String graduationDate = null;
                statement.setString(parameterIndex++, graduationDate);
                String graduationRank = null;
                statement.setString(parameterIndex++, graduationRank);
                String education = null;
                statement.setString(parameterIndex, education);
                break;
            case "intern":
                String majors = null;
                statement.setString(parameterIndex++, majors);
                String semester = null;
                statement.setString(parameterIndex++, semester);
                String universityName = null;
                statement.setString(parameterIndex, universityName);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + employeeType.toLowerCase());
        }

        statement.executeUpdate();
        statement.close();
        System.out.println("Employee added to database.");
    }


    static void updateEmployee(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee ID to update:");
        String ID = scanner.nextLine();
        System.out.println("Enter new email:");
        String newEmail = scanner.nextLine();
        String sql = "UPDATE employees SET email = ? WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newEmail);
        statement.setString(2, ID);
        statement.executeUpdate();
        statement.close();
        System.out.println("Employee updated in database.");
    }

    static void displayEmployee(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee ID to display:");
        String ID = scanner.nextLine();
        String sql = "SELECT * FROM employees WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, ID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getString("ID"));
            System.out.println("Full Name: " + resultSet.getString("full_name"));
            System.out.println("Birth Day: " + resultSet.getString("birth_day"));
            System.out.println("Phone: " + resultSet.getString("phone"));
            System.out.println("Email: " + resultSet.getString("email"));
            System.out.println("Employee Type: " + resultSet.getString("employee_type"));
            if (resultSet.getString("employee_type").equals("Experience")) {
                System.out.println("Experience in Year: " + resultSet.getInt("exp_in_year"));
                System.out.println("Professional Skill: " + resultSet.getString("pro_skill"));
            } else if (resultSet.getString("employee_type").equals("Fresher")) {
                System.out.println("Graduation Date: " + resultSet.getString("graduation_date"));
                System.out.println("Graduation Rank: " + resultSet.getString("graduation_rank"));
                System.out.println("Education: " + resultSet.getString("education"));
            } else if (resultSet.getString("employee_type").equals("Intern")) {
                System.out.println("Majors: " + resultSet.getString("majors"));
                System.out.println("Semester: " + resultSet.getString("semester"));
                System.out.println("University Name: " + resultSet.getString("university_name"));
            }
            System.out.println("------------------------");
        }
        resultSet.close();
        statement.close();
    }

    static void deleteEmployee(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee ID to delete:");
        String ID = scanner.nextLine();
        String sql = "DELETE FROM employees WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, ID);
        statement.executeUpdate();
        statement.close();
        System.out.println("Employee deleted from database.");
    }

    static int countEmployees(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM employees";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;
        if (resultSet.next()) {
            count = resultSet.getInt("count");
        }
        resultSet.close();
        statement.close();
        return count;
    }


    static void exportEmployeesToFile(Connection connection) throws SQLException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"))) {
            String sql = "SELECT * FROM employees";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(resultSet.getString("ID")).append(",");
                sb.append(resultSet.getString("full_name")).append(",");
                sb.append(resultSet.getString("birth_day")).append(",");
                sb.append(resultSet.getString("phone")).append(",");
                sb.append(resultSet.getString("email")).append(",");
                sb.append(resultSet.getString("employee_type")).append(",");
                // Append other columns according to their types
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Employees exported to file.");
    }

    static void importEmployeesFromFile(Connection connection) {
        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Employees imported from file.");
    }
}
