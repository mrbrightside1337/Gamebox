import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowQuitter extends WindowAdapter {
	
	public void windowClosing( WindowEvent e ) {
		System.exit ( 0 );
		}
}
