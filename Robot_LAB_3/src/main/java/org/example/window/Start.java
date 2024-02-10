package org.example.window;

import org.example.window.RobotFrame;
import org.example.window.RobotPanel;

public class Start {
    private RobotFrame gameFrame;
    private RobotPanel gamePanel;

    public Start(){
        gamePanel = new RobotPanel();
        gameFrame = new RobotFrame(gamePanel);
    }
}
