package plugin;

import generic.DocumentTypeConstant;
import generic.gui.document.battle.PanelCreateBattle;
import generic.gui.document.media.audio.PanelCreateAudio;
import generic.gui.document.media.audio.PanelViewAudio;
import generic.gui.document.media.image.PanelCreateImage;
import generic.gui.document.media.image.PanelViewImage;
import generic.gui.document.notepad.PanelCreateNote;
import generic.gui.document.notepad.PanelViewNote;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.alteiar.campaign.player.plugin.external.DocumentPlugin;
import net.alteiar.campaign.player.plugin.imageIcon.ImageIconFactory;
import net.alteiar.campaign.player.plugin.imageIcon.NullImageIconFactory;
import net.alteiar.campaign.player.plugin.imageIcon.SimpleImageIconFactory;
import plugin.imageIcon.CharacterImageIconFactory;
import shadowrun.gui.document.character.PanelCreateCharacter;
import shadowrun.gui.document.character.PanelViewCharacter;

public class DocumentPluginFactory {
	private static String MAP_ICON = "/icons/map.png";

	public static DocumentPlugin buildBattleMapDocumentPlugin()
			throws IOException {

		BufferedImage mapIcon = ImageIO.read(ShadowrunGeneralPlugin.class
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
				new PanelViewCharacter());
		return doc;
	}

	public static DocumentPlugin buildNoteDocumentPlugin() throws IOException {
		DocumentPlugin doc = new DocumentPlugin(DocumentTypeConstant.NOTE,
				new NullImageIconFactory(), new PanelCreateNote(),
				new PanelViewNote());
		return doc;
	}

	public static DocumentPlugin buildAudioDocumentPlugin() throws IOException {
		DocumentPlugin doc = new DocumentPlugin(DocumentTypeConstant.AUDIO,
				new NullImageIconFactory(), new PanelCreateAudio(),
				new PanelViewAudio());
		return doc;
	}
}
