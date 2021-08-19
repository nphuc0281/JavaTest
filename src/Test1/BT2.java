package Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BT2 {
	private static Connection con;
	public static void main(String[] args) {
		try {
			connectDB();
		
			String url1 = "C:\\Users\\nphuc\\Desktop\\data1.txt";
		    String url2 = "C:\\Users\\nphuc\\Desktop\\data2.txt";
		    
		    InsertData(url1, 1);
		    InsertData(url2, 2);
	    
	    
			con.close();
			System.out.println("Disconnected database!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void connectDB() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
		    String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=TOUR";
		    String dbUser = "abcd";
		    String dbPass = "123456";
		    
		    con = DriverManager.getConnection(dbURL,dbUser,dbPass);
		    System.out.println("Connected to database!");
		    
		} catch (Exception e) {
			System.out.println("Err: " + e.getMessage());
		}
	}
	
	private static void InsertData(String url, int key) {
		FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            fileInputStream = new FileInputStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            
            if(key == 1) {
            	String line = bufferedReader.readLine();
            	
                while (line != null) {
                	String[] sline = line.split(", ");
                	
                	for (int i = 0; i < sline.length; i++) {
                		sline[i] = "'" + sline[i] +"'";
                	}
                	
                	line = String.join(", ", sline);
                	
                	System.out.println(line);
                	
                	String query = "INSERT INTO THANHVIEN (MaThanhVien, TenThanhVien, NgaySinh, DiaChi, Email, SoDienThoai, ChiPhiNhan) VALUES ";
                	
                    excuteUpdate(query + "(" + line + ", 0)");
                    
                    line = bufferedReader.readLine();
                }
            } else if (key == 2) {
            	String line = bufferedReader.readLine();
            	while (line != null) {
            		String[] sline = line.split(", ");
            		int newCPN = 0;
            		int diemthuong = 0;
            		if (sline[2] == "VIP") diemthuong = Integer.parseInt(sline[1]) *50000;
            		else if (sline[2] == "NOR") diemthuong = Integer.parseInt(sline[1])*20000;
            		newCPN = Integer.parseInt(sline[1]) + diemthuong;
                	String query = "UPDATE THANHVIEN SET ChiPhiNhan = ChiPhiNhan + " + newCPN + " WHERE MaThanhVien = '" + sline[0] + "'";
                	
                	System.out.println(query);
                	
                    excuteUpdate(query);
                    
                    line = bufferedReader.readLine();
                }
            }
            
        } catch (Exception ex) {
           System.out.println(ex.getMessage());
        } finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (IOException ex) {
                
            }
        }
	}
	
	private static void excuteUpdate(String query) {
        try {
        	Statement stmt = con.createStatement();
        	stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static ResultSet excuteQuery(String query) {
        try {
        	Statement stmt = con.createStatement();
        	ResultSet set = stmt.executeQuery(query);
        	return set;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
