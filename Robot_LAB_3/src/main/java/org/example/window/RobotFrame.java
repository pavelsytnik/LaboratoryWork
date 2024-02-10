package org.example.window;

import org.example.robot.RobotMap;

import javax.swing.*;

public class RobotFrame extends JFrame {
    private RobotMap robotMap;

    public RobotFrame(RobotPanel robotPanel) {
        robotMap = new RobotMap();
        setSize(robotMap.getWidth(), robotMap.getHeight());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("MyRobot");
        add(robotPanel);
        robotPanel.startThread();
        pack();
        setVisible(true);
    }

}
