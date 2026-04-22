package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel gp;

    Font arial_40;
    public String message;
    public boolean messageOn;
    public boolean incrementMessage;
    int messageCounter = 0;

    public BufferedImage playerHands;
    public BufferedImage playerInventoryImg;

    public int playerHandsX;
    public int playerHandsY;
    public int playerInventoryX;
    public int playerInventoryY;

    KeyHandler keyH;

    public UI(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        try {
            playerInventoryImg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ui/PlayerInventory.png"));
            playerHands = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ui/PlayerHands.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        playerHandsX = gp.screenWidth/2;
        playerHandsY = gp.tileSize;

        playerInventoryX = gp.screenWidth/2;
        playerInventoryY = gp.tileSize*10;

        arial_40 = new Font("Arial", Font.PLAIN, 20);

        System.out.println("SUCCESS: LOADED + 'UI'!");
    }


    public void showMessage(String text) {
        if(messageOn){
            incrementMessage = true;
        }
        message = text;
        messageOn = true;
    }

    public  void displayHands(Graphics2D g2) {
        g2.drawImage(playerHands, playerHandsX-gp.tileSize, 0, (gp.tileSize*2)+16, gp.tileSize+16, null);
    }

    public  void displayInventory(Graphics2D g2) {
        g2.drawImage(playerInventoryImg, playerInventoryX-(gp.tileSize*3)+8, playerInventoryY-(gp.tileSize*7), (gp.tileSize*5)+32, (gp.tileSize*5)+32, null);
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

        displayHands(g2);

        if(keyH.inventoryPressed){
            displayInventory(g2);
            System.out.println("DISPLAYING INVENTORY");
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
