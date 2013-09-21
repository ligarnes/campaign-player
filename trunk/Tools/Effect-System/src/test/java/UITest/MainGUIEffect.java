package UITest;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.gui.effect.PanelEffectBuilder;
import net.alteiar.test.BasicTest;


public class MainGUIEffect extends BasicTest{
	private static PanelEffectBuilder monPanel;
	
	 public static void main(String[] args) {
		 
		 System.out.println("Setting up test");
			String address = "127.0.0.1";
			String port = "1099";

			String localDirectoryPath = "./Toto/";

			CampaignClient
					.startNewCampaignServer(address, port, localDirectoryPath);

			//CampaignClient.getInstance().createPlayer("Toto", true,
			//		Color.BLUE);
		 
		 
		 
		 JFrame window=new JFrame("test");
		 window.setSize(640, 480);
        window.setLocationRelativeTo(null);
        JPanel test=new JPanel();
        test.setSize(640, 480);

        monPanel=new PanelEffectBuilder();
        test.add(monPanel);
        JButton bouton=new JButton("create");
        bouton.addActionListener(new ActionListener(){
      
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 Effect effet=(Effect) monPanel.buildMapElement(new Point(15,15));
			     System.out.println("Effect class="+effet.getClass().getCanonicalName());
			     System.out.println("shape class="+effet.getAreaOfEffect().getClass().getCanonicalName());
			     System.out.println("Boolean class="+effet.isOneUse());
			     System.out.println("Activators class="+effet.getTypeActOn().getCanonicalName());
			}
       	 
        });
        test.add(bouton);
        monPanel.setVisible(true);
        bouton.setVisible(true);
        test.setVisible(true);
        window.setContentPane(test);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     window.setVisible(true);
	 }
}
