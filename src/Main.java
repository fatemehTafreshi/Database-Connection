import handler.DBHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserInfo userInfo;
        DBHandler handler = new DBHandler();
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        try (Connection connection = handler.getDBConnection()) {

            while (option != 5) {
                System.out.print("\n\nPlease enter menu option :\n 1)INSERT\t 2)Update\t 3)Delete\t 4)Read \t 5)Exit\n");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        userInfo = getData();
                        insertDB(connection, userInfo);
                        break;
                    case 2:
                        userInfo = getData();
                        updateDB(connection, userInfo);
                        break;
                    case 3:
                        int id = getID();
                        deleteDB(connection, id);
                        break;
                    case 4:
                        raedDB(connection);
                        break;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void raedDB(Connection connection) {
        String query = "SELECT * FROM table1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.print("name: " + resultSet.getString("name") + "\t");
                System.out.print("last_name : " + resultSet.getString("last_name") + "\t");
                System.out.print("age : " + resultSet.getString("age") + "\t");
                System.out.println("description : " + resultSet.getString("description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteDB(Connection connection, int ID) {
        String query = "DELETE FROM table1 where ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();

            System.out.println("Data deleted successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getID() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter ID for delete that row:");
        int id = scanner.nextInt();
        return id;
    }

    private static UserInfo getData() {
        Scanner scanner = new Scanner(System.in);
        UserInfo userInfo = new UserInfo();

        System.out.println("enter new name to update:");
        userInfo.name = scanner.nextLine();

        System.out.println("enter new last_name to update:");
        userInfo.lastName = scanner.nextLine();

        System.out.println("enter new age to update:");
        userInfo.age = Integer.parseInt(scanner.nextLine());

        System.out.println("enter new description to update:");
        userInfo.desc = scanner.nextLine();

        System.out.println("enter ID for update that row:");
        userInfo.id = Integer.parseInt(scanner.nextLine());

        return userInfo;
    }

    private static void insertDB(Connection connection, UserInfo userInfo) {
        String query = "INSERT INTO table1 (name,last_name,age,description) values(?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userInfo.name);
            preparedStatement.setString(2, userInfo.lastName);
            preparedStatement.setInt(3, userInfo.age);
            preparedStatement.setString(4, userInfo.desc);
            preparedStatement.executeUpdate();

            System.out.println("Data inserted successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateDB(Connection connection, UserInfo userInfo) {

        String query = "UPDATE table1 set name=?,last_name=?,age=?,description=? WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userInfo.name);
            preparedStatement.setString(2, userInfo.lastName);
            preparedStatement.setString(4, userInfo.desc);

            preparedStatement.setInt(3, userInfo.age);
            preparedStatement.setInt(5, userInfo.id);

            preparedStatement.executeUpdate();
            System.out.println("Data updated successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static class UserInfo {
        int id;
        String name;
        String lastName;
        int age;
        String desc;

    }
}