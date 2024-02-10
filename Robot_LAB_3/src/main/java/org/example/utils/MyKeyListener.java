package org.example.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
    public boolean isUp,isDown,isRight,isLeft;
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_D){
            isRight = true;
        }
        if(code==KeyEvent.VK_A){
            isLeft = true;
        }
        if(code==KeyEvent.VK_S){
            isDown = true;
        }
        if(code==KeyEvent.VK_W){
            isUp = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_A){
            isLeft = false;
        }
        if(code==KeyEvent.VK_D){
            isRight = false;
        }
        if(code==KeyEvent.VK_S){
            isDown = false;
        }
        if(code==KeyEvent.VK_W){
            isUp = false;
        }
    }
}
