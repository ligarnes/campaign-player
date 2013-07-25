package net.alteiar.campaign.player.gui.sideView.combatTraker;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.sideView.combatTraker.dnd.ListTransferHandler;
import net.alteiar.combatTraker.CombatTrackerUnit;
import net.alteiar.combatTraker.CombatTraker;

public class PanelCombatTraker extends JPanel {
	private static final long serialVersionUID = 1L;

	private final int maxWidth;

	private final CombatTraker traker;
	private final CombatTrakerModel model;

	public PanelCombatTraker(int width) {
		traker = CampaignClient.getInstance().getCombatTraker();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setMaximumSize(new Dimension(width, Short.MAX_VALUE));
		maxWidth = width;

		JList<CombatTrackerUnit> list = new JList<CombatTrackerUnit>();
		list.setLayoutOrientation(JList.VERTICAL);
		list.setCellRenderer(new CharacterCombatCellRenderer(maxWidth, traker));
		list.setTransferHandler(new ListTransferHandler(traker));
		list.setDragEnabled(true);
		model = new CombatTrakerModel(traker);
		list.setModel(model);

		JButton next = new JButton("next");
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});

		JButton sort = new JButton("Triée");
		sort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sort();
			}
		});

		JButton init = new JButton("Init");
		init.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initForAll();
			}
		});

		final JLabel lblTurn = new JLabel("Tour: " + traker.getCurrentTurn());
		lblTurn.setHorizontalAlignment(JLabel.CENTER);
		traker.addPropertyChangeListener(
				CombatTraker.PROP_CURRENT_TURN_PROPERTY,
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						lblTurn.setText("Tour: " + evt.getNewValue());
					}
				});

		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				traker.reset();
			}
		});

		JPanel button = new JPanel();
		button.setLayout(new GridLayout(2, 2));
		button.add(next);
		button.add(sort);
		button.add(init);
		button.add(reset);

		this.add(lblTurn);
		this.add(list);
		this.add(button);
	}

	public void sort() {
		traker.sort(new InitiativeComparator());
	}

	public void next() {
		traker.nextUnit();
	}

	public void initForAll() {
		for (int i = 0; i < model.getSize(); ++i) {
			model.getElementAt(i).rollInitiative();
		}
	}

	/*
	 * public static void main(String[] args) { JFrame frm = new JFrame();
	 * 
	 * // Initialize log4j
	 * DOMConfigurator.configure("./ressources/log/log4j.xml");
	 * 
	 * PanelCombatTraker main = new PanelCombatTraker(150);
	 * 
	 * JPanel pane = new JPanel(); pane.setLayout(new BoxLayout(pane,
	 * BoxLayout.X_AXIS)); // pane.setPreferredSize(new Dimension(300,
	 * Integer.MAX_VALUE)); // pane.setSize(new Dimension(300,
	 * Integer.MAX_VALUE)); // pane.setMinimumSize(new Dimension(300, 600)); //
	 * pane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
	 * 
	 * pane.add(main);
	 * 
	 * JPanel empty = new JPanel(); empty.setBackground(Color.RED);
	 * empty.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	 * empty.setMinimumSize(new Dimension(400, 600));
	 * 
	 * JPanel paneContain = new JPanel(); paneContain.setLayout(new
	 * BoxLayout(paneContain, BoxLayout.Y_AXIS)); paneContain.add(pane);
	 * paneContain.add(empty);
	 * 
	 * frm.add(paneContain);
	 * 
	 * frm.setPreferredSize(new Dimension(800, 600)); frm.pack();
	 * frm.setLocationRelativeTo(null);
	 * frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frm.setVisible(true);
	 * 
	 * main.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	 * main.addCharacter(new ToRemoveCharacter("gobelin", 25));
	 * main.addCharacter(new ToRemoveCharacter("test", 25));
	 * main.addCharacter(new ToRemoveCharacter("ligarnes", 25));
	 * main.addCharacter(new ToRemoveCharacter("elvin", 25));
	 * main.addCharacter(new ToRemoveCharacter("Robin", 25));
	 * main.addCharacter(new ToRemoveCharacter(
	 * "Elahfue dsfd fsd fgsd sdf dsg dfg dfh fgh hfdsh fvh gzdgfd gbfdzs gdfs gfdadz fdzdg fdz gfsd fdg fdg sdffcds"
	 * , 25));
	 * 
	 * }
	 * 
	 * private static class ToRemoveCharacter implements CombatTrackerUnit {
	 * private final PropertyChangeSupport support;
	 * 
	 * private final String name; private Integer hp; private int init;
	 * 
	 * public ToRemoveCharacter(String name, int hp) { this.name = name; this.hp
	 * = hp;
	 * 
	 * support = new PropertyChangeSupport(this); }
	 * 
	 * @Override public int getCurrentHp() { return hp; }
	 * 
	 * @Override public void doDamage(int value) { hp -= value; }
	 * 
	 * @Override public void doHeal(int value) { hp += value; }
	 * 
	 * @Override public int getInitiative() { return init; }
	 * 
	 * @Override public void rollInitiative() { init = new Random().nextInt(30);
	 * 
	 * support.firePropertyChange("init", -1, init); }
	 * 
	 * @Override public String getName(boolean isDM) { String name = "inconnu";
	 * if (isDM) { name = this.name; } return name; }
	 * 
	 * @Override public void addCombatTrackerChangeListener(
	 * PropertyChangeListener listener) {
	 * support.addPropertyChangeListener(listener); }
	 * 
	 * @Override public void removeCombatTrackerChangeListener(
	 * PropertyChangeListener listener) {
	 * support.removePropertyChangeListener(listener); }
	 * 
	 * }
	 */
}
