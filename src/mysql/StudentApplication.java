package mysql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class StudentApplication {
	
	public static void main(String[] args) {
		String url = null;
		String user = null;
		String pass = null;

		int choice = 9;

		int id;
		String name;
		float fees;
		String qualification;
		String mobile;

		Properties pros = new Properties();
		try {
		pros.load(new FileInputStream("Database.properties"));
		url = pros.getProperty("database.url");
		user = pros.getProperty("database.userName");
		pass = pros.getProperty("database.password");

		} 
		catch (IOException e) {
		e.printStackTrace();
		}
		try (Connection con = DriverManager.getConnection(url, user, pass);
		Scanner scanner = new Scanner(System.in);
		PreparedStatement pInsert = con.prepareStatement("INSERT INTO `student` values(?,?,?,?,?)");
		PreparedStatement pDelete=con.prepareStatement("DELETE FROM `student` where `StudentName`=?");
		PreparedStatement pFind=con.prepareStatement("SELECT * FROM `student` where `StudentName`=?");
		PreparedStatement pAll=con.prepareStatement("SELECT * FROM `student`");
		) {
		do {

		System.out.println("++++++++++++++++++++++++++++++++Student Manu++++++++++++++++++++++++");
		System.out.println(
		"1.Insert a new Student\n2.Delete a student\n3.Find a student\n4.List all student\n5.Exit");
		System.out.println("Enter your choice = ");
		choice = scanner.nextInt();
		
		switch (choice)
		{
		case 1:
		System.out.println("Enter ID: ");
		id = scanner.nextInt();

		System.out.println("Enter name: ");
		name = scanner.next();
		System.out.println("Enter Fees: ");
		fees = scanner.nextFloat();
		System.out.println("Enter qualification: ");
		qualification = scanner.next();
		System.out.println("Enter Mobile: ");
		mobile = scanner.next();

		pInsert.clearParameters();

		pInsert.setInt(1,id);
		pInsert.setString(2,name);
		pInsert.setFloat(3,fees);
		pInsert.setString(4, qualification);
		pInsert.setString(5, mobile);

		pInsert.executeUpdate();
		System.out.println("Inserted :)");
		break;
		
		case 2:
		System.out.println("Enter the name of the student : ");
		name= scanner.next();

		pDelete.clearParameters();

		pDelete.setString(1, name);
		pDelete.executeUpdate();
		System.out.println("Record Deleted :)");
		break;

		
		case 3:
		System.out.println("Enter the Name : ");
		name=scanner.next();

		pFind.clearParameters();

		pFind.setString(1,name);
		ResultSet resultSet = pFind.executeQuery();
		while(resultSet.next()) 
		{
		for(int iTmp=1;iTmp<=5;iTmp++)
		{
		System.out.println(resultSet.getString(iTmp));
		}
		}

		break;
		case 4:
		//Display All the Records
		ResultSet resultAll =pAll.executeQuery();
		int count=1;
		while(resultAll.next()) {
		System.out.println("Record : "+ count);
		for(int iTmp=1;iTmp<=5;iTmp++) {
		System.out.println(resultAll.getString(iTmp));
		}
		count++;

		}
		count=0;
		break;

		}
		} while (choice != 5);

		} catch (SQLException e) {
		e.printStackTrace();
		}

		}


}
