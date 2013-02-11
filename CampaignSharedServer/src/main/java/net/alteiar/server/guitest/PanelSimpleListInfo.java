package net.alteiar.server.guitest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.alteiar.ExceptionTool;

public abstract class PanelSimpleListInfo extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JPanel datas;

	public PanelSimpleListInfo(String name) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(name));

		datas = new JPanel();
		datas.setLayout(new BoxLayout(datas, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(datas);
		this.add(scroll, BorderLayout.CENTER);

		JButton button = new JButton("refresh");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					askRefresh();
				} catch (RemoteException e1) {
					ExceptionTool.showError(e1, "Erreur sur le serveur");
				}
			}
		});
		this.add(button, BorderLayout.SOUTH);

	}

	protected final JPanel getPanelData() {
		return datas;
	}

	protected abstract void askRefresh() throws RemoteException;
}
