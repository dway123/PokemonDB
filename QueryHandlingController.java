/*
 * Uses one of several SQL queries in order to retrieve query data, and convert that to JSON format
 * TODO: test all queries on Seb's server
 * TODO: create event listener (Swing?)
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

public class QueryHandlingController {
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
	
	//Print JSON
	private static void printResultSet(ResultSet resultSet) throws Exception{
		ResultSetMetaData rsmd = resultSet.getMetaData();
		
		int columnsNumber = rsmd.getColumnCount();
		for (int i = 1; i <= columnsNumber; i++) {
			System.out.print(rsmd.getColumnName(i) + "  ");
		}
		System.out.println("");
		
		while (resultSet.next()) {
		    for (int i = 1; i <= columnsNumber; i++) {
		        if (i > 1) System.out.print(",  ");
		        String columnValue = resultSet.getString(i);
		        System.out.print(columnValue + "\t");
		    }
		    System.out.println("");
		}
	}
	
	//DATABASE CONFIGURATION DATA
    private static final String url = "jdbc:mysql://localhost/PokemonDB?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "Machamp";
    
    //MAIN
	public static void main(String[] args) throws Exception {
		
		Connection conn = null;
		
		try {
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println("DB connection Success");
	 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
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
		System.out.println(jsonArray.toString());
        
    }
}
