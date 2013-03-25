package net.alteiar.campaign.player.gui.map.element;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.PanelOkCancel;
import net.alteiar.map.Map;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.elements.MapElementFactory;

public class PanelCreateMapElement extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private static PanelCreateMapElement mapElement = new PanelCreateMapElement();

	public static void createMapElement(Map map, MapEvent event) {
		DialogOkCancel<PanelCreateMapElement> dlg = new DialogOkCancel<PanelCreateMapElement>(
				null, "Créer un element", true, mapElement);

		dlg.getMainPanel().refreshElements();
		dlg.setOkText("Créer");
		dlg.setCancelText("Annuler");
		dlg.setLocation(event.getMouseEvent().getLocationOnScreen());
		dlg.pack();
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			MapElement element = dlg.getMainPanel().buildElement(
					event.getMapPosition());
			MapElementFactory.buildMapElement(element, map);
		}
	}

	private static ClassLoader loader;

	private static ClassLoader getClassLoader() {
		if (loader == null) {
			try {
				URL pluginJar = new File(
						"./ressources/plugin/Pathfinder-system-1.0-SNAPSHOT.jar")
						.toURI().toURL();
				loader = URLClassLoader.newInstance(new URL[] { pluginJar },
						PanelCreateMapElement.class.getClassLoader());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return loader;
	}

	public static ArrayList<PanelMapElementBuilder> getBuilders() {
		ArrayList<PanelMapElementBuilder> builders = new ArrayList<PanelMapElementBuilder>();
		try {

			// pluginName.gui.mapElement.builder.PanelBuilder
			Class<?> clazz = Class.forName(
					"pathfinder.gui.mapElement.builder.PanelBuilder", true,
					getClassLoader());
			Class<? extends IPanelBuilders> runClass = clazz
					.asSubclass(IPanelBuilders.class);
			// Avoid Class.newInstance, for it is evil.
			Constructor<? extends IPanelBuilders> ctor = runClass
					.getConstructor();
			IPanelBuilders doRun = ctor.newInstance();
			builders = doRun.getBuilders();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builders;
	}

	private final JPanel panelWest;
	private final JPanel panelCenter;
	private PanelMapElementBuilder builder;

	public PanelCreateMapElement() {
		super(new BorderLayout());

		panelWest = new JPanel(new GridLayout(getBuilders().size(), 1));

		this.add(panelWest, BorderLayout.WEST);

		builder = getBuilders().get(0);
		panelCenter = new JPanel();
		panelCenter.add(builder);
		this.add(panelCenter, BorderLayout.CENTER);
	}

	private void refreshElements() {
		panelWest.removeAll();
		int maxWidth = 50;
		int maxHeight = 50;
		for (final PanelMapElementBuilder panel : getBuilders()) {
			Dimension dim = panel.getPreferredSize();

			maxWidth = Math.max(maxWidth, dim.width);
			maxHeight = Math.max(maxHeight, dim.height);

			JButton select = new JButton(panel.getElementName());
			select.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectBuilder(panel);
				}
			});
			select.setEnabled(panel.isAvailable());
			panelWest.add(select);
		}

		this.setPreferredSize(new Dimension(maxWidth, maxHeight));
	}

	private void selectBuilder(PanelMapElementBuilder builder) {
		panelCenter.removeAll();
		this.builder = builder;
		panelCenter.add(this.builder);
		this.revalidate();
		this.repaint();
	}

	public MapElement buildElement(Point position) {
		return builder.buildMapElement(position);
	}

	@Override
	public Boolean isDataValid() {
		return true;
	}

	@Override
	public String getInvalidMessage() {
		return "";
	}
}
