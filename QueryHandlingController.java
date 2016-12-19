/*
 * Uses one of several SQL queries in order to retrieve query data, and convert that to JSON format
 * TODO: create event listener
 * TODO: coordinate with frontend layer
 * TODO: more SQL statements (ex. sort by *, order by TV episodes of appearance, which Pokemon is this Pokemon good against?)
 * TODO: improve OOP design
 * 
 * README!
 * Make sure you have jdbc and org.json.simple dependencies prepared
 * check configuration data url/user/password for correctness
 */




import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/QueryHandlingController")
public class QueryHandlingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//DATABASE CONFIGURATION DATA
	
//    private static final String url = "jdbc:mysql://199.98.20.192:5222/PokemonDB?autoReconnect=true&useSSL=false";
//    private static final String user = "root";
//    private static final String password = "machamp";
    
    private static final String url = "jdbc:mysql://localhost/PokemonDB?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "Machamp";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryHandlingController() {
        super();
        // Auto-generated constructor stub
    }
    
	//
	public JSONArray getResultsFromDB(String req) throws Exception{
		Connection conn = null;
		
        conn = DriverManager.getConnection(url, user, password);
        //System.out.println("DB connection Success");
		
		Statement stmt = conn.createStatement();
		String query;
		
		//ALL EXISTING SQL STATEMENTS BELOW FOR CONVENIENCE
		//query = getSelectAllQueryString("Types");
		//query = getPokemonWithMoveQueryString("Thunder Shock");
		query = getMovesForPokemonQueryString("Pikachu");
		//query = getEpisodesWithPokemonQueryString("Pikachu");
		//query = getPokemonInEpisodeQueryString(1,1);
		//query = getPokemonTypesQueryString("Pikachu");
		
		
		System.out.println(query +  "\n");
		ResultSet resultSet = stmt.executeQuery(query);
		
		//printResultSet(resultSet);
		JSONArray jsonArray = convertResultSetIntoJSON(resultSet);
		return jsonArray;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	//HTTP POST
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		String msg = request.getParameter("input");
		try {
			//CHECK INPUT
			System.out.println("Recieved HTTP POST with input = " + msg);
			//JSONParser parser = new JSONParser();
			//JSONObject json = (JSONObject) parser.parse(msg);			
			
			//REQ RESULTS FROM DB
			JSONArray results = getResultsFromDB(msg);
			System.out.println("Sending: " + results.toString());			
			
			//SEND OUTPUT
			response.setContentType("application/json");
		    PrintWriter responseprinter = response.getWriter();
		    responseprinter.println(results); 
		    responseprinter.close();
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	//QUERIES
	static String getSelectAllQueryString(String table_name){
		return "SELECT * FROM " + table_name;
	}
	
	static String getPokemonWithMoveQueryString(String move_name){
		return  "SELECT Pokemon.pokemonid, Pokemon.pname " +
				"FROM Pokemon, Moves, learns " + 
				"WHERE Pokemon.pokemonid = learns.pokemonid AND " +
				"learns.moveid = Moves.moveid AND " +
				"Moves.mname = '" + move_name + "'";
	}
	
	static String getMovesForPokemonQueryString(String pokemon_name){
		return  "SELECT Moves.mname, Moves.description, Moves.description, Moves.category, Moves.power, Moves.accuracy "+
				"FROM Pokemon, Moves, learns " + 
				"WHERE Pokemon.pokemonid = learns.pokemonid AND " +
				"learns.moveid = Moves.moveid AND " +
				"Pokemon.pname = '" + pokemon_name + "'";
	}
	
	static String getEpisodesWithPokemonQueryString(String pokemon_name){
		return  "SELECT Episodes.ename, Episodes.episode, Episodes.season, Episodes.releasedate "+
				"FROM Pokemon, Episodes, appears_in " + 
				"WHERE Pokemon.pokemonid = appears_in.pokemonid AND " +
				"Episodes.episodeid = appears_in.episodeid AND " +
				"Pokemon.pname = '" + pokemon_name + "'";
	}
	
	static String getPokemonInEpisodeQueryString(int season, int episode){
		return  "SELECT Pokemon.pokemonid, Pokemon.pname "+
				"FROM Pokemon, Episodes, appears_in " + 
				"WHERE Pokemon.pokemonid = appears_in.pokemonid AND " +
				"Episodes.episodeid = appears_in.episodeid AND " +
				"Episodes.episode = " + episode + " AND " +
				"Episodes.season = " + season;
	}
	
	static String getPokemonTypesQueryString(String pokemon_name){
		return  "SELECT Types.tname "+
				"FROM Pokemon, Types, has " + 
				"WHERE Pokemon.pokemonid = has.pokemonid AND " +
				"has.typeid = Types.typeid AND " +
				"Pokemon.pname = '" + pokemon_name + "'";
	}

	
	//RESULTSET -> JSON
	//Modified from https://gist.github.com/azakordonets/11040771
	private static JSONArray convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null){
                    columnValue = "null";
                }
                obj.put(columnName, columnValue);
            };
            jsonArray.add(obj);
        }
        return jsonArray;
    }

    
    //MAIN
//	public static void main(String[] args) throws Exception {
//		
//		Connection conn = null;
//		
//		try {
//            conn = DriverManager.getConnection(url, user, password);
//            //System.out.println("DB connection Success");
//	 
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//		
//		Statement stmt = conn.createStatement();
//		String query;
//		
//		//ALL EXISTING SQL STATEMENTS BELOW FOR CONVENIENCE
//		//query = getSelectAllQueryString("Types");
//		//query = getPokemonWithMoveQueryString("Thunder Shock");
//		query = getMovesForPokemonQueryString("Pikachu");
//		//query = getEpisodesWithPokemonQueryString("Pikachu");
//		//query = getPokemonInEpisodeQueryString(1,1);
//		//query = getPokemonTypesQueryString("Pikachu");
//		
//		
//		System.out.println(query +  "\n");
//		ResultSet resultSet = stmt.executeQuery(query);
//		
//		//printResultSet(resultSet);
//		JSONArray jsonArray = convertResultSetIntoJSON(resultSet);
//		System.out.println(jsonArray.toString());
//        
//    }
}
