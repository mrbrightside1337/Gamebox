/**
 * QuadFrame.java
 *
 * @author Timo Appenzeller, 191382
 */

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class QuodFrame extends JInternalFrame {
	
	private QuodViewPanel viewPanel;
	private QuodInfoPanel infoPanel;
	private QuodSpielfeld spielfeld;
	private JPanel wrapperPanel;
	
	private QuodFrameController controller;
	
	private JMenuBar menubar;
	private JMenu menuSpiel;
	private JMenuItem menuItemSpielStarten;
	private JMenuItem menuItemSpielBeenden;
	
	private BaseFrame baseframe;
	
	public QuodFrame(BaseFrame baseframe){
		// JInternalFrame(resizable, closable, maximizable, iconifiable)
		super("Quod", true, true, true, true);
		this.baseframe = baseframe;
		
		spielfeld = new QuodSpielfeld();
		viewPanel = new QuodViewPanel(spielfeld);
		infoPanel = new QuodInfoPanel(viewPanel.getGameController());
		
		controller = new QuodFrameController(this, viewPanel.getGameController());
		
		wrapperPanel = new JPanel();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 700);

		menubar = new JMenuBar();
		menuSpiel = new JMenu("Spiel");
		menuItemSpielBeenden = new JMenuItem("Beenden");
		menuItemSpielBeenden.setActionCommand("beenden");
		menuItemSpielBeenden.addActionListener(controller);
		
		menuItemSpielStarten = new JMenuItem("Starten");
		menuItemSpielStarten.setActionCommand("starten");
		menuItemSpielStarten.addActionListener(controller);
		
		menuSpiel.add(menuItemSpielStarten);
		menuSpiel.add(menuItemSpielBeenden);
		menubar.add(menuSpiel);
		
		setJMenuBar(menubar);
		wrapperPanel.add(infoPanel);
		
		wrapperPanel.add(Box.createRigidArea(new Dimension(50, 20)));
		wrapperPanel.add(viewPanel);
		add(wrapperPanel);
		setVisible(true);
		baseframe.add(this);
		try {
			this.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}

}
