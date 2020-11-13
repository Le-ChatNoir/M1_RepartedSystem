package rmi;

 

import java.rmi.registry.LocateRegistry;

import java.rmi.registry.Registry;

import bean.PicServ;

 

public class ClientRMI {

 
 public ClientRMI(PicServ picture) {
	 
	 int port = 10000;

	  try {
	   Registry registry = LocateRegistry.getRegistry(port);
	   ServeurRMI srmi = (ServeurRMI) registry.lookup("serveurRMI");

	   Boolean res = srmi.insert(picture);
	   //String res = srmi.meth();
	   
	   System.out.println("res = "+res);

	  } catch (Exception e) {

	   System.out.println("Erreur Client RMI "+e.getMessage());

	  }
 }
 
 public ClientRMI(String text) {
	 
	 int port = 10000;

	  try {
	   Registry registry = LocateRegistry.getRegistry(port);
	   ServeurRMI srmi = (ServeurRMI) registry.lookup("serveurRMI");

	   PicServ res = srmi.search(text);
	   
	   System.out.println("res = "+ res.getTitre());

	  } catch (Exception e) {

	   System.out.println("Erreur Client RMI "+e.getMessage());

	  }
 }

 public static void main(String [] args) {

	 int[] oct = {21, 12, 57};
	 PicServ pic = new PicServ("toto", oct);
	 new ClientRMI(pic);

 }

}