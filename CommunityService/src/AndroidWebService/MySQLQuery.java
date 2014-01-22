package AndroidWebService;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;         
import java.sql.ResultSet;          
import java.sql.SQLException;       
import java.sql.PreparedStatement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
 
public class MySQLQuery {           
 
    Connection conn = null;            
    PreparedStatement stmt = null;     
    String sql = null;
    
    public String getResultString(int kind, String id) { 
    	 
        String parse = "``"; //used to separate data when parsing (which is done in main android app)
        String parse1 = "~~"; //used to separate objects
    	
    	String result = "" + kind + parse + id + parse + parse1;
 
    	int step = 0;
    	Context ctx = null;
    	try {
    		ctx = (Context) new InitialContext().lookup("java:comp/env");
    	} catch (NamingException e1) {

    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		e1.printStackTrace(pw);
    		
        	result += "exception " + e1.toString();
        	result += " step " + step + " ";
        	result += sw.toString();
    	}
    	step = 1;
    	DataSource source = null;
    	try {
    		source = (DataSource)ctx.lookup("jdbc/mysql");
    	} catch (NamingException e1) {

    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		e1.printStackTrace(pw);
    		
        	result += "exception " + e1.toString();
        	result += " step " + step + " ";
        	result += sw.toString();
    	}
             
    	try {     
            step = 2;
            conn = source.getConnection();
            step = 3;
      
            if(kind == MainServlet.kindVolQuery)
            	sql = "SELECT * FROM dbAppData.Volunteer WHERE VolunteerName = ?";
            else if(kind == MainServlet.kindEventVolQuery)
            	sql = "SELECT * FROM dbAppData.EventVolunteer WHERE VolunteerD = ?";
            else if(kind == MainServlet.kindEventQuery)
            	sql = "SELECT * FROM dbAppData.Event WHERE EventID = ?";

            else if(kind == MainServlet.kindFindQuery)
            	sql = "SELECT * FROM dbAppData.Event LIMIT 100";
            else if(kind == MainServlet.kindInterestQuery)
            	sql = "SELECT * FROM dbAppData.Interest LIMIT 100";
            else if(kind == MainServlet.kindEventInterestQuery)
            	sql = "SELECT * FROM dbAppData.EventInterests WHERE EventID = ?";
            else
            	result += "error bad kind";
            
            stmt = conn.prepareStatement(sql);
            if(kind != MainServlet.kindFindQuery && kind != MainServlet.kindInterestQuery)
            	stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();
            
            step = 3;
            while(rs.next()){
            	
            	if(kind == MainServlet.kindVolQuery) {
            		
            		result += rs.getString("VolunteerID").trim() + parse;
            		result += rs.getString("VolunteerName").trim() + parse;
            		result += rs.getString("VolunteerPassword").trim() + parse;
            		result += rs.getString("PhoneNumber").trim() + parse;
            		result += rs.getString("EmailAddress").trim() + parse;
            	}
            	else if(kind == MainServlet.kindEventVolQuery) {
            		
            		result += rs.getString("EventVolunteerID").trim() + parse;
            		result += rs.getString("EventID").trim() + parse;
            		result += rs.getString("VolunteerD").trim() + parse;
            	}
            	else if(kind == MainServlet.kindEventQuery || kind == MainServlet.kindFindQuery) {
            		
            		result += rs.getString("EventID").trim() + parse;
            		result += rs.getString("EventName").trim() + parse;
            		result += rs.getString("Description").trim() + parse;
            		result += rs.getString("Location").trim() + parse;
            		result += rs.getString("BeginTime").trim() + parse;
            		result += rs.getString("EndTime").trim() + parse;
            	}
            	else if(kind == MainServlet.kindInterestQuery) {
            		
            		result += rs.getString("InterestID").trim() + parse;
            		result += rs.getString("Name").trim() + parse;
            		result += rs.getString("Description").trim() + parse;
            	}
            	else if(kind == MainServlet.kindEventInterestQuery) {
            		
            		result += rs.getString("EventInterestsID").trim() + parse;
            		result += rs.getString("EventID").trim() + parse;
            		result += rs.getString("InterestID").trim() + parse;
            	}            	
            	result += parse1;
            }                                                                         
 
            rs.close();                                                               
            stmt.close();                                                             
            stmt = null;
 
            conn.close();                                                             
            conn = null;                                                   
 
        }                                                               
        catch(Exception e){
        	
        	System.out.println(e);
        	
        	result += "exception " + e.toString();
        	result += " step " + step + " ";
        	result += e.getMessage();
        	result += e.getLocalizedMessage();
        }                      
 
        finally {                                                       
 
            if (stmt != null) {                                            
                try {                                                         
                    stmt.close();                                                
                } catch (SQLException sqlex) {                                
                    // ignore -- as we can't do anything about it here           
                }                                                             
 
                stmt = null;                                            
            }
            
            if (conn != null) {                                      
                try {                                                   
                    conn.close();                                          
                } catch (SQLException sqlex) {                          
                    // ignore -- as we can't do anything about it here     
                }
                conn = null;                                            
            }                                                        
        }              
 
        return result;
    }      
    
    //OLD VERSION
//    public EventData getEvent(String id) { 
// 
//    	EventData dat = new EventData();
// 
//        try {      
//            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
//            conn = ((DataSource) ctx.lookup("jdbc/mysql")).getConnection(); 
//      
//            sql = "SELECT * FROM dbAppData.Event WHERE EventID = ?;";
//            
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1,id);
//            ResultSet rs = stmt.executeQuery();
//            
//            while(rs.next()){
//            	dat.setEventId( id );
//            	dat.setEventName(rs.getString("EventName").trim());
//            	dat.setDescription(rs.getString("Description").trim());
//            	dat.setLocation(rs.getString("Location").trim());
//            	dat.setBeginTime(rs.getString("BeginTime").trim());
//            	
//            	break; //do not get > 1 result
//            	            	
//            	//original tutorial code
//            	/*
//            	dat.setCode(rs.getString("code").trim());
//            	dat.setName(rs.getString("name").trim());
//            	dat.setContinent(rs.getString("continent").trim());
//            	dat.setRegion(rs.getString("region").trim());
//            	dat.setLifeExpectancy(rs.getString("lifeExpectancy") == null ? new Double(0) : Double.parseDouble(rs.getString("lifeExpectancy").trim()));
//            	dat.setGnp(rs.getString("gnp") == null ? new Double(0)  : Double.parseDouble(rs.getString("gnp").trim()));
//            	dat.setSurfaceArea(rs.getString("surfaceArea") == null ? new Double(0)  : Double.parseDouble(rs.getString("surfaceArea").trim()));
//                dat.setPopulation(rs.getString("population") == null ? 0 : Integer.parseInt(rs.getString("population").trim()));
//                */
//            }                                                                         
// 
//            rs.close();                                                               
//            stmt.close();                                                             
//            stmt = null;
// 
//            conn.close();                                                             
//            conn = null;                                                   
// 
//        }                                                               
//        catch(Exception e){System.out.println(e);}                      
// 
//        finally {                                                       
// 
//            if (stmt != null) {                                            
//                try {                                                         
//                    stmt.close();                                                
//                } catch (SQLException sqlex) {                                
//                    // ignore -- as we can't do anything about it here           
//                }                                                             
// 
//                stmt = null;                                            
//            }                                                        
// 
//            if (conn != null) {                                      
//                try {                                                   
//                    conn.close();                                          
//                } catch (SQLException sqlex) {                          
//                    // ignore -- as we can't do anything about it here     
//                }                                                       
// 
//                conn = null;                                            
//            }                                                        
//        }              
// 
//        return dat;
//    }  
}