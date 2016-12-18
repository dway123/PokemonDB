import java.net.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.Locale;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class AddInEpisodes {
	
	private static final String url = "jdbc:mysql://localhost/PokemonDB";

	private static final String user = "root";

	private static final String password = "machamp";
	
	public static void main(String[] args) throws Exception {
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Success");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Validate.isTrue(args.length == 1, "usage: supply url to fetch");
		
		
		
		
		//Iterator<Element> ite = table.select("td").iterator();
		
		
		
		for (int id = 1; id < 141; id++) {
			
			int num = id + 799;
			
			String ep = String.format("%03d", id);
			
			String url = "http://bulbapedia.bulbagarden.net/wiki/XY" + ep;
			Document doc = Jsoup.connect(url).get();
				
			System.out.println("Episode: " + num);
			
			Elements list = doc.select("p+ ul li");
			
			for (int i = 0; i < list.size(); i++) {
				
				Element ele = list.get(i);
				
				String s1 = ele.text();
				
				String[] poke = s1.split(" ");
				
				String name = poke[0].substring(0,1).toLowerCase() + poke[0].substring(1);
				
				name = name.replaceAll("[^a-zA-Z]", "");
				
				/*if(name.equals("\"Venustoise\"")){
					continue;
				}*/
				
				//System.out.println(name);
				
				String dbcheck = 
						"SELECT pokemonid FROM Pokemon "+
						"WHERE Pokemon.pname = \"" + name + "\" ";
				
				
				
				Statement stmt = conn.createStatement();
			    ResultSet rs = stmt.executeQuery(dbcheck);
			    
			    while(rs.next()){
			    	int dex = rs.getInt("pokemonid");
			    	
			    	String dbcheck2 = 
							"SELECT * FROM appears_in "+
							"WHERE pokemonid = " + dex +
							" AND episodeid = " + num;
					
					
					
					Statement stmt2 = conn.createStatement();
				    ResultSet rs2 = stmt2.executeQuery(dbcheck2);
					
				    if(!rs2.next()){
				    	Statement cstmt = conn.createStatement();
					    String dbadd =
				        			"INSERT INTO appears_in "+
				        			"(episodeid, pokemonid) "+
				        			"VALUES( " + num + ", " + dex + ") ";
					    
					    //System.out.println("dbadd = " + dbadd);
					    cstmt.executeUpdate(dbadd);
				    }
			    	
			    }
			    
			    
				
			}

		}
		

	}
}


