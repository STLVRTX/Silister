package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Controller;


public class GUI extends JFrame{
	
	Container cp;

	//GameGUI
	JPanel body;
	JLabel[][] felder = new JLabel[6][22];
	JLabel[] wuerfel = new JLabel[5];
	JButton[] buttons = new JButton[4];
	JLabel infozeile;
	//GameController wurf;
	Integer[] augenzahlen;
	
	//MenuGUI
	JPanel container;
	JButton[] menuButtons = new JButton[3];
	
	//ScoresGUI
	JPanel scoreBody;
	JLabel[][] scores = new JLabel[3][11]; //Eintragbar: i + 1, j + 1
	JButton hauptmenu;
	
	public GUI(String titel){
		super(titel);
		cp = getContentPane();
		cp.setBackground(Color.LIGHT_GRAY);
		cp.setLayout(new BorderLayout());
		
		body = gameGUI();
		container = menuGUI();
		scoreBody = scoresGUI();
		//wurf = new GameController();
		
		//displayGameGUI();
		displayMenuGUI();
		//displayScoresGUI();
		
	}
	
	private JPanel gameGUI(){
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.35;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		body = new JPanel(new GridLayout(1, 2));
		
		JPanel sheet = new JPanel();		
		sheet = kniffelBlatt(sheet);
		
		body.add(sheet);
			
		JPanel dice = new JPanel(new FlowLayout());
		dice = wuerfelOberflaeche(dice);
		
		c.weightx = 0.65;
		c.gridx = 1;
		c.gridy = 0;
		c.weighty = 1.0;
		
		body.add(dice);
		
		return body;
	}
	
	private JPanel kniffelBlatt(JPanel sheet) {
		
		//Voreinstellungen
		sheet.setLayout(new BorderLayout());
		sheet.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		
		JPanel tabelle = new JPanel(new GridBagLayout());
		tabelle.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
		
		GridBagConstraints c = new GridBagConstraints();		
		c.weightx = 1.0;
		c.ipady = 16;
		c.fill = GridBagConstraints.BOTH;

		//Erstellen/F�llen der Ersten Zeile
		JLabel[] ersteZeile = {new JLabel(""), new JLabel("1. Spiel"), new JLabel("2. Spiel"), new JLabel("3. Spiel"), new JLabel("4. Spiel"), new JLabel("5. Spiel")};
		c.gridy = 0;
		
		for(int i = 0; i < 6; i++) {
			ersteZeile[i].setLayout(new BorderLayout());
			ersteZeile[i].setHorizontalAlignment(JLabel.CENTER);
			ersteZeile[i].setBorder(BorderFactory.createMatteBorder(3, 1, 0, 0, Color.BLACK));
			c.gridx = i;
			tabelle.add(ersteZeile[i], c);
			
		}
		ersteZeile[5].setBorder(BorderFactory.createMatteBorder(3, 1, 0, 3, Color.BLACK));
		ersteZeile[0].setBorder(BorderFactory.createMatteBorder(3, 3, 0, 0, Color.BLACK));
		
		//Erstellen/F�llen der ersten Spalte
		JLabel[] ersteSpalte = {new JLabel(""), new JLabel("Einser"), new JLabel("Zweier"), new JLabel("Dreier"), new JLabel("Vierer"), new JLabel("F�nfer"), new JLabel("Sechser"), new JLabel("Zwischensumme"), new JLabel("Bonus"), new JLabel("Gesamtsumme Teil 1"), new JLabel("Zwei Zwillinge"), new JLabel("Drilling"), new JLabel("Vierling"), new JLabel("Full House"), new JLabel("Kleine Stra�e"), new JLabel("Gro�e Stra�e"), new JLabel("Kniffel"), new JLabel("Chance"), new JLabel("Gesamtsumme Teil 1"), new JLabel("Gesamtsumme Teil 2"), new JLabel("Endergebnis")};
		c.gridx = 0;
		
		for(int i = 1; i < 21; i++) {
			ersteSpalte[i].setLayout(new BorderLayout());
			ersteSpalte[i].setHorizontalAlignment(JLabel.CENTER);
			ersteSpalte[i].setBorder(BorderFactory.createMatteBorder(1, 3, 0, 0, Color.BLACK));
			c.gridy = i;
			tabelle.add(ersteSpalte[i], c);
		}
		ersteSpalte[20].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 0, Color.BLACK));
		
		
		//Erstellen der restlichen Felder
		for(int i = 1; i < 6; i++) {
			for (int j = 1; j < 21; j++) {
				c.gridy = j;
				c.gridx = i;
				felder[i][j] = new JLabel();
				felder[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK));
				if(j != 7 && j != 8 && j != 9 && j != 18 && j != 19 && j != 20)
					felder[i][j].setName("Feld " + j);
				tabelle.add(felder[i][j], c);
				
				if(j == 20) {
					felder[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 0, Color.BLACK));
				}
				
				if(i == 5) {
					felder[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 3, Color.BLACK));
				}
			}
		}
		felder[5][20].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
								
		sheet.add(tabelle);
		
		return sheet;
	}

	private JPanel wuerfelOberflaeche(JPanel dice){
		dice.setLayout(new BorderLayout());
		dice.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		JPanel struktur = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.insets = new Insets(10, 0, 0, 0);
		
		infozeile = new JLabel("Kniffel - Spiel 1");
		infozeile.setLayout(new BorderLayout());
		infozeile.setHorizontalAlignment(JLabel.CENTER);
		infozeile.setBorder(BorderFactory.createLoweredBevelBorder());
		c.gridy = 0;
		c.weighty = 0.1;
		struktur.add(infozeile, c);
		
		JPanel wuerfelContainer = new JPanel(new BorderLayout());
		c.gridy = 1;
		c.weighty = 0.5;
		wuerfelContainer.setBorder(BorderFactory.createLoweredBevelBorder());
		wuerfelContainer = createWuerfel(wuerfelContainer);
		struktur.add(wuerfelContainer, c);
		
		buttons[0] = new JButton("W�rfeln");
		buttons[0].setLayout(new BorderLayout());
		c.gridy = 2;
		c.weighty = 0.1;
		struktur.add(buttons[0], c);
		
		buttons[1] = new JButton("Zur�cklegen");
		buttons[1].setLayout(new BorderLayout());
		c.gridy = 3;
		c.weighty = 0.1;
		struktur.add(buttons[1], c);

		buttons[2] = new JButton("Zug beenden");
		buttons[2].setLayout(new BorderLayout());
		c.gridy = 4;
		c.weighty = 0.1;
		buttons[2].setEnabled(false);
		struktur.add(buttons[2], c);
		
		buttons[3] = new JButton("Hauptmen�");
		buttons[3].setLayout(new BorderLayout());
		c.gridy = 5;
		c.weighty = 0.1;
		struktur.add(buttons[3], c);
		
		dice.add(struktur);
		
		return dice;
	}
	
	private JPanel createWuerfel(JPanel wuerfelContainer){	
		wuerfelContainer.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);	
		
		for(int i = 0; i < wuerfel.length; i++) {
			try {
				wuerfel[i] = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("blanko.png"))));
				wuerfel[i].setName("W�rfel " + i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		wuerfelContainer.add(wuerfel[0], c);
		
		c.gridx = 2;
		wuerfelContainer.add(wuerfel[1], c);
		
		c.gridx = 1;
		c.gridy = 1;
		wuerfelContainer.add(wuerfel[2], c);
		
		c.gridx = 0;
		c.gridy = 2;
		wuerfelContainer.add(wuerfel[3], c);
		
		c.gridx = 2;
		wuerfelContainer.add(wuerfel[4], c);
	
		return wuerfelContainer;
	}

	private JPanel menuGUI(){
		container = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.weightx = 0.55;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(50, 0, 50, 0);
		
		JLabel kniffelLogo;
		
		try {
			kniffelLogo = new JLabel((new ImageIcon(ImageIO.read(this.getClass().getResource("kniffel_logo.png")).getScaledInstance(350, 92, Image.SCALE_FAST))));
			container.add(kniffelLogo, c);
		} catch (IOException e) {
			e.printStackTrace();
		}

		c.insets = new Insets(30, 50, 30, 50);
		c.weightx = 0.15;
		c.gridy = 2;
		menuButtons[0] = new JButton("Spielen");
		menuButtons[0].setLayout(new BorderLayout());
		container.add(menuButtons[0], c);
		
		c.gridy = 3;
		menuButtons[1] = new JButton("Scores");
		menuButtons[1].setLayout(new BorderLayout());
		container.add(menuButtons[1], c);
		
		c.insets = new Insets(30, 50, 60, 50);
		c.gridy = 4;
		menuButtons[2] = new JButton("Beenden");
		menuButtons[2].setLayout(new BorderLayout());
		container.add(menuButtons[2], c);
				
		return container;
	}

	private JPanel scoresGUI() {
		scoreBody = new JPanel(new BorderLayout());
		scoreBody.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		JPanel inBody = new JPanel(new GridBagLayout());
		inBody.setBorder(BorderFactory.createLoweredBevelBorder());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		
		JLabel text = new JLabel("Top 10 - Highscores");
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setFont(text.getFont().deriveFont(32.0f));
		text.setBorder(BorderFactory.createLoweredBevelBorder());
		
		inBody.add(text, c);
		
		JPanel scoreTable = new JPanel(new GridBagLayout());
		scoreTable.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.BOTH;
		d.weightx = 1;
		d.weighty = 1;
		
		for(int i = 0; i < scores.length; i++) {
			for(int j = 0; j < scores[i].length; j++) {
				scores[i][j] = new JLabel();
				d.gridy = j;
				d.gridx = i;
				scores[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK));
				scores[i][j].setHorizontalAlignment(JLabel.CENTER);

				
				if(j == scores[i].length - 1)
					scores[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.BLACK));
				
				if(i == scores.length - 1)
					scores[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
				
				if(i == scores.length - 1 && j == scores[i].length - 1)
					scores[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
				
				if(i == 0)
					d.weightx = 0.1;
				else
					d.weightx = 0.45;
				
				if(i == 0 && j > 0)
					scores[i][j].setText(j + ".");					
					
				scoreTable.add(scores[i][j], d);	
			}
		}
		
		scores[0][0].setText("Rang");
		scores[0][0].setFont(scores[0][0].getFont().deriveFont(16.0f));
		scores[1][0].setText("Name/Initiale");
		scores[1][0].setFont(scores[1][0].getFont().deriveFont(16.0f));
		scores[2][0].setText("Score");
		scores[2][0].setFont(scores[2][0].getFont().deriveFont(16.0f));
		
		c.gridy = 1;
		c.weighty = 0.6;
		inBody.add(scoreTable, c);
		
		hauptmenu = new JButton("Hauptmen�");
		c.gridy = 2;
		c.weighty = 0.2;
		c.insets = new Insets(15, 20, 15, 20);
		inBody.add(hauptmenu, c);
		
		
		scoreBody.add(inBody);
		
		return scoreBody;
	}
	
	public void displayGameGUI(){
		cp.remove(container);
		cp.remove(scoreBody);
		cp.remove(body);
		
		body = gameGUI();
		//wurf.getWurf().wuerfeln();
		//wurf.setWurfCount(0);
		
		cp.add(body);
	
		setSize(1000, 800);
		setLocation(300, 10);
		setVisible(true);
		cp.validate();
	}
	
	public void displayMenuGUI(){
		cp.remove(body);
		cp.remove(scoreBody);

		cp.add(container);
		
		setSize(400, 600);
		setLocation(550, 125);
		setVisible(true);
		cp.validate();
	}

	public void displayScoresGUI(){
		cp.remove(body);
		cp.remove(container);
		
		cp.add(scoreBody);
		
		setSize(400, 600);
		setLocation(550, 125);
		setVisible(true);
		cp.validate();
	}
	
	public void wuerfelGUI(Integer[] zahlen) {
		//augenzahlen = wurf.getWurf().getAugenzahlen();
		//wurf.setWurfCount(wurf.getWurfCount() + 1);
		augenzahlen = zahlen;
		
		for(int i = 0; i < augenzahlen.length; i++) {
			try {
				wuerfel[i].setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource(augenzahlen[i].toString() + ".png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//public GameController getWurf() {
	//	return wurf;
	//}
	
	public JLabel[] getWuerfel() {
		return wuerfel;
	}
	
	public JButton[] getButtons() {
		return buttons;
	}
	
	public JLabel[][] getFelder() {
		return felder;
	}
	
	public void setScore(int[] coords, int score) {
		felder[coords[0]][coords[1]].setText(String.valueOf(score));
	}
	
	public void addListener(Controller l) {
		for(JButton b : menuButtons)
			b.addActionListener(l);
		
		for(JButton b : buttons)
			b.addActionListener(l);
		
		hauptmenu.addActionListener(l);
		
		for(JLabel w : wuerfel)
			w.addMouseListener(l);
		
		for(int i = 1; i < 21; i++) {
			if(i != 7 && i != 8 && i != 9 && i != 18 && i != 19 && i != 20)
				felder[1][i].addMouseListener(l);
		}
		
	}
	
	public void removeListener(Controller l) {
		for(JButton b : menuButtons)
			b.removeActionListener(l);
		
		for(JButton b : buttons)
			b.removeActionListener(l);
		
		hauptmenu.removeActionListener(l);
		
		for(JLabel w : wuerfel)
			w.removeMouseListener(l);
	
		for(int i = 1; i < 21; i++) {
			if(i != 7 && i != 8 && i != 9 && i != 18 && i != 19 && i != 20)
				felder[1][i].removeMouseListener(l);
		}
	}
}


