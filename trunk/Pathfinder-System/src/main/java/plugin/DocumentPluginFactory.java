package plugin;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.alteiar.campaign.player.plugin.external.DocumentPlugin;
import net.alteiar.campaign.player.plugin.imageIcon.ImageIconFactory;
import net.alteiar.campaign.player.plugin.imageIcon.NullImageIconFactory;
import net.alteiar.campaign.player.plugin.imageIcon.SimpleImageIconFactory;
import pathfinder.DocumentTypeConstant;
import pathfinder.gui.document.battle.PanelCreateBattle;
import pathfinder.gui.document.character.PanelCreateCharacter;
import pathfinder.gui.document.image.PanelCreateImage;
import pathfinder.gui.document.image.PanelViewImage;
import pathfinder.gui.document.notepad.PanelCreateNote;
import pathfinder.gui.document.notepad.PanelViewNote;
import pathfinder.gui.document.spell.PanelCreateSpellBook;
import pathfinder.gui.document.spell.PanelSpellBookViewer;
import plugin.imageIcon.CharacterImageIconFactory;

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
				new PanelCreateSpellBook(), new PanelSpellBookViewer());
		return doc;
	}

	public static DocumentPlugin buildNoteDocumentPlugin() throws IOException {
		DocumentPlugin doc = new DocumentPlugin(DocumentTypeConstant.NOTE,
				new NullImageIconFactory(), new PanelCreateNote(),
				new PanelViewNote());
		return doc;
	}
}
