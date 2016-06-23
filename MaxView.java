import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * GUI.java
 * Das GUI des Spiels MAX.
 * Enthält ein Menü, eine Zeile mit Informationen zum aktuellen Spiel sowie das Spielfeld.
 * 
 * @author Timo Appenzeller (191382), Anton Makarow (191721)
 * @date 19.04.2016
 */
public class MaxView extends JInternalFrame {
	
	//Elemente fürs Menü
	private JMenuBar menuBar;
	private JMenu menuSpiel;
	private JMenuItem menuItemSpielNeuStarten;
	private JMenuItem menuItemSpielBeenden;
	
	//Elemente für die Info-Zeile
	private JPanel infoPanel;
	private JLabel punkteP1;
	private JLabel punkteP2;
	private JLabel infoLabel;
	
	//Elemente für das Spielfeld
	private JLabel[][] felder;
	private JPanel felderPanel;
	
	//diverse Objektreferenzen
	private MaxSpielfeld spielfeld;
	private MaxSpieler p1,p2;
	private MaxSteuerung st;
	
	private BaseFrame baseFrame;
	
	public MaxView(BaseFrame parrent){
		super("Max", true, true, true, true);
		this.baseFrame = parrent;
		
		spielfeld = new MaxSpielfeld(8,8);

		p1 = new MaxSpieler("W", 3, 4, "W", "S", "A", "D", spielfeld);

		p2 = new MaxSpieler("B", 4, 3, "I", "K", "J", "L", spielfeld);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500,500);
		getContentPane().setBackground(Color.lightGray);
		st = new MaxSteuerung(p1,p2,this,spielfeld);
		//addKeyListener(st);
		
		//GridBagLayout für das Frame
		GridBagLayout gbl = new GridBagLayout(); 
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
				
		//Initialisierung des Menüs bzw. der Menüpunkte
		menuBar = new JMenuBar();
		menuSpiel = new JMenu("Spiel");
		menuItemSpielNeuStarten = new JMenuItem("Neues Spiel starten");
		menuItemSpielBeenden = new JMenuItem("Spiel beenden");
		
		menuBar.add(menuSpiel);
		menuSpiel.add(menuItemSpielNeuStarten);
		menuSpiel.add(menuItemSpielBeenden);
		
		menuItemSpielBeenden.addActionListener(st);
		menuItemSpielNeuStarten.addActionListener(st);
		
		setJMenuBar(menuBar);
		
		//Initialisierung der Elemente für die Info-Zeile
		punkteP1 = new JLabel();
		punkteP1.setHorizontalAlignment(JLabel.LEFT);
		punkteP2 = new JLabel();
		punkteP2.setHorizontalAlignment(JLabel.RIGHT);
		infoLabel = new JLabel();
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		
		punkteP1.setFont(new Font("Arial", Font.PLAIN, 16));
		punkteP2.setFont(new Font("Arial", Font.PLAIN, 16));
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		
		updateInfo(); //Startwerte eintragen
				
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(1, 3));
		infoPanel.add(punkteP1);
		infoPanel.add(infoLabel);
		infoPanel.add(punkteP2);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 10;
		gbc.weighty = 0;
		gbc.gridy = 0;
		add(infoPanel, gbc);
		
		//Initialisierung Spielfeld
		felderPanel = new JPanel();
		felderPanel.setLayout(new GridLayout(8, 8, 2, 2)); //Spielfeld im GridLayout
		
		//Workaround... mit Keybindings, weil KeyListener Probleme mit Focus...
		//Tasten für Spieler 1
		felderPanel.getInputMap().put(KeyStroke.getKeyStroke("W"), "taste");
		felderPanel.getInputMap().put(KeyStroke.getKeyStroke("A"), "taste");
		felderPanel.getInputMap().put(KeyStroke.getKeyStroke("S"), "taste");
		felderPanel.getInputMap().put(KeyStroke.getKeyStroke("D"), "taste");
		
		//Tasten für Spieler 2
		felderPanel.getInputMap().put(KeyStroke.getKeyStroke("I"), "taste");
		felderPanel.getInputMap().put(KeyStroke.getKeyStroke("J"), "taste");
		felderPanel.getInputMap().put(KeyStroke.getKeyStroke("K"), "taste");
		felderPanel.getInputMap().put(KeyStroke.getKeyStroke("L"), "taste");
		
		felderPanel.getActionMap().put("taste", new MoveAction());
		
		
		felder = new JLabel[8][8];
		
		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[0].length; j++) {
				felder[i][j] = new JLabel(" ");
				felder[i][j].setOpaque(true);
				felder[i][j].setFont(new Font("Console", Font.PLAIN, 14));
				felder[i][j].setHorizontalAlignment(JLabel.CENTER);
				felder[i][j].setVerticalAlignment(JLabel.CENTER);
				felderPanel.add(felder[i][j]);
			}
		}
		
		gbc.gridy = 1;
		gbc.weighty = 1;
		add(felderPanel,gbc);
		
		updateFeld(); //Werte anzeigen
		
		setVisible(true);
		
		baseFrame.add(this);
		try {
			this.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}
	
	/**
	 * Liest die aktuellen Werte des Spielfelds bzw. der Spieler
	 * aus und schreibt sie in das jeweilige Label.
	 */
	public void updateFeld(){
		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[0].length; j++) {
				felder[i][j].setText(spielfeld.getField(j,i));
				felder[i][j].setBackground(Color.gray);
				felder[p1.getPosY()][p1.getPosX()].setText(p1.getName());
				felder[p2.getPosY()][p2.getPosX()].setText(p2.getName());
				felder[p1.getPosY()][p1.getPosX()].setBackground(Color.white);
				felder[p2.getPosY()][p2.getPosX()].setBackground(Color.white);
			}
		}
	}
	
	/**
	 * Aktualisiert die Info-Zeile mit den aktuellen Informationen.
	 */
	public void updateInfo(){
		punkteP1.setText("Spieler "+p1.getName()+":"+p1.getPunkte());
		punkteP2.setText("Spieler "+p2.getName()+":"+p2.getPunkte());
		
		switch (st.getGameState()) {
		case P1:
			infoLabel.setText("Spieler "+p1.getName()+" ist dran");
			break;
			
		case P2:
			infoLabel.setText("Spieler "+p2.getName()+" ist dran");
			break;
			
		case QUIT:
			if(p1.getPunkte()>=105){
				infoLabel.setText(p1.getName()+" hat gewonnen!");
			}
			else if(p2.getPunkte()>=105){
				infoLabel.setText(p2.getName()+" hat gewonnen!");
			}
			break;
			
		default:
			break;
		}
	}

	class MoveAction extends AbstractAction{
		@Override
		public void actionPerformed(ActionEvent e) {
			st.spielzugAusfuehren(e.getActionCommand().toUpperCase());
		}
		
	}
}