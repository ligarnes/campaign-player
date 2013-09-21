package generic.gui.document.media.audio;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.beans.media.AudioBean;

public class PanelMp3Player extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final JButton btnPlay;
	private final JButton btnStop;
	private final JButton btnPause;

	private AudioBean audio;

	public PanelMp3Player() {
		this(null);
	}

	public PanelMp3Player(AudioBean audio) {
		this.audio = audio;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		btnPlay = new JButton("Play");
		btnPlay.setEnabled(false);
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				play();
			}
		});
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 0;
		add(btnPlay, gbc_btnPlay);

		btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});

		btnPause = new JButton("Pause");
		btnPause.setEnabled(false);

		// TODO fixme btn pause is not visible because the functionnality is not
		// working for the moment
		this.btnPause.setVisible(false);
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.insets = new Insets(0, 0, 0, 5);
		gbc_btnPause.gridx = 1;
		gbc_btnPause.gridy = 0;
		add(btnPause, gbc_btnPause);
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.gridx = 2;
		gbc_btnStop.gridy = 0;
		add(btnStop, gbc_btnStop);
	}

	public void setAudioBean(AudioBean audio) {
		if (this.audio != null) {
			this.audio.stop();
			this.audio.removePropertyChangeListener(this);
		}
		this.audio = audio;

		if (this.audio != null) {
			this.audio.addPropertyChangeListener(this);
			this.btnPlay.setEnabled(true);
		}
	}

	public void play() {
		this.audio.play();
	}

	public void pause() {
		this.audio.pause();
	}

	public void stop() {
		this.audio.stop();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (AudioBean.METH_PAUSE_METHOD.equals(evt.getPropertyName())) {
			this.btnPlay.setEnabled(true);
			this.btnStop.setEnabled(false);
			this.btnPause.setEnabled(false);
		} else if (AudioBean.METH_PLAY_METHOD.equals(evt.getPropertyName())) {
			this.btnPlay.setEnabled(false);
			this.btnStop.setEnabled(true);
			this.btnPause.setEnabled(true);
		}
	}
}
