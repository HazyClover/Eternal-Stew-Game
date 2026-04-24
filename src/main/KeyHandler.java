package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed, inventoryPressed, lifetimePressed, isOnePressed, isTwoPressed;

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        int code = keyEvent.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_E){
            interactPressed = true;
        }
        if(code == KeyEvent.VK_Q){
            inventoryPressed = true;
        }
        if(code == KeyEvent.VK_L){
            lifetimePressed = true;
        }
        if(code == KeyEvent.VK_1){
            isOnePressed = true;
        }
        if(code == KeyEvent.VK_2){
            isTwoPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_E){
            interactPressed = false;
        }
        if(code == KeyEvent.VK_Q){
            inventoryPressed = false;
        }
        if(code == KeyEvent.VK_L){
            lifetimePressed = false;
        }
        if(code == KeyEvent.VK_1){
            isOnePressed = true;
        }
        if(code == KeyEvent.VK_2){
            isTwoPressed = true;
        }
    }
}
