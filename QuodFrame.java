/**
 * QuadFrame.java
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 *
 * @date 24.06.2016
 */

import java.awt.Dimension;
import java.util.Random;

import javax.swing.Box;
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
		super("Quod", false, true, true, true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

		this.baseframe = baseframe;
		
		spielfeld = new QuodSpielfeld();
		viewPanel = new QuodViewPanel(spielfeld);
		infoPanel = new QuodInfoPanel(viewPanel.getGameController());
		
		controller = new QuodFrameController(this, viewPanel.getGameController());
		
		wrapperPanel = new JPanel();
		
		setSize(900, 700);

		// Der Offset von 55px bei der Höhe ist wegen dem Menü und der Kopzeile
		setLocation(new Random().nextInt(baseframe.getWidth() - getWidth()),
			new Random().nextInt(baseframe.getHeight() - getHeight() - 55));

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
