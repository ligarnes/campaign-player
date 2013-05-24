package pathfinder.actions;

import java.awt.FlowLayout;

import javax.swing.JTextField;

import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.PanelAlwaysValidOkCancel;
import pathfinder.bean.unit.PathfinderCharacter;

public abstract class ChangeHealthPoint extends IAction {
	private final PathfinderCharacter character;

	public ChangeHealthPoint(PathfinderCharacter character) {
		this.character = character;
	}

	@Override
	public final Boolean canDoAction() {
		return character != null;
	}

	protected final PathfinderCharacter getCharacter() {
		return character;
	}

	protected abstract void changeHealPoint(Integer healthPointModifier);

	@Override
	public final void doAction(int xOnScreen, int yOnScreen) throws Exception {
		final JTextField textFieldDegat = new JTextField(5);

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
		panelDegat.add(textFieldDegat);
		DialogOkCancel<PanelAlwaysValidOkCancel> dialog = new DialogOkCancel<PanelAlwaysValidOkCancel>(
				null, getName(), true, panelDegat);
		dialog.setLocation(xOnScreen - (dialog.getWidth() / 2), yOnScreen
				- (dialog.getHeight() / 2));
		dialog.setVisible(true);

		if (dialog.getReturnStatus() == DialogOkCancel.RET_OK) {
			Integer degat = Integer.valueOf(textFieldDegat.getText());

			changeHealPoint(degat);
		}
	}
}
