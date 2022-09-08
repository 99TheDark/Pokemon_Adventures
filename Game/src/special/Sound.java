package special;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import data.Settings;
import main.GamePanel;

public class Sound {

	Clip clip;
	GamePanel gp;
	Settings settings;
	URL soundURL[] = new URL[10]; // Change based on amount of sounds

	public Sound(GamePanel gp) {

		this.gp = gp;
		this.settings = gp.settings;

		soundURL[0] = getClass().getResource("/sound/bgm/littleroot.wav");
		soundURL[1] = getClass().getResource("/sound/sfx/select.wav");

	}

	public void setFile(int index) {

		try {

			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
			clip = AudioSystem.getClip();
			clip.open(ais);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void play() {

		if (!settings.mute) {

			clip.start();

		}

	}

	public void stop() {

		clip.stop();

	}

	public void loop() {

		if (!settings.mute) {

			clip.loop(Clip.LOOP_CONTINUOUSLY);

		}

	}

}
