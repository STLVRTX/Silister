
package Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Random;

public class Wurf extends UnicastRemoteObject implements IWurf{
	
	//Instanzvariablen
	private Random rn;
	private Integer[] augenzahlen;
	private int[] count = new int[6];
	public Auswertung[] auswertung = new Auswertung[14];
	
	//Getter & Setter
	public Integer[] getAugenzahlen() throws RemoteException {
		return augenzahlen;
	}

	public void setAugenzahlen(Integer[] augenzahlen) {
		this.augenzahlen = augenzahlen;
	}

	public int[] getCount() {
		return count;
	}

	//Defaultkonstruktor
	public Wurf() throws RemoteException {
		augenzahlen = new Integer[5];
		rn = new Random();
		wuerfeln();
	}

	//Konstruktor
	public Wurf(Integer[] arr) throws RemoteException {
		
		rn = new Random();
		wuerfelnAdjusted(arr);
	}

	//Methoden
	public void wuerfeln() throws RemoteException{
		rn = new Random();
		for (int i = 0; i < augenzahlen.length; i++) {
			augenzahlen[i] = rn.nextInt(6) + 1;
		}
	}
	
	private void wuerfelnAdjusted(Integer[] augenz) {
		int counter = 0;
		rn = new Random();
		augenzahlen = augenz;
		if(augenz != null) {
			for (int i = 0; i < augenzahlen.length; i++) {
				if (augenzahlen[i] == null) {
					rn = new Random();
					augenzahlen[i] = rn.nextInt(6) + 1;				
				}
				else
					counter++;
			}
			
			if(counter == 5) {
				for (int i = 0; i < augenzahlen.length; i++) {
					rn = new Random();
					augenzahlen[i] = rn.nextInt(6) + 1;	
				}
			}
		}
		else {
			augenzahlen = new Integer[5];
			for (int i = 0; i < augenzahlen.length; i++) {
				rn = new Random();
				augenzahlen[i] = rn.nextInt(6) + 1;
			}
		}
	}

	private void sortieren() {
		int temp;
		for (int i = 1; i < augenzahlen.length; i++) {
			if (augenzahlen[i] < augenzahlen[i - 1]) {
				temp = augenzahlen[i - 1];
				augenzahlen[i - 1] = augenzahlen[i];
				augenzahlen[i] = temp;
				i = 0;
			}
		}
	}

	private Auswertung Vierling() {
		for (int i = 0; i < count.length; i++) {
			if (count[i] >= 4)
				return Auswertung.isVierling;

		}
		return null;
	}

	private Auswertung ZweiZwilling() {
		int check = 0;
		for (int i = 0; i < count.length; i++) {
			if (count[i] >= 2) {
				check++;
			}
		}
		if (check == 2)
			return Auswertung.isZweiZwilling;
		return null;
	}

	private Auswertung Drilling() {
		for (int i = 0; i < count.length; i++) {
			if (count[i] >= 3)
				return Auswertung.isDrilling;
		}
		return null;

	}

	private Auswertung Kniffel() {
		for (int i = 0; i < count.length; i++) {
			if (count[i] == 5)
				return Auswertung.isKniffel;
		}
		return null;
	}

	private Auswertung FullHouse() {
		boolean fhtemp1 = false;
		boolean fhtemp2 = false;
		for (int i = 0; i < count.length; i++) {

			if (count[i] == 3) {
				fhtemp1 = true;
			}
			if (count[i] == 2) {
				fhtemp2 = true;
			}

		}
		if (fhtemp1 == true && fhtemp2 == true) {
			return Auswertung.isFullHouse;
		}
		return null;
	}

	private Auswertung kleineStrasse() {
		int counter = 0;
		for (int i = 1; i < augenzahlen.length; i++) {
			if (augenzahlen[i] == augenzahlen[i - 1] + 1) {
				counter++;

			}
			if (counter >= 3)
				return Auswertung.isKlStrasse;
		}
		return null;
	}

	private Auswertung grosseStrasse() {
		int counter = 0;
		for (int i = 1; i < augenzahlen.length; i++) {
			if (augenzahlen[i] == augenzahlen[i - 1] + 1) {
				counter++;
			}
			if (counter == 4)
				return Auswertung.isGrStrasse;
		}
		return null;

	}

	private void zaehlen() {
		for (int i = 0; i < augenzahlen.length; i++) {
			count[augenzahlen[i] - 1]++;
		}
	}

	public void auswerten() throws RemoteException{
		sortieren();
		zaehlen();
		if (Arrays.asList(augenzahlen).contains(1))
			auswertung[0] = Auswertung.isEins;
		else
			auswertung[0] = null;
		if (Arrays.asList(augenzahlen).contains(2))
			auswertung[1] = Auswertung.isZwei;
		else
			auswertung[1] = null;
		if (Arrays.asList(augenzahlen).contains(3))
			auswertung[2] = Auswertung.isDrei;
		else
			auswertung[2] = null;
		if (Arrays.asList(augenzahlen).contains(4))
			auswertung[3] = Auswertung.isVier;
		else
			auswertung[3] = null;
		if (Arrays.asList(augenzahlen).contains(5))
			auswertung[4] = Auswertung.isFuenf;
		else
			auswertung[4] = null;
		if (Arrays.asList(augenzahlen).contains(6))
			auswertung[5] = Auswertung.isSechs;
		else
			auswertung[5] = null;
		auswertung[6] = ZweiZwilling();
		auswertung[7] = Drilling();
		auswertung[8] = Vierling();
		auswertung[9] = FullHouse();
		auswertung[10] = kleineStrasse();
		auswertung[11] = grosseStrasse();
		auswertung[12] = Kniffel();
		auswertung[13] = Auswertung.isChance;
	}

	public enum Auswertung {
		isZweiZwilling, isDrilling, isVierling, isFullHouse, isKlStrasse, isGrStrasse, isKniffel, isChance,
		isEins, isZwei, isDrei, isVier, isFuenf, isSechs

	}

}