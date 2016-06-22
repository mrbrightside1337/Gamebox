/**
 * Hilfsklasse, die nach 750ms einen Farbewechsel des Hintergrunds veranlasst.
 * @author Timo Appenzeller, 191382
 * @date 24.03.2016
 */
public class RegenbogenThread extends Thread {
	
	RegenbogenFarben rbf;
	
	/**
	 * Konstruktor eines RegenbogenThread-Thread.
	 * Erzeugt ein RegenbogenFarben-Frame.
	 */
	public RegenbogenThread(){
		rbf = new RegenbogenFarben();
		this.start();
	}
	
	/**
	 * run-Methode mit Endlosschleife.
	 */
	public void run(){
		 while(true){
			 farbwechsel();
		 }
		
	}
	
	/**
	 * Die farbwechsel-Methode wartet 750ms und ruft dann die changebachgroundColor-Methode eines RegenbogenFarben Objekts auf. 
	 */
	synchronized void farbwechsel(){
		try {
			wait(750); // 3/4-Sekunde warten vor Farbwechsel
			rbf.changeBackgroundColor();
		} catch (InterruptedException e) {
			
		}
	}

}
