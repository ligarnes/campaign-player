/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.client.shared.campaign.battle.character;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.character.CharacterSheetClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.observer.campaign.battle.character.CharacterCombatObservableClient;
import net.alteiar.client.shared.observer.campaign.character.ICharacterClientObserver;
import net.alteiar.server.shared.campaign.battle.ICharacterCombatRemote;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSize;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSizeSquare;
import net.alteiar.server.shared.observer.campaign.battle.ICharacterCombatObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterCombatClient extends CharacterCombatObservableClient
		implements ICharacterCombatClient {
	private static final long serialVersionUID = 1L;

	private final IBattleClient map;

	private ICharacterSheetClient characterSheet;
	private int init;
	private Boolean isVisibleForPlayer;

	//
	private MapElementSize width;
	private MapElementSize height;

	protected Point localPosition;
	protected Double localAngle;

	/**
	 * Local information to know if we need to highlight the character or not
	 */
	private Boolean isHighlight;

	public CharacterCombatClient(IBattleClient map,
			ICharacterCombatRemote character) {
		super(character);
		this.map = map;

		isHighlight = false;

		try {
			Long guid = this.remoteObject.getCharacterSheetGuid();

			characterSheet = CampaignClient.INSTANCE.getCharacter(guid);
			// This should happen for monster because we create them each time
			if (characterSheet == null) {
				characterSheet = new CharacterSheetClient(
						this.remoteObject.getCharacterSheet());
			}

			isVisibleForPlayer = this.remoteObject.isVisibleForPlayer();
			init = this.remoteObject.getInitiative();

			localPosition = remoteObject.getPosition();
			localAngle = remoteObject.getAngle();

			width = new MapElementSizeSquare(characterSheet.getWidth());
			height = new MapElementSizeSquare(characterSheet.getHeight());

			characterSheet.addCharacterListener(new ICharacterClientObserver() {
				@Override
				public void imageLoaded(ICharacterSheetClient character) {
					notifyCharacterChange();
				}

				@Override
				public void characterChanged(ICharacterSheetClient character) {
					notifyCharacterChange();
				}
			});
			new CharacterCombatClientRemoteObserver(remoteObject);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Boolean isVisibleForPlayer() {
		return isVisibleForPlayer;
	}

	@Override
	public void setVisibleForAllPlayer(Boolean isVisible) {
		try {
			this.remoteObject.setVisibleForPlayer(isVisible);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Boolean isHighlighted() {
		return isHighlight;
	}

	@Override
	public void setHighlighted(Boolean isHighlight) {
		if (!this.isHighlight.equals(isHighlight)) {
			this.isHighlight = isHighlight;
			this.notifyHighLightChange(this.isHighlight);
		}
	}

	/**
	 * 
	 * @param init
	 *            - the result get by the dice all modifier wil be added
	 */
	@Override
	public void setInit(Integer init) {
		try {
			remoteObject.setInitiative(init);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	/**
	 * 
	 * @return final initiative result with all modifier applyed
	 */
	@Override
	public Integer getInitiative() {
		return init;
	}

	@Override
	public ICharacterSheetClient getCharacter() {
		return characterSheet;
	}

	@Override
	public void doDamage(Integer degat) {
		getCharacter().setCurrentHp(getCharacter().getCurrentHp() - degat);
	}

	@Override
	public void doHeal(Integer heal) {
		doDamage(-heal);
	}

	private BufferedImage getRealImage() {
		return getCharacter().getBackground();
	}

	@Override
	public BufferedImage getShape(double zoomFactor) {
		int heightLife = 10;

		Double width = this.width.getPixels(map.getScale()) * zoomFactor;
		Double height = this.height.getPixels(map.getScale()) * zoomFactor
				+ heightLife;

		int xLife = 0;
		int yLife = height.intValue() - heightLife;
		int widthLife = width.intValue();

		BufferedImage img = new BufferedImage(width.intValue(),
				height.intValue(), BufferedImage.TYPE_INT_ARGB);

		BufferedImage localImg = getRealImage();
		Graphics2D g2 = img.createGraphics();
		if (!isVisibleForPlayer()) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.6f));
		}

		g2.drawImage(localImg, 0, 0, width.intValue(), height.intValue()
				- heightLife, null);

		// Draw life
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.6f));
		Integer currentHp = getCharacter().getCurrentHp();
		Integer totalHp = getCharacter().getTotalHp();

		Float ratio = Math.min(1.0f, currentHp / (float) totalHp);

		if (currentHp > 0) {
			Color hp = new Color(1.0f - ratio, ratio, 0);
			g2.setColor(hp);
			g2.fillRect(xLife, yLife, (int) (widthLife * ratio), heightLife);
			g2.setColor(Color.BLACK);

			g2.setColor(Color.BLACK);
			g2.drawRect(xLife, yLife, widthLife - 1, heightLife - 1);
		}

		// Highlight
		if (isHighlighted()) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1f));
			g2.setColor(Color.BLUE);
			Stroke org = g2.getStroke();
			g2.setStroke(new BasicStroke(8));
			g2.drawRect(0, 0, width.intValue(), height.intValue());
			g2.setStroke(org);
		}

		g2.dispose();

		return img;
	}

	@Override
	public Integer getWidth() {
		return width.getPixels(map.getScale()).intValue();
	}

	@Override
	public Integer getHeight() {
		return height.getPixels(map.getScale()).intValue();
	}

	@Override
	public void applyPosition() {
		try {
			this.remoteObject.setPosition(localPosition);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Point getPosition() {
		return this.localPosition;
	}

	@Override
	public Point getCenterPosition() {
		return this.localPosition;
	}

	@Override
	public Point getCenterOffset() {
		return new Point(0, 0);
	}

	@Override
	public void setPosition(Point position) {
		this.localPosition = position;
	}

	@Override
	public void revertPosition() {
		try {
			this.localPosition = remoteObject.getPosition();
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Double getAngle() {
		return this.localAngle;
	}

	@Override
	public void applyRotate() {
		try {
			this.remoteObject.setAngle(localAngle);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public void setAngle(Double angle) {
		localAngle = angle;
	}

	@Override
	public Boolean isInside(Point point) {
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(localPosition.x, localPosition.y);
		Point p = getCenterOffset();
		affineTransform.rotate(Math.toRadians(getAngle()), p.x, p.y);

		Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
		Shape s = affineTransform.createTransformedShape(rect);

		return s.contains(point);
	}

	/**
	 * this class should be observer and will use the notify of the Map2DClient
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class CharacterCombatClientRemoteObserver extends
			UnicastRemoteObject implements ICharacterCombatObserverRemote {

		private static final long serialVersionUID = 2559145398149500009L;

		/**
		 * @throws RemoteException
		 */
		protected CharacterCombatClientRemoteObserver(
				ICharacterCombatRemote character) throws RemoteException {
			super();
			character.addCharacterCombatListener(this);
		}

		@Override
		public void initiativeChanged(Integer initi) throws RemoteException {
			init = initi;
			notifyInitiativeChange();
		}

		@Override
		public void visibilityChanged(Boolean visibility)
				throws RemoteException {
			isVisibleForPlayer = visibility;
			notifyVisibilityChange();
		}

		@Override
		public void elementMoved(Point newPosition) throws RemoteException {
			localPosition = newPosition;
			notifyCharacterMovedChange();
		}

		@Override
		public void elementRotate(Double angle) throws RemoteException {
			localAngle = angle;
			notifyRotationChange();
		}
	}
}
