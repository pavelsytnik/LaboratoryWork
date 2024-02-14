package org.example.window;

import org.example.robot.*;
import org.example.utils.MyKeyListener;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.concurrent.*;

public class RobotPanel extends JPanel implements Runnable {
    private MyRobot myRobot;
    private RobotMap robotMap;
    private CollisionChecker collisionChecker;
    private TileManager tileManager;
    private RobotLogic robotLogic;
    private MyKeyListener myKeyListener;
    private boolean flag;
    private volatile Thread robotThread;
    private final int TARGET_FPS = 30;
    private final long FRAME_INTERVAL = 1000 / TARGET_FPS;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean robotCoordinatesSet = false;
    public RobotPanel() {
        this.myRobot = new MyRobot();
        this.robotMap = new RobotMap();
        this.collisionChecker = new CollisionChecker();
        this.tileManager = new TileManager();
        this.myKeyListener = new MyKeyListener();
        robotLogic = new RobotLogic( myRobot, robotMap,myKeyListener, collisionChecker, tileManager,this);
        MyMouseListener mouseListener = new MyMouseListener();
        this.addMouseListener(mouseListener);
        flag = robotLogic.finish;
        initRobotPanel();
    }
    public void startThread() {
        if (robotThread == null) {
            robotThread = new Thread(this);
            robotThread.start();
        }
    }
    public void stopThread() {
        if (robotThread != null) {
            robotThread.interrupt();
            robotThread = null;
        }
    }
    private void initRobotPanel() {
        this.setPreferredSize(new Dimension(robotMap.getWidth(), robotMap.getHeight()));
        this.setBackground(Color.GREEN);
        this.setDoubleBuffered(true);
        this.addKeyListener(myKeyListener);
        this.setFocusable(true);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        tileManager.drawTilesMap(g2d);
        try {
            myRobot.draw(g2d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2d.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 16);
        g2d.setFont(font);
        int x = myRobot.getxPosition() / robotMap.getTileSize();
        int y = myRobot.getyPosition() / robotMap.getTileSize();
        String coordinatesText = "Robot Coordinates: (" + x + ", " + y + ")";
        int textX = 10;
        int textY = getHeight() - 20;
        g2d.drawString(coordinatesText, textX, textY);
        g2d.drawString("FPS: " + TARGET_FPS, getWidth() - 60, textY);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    public void update() {
        robotLogic.robotMove();
    }
    public void run() {
        try {
            scheduler.scheduleAtFixedRate(this::updateAndRepaint, 0, FRAME_INTERVAL, TimeUnit.MILLISECONDS);
            while (!Thread.currentThread().isInterrupted() && !flag) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private void updateAndRepaint() {
        update();
        repaint();
        if (!flag) {
            stopThread();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (!robotCoordinatesSet) {
                int tileX;
                int tileY;
                int mouseX = e.getX();
                int mouseY = e.getY();
                tileX = mouseX / robotMap.getTileSize();
                tileY = mouseY / robotMap.getTileSize();
                if(!tileManager.checkCoordinateOfValue(tileManager.getMap(), 1, tileX, tileY)) {
                    myRobot.setxPosition(tileX * robotMap.getTileSize());
                    myRobot.setyPosition(tileY * robotMap.getTileSize());
                    repaint();
                    robotCoordinatesSet = true;
                    startThread();
                }
            }
        }
    }
}