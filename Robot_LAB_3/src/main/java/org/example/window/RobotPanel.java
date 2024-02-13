package org.example.window;

import org.example.robot.*;
import org.example.utils.MyKeyListener;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.util.concurrent.*;

public class RobotPanel extends JPanel implements Runnable {
    private MyRobot myRobot;
    private RobotMap robotMap;
    private CollisionChecker collisionChecker;
    private TileManager tileManager;
    private RobotLogic robotLogic;
    private MyKeyListener myKeyListener;
    private volatile Thread robotThread;
    private final int TARGET_FPS = 10;
    private final long FRAME_INTERVAL = 1000 / TARGET_FPS;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public RobotPanel() {
        this.myRobot = new MyRobot(10,11);
        this.robotMap = new RobotMap();
        this.collisionChecker = new CollisionChecker();
        this.tileManager = new TileManager();
        this.myKeyListener = new MyKeyListener();
        robotLogic = new RobotLogic( myRobot, robotMap,myKeyListener, collisionChecker, tileManager,this);
        initRobotPanel();
    }

    public void startThread() {
        if (robotThread == null) {
            robotThread = new Thread(this);
            robotThread.start();
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
        tileManager.drawTilesMap((Graphics2D) g);
        try {
            myRobot.draw((Graphics2D) g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void update() {
        robotLogic.robotMove();
    }

    @Override
    public void run() {
        try {
            scheduler.scheduleAtFixedRate(this::updateAndRepaint, 0, FRAME_INTERVAL, TimeUnit.MILLISECONDS);
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void updateAndRepaint() {
        update();
        repaint();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}