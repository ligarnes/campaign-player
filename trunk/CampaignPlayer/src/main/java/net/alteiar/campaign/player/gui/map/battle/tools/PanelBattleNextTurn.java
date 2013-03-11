package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.server.document.map.battle.BattleClient;

public class PanelBattleNextTurn extends JPanel /* implements IBattleObserver */{
	private static final long serialVersionUID = 1L;

	private final BattleClient battle;

	private final JLabel lblTour;

	public PanelBattleNextTurn(BattleClient battle) {
		this.setPreferredSize(new Dimension(70, 50));

		this.battle = battle;
		// TODO this.battle.addBattleListener(this);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblTour = new JLabel();
		lblTour.setText("Tour " + this.battle.getCurrentTurn());
		GridBagConstraints gbc_lblTour = new GridBagConstraints();
		gbc_lblTour.insets = new Insets(0, 0, 5, 0);
		gbc_lblTour.gridx = 0;
		gbc_lblTour.gridy = 0;
		add(lblTour, gbc_lblTour);

		JButton btnTourSuivant = new JButton("Suivant");
		GridBagConstraints gbc_btnTourSuivant = new GridBagConstraints();
		gbc_btnTourSuivant.gridx = 0;
		gbc_btnTourSuivant.gridy = 1;
		add(btnTourSuivant, gbc_btnTourSuivant);

		this.battle.getCurrentTurn();

		btnTourSuivant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextTurn();
			}
		});
	}

	protected void nextTurn() {
		this.battle.nextTurn();
	}

	/*
	 * TODO
	 * 
	 * @Override public void characterAdded(BattleClient battle,
	 * ICharacterCombatClient character) { // Do not care }
	 * 
	 * @Override public void characterRemove(BattleClient battle,
	 * ICharacterCombatClient character) { // Do not care }
	 * 
	 * @Override public void turnChanged(BattleClient battle) {
	 * lblTour.setText("Tour " + this.battle.getCurrentTurn()); }
	 */
}
