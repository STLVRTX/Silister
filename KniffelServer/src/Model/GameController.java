package Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameController extends UnicastRemoteObject implements IGameController {

	private int wurfCount;
	public static int spielCount;
	private Score[] scores;
	private IWurf wurf;

	public GameController() throws RemoteException{
		wurfCount = 0;
		spielCount = 1;
		scores = new Score[10];
		wurf = new Wurf();
	}

	public IWurf getWurf()throws RemoteException {
		return wurf;
	}
	
	public int getWurfCount()throws RemoteException {
		return wurfCount;
	}

	public void setWurfCount(int wurfCount)throws RemoteException {
		this.wurfCount = wurfCount;
	}
	
	public int[] punkteErrechnen(IWurf test) throws RemoteException{
		test.auswerten();
		int[] add = new int[14];
		for (int i = 0; i < ((Wurf)test).auswertung.length; i++) {
			if(((Wurf)test).auswertung[i] != null) {
				switch (((Wurf)test).auswertung[i]) {
	
				case isEins:
					add[i] = ((Wurf)test).getCount()[0] * 1;
					break;
				case isZwei:
					add[i] = ((Wurf)test).getCount()[1] * 2;
					break;
				case isDrei:
					add[i] = ((Wurf)test).getCount()[2] * 3;
					break;
				case isVier:
					add[i] = ((Wurf)test).getCount()[3] * 4;
					break;
				case isFuenf:
					add[i] = ((Wurf)test).getCount()[4] * 5;
					break;
				case isSechs:
					add[i] = ((Wurf)test).getCount()[5] * 6;
					break;
				case isZweiZwilling:
					for (int j = 0; j < test.getAugenzahlen().length; j++) {
						add[i] += test.getAugenzahlen()[j];
					}
					break;
				case isDrilling:
					for (int j = 0; j < test.getAugenzahlen().length; j++) {
						add[i] += test.getAugenzahlen()[j];
					}
					break;
				case isVierling:
					for (int j = 0; j < test.getAugenzahlen().length; j++) {
						add[i] += test.getAugenzahlen()[j];
					}
					break;
				case isFullHouse:
					add[i] = 25;
					break;
				case isKlStrasse:
					add[i] = 30;
					break;
				case isGrStrasse:
					add[i] = 40;
					break;
				case isKniffel:
					add[i] = 50;
					break;
				case isChance:
					for (int j = 0; j < test.getAugenzahlen().length; j++) {
						add[i] += test.getAugenzahlen()[j];
					}
					break;
				default:
					add[i] = 0;
				}
			}
		}
		return add;
	}

	public void neuWuerfeln(Integer[] arr)throws RemoteException {
		this.wurf = new Wurf(arr);		
	}
	
	public void newWuerfeln()throws RemoteException {
		this.wurf = new Wurf();
	}
}
