/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.beans.Book;
import web.controllers.SearchController;

/**
 *
 * @author Houston
 */

@WebServlet(name = "readpdf",
urlPatterns = {"/faces/pages/readpdf"})
public class ReadPdfSecond extends HttpServlet {
   
    public void doGet(HttpServletRequest request,
 HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("application/pdf");
        
        OutputStream out = response.getOutputStream();
        try {
            
            int id = Integer.valueOf(request.getParameter("id"));
            SearchController searchController = (SearchController) request.getSession(false).getAttribute("searchController");
            byte[] content = searchController.getContent(id);
            response.setContentLength(content.length);
            out.write(content);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }
}
        
   /*     try {
            // step 1: create a document
            Document document = new Document();
            // step 2: create a writer
            PdfWriter.getInstance(document, response.getOutputStream());
            // step 3: open the document
            document.open();
            // step 4: add content
            document.add(new Paragraph("Science, Bitch!"));
            // step 5: close the document
            document.close();
        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        }
  }
*/
