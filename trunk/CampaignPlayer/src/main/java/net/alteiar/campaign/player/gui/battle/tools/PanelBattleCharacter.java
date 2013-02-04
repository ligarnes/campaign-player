package net.alteiar.campaign.player.gui.battle.tools;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import net.alteiar.campaign.player.gui.tools.DialogOkCancel;
import net.alteiar.campaign.player.gui.tools.PanelAlwaysValidOkCancel;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.observer.campaign.battle.character.ICharacterCombatObserver;

public class PanelBattleCharacter extends JPanel implements
		ICharacterCombatObserver {
	private static final long serialVersionUID = 1L;

	private final ICharacterCombatClient character;
	private final IBattleClient battle;

	public PanelBattleCharacter(IBattleClient battle,
			ICharacterCombatClient character) {
		this.setPreferredSize(new Dimension(50, 50));

		this.battle = battle;
		this.character = character;
		this.character.addCharacterCombatListener(this);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				PanelBattleCharacter.this.character.setHighlighted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				PanelBattleCharacter.this.character.setHighlighted(false);
			}
		});
	}

	public ICharacterCombatClient getCharacter() {
		return character;
	}

	private void onClick(final MouseEvent mouseEvent) {
		JPopupMenu menu = new JPopupMenu();

		JMenuItem damage = new JMenuItem("Dégat");
		JMenuItem heal = new JMenuItem("Soins");
		JMenuItem changeInitiative = new JMenuItem("Changer initiative");
		JMenuItem delete = new JMenuItem("Supprimer");

		damage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDamage(mouseEvent);
			}
		});

		heal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doHeal(mouseEvent);
			}
		});

		changeInitiative.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeInit(mouseEvent);
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		menu.add(damage);
		menu.add(heal);
		menu.add(changeInitiative);
		menu.add(delete);

		menu.show(this, mouseEvent.getX(), mouseEvent.getY());
	}

	protected void delete() {
		this.battle.removeCharacter(character);
	}

	protected void changeInit(MouseEvent e) {
		PanelAlwaysValidOkCancel panelDegat = new PanelAlwaysValidOkCancel();
		panelDegat.setLayout(new FlowLayout());
		final JTextField textFieldDegat = new JTextField(5);
		panelDegat.add(textFieldDegat);
		DialogOkCancel<PanelAlwaysValidOkCancel> dialog = new DialogOkCancel<PanelAlwaysValidOkCancel>(
				null, "Initiative", true, panelDegat);

		dialog.setLocation(e.getLocationOnScreen());
		dialog.setVisible(true);

		if (dialog.getReturnStatus() == DialogOkCancel.RET_OK) {
			Integer degat = Integer.valueOf(textFieldDegat.getText());
			this.character.setInit(degat);
		}
	}

	protected void doDamage(MouseEvent e) {
		PanelAlwaysValidOkCancel panelDegat = new PanelAlwaysValidOkCancel();
		panelDegat.setLayout(new FlowLayout());
		final JTextField textFieldDegat = new JTextField(5);
		panelDegat.add(textFieldDegat);
		DialogOkCancel<PanelAlwaysValidOkCancel> dialog = new DialogOkCancel<PanelAlwaysValidOkCancel>(
				null, "Dégat", true, panelDegat);

		dialog.setLocation(e.getLocationOnScreen());
		dialog.setVisible(true);

		if (dialog.getReturnStatus() == DialogOkCancel.RET_OK) {
			Integer degat = Integer.valueOf(textFieldDegat.getText());
			this.character.doDamage(degat);
		}
	}

	protected void doHeal(MouseEvent e) {
		PanelAlwaysValidOkCancel panelDegat = new PanelAlwaysValidOkCancel();
		panelDegat.setLayout(new FlowLayout());
		final JTextField textFieldDegat = new JTextField(5);
		panelDegat.add(textFieldDegat);
		DialogOkCancel<PanelAlwaysValidOkCancel> dialog = new DialogOkCancel<PanelAlwaysValidOkCancel>(
				null, "Soins", true, panelDegat);

		dialog.setLocation(e.getLocationOnScreen());
		dialog.setVisible(true);

		if (dialog.getReturnStatus() == DialogOkCancel.RET_OK) {
			Integer degat = Integer.valueOf(textFieldDegat.getText());
			this.character.doHeal(degat);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// Draw image
		if (!character.IsVisibleForPlayer()) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.7f));
		}

		g.drawImage(character.getCharacter().getBackground(), 5, 0, 45, 40,
				null);

		Integer currentHp = character.getCharacter().getCurrentHp();
		Integer totalHp = character.getCharacter().getTotalHp();

		Float ratio = Math.min(1.0f, currentHp / (float) totalHp);

		Color hp = Color.RED;
		if (currentHp > 0) {
			hp = new Color(1.0F - ratio.floatValue(), ratio.floatValue(), 0);
		}

		// Draw life
		g.setColor(hp);
		g.fillRect(0, 40, (int) (50 * ratio), 10);
		g.setColor(Color.BLACK);

		FontMetrics metrics = g.getFontMetrics();
		String text = currentHp + "/" + totalHp;

		int textWidth = metrics.stringWidth(text);
		g.setColor(Color.BLACK);
		g.drawRect(0, 40, 49, 9);

		if (CampaignClient.INSTANCE.canAccess(this.character.getCharacter())) {
			g.drawString(text, (50 - textWidth) / 2, 49);
		}

		// draw highlight
		if (character.isHighlighted()) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLUE);
			Stroke org = g2.getStroke();
			g2.setStroke(new BasicStroke(8));
			g2.drawRect(0, 0, 50, 50);
			g2.setStroke(org);
		}
	}

	@Override
	public void characterChange(ICharacterCombatClient character) {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void highLightChange(ICharacterCombatClient character,
			Boolean isHighlighted) {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void visibilityChange(ICharacterCombatClient character) {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void initiativeChange(ICharacterCombatClient character) {
		// Do not impact
	}

	@Override
	public void positionChanged(ICharacterCombatClient character) {
		// Do not care
	}

	@Override
	public void rotationChanged(ICharacterCombatClient character) {
		// Do not care
	}
}
