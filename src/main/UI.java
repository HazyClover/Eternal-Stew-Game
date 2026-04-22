package main;

import java.awt.*;

public class UI {
    GamePanel gp;

    Font arial_40;
    public String message;
    public boolean messageOn;
    public boolean incrementMessage;
    int messageCounter = 0;

    public  UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 20);
    }

    public void showMessage(String text) {
        if(messageOn){
            incrementMessage = true;
        }
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        int startPositionX = 20;
        int startPositionY = 30;
        int newPositionX = startPositionX;
        int newPositionY = startPositionY;
        int increaseBy = 20;

        if(messageOn) {
            g2.setFont(g2.getFont().deriveFont(15F));
            g2.drawString(message, gp.tileSize*12, gp.tileSize*11 );
            //g2.drawString(message, gp.tileSize, gp.tileSize );

            messageCounter++;

            if(messageCounter > 180) {
                messageCounter = 0;
                messageOn = false;
            }
        }

        /*
        g2.drawString("--- Inventory ---", startPositionX, startPositionY);
        for (String item : gp.player.inventoryItems.keySet()) {
            newPositionY += increaseBy;
            g2.drawString(item+": " + gp.player.inventoryItems.get(item), newPositionX, newPositionY);
        }

        newPositionX = startPositionX;
        newPositionY = startPositionY;

         */
    }
}
