package shadowrun.gui.document.character;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.alteiar.panel.ImagePanel;
import shadowrun.bean.unit.ShadowrunCharacter;

public class PanelAttributes extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final Integer MIN_WIDTH = 693;
	public static final Integer MIN_HEIGHT = 155;

	private final JLabel textFieldCon;
	private final JLabel textFieldAgi;
	private final JLabel textFieldRea;
	private final JLabel textFieldFor;
	private final JLabel textFieldCha;
	private final JLabel textFieldInt;
	private final JLabel textFieldLog;
	private final JLabel textFieldVol;
	private final JLabel textFieldChance;
	private final JLabel textFieldMagie;
	private final JLabel textFieldEss;
	private final JLabel textFieldInit;
	private final JLabel textFieldPI;

	public PanelAttributes() {
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

		textFieldCon = new JLabel();
		textFieldCon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldCon.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCon.setText("1");
		textFieldCon.setBounds(28, 39, 52, 32);
		panelBackground.add(textFieldCon);

		textFieldAgi = new JLabel();
		textFieldAgi.setText("1");
		textFieldAgi.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAgi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldAgi.setBounds(126, 39, 52, 32);
		panelBackground.add(textFieldAgi);

		textFieldRea = new JLabel();
		textFieldRea.setText("1");
		textFieldRea.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldRea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldRea.setBounds(232, 39, 52, 32);
		panelBackground.add(textFieldRea);

		textFieldFor = new JLabel();
		textFieldFor.setText("1");
		textFieldFor.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldFor.setBounds(332, 39, 52, 32);
		panelBackground.add(textFieldFor);

		textFieldCha = new JLabel();
		textFieldCha.setText("1");
		textFieldCha.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldCha.setBounds(429, 39, 52, 32);
		panelBackground.add(textFieldCha);

		textFieldInt = new JLabel();
		textFieldInt.setText("1");
		textFieldInt.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldInt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldInt.setBounds(528, 39, 52, 32);
		panelBackground.add(textFieldInt);

		textFieldLog = new JLabel();
		textFieldLog.setText("1");
		textFieldLog.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldLog.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldLog.setBounds(618, 39, 52, 32);
		panelBackground.add(textFieldLog);

		textFieldVol = new JLabel();
		textFieldVol.setText("1");
		textFieldVol.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldVol.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldVol.setBounds(28, 118, 52, 32);
		panelBackground.add(textFieldVol);

		textFieldChance = new JLabel();
		textFieldChance.setText("1");
		textFieldChance.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldChance.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldChance.setBounds(126, 118, 52, 32);
		panelBackground.add(textFieldChance);

		textFieldMagie = new JLabel();
		textFieldMagie.setText("1");
		textFieldMagie.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMagie.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldMagie.setBounds(227, 118, 52, 32);
		panelBackground.add(textFieldMagie);

		textFieldEss = new JLabel();
		textFieldEss.setText("1");
		textFieldEss.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEss.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldEss.setBounds(332, 118, 52, 32);
		panelBackground.add(textFieldEss);

		textFieldInit = new JLabel();
		textFieldInit.setText("1");
		textFieldInit.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldInit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldInit.setBounds(429, 118, 52, 32);
		panelBackground.add(textFieldInit);

		textFieldPI = new JLabel();
		textFieldPI.setText("1");
		textFieldPI.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPI.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

	public void setCharacter(ShadowrunCharacter c) {
		textFieldCon.setText("" + c.getBody());
		textFieldAgi.setText("" + c.getAgility());
		textFieldRea.setText("" + c.getReaction());
		textFieldFor.setText("" + c.getStrength());
		textFieldCha.setText("" + c.getCharisma());
		textFieldInt.setText("" + c.getIntuition());
		textFieldLog.setText("" + c.getLogic());
		textFieldVol.setText("" + c.getWillpower());
		textFieldChance.setText("" + c.getEdge());
		textFieldMagie.setText("" + c.getMagicResonnace());
		textFieldEss.setText("" + c.getEssence());
		textFieldInit.setText("" + c.getInitiative());
		textFieldPI.setText("" + c.getInitPasses());
	}
}
