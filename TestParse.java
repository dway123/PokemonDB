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


public class TestParse {
	
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
		String url = "http://bulbapedia.bulbagarden.net/wiki/List_of_anime_episodes";
		Document doc = Jsoup.connect(url).get();
			
		System.out.println("title: " + doc.title());
		
		Element table = doc.select("table[class=roundy]").get(11);
		
		Iterator<Element> ite = table.select("td").iterator();
		
		
		
		for (int id = 893; id < 940; id++) {
			
			if(id == 680){
				for(int i = 1; i<15; i++){
					ite.next();
				}
			}
			
			ite.next();
			ite.next();
			
			String title = ite.next().text();
			ite.next();
			String dateText = ite.next().text();
			//String dateText = "09/08/1998";
			java.sql.Date release;
			
			if(dateText.equals("Unaired") || id > 936){
				release = null;
			}
			else{
				SimpleDateFormat df = new SimpleDateFormat("MMMMM d, yyyy");
				Date date = df.parse(dateText);
				SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
				String d3 = df2.format(date);
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date myDate = formatter.parse(d3);
				release = new java.sql.Date(myDate.getTime());
			}
			
			
			
			
			ite.next();
			ite.next();
			
			String dbadd = 
        			"INSERT INTO Episodes "+
        			"(episodeid, ename, episode, season, releasedate)"+
        			"VALUES( ?, ?, ?, ?, ?)";
			
			PreparedStatement cstmt = conn.prepareStatement(dbadd);
			
			cstmt.setInt(1, id);
			cstmt.setString(2, title);
			cstmt.setInt(3, id - 799);
			cstmt.setInt(4, 12);
			
			if(release == null){
				cstmt.setNull(5, Types.DATE);
			}
			else{
				cstmt.setDate(5, (java.sql.Date) release);
			}
			
			System.out.println("Episode:" + id);
			System.out.println("Title: " + title);
			System.out.println("Season: " + 12);
			System.out.println("Number: " + (id - 799));
			System.out.println("Release date: " + release);
			System.out.println();		
			
			
	        /*String dbadd = 
	        			"INSERT INTO Episodes "+
	        			"(episodeid, ename, episode, season, releasedate)"+
	        			"VALUES(" + id + ", " + "\"" + title + "\"" + ", " + id + ", " + 1 + ", " + "\"" + release + "\"" + ")";*/
	        //System.out.println("dbadd = " + dbadd);
	        cstmt.executeUpdate();

		}
		

	}
}
