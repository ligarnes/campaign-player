package shadowrun.actions;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import net.alteiar.beans.map.elements.IAction;
import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.PanelAlwaysValidOkCancel;
import shadowrun.bean.unit.ShadowrunCharacter;

public abstract class ChangeHealthPoint extends IAction {
	private final ShadowrunCharacter character;

	private enum HitPointPossibility {
		Physique, Étourdissant
	}

	public ChangeHealthPoint(ShadowrunCharacter character) {
		this.character = character;
	}

	@Override
	public final Boolean canDoAction() {
		return character != null;
	}

	protected final ShadowrunCharacter getCharacter() {
		return character;
	}

	protected abstract void changePhysicalPoint(Integer healthPointModifier);

	protected abstract void changeStunPoint(Integer healthPointModifier);

	@Override
	public final void doAction(int xOnScreen, int yOnScreen) throws Exception {
		final JTextField textFieldDegat = new JTextField(5);
		final JComboBox<HitPointPossibility> combobox = new JComboBox<>(
				HitPointPossibility.values());

		PanelAlwaysValidOkCancel panelDegat = new PanelAlwaysValidOkCancel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean isDataValid() {
				Boolean isValid = true;
				try {
					Integer.valueOf(textFieldDegat.getText());
				} catch (NumberFormatException ex) {
					isValid = false;
				}
				return isValid;
			}

			@Override
			public String getInvalidMessage() {
				return "les " + getName() + " doivents être des chiffres";
			}
		};
		panelDegat.setLayout(new FlowLayout());
		panelDegat.add(combobox);
		panelDegat.add(textFieldDegat);

		DialogOkCancel<PanelAlwaysValidOkCancel> dialog = new DialogOkCancel<PanelAlwaysValidOkCancel>(
				MainFrame.FRAME, getName(), true, panelDegat);
		dialog.setLocation(xOnScreen - (dialog.getWidth() / 2), yOnScreen
				- (dialog.getHeight() / 2));
		dialog.setVisible(true);

		if (dialog.getReturnStatus() == DialogOkCancel.RET_OK) {
			Integer damage = Integer.valueOf(textFieldDegat.getText());

			HitPointPossibility choice = (HitPointPossibility) combobox
					.getSelectedItem();
			switch (choice) {
			case Physique:
				changePhysicalPoint(damage);
				break;
			case Étourdissant:
				changeStunPoint(damage);
				break;
			}
		}
	}
}
