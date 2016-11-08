package web.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import web.db.Database;

public class AuthorList {
  ArrayList<Author> AuthorList = new ArrayList<Author>(); 
  
  public ArrayList<Author> getAuthors(){
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
      
      try{
          SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ssss");
            System.out.println("authors access: " + dateFormat.format(new Date()));
      conn = Database.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select * from author");
            while(rs.next()){
                Author author = new Author();
                author.setName(rs.getString("fio"));
                AuthorList.add(author);
            }
      }
      catch(SQLException ex){
          Logger.getLogger(AuthorList.class.getName()).log(Level.SEVERE, null, ex);
      }
      finally{
        try{
            if(stmt!=null){
                stmt.close();
            }
            if(rs!=null){
                rs.close();
            }
            if(conn!=null){
                Database.nulledConnVariables();
            }
        }
        catch(SQLException ex){
            Logger.getLogger(AuthorList.class.getName()).log(Level.SEVERE, null, ex);
        } 
      
      }
      
      return AuthorList;
  }
    
    public ArrayList<Author> getAuthorList(){
        if(!AuthorList.isEmpty()){
            return AuthorList;
        }
        else {
            return getAuthors();
        }
  

  }
  
}
