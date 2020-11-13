package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import bean.PicServ;
 
public interface ServeurRMI  extends Remote {

 public String meth() throws RemoteException ;
 public boolean insert(PicServ image) throws RemoteException;
 public PicServ search(String titre) throws RemoteException;

}