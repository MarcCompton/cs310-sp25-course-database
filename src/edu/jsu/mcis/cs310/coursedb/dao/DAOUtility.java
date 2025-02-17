package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        ResultSetMetaData rsmd = null;
        
        try {
        
            if (rs != null) {

                // Get metadata for the result set
                rsmd = rs.getMetaData();      
                int cols = rsmd.getColumnCount();
                
                // For each row in the result set...
                while(rs.next()){
                    
                    LinkedHashMap<String, String> jObject = new LinkedHashMap<>();
                    // For each column in the result set...
                    for(int i = 1; i<= cols;i++){
                        jObject.put(rsmd.getColumnLabel(i), rs.getString(i));
                    }
                // Add the JSON object to the array        
                records.add(jObject);
                }
                

            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        // Return the JSON array as a string
        return Jsoner.serialize(records);
        
    }
    
}