import java.net.*;
import java.sql.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TestScrap {
	
    private static final String url = "jdbc:mysql://localhost/PokemonDB";
    
    private static final String user = "root";
 
    private static final String password = "machamp";
    
	public static void main(String[] args) throws Exception {
		
		int id = 493;
		
		Connection conn = null;
		
		 try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
	 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		for(id = 152; id < 722; id++){
			URL oracle = new URL("http://pokeapi.co/api/v2/pokemon/"+ id + "/");
	        URLConnection yc = oracle.openConnection();
	        yc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                                    yc.getInputStream()));
	        
	        
	        String response = in.readLine();
	        //System.out.println(response);
	        JSONParser parser = new JSONParser();
	        JSONObject j1 = (JSONObject) parser.parse(response);
	        
	        //Object species = parser.parse(j1.get("species"));
	        JSONObject j2 = (JSONObject)(j1.get("species"));
	        
	        String pname = (String)j2.get("name");
	        
	        System.out.println("Name = " + pname);
	        
	        long [] pstats = {0,0,0,0,0,0};
	        JSONArray stats = (JSONArray)(j1.get("stats"));
	        for(int i=0; i<6; i++){
	            j2 = (JSONObject)(stats.get(i));
	            pstats[i] = (long)j2.get("base_stat");
	            //System.out.println(pstats[i]);
	        }
	        
	              
	        Statement cstmt = conn.createStatement();
	        
	        String dbadd = "INSERT INTO Pokemon (pokemonid, pname, hp, attack, defense, specialattack, specialdefense, speed) VALUES(" + id + ", " + "\"" + pname + "\"" + ", " + pstats[5] + ", " + pstats[4] + ", " + pstats[3] + ", " + pstats[2] + ", " + pstats[1] + ", " + pstats[0]+ ")";
	        
	        cstmt.executeUpdate(dbadd);
	        
	        in.close();
		}
		 
		
        
    }
}
