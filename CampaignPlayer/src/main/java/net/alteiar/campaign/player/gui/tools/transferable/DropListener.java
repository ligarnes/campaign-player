package net.alteiar.campaign.player.gui.tools.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import net.alteiar.campaign.player.gui.tools.adapter.BasicAdapter;

public class DropListener implements DropTargetListener {

	private final ListSource dest;

	public DropListener(ListSource dest) {
		this.dest = dest;
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// Get the type of object being transferred and determine
		// whether it is appropriate.
		checkTransferType(dtde);

		// Accept or reject the drag.
		acceptOrRejectDrag(dtde);
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// Accept or reject the drag
		acceptOrRejectDrag(dtde);
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// Accept or reject the drag
		acceptOrRejectDrag(dtde);
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		// Check the drop action
		if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
			// Accept the drop and get the transfer data
			dtde.acceptDrop(dtde.getDropAction());
			Transferable transferable = dtde.getTransferable();

			try {
				boolean result = dropComponent(transferable);

				dtde.dropComplete(result);
			} catch (Exception e) {
				dtde.dropComplete(false);
			}
		} else {
			dtde.rejectDrop();
		}
	}

	protected Boolean checkTransferType(DropTargetDragEvent dtde) {
		// Only accept a flavor that returns a Component
		boolean acceptableType = false;
		DataFlavor[] fl = dtde.getCurrentDataFlavors();

		for (DataFlavor dataFlavor : fl) {
			if (dataFlavor instanceof MyDataFlavor) {
				acceptableType = true;
				break;
			}
		}

		return acceptableType;
	}

	protected boolean dropComponent(Transferable transferable)
			throws IOException, UnsupportedFlavorException {

		MyDataFlavor flavor = null;
		for (DataFlavor dataFlavor : transferable.getTransferDataFlavors()) {
			if (dataFlavor instanceof MyDataFlavor) {
				flavor = (MyDataFlavor) dataFlavor;
			}
		}
		if (flavor != null) {
			BasicAdapter<?> o = (BasicAdapter<?>) transferable
					.getTransferData(flavor);
			dest.addElement(o);
			return true;
		}
		return false;
	}

	protected boolean acceptOrRejectDrag(DropTargetDragEvent dtde) {
		int dropAction = dtde.getDropAction();
		int sourceActions = dtde.getSourceActions();
		boolean acceptedDrag = false;

		// Reject if the object being transferred
		// or the operations available are not acceptable.
		if (!checkTransferType(dtde)
				|| (sourceActions & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
			dtde.rejectDrag();
		} else if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
			// Not offering copy or move - suggest a copy
			dtde.acceptDrag(DnDConstants.ACTION_COPY);
			acceptedDrag = true;
		} else {
			// Offering an acceptable operation: accept
			dtde.acceptDrag(dropAction);
			acceptedDrag = true;
		}

		return acceptedDrag;
	}

}
