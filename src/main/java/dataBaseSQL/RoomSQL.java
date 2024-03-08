package dataBaseSQL;

import classes.Movie;
import classes.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomSQL {
    static Connection connection = ConnectionBDD.ConnectionBDD();

    public static SQLException AddRoom(Room room){
        String sql = "INSERT INTO Movie (`Name`, `Capacity`) " + "VALUES (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, room.getName());
            preparedStatement.setInt(5, room.getCapacity());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException : " +ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
            return ex;
        }
        return null;
    }

    public static List<Room> GetRooms(){
        String sql = "SELECT * FROM Room";
        List<Room> rooms = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql);) {
            while(resultSet.next()){
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                int capacity = resultSet.getInt("Capacity");

                Room room = new Room(id, name, capacity);
                rooms.add(room);
            }
        } catch (SQLException ex) {
            //Handle any errors
            System.out.println("SQLException : " +ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
        }
        return rooms;
    }

    public static Room GetRoomByIdForDisplay(int roomId){
        String sql = "SELECT * FROM Room WHERE Id = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String name = resultSet.getString("Name");
                    int capacity = resultSet.getInt("Capacity");

                    return new Room(id, name, capacity);
                }
            }
        } catch (SQLException ex) {
            //Handle any errors
            System.out.println("SQLException : " +ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
        }
        return null;
    }

    public static SQLException UpdateRoom(Room room){
        String sql = "UPDATE Room SET Name = ?, Capacity = ? WHERE Id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, room.getName());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setInt(3, room.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            //Handle any errors
            System.out.println("SQLException : " +ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
            return ex;
        }
        return null;
    }

    public static SQLException DeleteRoom(Room room){
        String sql = "DELETE FROM Room WHERE Id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, room.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            //Handle any errors
            System.out.println("SQLException : " +ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
            return ex;
        }
        return null;
    }
}

