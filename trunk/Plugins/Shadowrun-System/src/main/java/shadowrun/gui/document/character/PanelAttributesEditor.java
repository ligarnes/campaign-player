package shadowrun.gui.document.character;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.alteiar.panel.ImagePanel;

import org.apache.log4j.Logger;

import shadowrun.bean.unit.ShadowrunCharacter;

public class PanelAttributesEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final Integer MIN_WIDTH = 693;
	public static final Integer MIN_HEIGHT = 155;

	private final JTextField textFieldCon;
	private final JTextField textFieldAgi;
	private final JTextField textFieldRea;
	private final JTextField textFieldFor;
	private final JTextField textFieldCha;
	private final JTextField textFieldInt;
	private final JTextField textFieldLog;
	private final JTextField textFieldVol;
	private final JTextField textFieldChance;
	private final JTextField textFieldMagie;
	private final JTextField textFieldEss;
	private final JTextField textFieldInit;
	private final JTextField textFieldPI;

	public PanelAttributesEditor() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLayeredPane layeredPane = new JLayeredPane();
		GridBagConstraints gbc_layeredPane = new GridBagConstraints();
		gbc_layeredPane.fill = GridBagConstraints.BOTH;
		gbc_layeredPane.gridx = 0;
		gbc_layeredPane.gridy = 0;
		add(layeredPane, gbc_layeredPane);

		ImageIcon background = new ImageIcon(getClass().getResource(
				"/shadowrun/gui/document/character/attributs.png"));

		JPanel panelBackground = new ImagePanel(background.getImage());
		panelBackground.setLocation(0, 0);
		layeredPane.add(panelBackground, new Integer(1));

		textFieldCon = new FocusTextField();
		textFieldCon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldCon.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCon.setText("1");
		textFieldCon.setBounds(28, 39, 52, 32);
		panelBackground.add(textFieldCon);
		textFieldCon.setColumns(10);

		textFieldAgi = new FocusTextField();
		textFieldAgi.setText("1");
		textFieldAgi.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAgi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldAgi.setColumns(10);
		textFieldAgi.setBounds(126, 39, 52, 32);
		panelBackground.add(textFieldAgi);

		textFieldRea = new FocusTextField();
		textFieldRea.setText("1");
		textFieldRea.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldRea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldRea.setColumns(10);
		textFieldRea.setBounds(232, 39, 52, 32);
		panelBackground.add(textFieldRea);

		textFieldFor = new FocusTextField();
		textFieldFor.setText("1");
		textFieldFor.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFor.setColumns(10);
		textFieldFor.setBounds(332, 39, 52, 32);
		panelBackground.add(textFieldFor);

		textFieldCha = new FocusTextField();
		textFieldCha.setText("1");
		textFieldCha.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldCha.setColumns(10);
		textFieldCha.setBounds(429, 39, 52, 32);
		panelBackground.add(textFieldCha);

		textFieldInt = new FocusTextField();
		textFieldInt.setText("1");
		textFieldInt.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldInt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldInt.setColumns(10);
		textFieldInt.setBounds(528, 39, 52, 32);
		panelBackground.add(textFieldInt);

		textFieldLog = new FocusTextField();
		textFieldLog.setText("1");
		textFieldLog.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldLog.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldLog.setColumns(10);
		textFieldLog.setBounds(618, 39, 52, 32);
		panelBackground.add(textFieldLog);

		textFieldVol = new FocusTextField();
		textFieldVol.setText("1");
		textFieldVol.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldVol.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldVol.setColumns(10);
		textFieldVol.setBounds(28, 118, 52, 32);
		panelBackground.add(textFieldVol);

		textFieldChance = new FocusTextField();
		textFieldChance.setText("1");
		textFieldChance.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldChance.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldChance.setColumns(10);
		textFieldChance.setBounds(126, 118, 52, 32);
		panelBackground.add(textFieldChance);

		textFieldMagie = new FocusTextField();
		textFieldMagie.setText("1");
		textFieldMagie.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMagie.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldMagie.setColumns(10);
		textFieldMagie.setBounds(227, 118, 52, 32);
		panelBackground.add(textFieldMagie);

		textFieldEss = new FocusTextField();
		textFieldEss.setText("1");
		textFieldEss.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEss.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldEss.setColumns(10);
		textFieldEss.setBounds(332, 118, 52, 32);
		panelBackground.add(textFieldEss);

		textFieldInit = new FocusTextField();
		textFieldInit.setText("1");
		textFieldInit.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldInit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldInit.setColumns(10);
		textFieldInit.setBounds(429, 118, 52, 32);
		panelBackground.add(textFieldInit);

		textFieldPI = new FocusTextField();
		textFieldPI.setText("1");
		textFieldPI.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldPI.setColumns(10);
		textFieldPI.setBounds(528, 118, 52, 32);
		panelBackground.add(textFieldPI);

		JPanel panelTextFields = new JPanel();
		layeredPane.add(panelTextFields, new Integer(0));

		layeredPane.setPreferredSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		layeredPane.setMaximumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		layeredPane.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

		this.setPreferredSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		this.setMaximumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
	}

	private Integer getValue(JTextField val) {
		String text = val.getText();
		Integer value = 0;
		try {
			value = Integer.valueOf(text);
		} catch (Exception e) {
			Logger.getLogger(getClass()).warn("Valeur non valide", e);
		}
		return value;
	}

	private static class FocusTextField extends JTextField {
		private static final long serialVersionUID = 1L;

		{
			addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
					FocusTextField.this.select(0, getText().length());
				}

				@Override
				public void focusLost(FocusEvent e) {
					FocusTextField.this.select(0, 0);
				}
			});
		}
	}

	public void apply(ShadowrunCharacter character) {
		character.setBody(getValue(textFieldCon));
		character.setAgility(getValue(textFieldAgi));
		character.setReaction(getValue(textFieldRea));
		character.setStrenght(getValue(textFieldFor));

		character.setCharisma(getValue(textFieldCha));
		character.setIntuition(getValue(textFieldInt));
		character.setLogic(getValue(textFieldLog));

		character.setWillpower(getValue(textFieldVol));
		character.setEdge(getValue(textFieldChance));
		character.setMagicResonnace(getValue(textFieldMagie));
		character.setEssence(getValue(textFieldEss));

		character.setInitiative(getValue(textFieldInit));
		character.setInitPasses(getValue(textFieldPI));
	}
}
