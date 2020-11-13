package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import bean.PicServ;


/**
 * Servlet implementation class Enregistrer
 */
@WebServlet("/Chercher")
public class Chercher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Chercher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet Chercher");
		
		////ServeurRMIImpl serv = new ServeurRMIImpl();
		//PicServ pic = ClientRMI(response);
		
		//Prevenir le navigateur qu'on lui envoie une image
		response.setContentType("image/jpeg");
		
		//Envoi vers le navigateur
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(pic.jpeg));
		
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		int c;
		while((c = bis.read()) != -1) {
			bos.write(c);
		}
		
		bis.close();
		bos.close();
		
		request.getServletContext()
		.getRequestDispatcher("/WEB-INF/chercher.jsp")
			.forward(request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Chercher");
		
		try {
			if (!ServletFileUpload.isMultipartContent(request)) {
				System.out.println("Erreur doPost Chercher : il faut faut un formulaire multipart");
			}
			else {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = 
						new ServletFileUpload(factory);
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					String titreImg = new String();

					if (item.isFormField()) {
						String param = item.getFieldName();
						String val = item.getString("utf-8");
						System.out.println("parametre trouve : param = "+param+" val = "+val);
						titreImg = val;
					}
					else {						
						// On enregistre le fichier joint sous le nom "fic.jpg"
						String nfic = "im.jpg";
						nfic = request.getServletContext().getRealPath("/") + "/" + nfic;
						System.out.println("nfic = "+nfic);
						BufferedInputStream bis = new BufferedInputStream(item.getInputStream());
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(nfic));
						// is permet d'obtenir les octets de la pièce jointe
						int c;
						while ((c = bis.read()) != -1) {
							bos.write(c);
						}
						bis.close();
						bos.close();
					
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Erreur doPost Chercher "+e.getMessage());
		}
		
		response.sendRedirect("Chercher");
	}

}
