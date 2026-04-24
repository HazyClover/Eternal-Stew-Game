package object;

import buff.SuperBuff;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.Random;



public class SuperObject {

    public BufferedImage image;
    public BufferedImage image2;
    public String name;
    public String type;
    public int value;
    public boolean collision = false;
    public int worldX, worldY;

    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public double lastHarvested;
    public double nextHarvestableTime;



    public SuperBuff[] buffsApplied;


    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY + gp.player.screenY){

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            if(image2 != null) {
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        }
    }

    public void addToInventory() {

    }

    public void activateObj(SuperObject object, GamePanel gp) {
        switch (object.type) {
            case "berry_bush_blackberry", "berry_bush_blueberry":
                double timeTillNextHarvest = 60;
                int harvestableChangeDifference = 300;
                Random harvestRandomTime = new Random();

                object.nextHarvestableTime= gp.gameLifetime;
                object.nextHarvestableTime = ((gp.gameLifetime + timeTillNextHarvest)+harvestRandomTime.nextInt(0, harvestableChangeDifference));
                System.out.println("SUPEROBJECT :: activateobj | Activated - '" + object.type+"'.");
                break;
            case "stew_test":
                System.out.println("SUPEROBJECT :: activateobj | Activated - '" + object.type+"'.");
                break;
            case "bowl_test":
                System.out.println("SUPEROBJECT :: activateobj | Activated - '" + object.type+"'.");
                break;
        }
    }

}
