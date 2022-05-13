package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Model.IGameController;
import View.GUI;

public class Controller implements ActionListener, MouseListener {
	GUI kniffel;
	IGameController wurf;
	Integer[] augenzahlen;

	public Controller() {
		String host = "localhost"; //"ux-05.bg.bib.de";  
		try {
			wurf = (IGameController) Naming.lookup("//" + host + ":7777/kniffelkey");
			//wurf = new IGameController();
			kniffel = new GUI("Kniffel");
			kniffel.addListener(this);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IGameController getWurf() {
		return wurf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getActionCommand().equals("Spielen")) {
				kniffel.removeListener(this);
				wurf.getWurf().wuerfeln();

				wurf.setWurfCount(0);

				kniffel.displayGameGUI();
				kniffel.addListener(this);
			}

			if (e.getActionCommand().equals("Scores"))
				kniffel.displayScoresGUI();

			if (e.getActionCommand().equals("Beenden"))
				System.exit(0);

			if (e.getActionCommand().equals("Hauptmenü"))
				kniffel.displayMenuGUI();

			if (e.getActionCommand().equals("Würfeln") && getWurf().getWurfCount() > 0
					&& getWurf().getWurfCount() < 3) {
				getWurf().neuWuerfeln(augenzahlen);


				kniffel.wuerfelGUI(wurf.getWurf().getAugenzahlen());
				wurf.setWurfCount(wurf.getWurfCount() + 1);

				augenzahlen = getWurf().getWurf().getAugenzahlen();

				kniffel.getButtons()[2].setEnabled(true);

				JLabel[] wuerfel = kniffel.getWuerfel();
				for (int i = 0; i < wuerfel.length; i++) {
					wuerfel[i].setBorder(BorderFactory.createEmptyBorder());
				}

				if (getWurf().getWurfCount() == 3) {
					kniffel.getButtons()[0].setEnabled(false);
					kniffel.getButtons()[1].setEnabled(false);
					kniffel.getButtons()[2].setEnabled(false);

					punkteEintragen();
				}

			}

			else if (e.getActionCommand().equals("Würfeln") && getWurf().getWurfCount() == 0) {
				getWurf().getWurf().wuerfeln();
				kniffel.wuerfelGUI(wurf.getWurf().getAugenzahlen());
				wurf.setWurfCount(wurf.getWurfCount() + 1);
				augenzahlen = getWurf().getWurf().getAugenzahlen();
				kniffel.getButtons()[2].setEnabled(true);

				JLabel[] wuerfel = kniffel.getWuerfel();
				for (int i = 0; i < wuerfel.length; i++) {
					wuerfel[i].setBorder(BorderFactory.createEmptyBorder());
				}

			}

			if (e.getActionCommand().equals("Zurücklegen")) {
				JLabel[] clickedWuerfel = new JLabel[5];
				JLabel[] wuerfel = kniffel.getWuerfel();

				if (getWurf().getWurfCount() > 0) {
					for (int i = 0; i < clickedWuerfel.length; i++) {
						if (wuerfel[i].getBorder() != BorderFactory.createEmptyBorder()) {
							kniffel.getButtons()[2].setEnabled(false);
							augenzahlen[i] = null;
							try {
								wuerfel[i].setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("blanko.png"))));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							wuerfel[i].setBorder(BorderFactory.createEmptyBorder());
						}
					}
				}
			}

			if (e.getActionCommand().equals("Zug beenden")) {
				if (getWurf().getWurfCount() > 0) {
					getWurf().setWurfCount(3);
					kniffel.getButtons()[0].setEnabled(false);
					kniffel.getButtons()[1].setEnabled(false);
					kniffel.getButtons()[2].setEnabled(false);
				}

				for (int i = 0; i < augenzahlen.length; i++) {
					kniffel.getWuerfel()[i].setBorder(BorderFactory.createEmptyBorder());
				}

				punkteEintragen();
			}

			kniffel.repaint();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			JLabel source = (JLabel) e.getSource();
			try {
				augenzahlen = getWurf().getWurf().getAugenzahlen();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			try {
				if (source.getName().contains("Würfel") && getWurf().getWurfCount() > 0
						&& getWurf().getWurfCount() < 3 && source.getBorder() == BorderFactory.createEmptyBorder()) {
					source.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else if (source.getName().contains("Würfel"))
					source.setBorder(BorderFactory.createEmptyBorder());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			if (source.getName().contains("Feld") && source.isEnabled() == true) {
				for (int i = 0; i < kniffel.getFelder()[0].length; i++) {
					if (source.getName().matches("Feld " + String.valueOf(i))) {
						kniffel.getFelder()[1][i].setEnabled(false);

						for (int j = 1; j < 21; j++) {
							if (kniffel.getFelder()[1][j].isEnabled() == true) {
								kniffel.getFelder()[1][j].setText(null);
							}
						}
						break;
					}
				}

				kniffel.getButtons()[0].setEnabled(true);
				kniffel.getButtons()[1].setEnabled(true);
				kniffel.getButtons()[2].setEnabled(true);


				getWurf().setWurfCount(0);


				blankoWuerfel();

			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private void punkteEintragen() throws RemoteException {
		int[] auswertung = getWurf().punkteErrechnen(getWurf().getWurf());

		for (int i = 0; i < auswertung.length; i++) {
			System.out.println(auswertung[i]);
		}

		int counter = 1;
		int index = 0;
		for (int i = 1; i < kniffel.getFelder()[0].length; i++) {
			if (i == 7 || i == 8 || i == 9 || i == 18 || i == 19 || i == 20)
				continue;
			if (kniffel.getFelder()[1][i].isEnabled() == true) {
				kniffel.getFelder()[1][i].setText(String.valueOf(auswertung[index]));
				kniffel.getFelder()[1][i].setHorizontalAlignment(JLabel.CENTER);
				counter++;
			}
			if (index > 12)
				break;
			index++;
		}

		if (counter <= 1)
			countPoints();
	}

	private void blankoWuerfel() {
		for (int i = 0; i < kniffel.getWuerfel().length; i++) {

			try {
				kniffel.getWuerfel()[i]
						.setIcon((new ImageIcon(ImageIO.read(this.getClass().getResource("blanko.png")))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		kniffel.getButtons()[2].setEnabled(false);
	}

	public void countPoints() {
		int points1 = 0;
		int points2 = 0;
		for (int i = 1; i < 7; i++) {
			points1 += Integer.parseInt(kniffel.getFelder()[1][i].getText());
		}
		kniffel.getFelder()[1][7].setText(String.valueOf(points1));
		kniffel.getFelder()[1][7].setHorizontalAlignment(JLabel.CENTER);
		if (points1 >= 63) {
			kniffel.getFelder()[1][8].setText(String.valueOf(35));
			kniffel.getFelder()[1][8].setHorizontalAlignment(JLabel.CENTER);
			points1 += Integer.parseInt(kniffel.getFelder()[1][8].getText());
		} else {
			kniffel.getFelder()[1][8].setText("0");
			kniffel.getFelder()[1][8].setHorizontalAlignment(JLabel.CENTER);
		}
		kniffel.getFelder()[1][9].setText(String.valueOf(points1));
		kniffel.getFelder()[1][9].setHorizontalAlignment(JLabel.CENTER);

		for (int i = 10; i < 18; i++) {
			points2 += Integer.parseInt(kniffel.getFelder()[1][i].getText());
		}
		kniffel.getFelder()[1][18].setText(String.valueOf(points1));
		kniffel.getFelder()[1][18].setHorizontalAlignment(JLabel.CENTER);
		kniffel.getFelder()[1][19].setText(String.valueOf(points2));
		kniffel.getFelder()[1][19].setHorizontalAlignment(JLabel.CENTER);
		kniffel.getFelder()[1][20].setText(String.valueOf(points1 + points2));
		kniffel.getFelder()[1][20].setHorizontalAlignment(JLabel.CENTER);

	}

}
