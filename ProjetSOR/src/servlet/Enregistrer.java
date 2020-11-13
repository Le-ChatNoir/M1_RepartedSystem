package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
@SuppressWarnings("unused")
@WebServlet("/Enregistrer")
public class Enregistrer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Enregistrer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet Enregistrer");
		request.getServletContext()
		.getRequestDispatcher("/WEB-INF/enregistrer.jsp")
			.forward(request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "rawtypes", "null" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Enregistrer");
		
		try {
			if (!ServletFileUpload.isMultipartContent(request)) {
				System.out.println("Erreur doPost Enregistrer : il faut faut un formulaire multipart");
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
						String param = item.getFieldName();
						String fic = item.getName();
						System.out.println("piece jointe trouvee : param = "+param+" fic = "+fic);
						
						// On enregistre le fichier joint sous le nom "fic.jpg"
						String nfic = "im.jpg";
						nfic = request.getServletContext().getRealPath("/") + "/" + nfic;
						System.out.println("nfic = "+nfic);
						BufferedInputStream bis = new BufferedInputStream(item.getInputStream());
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(nfic));
						// is permet d'obtenir les octets de la pièce jointe
						int c;
						int i = 0;
						int[] oct = null;
						while ((c = bis.read()) != -1) {
							bos.write(c);
							oct[i] = c;
							i++;
						}
						bis.close();
						bos.close();
						
						//Creation de l'objet image
						PicServ picserv = new PicServ(titreImg, oct);
						//ServeurRMIImpl serv = new ServeurRMIImpl();
						//ClientRMI client = new ClientRMI(picserv);
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Erreur doPost Enregistrer "+e.getMessage());
		}
		
		response.sendRedirect("Enregistrer");
	}

}
