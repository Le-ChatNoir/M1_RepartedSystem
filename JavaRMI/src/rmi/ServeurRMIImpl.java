package rmi;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.PicServ;
 

@SuppressWarnings("unused")
public class ServeurRMIImpl implements ServeurRMI {

 

 @Override
 public String meth() throws RemoteException {

  // TODO Auto-generated method stub
  System.out.println("RMI : meth");
  return "reponse du serveur rmi";
 }
 
 @Override
 public boolean insert(PicServ image) throws RemoteException {
 	String url = "jdbc:mysql://localhost/m1sor";
 	String user = "root";
 	String password = "";
 	String JDBC_DRIVER = "com.mysql.jdbc.Driver";   
 	
 	Connection co = null;
 	
 	try {
 		//Register JBDC driver
 		Class.forName("com.mysql.jbdc.Driver");
 		
 		//Open connexion
 		System.out.println("Connection a la BDD...");
 		co = DriverManager.getConnection(url, user, password);
 		System.out.println("Connecté à la BDD...");
 	} catch (SQLException | ClassNotFoundException e) {
 		System.out.println("Erreur getConnection() " + e.getMessage());
 		e.printStackTrace();
 	}
 	
 	try {
 	
 		System.out.println("Creation de la query...");
 		String statement = "INSERT INTO t_image (titre, jpeg) VALUES (?, ?) ";
 		
 		String titre = image.getTitre();
 		int[] jpeg = image.getJpeg();
 		String jpegwhole = "";
 		
 		for(int i : jpeg) {
 			jpegwhole += Integer.toString(i);
 		}
 		
 		PreparedStatement ps = co.prepareStatement(statement);
 		ps.setString(1, titre);
 		ps.setInt(2, Integer.valueOf(jpegwhole));
 		System.out.println("Query : " + ps.toString());
 		
 		int res = ps.executeUpdate();
 		
 		if(res != 0) {
 			System.out.println("Insertion effectuee");
 			return true;
 		} else {
 			System.out.println("Echec de l'insertion");
 			return false;
 		}
 	} catch (SQLException e) {
 		System.out.println("Erreur sql : " + e.getMessage());
 		e.printStackTrace();
 	}
 	
 	try {co.close();}catch (Exception e) {}
 	return false;
 }
 
 
 @SuppressWarnings("null")
@Override
 public PicServ search(String titre) throws RemoteException {
 	String url = "jdbc:mysql://localhost/m1sor";
  	String user = "root";
  	String password = "";
  	String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  	PicServ res = new PicServ("", null);
  	
  	Connection co = null;
  	
  	try {
  		//Register JBDC driver
  		Class.forName("com.mysql.jbdc.Driver");
  		
  		//Open connexion
  		System.out.println("Connection a la BDD...");
  		co = DriverManager.getConnection(url, user, password);
  		System.out.println("Connecté à la BDD...");
  	} catch (SQLException | ClassNotFoundException e) {
  		System.out.println("Erreur getConnection() " + e.getMessage());
  		e.printStackTrace();
  	}
  	
  	try {
  	
  		System.out.println("Creation de la query...");
  		String statement = "SELECT * FROM t_image WHERE titre = ?";
  		
  		PreparedStatement ps = co.prepareStatement(statement);
  		ps.setString(1,  titre);
  		System.out.println("Query : " + ps.toString());
  		ResultSet rs = ps.executeQuery();
  		
  		//Vidage du resultset
  		int jpegwhole;
  		String jpegwholeString;
  		int[] oct = null;
  		int i = 0;
		while(rs.next()) {
			res.setTitre(rs.getString("titre"));
			
			jpegwhole = rs.getInt("jpeg");
			jpegwholeString = Integer.toString(jpegwhole);
			
			for(int j = 0; j<(jpegwholeString.length()/8); j++) {
				oct[j] = Integer.valueOf(jpegwholeString.substring(i, i+8));
				i = i+8;
			}
			
			res.setJpeg(oct);
		}
		
		if(res.getTitre() != "") {
			System.out.println("Reccuperation reussie");
		} else {
			System.out.println("Pas d'image correspondante");
		}
		
		rs.close();
		ps.close();
		
  	} catch (SQLException e) {
  		System.out.println("Erreur sql : " + e.getMessage());
  		e.printStackTrace();
  	}
  	
  	try {co.close();}catch (Exception e) {}
  	return res;
 }

 

 public static void main(String [] args) {

  int port = 10000;
  Registry registry = null;

  // création registry
  try {
   LocateRegistry.createRegistry(port);
   registry = LocateRegistry.getRegistry(port);
  } catch (RemoteException e) {
   System.out.println("Erreur RMI createRegistry "+e.getMessage());
  }

  

  ServeurRMIImpl srmii = new ServeurRMIImpl();
  ServeurRMI srmi = null;

  // création objet distant
  try {
   srmi = (ServeurRMI) UnicastRemoteObject.
     exportObject(srmii,0);
  } catch (RemoteException e) {
   System.out.println("Erreur RMI exportObject "+e.getMessage());
  }

  

  // enregistrement objet distant
  try {
   registry.rebind("serveurRMI", srmi);
  } catch (Exception e) {
   System.out.println("Erreur RMI rebind "+
     e.getMessage());   
  }

  System.out.println("Serveur RMI lancé");
 }



}