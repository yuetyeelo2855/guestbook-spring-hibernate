package com.guestbook.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.guestbook.model.Entry;
import com.guestbook.model.Guest;
import com.guestbook.service.GuestbookManager;
import com.guestbook.service.util.BasicQuery;


public class GuestbookHibernateImplServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final Logger log = Logger.getLogger(GuestbookHibernateImplServlet.class);
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		List<Entry> entries = GuestbookManager.forEntry().find(new BasicQuery().sort("id"));
		request.setAttribute("entries", entries);
		
		
		List<Guest> guests = GuestbookManager.forGuest().find(new BasicQuery()
					.sort("firstName")
					.sort("lastName") );
		
		request.setAttribute("guests", guests);
		
		
		forward(request, response, "/WEB-INF/jsp/guestbook.jsp");
	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		  PrintWriter out = response.getWriter();
	      String firstName = request.getParameter("first_name");
	      String lastName = request.getParameter("last_name");
	      String content = request.getParameter("content");
	      if (firstName == "" || content == "") {
	        out.println("<html><head></head><body>You are missing either a message or a name! Try again! Redirecting in 3 seconds...</body></html>");
	      } else {
	    	  
	    	  Guest guest = null;

	    	  // Has this guest left an entry before?
	    	  List<Guest> guests = GuestbookManager.forGuest().find(new BasicQuery()
	    			  .filter("lastName", lastName)
	    			  .filter("firstName", firstName) );
	    	  
	    	  if (guests.size() != 0) {
	    		  guest = guests.get(0);
	    	  } else {
		    	  guest = new Guest();
		    	  guest.setFirstName(firstName);
		    	  guest.setLastName(lastName);
		    	  GuestbookManager.forGuest().save(guest);
	    	  }
	    	  
	    	  Entry entry = new Entry();	    	  
	    	  entry.setContent(content);
	    	  entry.setGuest(guest);
	    	  GuestbookManager.forEntry().save(entry);
	      }
	      
	      doGet(request, response);
	      return;
	  }
	
	
	

	
    /**
     * Forwards request and response to given path. Handles any
     * exceptions caused by forward target by printing them to logger.
     * 
     * @param request
     * @param response
     * @param path
     */
    protected void forward(final HttpServletRequest request, final HttpServletResponse response, final String path) {
        try {
            final RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);
        }
        catch (final Throwable tr) {
        	// TODO
        	log.error(tr);
        }
        finally {
        	// TODO
        }
    }
}
