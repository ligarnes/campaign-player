package net.alteiar.campaign.player.gui.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JPanel;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.ExceptionTool;

public class DynamicPanelBeanBuilder {
	private final HashMap<Class<? extends BasicBean>, Class<? extends JPanel>> classesObject;

	public DynamicPanelBeanBuilder() {
		classesObject = new HashMap<Class<? extends BasicBean>, Class<? extends JPanel>>();
	}

	public void add(Class<? extends BasicBean> classes,
			Class<? extends JPanel> panel) {
		classesObject.put(classes, panel);
	}

	public <I extends BasicBean> JPanel getPanel(I bean) {
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
