package net.alteiar.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.JAXBException;

import net.alteiar.CharacterIO;
import net.alteiar.ExceptionTool;
import net.alteiar.pcgen.PathfinderCharacter;

public class FrameCharacterBuilder extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final CloseTabbedPane tabbedPane;
	private final JFileChooser chooser;

	public FrameCharacterBuilder() {
		setTitle("CharacterBuilder");
		setDefaultCloseOperation(3);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);

		JMenuItem mntmNouveau = new JMenuItem("Nouveau");
		mntmNouveau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameCharacterBuilder.this.newCharacter();
			}
		});
		mntmNouveau.setAccelerator(KeyStroke.getKeyStroke(78, 2));
		mnFichier.add(mntmNouveau);

		JMenuItem mntmOuvrir = new JMenuItem("Ouvrir");
		mntmOuvrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameCharacterBuilder.this.openCharacter();
			}
		});
		mntmOuvrir.setAccelerator(KeyStroke.getKeyStroke(79, 2));
		mnFichier.add(mntmOuvrir);

		mnFichier.addSeparator();

		JMenuItem mntmSauver = new JMenuItem("Sauver");
		mntmSauver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameCharacterBuilder.this.saveCharacter();
			}
		});
		mntmSauver.setAccelerator(KeyStroke.getKeyStroke(83, 2));
		mnFichier.add(mntmSauver);

		JMenuItem mntmSauverSous = new JMenuItem("Sauver sous...");
		mntmSauverSous.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameCharacterBuilder.this.saveCharacterAs();
			}
		});
		mnFichier.add(mntmSauverSous);

		mnFichier.addSeparator();

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameCharacterBuilder.this.exit();
			}
		});
		mntmQuitter.setAccelerator(KeyStroke.getKeyStroke(81, 2));
		mnFichier.add(mntmQuitter);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));

		this.tabbedPane = new CloseTabbedPane();
		this.contentPane.add(this.tabbedPane, "Center");

		this.chooser = new JFileChooser(".");
		this.chooser.setFileFilter(new CharacterFileFilter());
	}

	protected void newCharacter() {
		this.tabbedPane.addTab("new", new PanelCharacter());
	}

	protected void openCharacter() {
		if (this.chooser.showOpenDialog(this) == 0) {
			try {
				File file = this.chooser.getSelectedFile();
				PanelCharacter panel = new PanelCharacter();
				PathfinderCharacter character = CharacterIO.readFile(file);

				panel.setCharacter(character);
				String title = "new";
				if (!file.getName().endsWith(
						CharacterFileFilter.PCGEN_EXTENSION)) {
					panel.setFile(file);
					title = file.getName();
				}
				this.tabbedPane.addTab(title, panel);
			} catch (FileNotFoundException e) {
				ExceptionTool.showError(e,
						"il y a eu un problème pour ouvrir le personnage");
			} catch (JAXBException e) {
				ExceptionTool.showError(e,
						"il y a eu un problème pour ouvrir le personnage");
			}
		}
	}

	protected void saveCharacter() {
		PanelCharacter character = (PanelCharacter) this.tabbedPane
				.getSelectedComponent();

		if (character != null) {
			File f = character.getFile();
			if (f != null) {
				try {
					CharacterIO.writeFile(f, character.getCharacter());
				} catch (JAXBException e) {
					ExceptionTool
							.showError(e,
									"il y a eu un problème lors de la sauvegarde du personnage");
				} catch (IOException e) {
					ExceptionTool
							.showError(e,
									"il y a eu un problème lors de la sauvegarde du personnage");
				}
			} else {
				saveCharacterAs();
			}
		} else {
			JOptionPane.showConfirmDialog(this,
					"Un personnage doit être ouvert", "Information", -1, 1);
		}
	}

	protected void saveCharacterAs() {
		PanelCharacter panelCharacter = (PanelCharacter) this.tabbedPane
				.getSelectedComponent();

		if (this.chooser.showSaveDialog(this) == 0)
			try {
				File file = this.chooser.getSelectedFile();

				if (!file.getName().endsWith(".psr")) {
					file = new File(file.getAbsolutePath() + ".psr");
				}
				if (!file.createNewFile()) {
					throw new IOException("Impossible de créer le fichier");
				}

				panelCharacter.setFile(file);
				this.tabbedPane.setTitleAt(this.tabbedPane.getSelectedIndex(),
						file.getName());

				saveCharacter();
			} catch (IOException e) {
				ExceptionTool.showError(e,
						"il y a eu un problème lors de la création du fichier");
			}
	}

	protected void exit() {
		Boolean quit = Boolean.valueOf(true);
		Boolean isSaved = Boolean.valueOf(true);
		for (int i = 0; i < this.tabbedPane.getTabCount(); i++) {
			PanelCharacter character = (PanelCharacter) this.tabbedPane
					.getComponentAt(i);

			isSaved = Boolean.valueOf(character.getFile() != null);
		}
		if (!isSaved.booleanValue()) {
			int retVal = JOptionPane
					.showConfirmDialog(
							this,
							"Des personnages sont non sauvegarder \n êtes vous sur de vouloir quitter ?",
							"Information", 0, 1);

			quit = Boolean.valueOf(retVal == 0);
		}

		if (quit.booleanValue())
			System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		exit();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}