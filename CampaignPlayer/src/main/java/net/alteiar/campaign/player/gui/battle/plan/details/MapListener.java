package net.alteiar.campaign.player.gui.battle.plan.details;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.EventListener;

public interface MapListener extends EventListener {

	void mouseClicked(MapEvent element);

	void mousePressed(MapEvent element);

	void mouseReleased(MapEvent element);

	void mouseElementEntered(MapEvent element);

	void mouseElementExited(MapEvent element);

	void mouseDragged(MouseEvent e, Point mapPosition);

	void mouseMove(MouseEvent e, Point mapPosition);

	void mouseWheelMoved(MouseWheelEvent event, Point mapPosition);
}
