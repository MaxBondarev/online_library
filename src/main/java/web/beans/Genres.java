package web.beans;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import web.db.Database;


@ManagedBean
@ApplicationScoped
public class Genres implements Serializable {
    ArrayList<Genre> genreList = new ArrayList<Genre>();

    public ArrayList<Genre> getGenres(){
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try{
        conn = Database.getConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery("select * from genre order by name");
        
            while(rs.next()){
                Genre genre = new Genre();
                genre.setName(rs.getString("name"));
                genre.setId(rs.getLong("id"));
                genreList.add(genre);
            }

        }
        catch(SQLException ex){
            Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Genres.class.getName()).log(Level.SEVERE, null, ex);
        } 
        }
        
        return genreList;
      
    }

    public ArrayList<Genre> getGenreList(){
        if(!genreList.isEmpty()){
            return genreList;
        }
        else{
            ArrayList<Genre> genre = getGenres();
            return genre;
        }
    }

}
