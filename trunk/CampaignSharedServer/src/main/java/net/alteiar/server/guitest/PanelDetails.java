package net.alteiar.server.guitest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.alteiar.ExceptionTool;

public abstract class PanelDetails extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JComboBox<String> combobox;
	private final JPanel datas;

	public PanelDetails(String name) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(name));

		combobox = new JComboBox<String>();
		combobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				change();
			}
		});
		datas = new JPanel();
		datas.setLayout(new BoxLayout(datas, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(datas);
		this.add(scroll, BorderLayout.CENTER);

		this.add(combobox, BorderLayout.NORTH);
		JButton button = new JButton("refresh");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		this.add(button, BorderLayout.SOUTH);
	}

	protected final JPanel getPanelData() {
		return datas;
	}

	protected String getSelectedName() {
		return (String) combobox.getSelectedItem();
	}

	protected abstract String[] getNames() throws RemoteException;

	protected abstract void askChange() throws RemoteException;

	protected void change() {
		try {
			askChange();
		} catch (RemoteException e1) {
			ExceptionTool.showError(e1, "Erreur sur le serveur");
		}
		this.revalidate();
		this.repaint();
	}

	protected void refresh() {
		combobox.removeAllItems();

		try {
			for (String item : getNames()) {
				combobox.addItem(item);
			}

			askRefresh();
		} catch (RemoteException e1) {
			ExceptionTool.showError(e1, "Erreur sur le serveur");
		}
		this.revalidate();
		this.repaint();
	}

	protected abstract void askRefresh() throws RemoteException;
}
