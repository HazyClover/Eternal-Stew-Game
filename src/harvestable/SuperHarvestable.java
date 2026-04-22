package harvestable;

import buff.SuperBuff;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SuperHarvestable {

    public BufferedImage image;
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
    public double timeTillNextHarvest = 60;
    public int harvestableChangeDifference = 300;

    public Random harvestRandomTime;

    public SuperBuff[] buffsApplied;

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY + gp.player.screenY){

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
