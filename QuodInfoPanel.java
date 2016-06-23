/**
 * QuodInfoPanel.java
 *
 * @author Timo Appenzeller, 191382
 */

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuodInfoPanel extends JPanel implements Observer {
		
	private QuodSpieler spielerEins;
	private QuodSpieler spielerZwei;
	private QuodGameController controller;
	
	private JLabel statusLabel;
	private JLabel spielerEinsLabel;
	private JLabel spielerEinsQuodsLabel;
	private JLabel spielerEinsQuasareLabel;
	private JLabel spielerZweiLabel;
	private JLabel spielerZweiQuodsLabel;
	private JLabel spielerZweiQuasareLabel;
	private JPanel steinePanel;
	private JPanel statusPanel;
	private JPanel spielerEinsPanel;
	private JPanel spielerZweiPanel;
	
	public QuodInfoPanel(QuodGameController controller){
		
		this.controller = controller;
		
		spielerEins = controller.getSpieler(1);
		spielerZwei = controller.getSpieler(2);
		
		spielerEins.addObserver(this);
		spielerZwei.addObserver(this);
		controller.addObserver(this);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		statusLabel = new JLabel();
		spielerEinsPanel = new JPanel();
		spielerZweiPanel = new JPanel();
		
		steinePanel = new JPanel();
		steinePanel.setLayout(new BoxLayout(steinePanel, BoxLayout.X_AXIS));
		steinePanel.add(spielerEinsPanel);
		steinePanel.add(Box.createRigidArea(new Dimension(50, 50)));
		steinePanel.add(spielerZweiPanel);
	
		spielerEinsLabel = new JLabel("Spieler 1");
		spielerEinsLabel.setFont(spielerEinsLabel.getFont().deriveFont(18f));

		spielerZweiLabel = new JLabel("Spieler 2");
		spielerZweiLabel.setFont(spielerZweiLabel.getFont().deriveFont(18f));
		spielerEinsQuodsLabel = new JLabel("Quods: "+spielerEins.getAnzahlQuods());
		spielerEinsQuasareLabel = new JLabel("Quasare: "+spielerEins.getAnzahlQuasare());
		spielerEinsQuodsLabel.setFont(spielerEinsQuodsLabel.getFont().deriveFont(14f));
		spielerEinsQuasareLabel.setFont(spielerEinsQuasareLabel.getFont().deriveFont(14f));
		
		spielerZweiQuodsLabel = new JLabel("Quods: "+spielerZwei.getAnzahlQuods());
		spielerZweiQuasareLabel = new JLabel("Quasare: "+spielerZwei.getAnzahlQuasare());
		spielerZweiQuodsLabel.setFont(spielerZweiQuodsLabel.getFont().deriveFont(14f));
		spielerZweiQuasareLabel.setFont(spielerZweiQuasareLabel.getFont().deriveFont(14f));
		
		spielerEinsPanel.setLayout(new BoxLayout(spielerEinsPanel, BoxLayout.Y_AXIS));
		spielerEinsPanel.add(spielerEinsLabel);
		spielerEinsPanel.add(spielerEinsQuodsLabel);
		spielerEinsPanel.add(spielerEinsQuasareLabel);
		
		spielerZweiPanel.setLayout(new BoxLayout(spielerZweiPanel, BoxLayout.Y_AXIS));
		spielerZweiPanel.add(spielerZweiLabel);
		spielerZweiPanel.add(spielerZweiQuodsLabel);
		spielerZweiPanel.add(spielerZweiQuasareLabel);
		
		statusPanel = new JPanel();
		statusPanel.add(statusLabel);
		
		add(steinePanel);
		add(Box.createRigidArea(new Dimension(50, 20)));
		add(statusPanel);
		
		statusLabel.setText("Spieler 1 ist dran");
		statusLabel.setFont(statusLabel.getFont().deriveFont(16f));
		
		setVisible(true);
	}

	/**
	 * Ändert den Text der Info-Label abhängig davon, welches Objekt eine Veränderung meldet.
	 */
	public void update(Observable obs, Object obj) {
		if (obs==spielerEins) {
			spielerEinsQuodsLabel.setText("Quods: "+spielerEins.getAnzahlQuods());
			spielerEinsQuasareLabel.setText("Quasare: "+spielerEins.getAnzahlQuasare());
		}
		else if(obs==spielerZwei){
			spielerZweiQuodsLabel.setText("Quods: "+spielerZwei.getAnzahlQuods());
			spielerZweiQuasareLabel.setText("Quasare: "+spielerZwei.getAnzahlQuasare());
		}
		else if(obs==controller){
			QuodGameState state = controller.getGameState();
			if (state==QuodGameState.P1){
				statusLabel.setText("Spieler 1 ist dran");
			}
			else if (state==QuodGameState.P2){
				statusLabel.setText("Spieler 2 ist dran");
			}
			else if (state==QuodGameState.WinP1){
				statusLabel.setText("Spieler 1 hat Gewonnen!");
			}
			else if (state==QuodGameState.WinP2){
				statusLabel.setText("Spieler 2 hat Gewonnen!");
			}
			else if (state==QuodGameState.Draw){
				statusLabel.setText("Unentschieden!");
			}
		}
		
	}

}
