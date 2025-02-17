package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE subjectid = ? AND num = ? ORDER BY ?";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Create a prepared statement using the query string defined above
                ps = conn.prepareStatement(QUERY_FIND);
                // Set the first parameter (the first "?") in the prepared statement to the given student ID
                ps.setNString(1, subjectid);
                ps.setNString(2,num);
                ps.setInt(3, termid);
                // Execute the query, and get a java.sql.ResultSet
                boolean hasresults = ps.execute();
                // Get the metadata from the result set
                if(hasresults){
                    rs = ps.getResultSet();
                    result = DAOUtility.getResultSetAsJson(rs);
                }
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}