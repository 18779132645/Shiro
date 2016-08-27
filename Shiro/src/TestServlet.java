import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;


public class TestServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TestServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String status = request.getParameter("status");
		if("index".equals(status)){
			request.setAttribute("test", "中国人的");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}else if("create".equals(status)) {
			HttpSession session = request.getSession();     
	        String url = ""; 
	        String name = ""; 
	        System.out.println("name==="+name);
	        ServletContext sc = getServletContext(); 
	        String file_name = request.getParameter("file_name"); 
	        String realName = request.getParameter("realName");
	        String path = request.getParameter("path");
	        String realPath = request.getParameter("realPath");
	        if (realName == null || realName == "") { 
	            int a = 0; 
	            a = file_name.indexOf("=") + 1; 
	            realName = file_name.substring(a); 
	            if (realName.indexOf(".")>0) { 
	                realName = file_name.substring(0, file_name.indexOf(".")); 
	            } 
	        } 
	        if (path == null || path.equals("")) { 
	            url = "/" + file_name; 
	        } else { 
	            url = "/" + path + "/" + file_name; 
	        }
	        if (realPath == null || realPath.equals("") ) { 
	            if (path == null || path.equals("") ) {
	                name = session.getServletContext().getRealPath("") + "\\" + realName + ".html";
	            } else { 
	                name = session.getServletContext().getRealPath("") + "\\" + path + "\\" 
	                        + realName + ".html"; 
	            } 
	        } else { 
	            name = session.getServletContext().getRealPath("") + "\\" + realPath + "\\" 
	                    + realName + ".html"; 
	        } 
	        RequestDispatcher rd = sc.getRequestDispatcher(url); 
	        final ByteArrayOutputStream os = new ByteArrayOutputStream(); 
	        final ServletOutputStream stream = new ServletOutputStream() { 
	            public void write(byte[] data, int offset, int length) { 
	                os.write(data, offset, length); 
	            } 
	            public void write(int b) throws IOException { 
	                os.write(b); 
	            } 
	        }; 
	        final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os)); 
	        HttpServletResponse rep = new HttpServletResponseWrapper(response) { 
	            public ServletOutputStream getOutputStream(){
	                return stream; 
	            } 
	            public PrintWriter getWriter() { 
	                return pw; 
	            } 
	        };
	        rep.setCharacterEncoding("UTF-8");
	        rd.include(request, rep);
	        pw.flush(); 
	        FileOutputStream fos = new FileOutputStream(name);
	        os.writeTo(fos); 
	        fos.close(); 
		}
		return;
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
