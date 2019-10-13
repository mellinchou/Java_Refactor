package client;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {
	public static void playMusic() {
		try {
			AudioInputStream musicFile = AudioSystem.getAudioInputStream(new File(".\\javamusic.wav"));
			Clip music = AudioSystem.getClip();
			music.open(musicFile);
			music.start();
			music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("Exception 1 from MusicPlayer " + e);
			System.exit(0);
		}
	}
	public static void playButtonClickSound() {
		try {
			AudioInputStream musicFile = AudioSystem.getAudioInputStream(new File(".\\Button_click_sound.wav"));
			Clip music = AudioSystem.getClip();
			music.open(musicFile);
			music.start();
		} catch (Exception e) {
			System.out.println("Exception 2 from MusicPlayer " + e);
			System.exit(0);
		}
	}
}
