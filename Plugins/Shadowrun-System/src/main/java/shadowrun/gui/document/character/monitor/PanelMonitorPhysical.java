package shadowrun.gui.document.character.monitor;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import shadowrun.bean.unit.ShadowrunCharacter;

public class PanelMonitorPhysical extends JPanel implements
		PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private int rowCount;
	private ShadowrunCharacter character;

	public PanelMonitorPhysical() {

	}

	public void setCharacter(ShadowrunCharacter character) {
		if (this.character != null) {
			this.character.removePropertyChangeListener(this);
		}

		this.character = character;
		this.character.addPropertyChangeListener(this);

		int total = this.character.getPhysicalPoint();

		rowCount = (int) Math.ceil(total / 3.0);
		setLayout(new GridLayout(rowCount, 4, 0, 0));

		refresh();
	}

	public void refresh() {
		this.removeAll();

		int totalDamage = this.character.getPhysicalDamage();
		int totalPhysical = this.character.getPhysicalPoint();

		for (int row = 0; row < rowCount; ++row) {
			for (int i = 0; i < 3; ++i) {
				if (totalDamage > 0) {
					this.add(new DamageMonitor());
				} else if (totalPhysical > 0) {
					this.add(new NoDamageMonitor());
				} else {
					this.add(new NoMonitor());
				}
				totalDamage--;
				totalPhysical--;
			}
			this.add(new ValueMonitor("-" + (row + 1)));
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				revalidate();
				repaint();
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}
}
