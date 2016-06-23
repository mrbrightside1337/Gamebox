import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * Schreiben Sie das GUI-Programm mit den zehn Kn�pfen f�r ein Schloss aus der letzten Woche
 * um: Nun soll eine Kombination von 8 Knopfdr�cken (0-1-0-4-2-0-1-6) das Schloss �ffnen, also
 * das Programm beenden. Die Kn�pfe sollen aber diesmal wie in der Abbildung ringf�rmig
 * angeordnet sein.
 * Zus�tzlich soll sich die Beschriftung (und damit die Wirkung) der Kn�pfe im Sekundentakt um
 * eine Position drehen, zun�chst nach rechts. Bei jedem falschen Knopfdruck �ndert sich allerdings
 * die Drehrichtung. 
 * @author Timo Appenzeller, 191382
 * @date 31.03.2016
 */


public class DrehSchloss extends JInternalFrame implements ActionListener {
	
	byte[] code = {0,1,0,4,2,0,1,6}; //Der richtige Code
	byte tocheck = 0; //Stelle, des Codes, die als n�chste zu �berpr�fen ist
	
	JButton[] buttons; //10 Buttons, die kreisf�rmig angeordnet werden und die 
						//Ziffern des Dezimalsystem repr�sentieren 
	JPanel empty, empty2; // leere Panel zum F�llen der Mitte
	
	Timer t; //Timer, der f�r das kreisf�rmige Drehen verantwortlich ist
	
	boolean directionRight; //Rechtsrum ja oder nein
	
	ButtonListener bl; //separater Listener f�r die Button-Klicks
	
	private BaseFrame baseframe;
	
	/**
	 * DrehSchloss-Konstruktor.
	 * Zuerst wird das Button-Array initialisiert und anschlie�end die Buttons erzeugt 
	 * und beim ButtonListener registriert.
	 * Danach werden die Buttons mithilfe des Grid-Layouts auf dem Frame positioniert.
	 * Am Ende wird ein Timer gestartet, der jede Sekunde ein ActionEvent erzeugt.
	 */
	public DrehSchloss(BaseFrame baseframe){
		//title, resizable, closable, maximizable, iconifiable
		super("Drehschloss 0,1,0,4,2,0,1,6", true, true, true, true);
		
		this.baseframe = baseframe;
		
		setSize(300, 300);
		
		buttons = new JButton[10];
		bl = new ButtonListener();
		
		for (int i = 0; i < 10; i++) {
			buttons[i] = new JButton(""+i);
			buttons[i].addActionListener(bl);
		}
		
		directionRight = true;
		
		setLayout(new GridLayout(4, 3));
		
		empty = new JPanel();
		empty2 = new JPanel();
		
		add(buttons[1]);
		add(buttons[0]);
		add(buttons[9]);
		
		add(buttons[2]);
		add(empty);
		add(buttons[8]);
		
		add(buttons[3]);
		add(empty2);		
		add(buttons[7]);
		
		add(buttons[4]);
		add(buttons[5]);
		add(buttons[6]);
		
		t= new Timer(1000,this);
		t.setActionCommand("timer");
		t.start();
		
		setVisible(true);
		
		baseframe.add(this);
		try {
			this.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
		
	}
	
	
	/**
	 * Die actionPerformed Methode veranlasst bei jedem ActionEvent, das vom timer t 
	 * erzeugt wird einen Wechsel der Zahlen entweder nach rechts oder nach links.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("timer")) {
			if(directionRight){
				changeRight();
			}
			else{
				changeLeft();
			}
		}
	}
	
	/**
	 * changeRight-Methode.
	 * Wechselt die Zahlen im Uhrzeigersinn. An der Stelle an der vorher eine 0 war, wird dann die 1 angezeigt,
	 * an der Stelle wo eine 1 war, wird dann eine 2 angezeigt usw.
	 */
	public void changeRight(){
		for (int i = 0; i < buttons.length; i++) {
			byte actCom = Byte.parseByte(buttons[i].getActionCommand());
			switch (actCom) {
			case 0:
				buttons[i].setActionCommand(""+1);
				buttons[i].setText(""+1);
				break;
			case 1:
				buttons[i].setActionCommand(""+2);
				buttons[i].setText(""+2);
				break;
			case 2:
				buttons[i].setActionCommand(""+3);
				buttons[i].setText(""+3);
				break;
			case 3:
				buttons[i].setActionCommand(""+4);
				buttons[i].setText(""+4);
				break;
			case 4:
				buttons[i].setActionCommand(""+5);
				buttons[i].setText(""+5);
				break;
			case 5:
				buttons[i].setActionCommand(""+6);
				buttons[i].setText(""+6);
				break;
			case 6:
				buttons[i].setActionCommand(""+7);
				buttons[i].setText(""+7);
				break;
			case 7:
				buttons[i].setActionCommand(""+8);
				buttons[i].setText(""+8);
				break;
			case 8:
				buttons[i].setActionCommand(""+9);
				buttons[i].setText(""+9);
				break;
			case 9:
				buttons[i].setActionCommand(""+0);
				buttons[i].setText(""+0);
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * changeLeft-Methode.
	 * Wechselt die Zahlen gegen den Uhrzeigersinn. An der Stelle an der vorher eine 0 war, wird dann die 9
	 * angezeigt, an der Stelle wo eine 1 war, wird dann eine 0 angezeigt usw.
	 */
	public void changeLeft(){
		for (int i = 0; i < buttons.length; i++) {
			byte actCom = Byte.parseByte(buttons[i].getActionCommand());
			switch (actCom) {
			case 0:
				buttons[i].setActionCommand(""+9);
				buttons[i].setText(""+9);
				break;
			case 1:
				buttons[i].setActionCommand(""+0);
				buttons[i].setText(""+0);
				break;
			case 2:
				buttons[i].setActionCommand(""+1);
				buttons[i].setText(""+1);
				break;
			case 3:
				buttons[i].setActionCommand(""+2);
				buttons[i].setText(""+2);
				break;
			case 4:
				buttons[i].setActionCommand(""+3);
				buttons[i].setText(""+3);
				break;
			case 5:
				buttons[i].setActionCommand(""+4);
				buttons[i].setText(""+4);
				break;
			case 6:
				buttons[i].setActionCommand(""+5);
				buttons[i].setText(""+5);
				break;
			case 7:
				buttons[i].setActionCommand(""+6);
				buttons[i].setText(""+6);
				break;
			case 8:
				buttons[i].setActionCommand(""+7);
				buttons[i].setText(""+7);
				break;
			case 9:
				buttons[i].setActionCommand(""+8);
				buttons[i].setText(""+8);
				break;
			default:
				break;
			}
		}
	}

	
	/**
	 * ButtonListener ist eine innere Klasse, die als ActionListener dient.
	 * Hier werden die ActionEvents verarbeitet, die beim Klicken der buttons erzeugt werden.
	 */
	public class ButtonListener implements ActionListener{

		/**
		 * Die actionPerformed-Methode �berpr�ft, ob die richtigen Kn�pfe in der 
		 * korrekten Reihenfolge gedr�ckt werden.
		 */
		public void actionPerformed(ActionEvent e) {
			
			System.out.println(e.getActionCommand());
			
			byte zahl = Byte.parseByte(e.getActionCommand());
			
			if(zahl == code[tocheck]){ //Wenn der gedr�ckte Kn�pf korrekt ist: 
									// n�chste Stelle zur �berpr�fung setzen, Farbe gr�n,
									// und ggf. Programm beenden, wenn Code komplett richtig
				tocheck++;
				
				for (int i = 0; i < buttons.length; i++) {
					buttons[i].setBackground(Color.green);
				}
				
				if(tocheck==code.length){
					dispose();
				}
			}
			else{ //Wenn der gedr�ckte Knopf falsch: Drehrichtung umkehren, Farbe rot, von vorne pr�fen
				for (int i = 0; i < buttons.length; i++) {
					buttons[i].setBackground(Color.red);
				}
				
				if(directionRight){
					directionRight=false;
				}
				else{
					directionRight=true;
				}
				tocheck=0;
			}
			
		}
		
	}

}
