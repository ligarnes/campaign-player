package net.alteiar.list;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import net.alteiar.list.dnd.ListTransferHandler;

public class MyDoubleList<E> extends JPanel {

	private static final long serialVersionUID = 1L;

	private final DataListModel<E> modelSrc;
	private final DataListModel<E> modelTarget;

	private final JList<E> listSrc;
	private final JList<E> listTarget;

	public MyDoubleList(String titleSrc, String titleTarget) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 47, -5, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 39, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lbltitleSrc = new JLabel("titleSrc");
		lbltitleSrc.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lbltitleSrc = new GridBagConstraints();
		gbc_lbltitleSrc.insets = new Insets(0, 0, 5, 5);
		gbc_lbltitleSrc.gridx = 0;
		gbc_lbltitleSrc.gridy = 0;
		add(lbltitleSrc, gbc_lbltitleSrc);

		JLabel lblTitletarget = new JLabel("titleTarget");
		lblTitletarget.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblTitletarget = new GridBagConstraints();
		gbc_lblTitletarget.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitletarget.gridx = 2;
		gbc_lblTitletarget.gridy = 0;
		add(lblTitletarget, gbc_lblTitletarget);

		JScrollPane scrollPaneSrc = new JScrollPane();
		GridBagConstraints gbc_scrollPaneSrc = new GridBagConstraints();
		gbc_scrollPaneSrc.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPaneSrc.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneSrc.gridx = 0;
		gbc_scrollPaneSrc.gridy = 1;
		add(scrollPaneSrc, gbc_scrollPaneSrc);

		listSrc = new JList<E>();
		scrollPaneSrc.setViewportView(listSrc);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel.add(panel_2, gbc_panel_2);

		JButton btnAdd = new JButton(">>");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 1;
		panel.add(btnAdd, gbc_btnAdd);

		JButton btnRemove = new JButton("<<");
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 2;
		panel.add(btnRemove, gbc_btnRemove);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		panel.add(panel_1, gbc_panel_1);

		JScrollPane scrollPaneTarget = new JScrollPane();
		GridBagConstraints gbc_scrollPaneTarget = new GridBagConstraints();
		gbc_scrollPaneTarget.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTarget.gridx = 2;
		gbc_scrollPaneTarget.gridy = 1;
		add(scrollPaneTarget, gbc_scrollPaneTarget);

		listTarget = new JList<E>();
		scrollPaneTarget.setViewportView(listTarget);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addList();
			}
		});

		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeList();
			}
		});

		modelSrc = new DataListModel<>();
		listSrc.setModel(modelSrc);
		listSrc.setDragEnabled(true);
		listSrc.setTransferHandler(new ListTransferHandler<E>());

		modelTarget = new DataListModel<>();
		listTarget.setModel(modelTarget);
		listTarget.setDragEnabled(true);
		listTarget.setTransferHandler(new ListTransferHandler<E>());

		lbltitleSrc.setText(titleSrc);
		lblTitletarget.setText(titleTarget);
	}

	public void setEnable(boolean isEnable) {
		listSrc.setEnabled(isEnable);
		listTarget.setEnabled(isEnable);
	}

	private void addList() {
		List<E> datas = listSrc.getSelectedValuesList();

		modelSrc.removeDatas(datas);
		modelTarget.addDatas(datas);
	}

	private void removeList() {
		List<E> datas = listTarget.getSelectedValuesList();
		modelSrc.addDatas(datas);
		modelTarget.removeDatas(datas);
	}

	public void setCellRenderer(ListCellRenderer<E> cellRenderer) {
		listSrc.setCellRenderer(cellRenderer);
		listTarget.setCellRenderer(cellRenderer);
	}

	public void selectData(E data) {
		modelSrc.removeData(data);
		modelTarget.addData(data);
	}

	public void setDatas(List<E> datas) {
		modelSrc.addDatas(datas);
		modelTarget.clear();
	}

	public void clear() {
		modelSrc.clear();
		modelTarget.clear();
	}

	public ArrayList<E> getSourceDatas() {
		return modelSrc.getDatas();
	}

	public ArrayList<E> getTargetDatas() {
		return modelTarget.getDatas();
	}

}
