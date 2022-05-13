package Model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IWurf extends Remote{
	public Integer[] getAugenzahlen() throws RemoteException ;
	public void wuerfeln() throws RemoteException;
	public void auswerten() throws RemoteException;	
}
