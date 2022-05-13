package Model;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerMain {
	public static void main(String args[]) throws Exception {
	    try{
		  //LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
	      LocateRegistry.createRegistry(7777);
	      Remote remote = null;
	      remote = (Remote) new GameController();
	      
	      //Naming.rebind("//ux-05.bg.bib.de:7777/kniffelkey", remote);
	      Naming.rebind("//localhost:7777/kniffelkey", remote);
	      System.out.println("Server gestartet ...");
	      
			}
			catch (RemoteException e){
				System.out.println(e.getMessage());
			}
	   
	  }

}
