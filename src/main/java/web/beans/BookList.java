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
import web.enums.SearchType;

public class BookList {
    ArrayList<Book> bookList = new ArrayList<Book>();

    public ArrayList<Book> getBooks(String str){
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try{
            //SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ssss");
            //System.out.println("books access: " + dateFormat.format(new Date()));
        conn = Database.getConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(str);
        
            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setPageCount(rs.getInt("page_count"));
                book.setIsbn(rs.getString("isbn"));
                book.setPublishDate(rs.getDate("publish_year"));
                //book.setFile(rs.getBytes("content"));
                book.setPublisher(rs.getString("publisher"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setImage(rs.getBytes("image"));
                bookList.add(book);
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
                Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
       
        return bookList;
    }

    public ArrayList<Book> getAllBooks(){
        if(!bookList.isEmpty()){
            return bookList;
        }
        else{
            return getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, "
                + "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " 
                + "inner join author a on b.author_id=a.id " 
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id ");
        }
    }
    
    public ArrayList<Book> getBooksByGenre(long id){
        if (id == 0) {
            return getAllBooks();
        } else {
        return getBooks("select b.id,b.name,b.isbn,b.page_count,b.publish_year, "
                + "p.name as publisher, a.fio as author, g.name as genre, b.image from book b "
                + "inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id "
                + "where genre_id=" + id + " order by b.publish_year ");
        }
    }
    
    public ArrayList<Book> getBooksByLetter(String letter) {
        return getBooks("select b.id,b.name,b.isbn,b.page_count,b.publish_year, "
                + "p.name as publisher, a.fio as author, g.name as genre, b.image from book b "
                + "inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id "
                + "where substr(b.name,1,1)='" + letter + "' order by b.name");
    }
    
    public ArrayList<Book> getBooksBySearch (String searchStr, SearchType type){
        StringBuilder sql = new StringBuilder("select b.id,b.name,b.isbn,b.page_count,b.publish_year, "
                + "p.name as publisher, a.fio as author, g.name as genre, b.image from book b "
                + "inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id ");
        
        if (type == SearchType.AUTHOR) {
            sql.append("where lower(a.fio) like '%" + searchStr.toLowerCase() + "%' order by b.name ");
        }
        else if (type == SearchType.TITLE) {
            sql.append("where lower(b.name) like '%" + searchStr.toLowerCase() + "%' order by b.name ");
        }
        
        return getBooks(sql.toString());
    }
    
}


