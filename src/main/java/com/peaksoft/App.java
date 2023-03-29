package com.peaksoft;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
//       addStudents("Nurtilek", 23);
//       addStudents("Uson", 24);
//       addStudents("Asan", 25);
//       addStudents("Nurisa", 22);
       deleteStudentById(5);
        for (Student student : getList()) {
            System.out.println(student);
        }

    }
    public static void createTable(){
        String SQL = "CREATE TABLE IF TABLE NOT EXISTS students(" +
                "ID SERIAL PRIMARY KEY," +
                "NAME VARCHAR(50) NOT NULL,"+
                "Age INTEGER);";
        try (Connection connection = Db.connection();
             Statement statement = connection.createStatement()){
             statement.executeUpdate(SQL);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static int getCount(){
        String SQL = "SELECT count(*) FROM students";
        int count = 0;
        try(Connection con = Db.connection();
        Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL);
            resultSet.next();
            count = resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }
    public static void addStudents(String name,int age){
        String SQL = "INSERT INTO students(name,age)"+
                "VALUES(?,?)";
        try(Connection con = Db.connection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL)){
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void getAllStudents(){
        String SQL = "SELECT * FROM students";
        try(Connection con = Db.connection();
            Statement statement = con.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()){
                System.out.print(" id: "+resultSet.getInt(1));
                System.out.print(" name: "+resultSet.getString("name"));
                System.out.println(" age: "+resultSet.getInt("age"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static List<Student> getList(){
        String SQL = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try(Connection con = Db.connection();
            Statement statement = con.createStatement()){
                ResultSet resultSet = statement.executeQuery(SQL);
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setId(resultSet.getInt("id"));
                    student.setName(resultSet.getString("name"));
                    student.setAge(resultSet.getInt("age"));
                    students.add(student);
                }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return students;
    }

    public static void deleteStudentById(int id){
        String SQL = "DELETE FROM students WHERE id = ?";
        try(Connection connection = Db.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            System.out.println("Студент удален "+ id);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

}
