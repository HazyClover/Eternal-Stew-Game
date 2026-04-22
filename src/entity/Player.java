package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    boolean isMoving = false;
    int pixelCounter = 0;

    public final int screenX;
    public final int screenY;

    public HashMap<String, Integer> inventoryItemsRow1 = new HashMap<>();
    public HashMap<String, Integer> inventoryItemsRow2 = new HashMap<>();
    public HashMap<String, Integer> inventoryItemsRow3 = new HashMap<>();
    public HashMap<String, Integer> inventoryItemsRow4 = new HashMap<>();
    public HashMap<String, Integer> inventoryItemsRow5 = new HashMap<>();

    public long secondsToSleep = 0;
    public boolean isSleeping = false;

    public Player(GamePanel gp, KeyHandler keyH) throws IOException {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 8, 8, 8);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 15;
        worldY = gp.tileSize * 15 ;
        pixelSpeed = gp.tileSize;
        moveSpeed = 0.2;
        direction = "down";
    }

    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player_up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player_up2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player_down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player_down2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player_left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player_left2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player_right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player_right2.png"));
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("SUCCESS: LOADED + 'player'!");

    }

    public long callThreadSleep() {
        if(isSleeping) {
            return secondsToSleep;
        }
        return 0;
    }

    public void getThreadSleep(double timeToSleep) {
        secondsToSleep = ((long) (timeToSleep*1000));
        isSleeping = true;
    }

    public void update() {


        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Check tile collisions
            collisionOn = false;
            gp.collisionDet.checkTile(this);


            if(!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= pixelSpeed;
                    case "down" -> worldY += pixelSpeed;
                    case "left" -> worldX -= pixelSpeed;
                    case "right" -> worldX += pixelSpeed;
                }
            }
            /*
            spriteCounter++;
            if(spriteCounter > 10) {
                if(spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            */
            if(moveSpeed >= 0){
                getThreadSleep(moveSpeed);
            } else {
                getThreadSleep(0);
            }
        }

        if(keyH.interactPressed){
            int objIndex = gp.collisionDet.checkObject(this, true);
            pickUpObject(objIndex);
            int stewIndex = gp.collisionDet.checkStew(this, true);
            if(stewIndex != 999) {
                System.out.println("PLAYER :: update | Interacted with stew!");
            }
            getThreadSleep(0.3);
        }

        if(keyH.lifetimePressed) {
            System.out.println("PLAYER :: Update - Gamelifetime is currently | '" + gp.gameLifetime + "' Seconds.");
            getThreadSleep(0.3);
        }
    }
    public void harvested(int i) {
        gp.obj[i].nextHarvestableTime= gp.gameLifetime;
        //gp.obj[i].nextHarvestableTime = ((gp.gameLifetime + gp.obj[i].timeTillNextHarvest)+gp.obj[i].harvestRandomTime.nextInt(0, gp.obj[i].harvestableChangeDifference));
        gp.obj[i].nextHarvestableTime = (gp.gameLifetime + 20);
    }

    public int isInventoryFull() {
            if (inventoryItemsRow1.size() >= 5) {
                if (inventoryItemsRow2.size() >= 5) {
                    if (inventoryItemsRow3.size() >= 5) {
                        if (inventoryItemsRow4.size() >= 5) {
                            if (inventoryItemsRow5.size() >= 5) {
                                System.out.println("PLAYER :: isInventoryFull | TRUE");
                                return 0;
                            } else {
                                return 5;
                            }
                        } else {
                            return 4;
                        }
                    } else {
                        return 3;
                    }
                } else {
                    return 2;
                }
            } else {
                return 1;
            }

    }

    public void pickUpObject(int i) {
        if(i != 999) {
            String type = gp.obj[i].type;

            // sphaghetti code hell
            switch (type) {
                case "berry_bush_blackberry", "berry_bush_blueberry":
                    if(gp.obj[i].nextHarvestableTime <= gp.gameLifetime) {

                        int inventoryFullIndex = isInventoryFull();
                        if (inventoryFullIndex == 1) {
                            inventoryItemsRow1.putIfAbsent(type, 0);
                            for (String item : inventoryItemsRow1.keySet()) {
                                if (item.equals(type)) {
                                    int entryValue = inventoryItemsRow1.get(item);
                                    harvested(i);
                                    inventoryItemsRow1.replace(item, entryValue + gp.obj[i].value);

                                    int currentBuff = 0;

                                    System.out.println("PLAYER :: pickUpObject - Found  '" + type + "'! | adding '" + gp.obj[i].value + "' to key");

                                    if (gp.obj[i].buffsApplied != null) {
                                        while (currentBuff < gp.obj[i].buffsApplied.length) {
                                            //gp.obj[i].buffsApplied[currentBuff].applyBuff(i, currentBuff, gp);
                                            System.out.println("PLAYER :: pickupObject - Applied buff '" + gp.obj[i].buffsApplied[currentBuff].buffID + "' | Stats changed -> ");
                                            System.out.println("Variable Changed: '" + gp.obj[i].buffsApplied[currentBuff].valueToChange + "' | value changed by: '" + gp.obj[i].buffsApplied[currentBuff].howMuchToChange + "'.");
                                            currentBuff++;
                                        }
                                    } else {
                                        System.out.println("PLAYER :: pickupObject - '" + gp.obj[i].name + "' has no buff!");
                                    }

                                }
                            }
                        } else if (inventoryFullIndex == 2) {
                            inventoryItemsRow2.putIfAbsent(type, 0);
                            for (String item : inventoryItemsRow2.keySet()) {
                                if (item.equals(type)) {
                                    int entryValue = inventoryItemsRow2.get(item);
                                    harvested(i);
                                    inventoryItemsRow2.replace(item, entryValue+gp.obj[i].value);

                                    int currentBuff = 0;

                                    System.out.println("PLAYER :: pickUpObject - Found  '" + type + "'! | adding '" + gp.obj[i].value + "' to key");

                                    if(gp.obj[i].buffsApplied != null) {
                                        while(currentBuff  < gp.obj[i].buffsApplied.length) {
                                            //gp.obj[i].buffsApplied[currentBuff].applyBuff(i, currentBuff, gp);
                                            System.out.println("PLAYER :: pickupObject - Applied buff '"+ gp.obj[i].buffsApplied[currentBuff].buffID+"' | Stats changed -> ");
                                            System.out.println("Variable Changed: '"+ gp.obj[i].buffsApplied[currentBuff].valueToChange+"' | value changed by: '"+gp.obj[i].buffsApplied[currentBuff].howMuchToChange + "'.");
                                            currentBuff++;
                                        }
                                    } else {
                                        System.out.println("PLAYER :: pickupObject - '" + gp.obj[i].name + "' has no buff!");
                                    }

                                }
                            }
                        } else if (inventoryFullIndex == 3) {
                            inventoryItemsRow3.putIfAbsent(type, 0);
                            for (String item : inventoryItemsRow3.keySet()) {
                                if (item.equals(type)) {
                                    int entryValue = inventoryItemsRow3.get(item);
                                    harvested(i);
                                    inventoryItemsRow3.replace(item, entryValue+gp.obj[i].value);

                                    int currentBuff = 0;

                                    System.out.println("PLAYER :: pickUpObject - Found  '" + type + "'! | adding '" + gp.obj[i].value + "' to key");

                                    if(gp.obj[i].buffsApplied != null) {
                                        while(currentBuff  < gp.obj[i].buffsApplied.length) {
                                            //gp.obj[i].buffsApplied[currentBuff].applyBuff(i, currentBuff, gp);
                                            System.out.println("PLAYER :: pickupObject - Applied buff '"+ gp.obj[i].buffsApplied[currentBuff].buffID+"' | Stats changed -> ");
                                            System.out.println("Variable Changed: '"+ gp.obj[i].buffsApplied[currentBuff].valueToChange+"' | value changed by: '"+gp.obj[i].buffsApplied[currentBuff].howMuchToChange + "'.");
                                            currentBuff++;
                                        }
                                    } else {
                                        System.out.println("PLAYER :: pickupObject - '" + gp.obj[i].name + "' has no buff!");
                                    }

                                }
                            }
                        } else if (inventoryFullIndex == 4) {
                            inventoryItemsRow4.putIfAbsent(type, 0);
                            for (String item : inventoryItemsRow4.keySet()) {
                                if (item.equals(type)) {
                                    int entryValue = inventoryItemsRow4.get(item);
                                    harvested(i);
                                    inventoryItemsRow4.replace(item, entryValue+gp.obj[i].value);

                                    int currentBuff = 0;

                                    System.out.println("PLAYER :: pickUpObject - Found  '" + type + "'! | adding '" + gp.obj[i].value + "' to key");

                                    if(gp.obj[i].buffsApplied != null) {
                                        while(currentBuff  < gp.obj[i].buffsApplied.length) {
                                            //gp.obj[i].buffsApplied[currentBuff].applyBuff(i, currentBuff, gp);
                                            System.out.println("PLAYER :: pickupObject - Applied buff '"+ gp.obj[i].buffsApplied[currentBuff].buffID+"' | Stats changed -> ");
                                            System.out.println("Variable Changed: '"+ gp.obj[i].buffsApplied[currentBuff].valueToChange+"' | value changed by: '"+gp.obj[i].buffsApplied[currentBuff].howMuchToChange + "'.");
                                            currentBuff++;
                                        }
                                    } else {
                                        System.out.println("PLAYER :: pickupObject - '" + gp.obj[i].name + "' has no buff!");
                                    }

                                }
                            }
                        } else if (inventoryFullIndex == 5) {
                            inventoryItemsRow5.putIfAbsent(type, 0);
                            for (String item : inventoryItemsRow5.keySet()) {
                                if (item.equals(type)) {
                                    int entryValue = inventoryItemsRow5.get(item);
                                    harvested(i);
                                    inventoryItemsRow5.replace(item, entryValue+gp.obj[i].value);

                                    int currentBuff = 0;

                                    System.out.println("PLAYER :: pickUpObject - Found  '" + type + "'! | adding '" + gp.obj[i].value + "' to key");

                                    if(gp.obj[i].buffsApplied != null) {
                                        while(currentBuff  < gp.obj[i].buffsApplied.length) {
                                            //gp.obj[i].buffsApplied[currentBuff].applyBuff(i, currentBuff, gp);
                                            System.out.println("PLAYER :: pickupObject - Applied buff '"+ gp.obj[i].buffsApplied[currentBuff].buffID+"' | Stats changed -> ");
                                            System.out.println("Variable Changed: '"+ gp.obj[i].buffsApplied[currentBuff].valueToChange+"' | value changed by: '"+gp.obj[i].buffsApplied[currentBuff].howMuchToChange + "'.");
                                            currentBuff++;
                                        }
                                    } else {
                                        System.out.println("PLAYER :: pickupObject - '" + gp.obj[i].name + "' has no buff!");
                                    }

                                }
                            }
                        }
                    System.out.println("PLAYER :: pickUpObject - Berry bush is harvestable in  | '" + (gp.obj[i].nextHarvestableTime-gp.gameLifetime) + "' Seconds.");
                    break;
            }
            gp.ui.showMessage("Picked up: '" + gp.obj[i].name +"' X " + gp.obj[i].value);

            }
            getThreadSleep(0.1);
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;

        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
