package org.example.window;
public class Start {
    private RobotFrame gameFrame;
    private RobotPanel gamePanel;
    public Start(){
        gamePanel = new RobotPanel();
        gameFrame = new RobotFrame(gamePanel);
    }
}
