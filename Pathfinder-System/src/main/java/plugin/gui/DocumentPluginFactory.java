package plugin.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import pathfinder.DocumentTypeConstant;
import pathfinder.gui.document.builder.PanelCreateBattle;
import pathfinder.gui.document.builder.PanelCreateImage;
import pathfinder.gui.document.builder.character.PanelCreateCharacter;
import pathfinder.gui.document.builder.spell.PanelCreateSpellBook;
import pathfinder.gui.document.viewer.PanelViewImage;
import plugin.gui.imageIcon.CharacterImageIconFactory;
import plugin.gui.imageIcon.ImageIconFactory;
import plugin.gui.imageIcon.NullImageIconFactory;
import plugin.gui.imageIcon.SimpleImageIconFactory;

public class DocumentPluginFactory {
	private static String MAP_ICON = "/icons/map.png";

	public static DocumentPlugin buildBattleMapDocumentPlugin()
			throws IOException {

		BufferedImage mapIcon = ImageIO.read(PathfinderGeneralPlugin.class
				.getResource(MAP_ICON));

		ImageIconFactory icon = new SimpleImageIconFactory(mapIcon);
		DocumentPlugin doc = new DocumentPlugin(
				DocumentTypeConstant.BATTLE_MAP, icon, new PanelCreateBattle(),
				null);

		return doc;
	}

	public static DocumentPlugin buildImageDocumentPlugin() throws IOException {
		DocumentPlugin doc = new DocumentPlugin(DocumentTypeConstant.IMAGE,
				new NullImageIconFactory(), new PanelCreateImage(),
				new PanelViewImage());
		return doc;
	}

	public static DocumentPlugin buildCharacterDocumentPlugin()
			throws IOException {
		DocumentPlugin doc = new DocumentPlugin(DocumentTypeConstant.CHARACTER,
				new CharacterImageIconFactory(), new PanelCreateCharacter(),
				null);
		return doc;
	}

	public static DocumentPlugin buildSpellBookDocumentPlugin()
			throws IOException {
		DocumentPlugin doc = new DocumentPlugin(
				DocumentTypeConstant.SPELL_BOOK, new NullImageIconFactory(),
				new PanelCreateSpellBook(), null);
		return doc;
	}

}
