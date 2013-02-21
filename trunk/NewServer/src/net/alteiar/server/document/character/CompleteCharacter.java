package net.alteiar.server.document.character;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import net.alteiar.shared.SerializableImage;
import net.alteiar.shared.TransfertImage;

public class CompleteCharacter {

	private PathfinderCharacter character;
	private TransfertImage img;

	public CompleteCharacter(File character) throws FileNotFoundException,
			JAXBException {
		parse(character);
	}

	public CompleteCharacter(File character, TransfertImage transfert)
			throws FileNotFoundException, JAXBException {
		parse(character);
		setImage(transfert);
	}

	public void setImage(TransfertImage img) {
		this.img = img;
	}

	protected void parse(File character) throws FileNotFoundException,
			JAXBException {
		this.character = CharacterIO.readFile(character);
		this.img = new SerializableImage(this.character.getImage());
	}

	public IPathfinderCharacter getCharacter() {
		return character;
	}

	public TransfertImage getImage() {
		return img;
	}
}
