package net.alteiar.campaign.player.gui.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JPanel;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.shared.ExceptionTool;

public class DynamicPanelBeanBuilder {
	private final HashMap<Class<? extends BasicBeans>, Class<? extends JPanel>> classesObject;

	public DynamicPanelBeanBuilder() {
		classesObject = new HashMap<Class<? extends BasicBeans>, Class<? extends JPanel>>();
	}

	public void add(Class<? extends BasicBeans> classes,
			Class<? extends JPanel> panel) {
		classesObject.put(classes, panel);
	}

	public <I extends BasicBeans> JPanel getPanel(I bean) {
		Class<? extends JPanel> classes = classesObject.get(bean.getClass());
		if (classes != null) {
			try {
				return classes.getConstructor(bean.getClass())
						.newInstance(bean);
			} catch (InstantiationException e) {
				ExceptionTool.showError(e);
			} catch (IllegalAccessException e) {
				ExceptionTool.showError(e);
			} catch (IllegalArgumentException e) {
				ExceptionTool.showError(e);
			} catch (InvocationTargetException e) {
				ExceptionTool.showError(e);
			} catch (NoSuchMethodException e) {
				ExceptionTool.showError(e);
			} catch (SecurityException e) {
				ExceptionTool.showError(e);
			}
		}
		return null;
	}
}
