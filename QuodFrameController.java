import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuodFrameController implements ActionListener{
	
	private QuodFrame frame;
	private QuodGameController gController;
	
	
	/**
	 * Konstruktor.
	 * @param frame Fenster, zum dem der Controller gehört
	 * @param controller GameController, des Quod Spiels.
	 */
	public QuodFrameController(QuodFrame frame, QuodGameController controller){
		this.frame = frame;
		gController = controller;
	}

	/**
	 * Zum Verarbeiten von Action-Events des Frames. (Menüleiste)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("beenden")){
			frame.dispose();
		}
		
		if(e.getActionCommand().equals("starten")){
			gController.restartGame();
		}
		
	}
	
	

}