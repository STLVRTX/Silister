package Model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameController extends Remote{
	public int getWurfCount() throws RemoteException;
	public void setWurfCount(int wurfCount) throws RemoteException;
	public void neuWuerfeln(Integer[] arr) throws RemoteException;
	public IWurf getWurf() throws RemoteException;
	public int[] punkteErrechnen(IWurf test) throws RemoteException;
}