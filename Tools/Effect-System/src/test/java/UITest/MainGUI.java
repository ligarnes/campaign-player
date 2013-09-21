package UITest;


import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



import net.alteiar.CampaignClient;
import net.alteiar.effectBean.gui.effect.trigger.PanelTriggerBuilder;
import net.alteiar.player.Player;
import net.alteiar.test.NewCampaignTest;
import net.alteiar.trigger.TriggerBean;

public class MainGUI extends BasicTest{
	private static PanelTriggerBuilder monPanel;
	
	 public static void main(String[] args) {
		 
		 System.out.println("Setting up test");
			String address = "127.0.0.1";
			String port = "1099";

			String localDirectoryPath = "./Toto/";

			CampaignClient
					.startNewCampaignServer(address, port, localDirectoryPath);

			//CampaignClient.getInstance().createPlayer(getPlayerName(), true,
			//		Color.BLUE);
		 
		 
		 
		 JFrame window=new JFrame("test");
		 window.setSize(640, 480);
         window.setLocationRelativeTo(null);
         JPanel test=new JPanel();
         test.setSize(640, 480);

         monPanel=new PanelTriggerBuilder();
         test.add(monPanel);
         JButton bouton=new JButton("create");
         bouton.addActionListener(new ActionListener(){
       
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TriggerBean trigger=(TriggerBean) monPanel.buildMapElement(new Point(15,15));
			     System.out.println("trigger class="+trigger.getClass().getCanonicalName());
			     System.out.println("shape class="+trigger.getAreaOfActivation().getClass().getCanonicalName());
			     System.out.println("effect class="+trigger.getEffect().getClass().getCanonicalName());
			     System.out.println("Activators class="+trigger.getTypeOfActivator().getCanonicalName());
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
