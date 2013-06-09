package net.alteiar.zoom;

import java.util.EventListener;

public interface ZoomListener extends EventListener {
	void zoomChanged(Double zoomFactor);
}
