package ph.edu.dlsu.utils;

import javafx.scene.media.AudioClip;

public class Sound {

    private AudioClip menuClip;

    public Sound(String path) {                                     //A class to be called whenever the video needs to play music
        try {
            menuClip = new AudioClip(path);
        } catch (Exception e) {
            System.out.println("Failed to load audio clip!");
        }
    }

    public void play() {
        menuClip.play();
    }
}
