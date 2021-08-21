package Test1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BT2 {
	private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String dbURL = "jdbc:sqlserver://103.95.197.121:9696;databaseName=JavaTest7";
	private static String dbUser = "DACNPM";
	private static String dbPass = "khoa19@itf";
	private static Connection con;
	
	public static void main(String[] args) {
		String url1 = "C:\\Users\\nphuc\\Desktop\\data1.txt";
	    String url2 = "C:\\Users\\nphuc\\Desktop\\data2.txt";
	    
	    String[] data1 = null;
	    String[] data2 = null;
		try {
			data1 = readData(url1);
			data2 = readData(url2);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	    
	    
	    System.out.println("Content of data1.txt:");
	    for(String i:data1) {
	    	System.out.println(i);
	    }
	    
	    System.out.println("\nContent of data2.txt:");
	    for(String i:data2) {
	    	System.out.println(i);
	    }
	    
	    try {
			insertData(url1, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	    try {
			insertData(url2, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static Connection getConnection() throws SQLException, ClassNotFoundException{
			Class.forName(driver);
		    return DriverManager.getConnection(dbURL,dbUser,dbPass);		    
	}
	
	private static String[] readData(String url) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(url);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        
        String res = "";
		String line = bufferedReader.readLine();
    	
        while (line != null) {
        	res += line + "\n";
            line = bufferedReader.readLine();
        }
        
        bufferedReader.close();
        fileInputStream.close();
        
        return res.split("\n");
	}
	
	private static void writeData(String url, String data) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(url);
    	BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
    	
    	bufferedWriter.write(data);
    	
    	bufferedWriter.close();
    	fileOutputStream.close();
    	
    	System.out.println("\nData exported to " + url);
	}
	
	private static void insertData(String url, Boolean mode) throws ClassNotFoundException, SQLException, IOException {
		Connection con = getConnection();
		PreparedStatement stmt;
		
		if (mode) {
			String[] data = readData(url);
			
			
			String query = "INSERT INTO THANHVIEN (MaThanhVien, TenThanhVien, NgaySinh, DiaChi, Email, SoDienThoai, ChiPhiNhan) VALUES (?,?,?,?,?,?,0)";
			stmt = con.prepareStatement(query);
			
			for(String line : data) {
				String[] parts = line.split(", ");
				
				stmt.setString(1, parts[0]);
				stmt.setString(2, parts[1]);
				stmt.setString(3, parts[2]);
				stmt.setString(4, parts[3]);
				stmt.setString(5, parts[4]);
				stmt.setString(6, parts[5]);
				
				try {
					stmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
			
			stmt.close();
			con.close();
		} else {
			String errurl = "C:\\Users\\nphuc\\Desktop\\error.txt";
			String res = "";
			String[] data = readData(url);
			
			String query = "UPDATE THANHVIEN SET ChiPhiNhan = ChiPhiNhan + ? where MaThanhVien = ?";
			stmt = con.prepareStatement(query);
			
			int index = 1;
			for(String line : data) {				
				String[] parts = line.split(", ");
				String error = checkLineError(parts, index);
				
				if (error == "") {
            		int diemthuong = 0;
            		if (parts[2] == "VIP") diemthuong = Integer.parseInt(parts[1]) * 50000;
            		else if (parts[2] == "NOR") diemthuong = Integer.parseInt(parts[1]) * 20000;
            		int newCPN = Integer.parseInt(parts[1]) + diemthuong;
                	                    	
                    stmt.setInt(1, newCPN);
                    stmt.setString(2, parts[0]);
                    try {
						stmt.executeUpdate();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
        		} else {
        			res += error;
        		}
				
				index++;
			}
			
			writeData(errurl, res);
		}
		
		stmt.close();
		con.close();
		
		System.out.println("\nInsert successfully! url = " + url);
	}
	
	
	private static String checkLineError(String[] parts, int index) throws ClassNotFoundException, SQLException {
		String res = "";
		String query = "select * from THANHVIEN Where MaThanhVien = ?";
		
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, parts[0]);
		ResultSet set = stmt.executeQuery();
		if (!set.next()) res += "Dong " + index + ": Khong tim thay ma thanh vien tuong ung\n";
		stmt.close();
		con.close();
    	
		int i;
		try {
			i = Integer.parseInt(parts[1]);
			if (i < 0) res += "Dong " + index + ": Diem thuong khong la so nguyen duong\n";
			if (i > 500 ) res += "Dong " + index + ": Diem thuong lon hon 500\n";
		} catch (Exception e) {
			res += "Dong " + index + ": Diem thuong khong la so nguyen duong\n";
		}
		
		if (!parts[2].equals("VIP") && !parts[2].equals("NOR")) res += "Dong " + index + ": Level khong phai VIP hoac NOR\n";		
		
		return res;
	}
}
