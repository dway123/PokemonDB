import java.net.*;
import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GetAbilities {
	


	public static String addSlashes(String s) {
		s = s.replaceAll("\"", "\\\\\"");
	    s = s.replaceAll("'", "\\\\'");
	    return s;
	}


	
	private static final String url = "jdbc:mysql://localhost/PokemonDB?autoReconnect=true&useSSL=false";
    
    private static final String user = "root";
 
    private static final String password = "machamp";
    
	public static void main(String[] args) throws Exception {
		
		int id = 0;
		
		Connection conn = null;
		
		 try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
	 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		for(id = 1; id < 192; id++){
			URL oracle = new URL("http://pokeapi.co/api/v2/ability/"+ id + "/");
	        URLConnection yc = oracle.openConnection();
	        yc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                                    yc.getInputStream()));
	        
	        
	        String response = in.readLine();
	        //System.out.println(response);
	        JSONParser parser = new JSONParser();
	        JSONObject j1 = (JSONObject) parser.parse(response);
	        
	        
	        String aname = (String)j1.get("name");
	        
	        System.out.println("Name = " + aname);
	        
	        JSONArray j2 = (JSONArray)(j1.get("effect_entries"));
	        JSONObject j3 = (JSONObject)(j2.get(0));
	        String eff = (String)j3.get("effect");
	        eff = addSlashes(eff);
	        //System.out.println("Effect: " + eff);
	        
	        
	              
	        Statement cstmt = conn.createStatement();
	        
	        String dbadd = "INSERT INTO Abilities (abilityid, aname, effect) VALUES(" + id + ", " + "\"" + aname + "\"" + ", " + "\"" + eff + "\"" + ")";
	        
	        cstmt.executeUpdate(dbadd);
	        
	        in.close();
		}
		 
		
        
    }
}
