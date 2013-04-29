package pathfinder.effect;

import net.alteiar.CampaignClient;
import net.alteiar.map.elements.MapElement;

public class TrapBuilder {

	public static void buildFireTrap(MapElement element) {
		FireTrapEffect effect = new FireTrapEffect();
		CampaignClient.getInstance().addBean(effect);

		TriggerCharacter trigger = new TriggerCharacter(effect.getId(), element);
		CampaignClient.getInstance().addBean(trigger);
	}

	public static void buildVision(MapElement element) {
		VisionEffect effect = new VisionEffect(element.getMapId(),
				element.getId());
		CampaignClient.getInstance().addBean(effect);

		TriggerCharacter trigger = new TriggerCharacter(effect.getId(), element);
		CampaignClient.getInstance().addBean(trigger);
	}
}
