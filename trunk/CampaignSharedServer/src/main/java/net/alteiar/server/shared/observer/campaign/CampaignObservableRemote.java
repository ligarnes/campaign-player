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
package net.alteiar.server.shared.observer.campaign;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.campaign.notes.INoteRemote;
import net.alteiar.server.shared.campaign.player.IPlayerRemote;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CampaignObservableRemote extends BaseObservableRemote {
	private static final long serialVersionUID = -5548287991832173433L;

	private static final Class<?> LISTENER = ICampaignObserverRemote.class;

	public CampaignObservableRemote() throws RemoteException {
		super();
	}

	public void addCampaignListener(ICampaignObserverRemote listener)
			throws RemoteException {
		this.addListener(LISTENER, listener);
	}

	public void removeCampaignListener(ICampaignObserverRemote listener)
			throws RemoteException {
		this.removeListener(LISTENER, listener);
	}

	protected void notifyBattleAdded(IBattleRemote remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new BattleAddedTask(this,
					observer, remote));
		}
	}

	protected void notifyBattleRemoved(Long remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new BattleRemovedTask(
					this, observer, remote));
		}
	}

	protected void notifyPlayerAdded(IPlayerRemote remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new PlayerAddedTask(this,
					observer, remote));
		}
	}

	protected void notifyPlayerRemoved(Long remoteId) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new PlayerRemovedTask(
					this, observer, remoteId));
		}
	}

	protected void notifyNoteAdded(INoteRemote remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new NoteAddedTask(this,
					observer, remote));
		}
	}

	protected void notifyNoteRemoved(INoteRemote remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new NoteRemovedTask(this,
					observer, remote));
		}
	}

	protected void notifyCharacterAdded(ICharacterRemote remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new CharacterSheetAddedTask(this, observer, remote));
		}
	}

	protected void notifyCharacterRemoved(Long remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new CharacterSheetRemovedTask(this, observer,
							remote));
		}
	}

	protected void notifyMonsterAdded(ICharacterRemote remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MonsterAddedTask(
					this, observer, remote));
		}
	}

	protected void notifyMonsterRemoved(Long remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MonsterRemovedTask(
					this, observer, remote));
		}
	}

	private class BattleAddedTask extends BaseNotify {

		private final IBattleRemote added;

		public BattleAddedTask(CampaignObservableRemote observable,
				Remote observer, IBattleRemote added) {
			super(observable, LISTENER, observer);
			this.added = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).battleAdded(added);
		}

		@Override
		public String getStartText() {
			return "start notify battled added";
		}

		@Override
		public String getFinishText() {
			return "finish notify battle added";
		}
	}

	private class BattleRemovedTask extends BaseNotify {

		private final Long removedId;

		public BattleRemovedTask(CampaignObservableRemote observable,
				Remote observer, Long added) {
			super(observable, LISTENER, observer);
			this.removedId = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).battleRemoved(removedId);
		}

		@Override
		public String getStartText() {
			return "start notify battled player";
		}

		@Override
		public String getFinishText() {
			return "finish notify battle player";
		}
	}

	private class PlayerRemovedTask extends BaseNotify {

		private final Long removedId;

		public PlayerRemovedTask(CampaignObservableRemote observable,
				Remote observer, Long removed) {
			super(observable, LISTENER, observer);
			this.removedId = removed;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).playerRemoved(removedId);
		}

		@Override
		public String getStartText() {
			return "start notify player removed";
		}

		@Override
		public String getFinishText() {
			return "finish notify player removed";
		}
	}

	private class PlayerAddedTask extends BaseNotify {

		private final IPlayerRemote player;

		public PlayerAddedTask(CampaignObservableRemote observable,
				Remote observer, IPlayerRemote added) {
			super(observable, LISTENER, observer);
			this.player = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).playerAdded(player);
		}

		@Override
		public String getStartText() {
			return "start notify player added";
		}

		@Override
		public String getFinishText() {
			return "finish notify player added";
		}
	}

	private class NoteAddedTask extends BaseNotify {

		private final INoteRemote note;

		public NoteAddedTask(CampaignObservableRemote observable,
				Remote observer, INoteRemote added) {
			super(observable, LISTENER, observer);
			this.note = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).noteAdded(note);
		}

		@Override
		public String getStartText() {
			return "start notify note added";
		}

		@Override
		public String getFinishText() {
			return "finish notify note added";
		}
	}

	private class NoteRemovedTask extends BaseNotify {

		private final INoteRemote note;

		public NoteRemovedTask(CampaignObservableRemote observable,
				Remote observer, INoteRemote added) {
			super(observable, LISTENER, observer);
			this.note = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).noteRemoved(note);
		}

		@Override
		public String getStartText() {
			return "start notify note removed";
		}

		@Override
		public String getFinishText() {
			return "finish notify note removed";
		}
	}

	private class CharacterSheetAddedTask extends BaseNotify {

		private final ICharacterRemote characterSheet;

		public CharacterSheetAddedTask(CampaignObservableRemote observable,
				Remote observer, ICharacterRemote added) {
			super(observable, LISTENER, observer);
			this.characterSheet = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).characterAdded(characterSheet);
		}

		@Override
		public String getStartText() {
			return "start notify character added";
		}

		@Override
		public String getFinishText() {
			return "finish notify character added";
		}
	}

	private class CharacterSheetRemovedTask extends BaseNotify {

		private final Long characterSheet;

		public CharacterSheetRemovedTask(CampaignObservableRemote observable,
				Remote observer, Long added) {
			super(observable, LISTENER, observer);
			this.characterSheet = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer)
					.characterRemoved(characterSheet);
		}

		@Override
		public String getStartText() {
			return "start notify character removed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character removed";
		}
	}

	private class MonsterAddedTask extends BaseNotify {

		private final ICharacterRemote characterSheet;

		public MonsterAddedTask(CampaignObservableRemote observable,
				Remote observer, ICharacterRemote added) {
			super(observable, LISTENER, observer);
			this.characterSheet = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).monsterAdded(characterSheet);
		}

		@Override
		public String getStartText() {
			return "start notify monster added";
		}

		@Override
		public String getFinishText() {
			return "finish notify monster added";
		}
	}

	private class MonsterRemovedTask extends BaseNotify {

		private final Long characterSheet;

		public MonsterRemovedTask(CampaignObservableRemote observable,
				Remote observer, Long added) {
			super(observable, LISTENER, observer);
			this.characterSheet = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICampaignObserverRemote) observer).monsterRemoved(characterSheet);
		}

		@Override
		public String getStartText() {
			return "start notify monster removed";
		}

		@Override
		public String getFinishText() {
			return "finish notify monster removed";
		}
	}

}
