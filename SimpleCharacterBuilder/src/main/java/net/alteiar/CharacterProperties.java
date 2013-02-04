package net.alteiar;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JFileChooser;

/**
 * 
 * @author Cody Stoutenburg
 * 
 */

public class CharacterProperties implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String KEY_PC_GEN_PATH = "pcgenPath";
	private static final String KEY_PC_GEN_STYLE_XML = "pcgenStyle";

	private final PropertieBase property;

	public CharacterProperties() {
		super();
		property = new PropertieBase("./data/CharacterProperties.prop");
		try {
			property.load();
		} catch (IOException ex) {
			ExceptionTool.showError(ex);
		}
	}

	public void save() throws IOException {
		property.save();
	}

	public String getPcgenPath() {
		String value = property.getValue(KEY_PC_GEN_PATH, "");
		if (value.isEmpty()) {
			JFileChooser chooser = new JFileChooser(".");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			int retVal = chooser.showDialog(null, "Dossier de PCGen");
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File pcGenPath = chooser.getSelectedFile();
				if (pcGenPath.isDirectory()) {
					try {
						property.setValue(KEY_PC_GEN_PATH,
								pcGenPath.getCanonicalPath());
						value = pcGenPath.getCanonicalPath();
					} catch (IOException e) {
						ExceptionTool.showError(e);
					}
				}
			}

			try {
				this.save();
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		}
		return value;
	}

	public String getPcgenStyle() {
		String value = property.getValue(KEY_PC_GEN_STYLE_XML, "");
		if (value.isEmpty()) {
			JFileChooser chooser = new JFileChooser(getPcgenPath());
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			int retVal = chooser.showDialog(null,
					"Xml de generation de personnage");
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File pcGenPath = chooser.getSelectedFile();
				try {
					String path = pcGenPath.getCanonicalPath();
					path = path.substring(path.indexOf("outputsheets"));
					property.setValue(KEY_PC_GEN_STYLE_XML, path);
					value = path;
				} catch (IOException e) {
					ExceptionTool.showError(e);
				}
			}

			try {
				this.save();
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		}
		return value;
	}
}
