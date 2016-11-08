package web.beans;

import java.awt.Image;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import web.db.Database;

public class Book implements Serializable{
    /*
    public class Element {
    private Element elem;
    
    public void SetEl(byte[] file){
        this.elem = file;
    }
    }
    */
    public Book(){
    }
    private long id;
    private String name;
    private int pages;
    private String isbn;
    private Date year;
    private byte[] file;
    private String publisher;
    private String author;
    private String genre;
    private byte[] image;
    private String descr;
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Book(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public Integer getPageCount(){
        return pages;
    }
    
    public void setPageCount(Integer pages){
        this.pages = pages;
    }
    
    public String getIsbn(){
        return isbn;
    }
    
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }
    
    public Date getPublishDate(){
        return year;
    }
    
    public void setPublishDate(Date year){
        this.year = year;
    }
    
    public byte[] getFile(){
        return file;
    }
    
    public void setFile(byte[] file){
        this.file = file;
    }
    
    public String getPublisher(){
        return publisher;
    }
    
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public void setAuthor(String author){
        this.author = author;
    }
    
    public String getGenre(){
        return genre;
    }
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public byte[] getImage(){
        return image;
    }
    
    public void setImage(byte[] image){
        this.image = image;
    }
    
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
    
    public void fillPdfContent() {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("select content from book where id="+this.getId());
            while (rs.next()) {
               this.setFile(rs.getBytes("content"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
