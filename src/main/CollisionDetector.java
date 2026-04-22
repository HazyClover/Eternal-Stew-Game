package main;

import entity.Entity;

public class CollisionDetector {
    GamePanel gp;

    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldx = entity.worldX + entity.solidArea.x;
        int entityRightWorldx = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldx/gp.tileSize;
        int entityRightCol = entityRightWorldx/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.pixelSpeed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case  "down":
                entityBottomRow = (entityBottomWorldY + entity.pixelSpeed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case  "left":
                entityLeftCol = (entityLeftWorldx - entity.pixelSpeed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case  "right":
                entityRightCol = (entityRightWorldx + entity.pixelSpeed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] != null) {
                // Get entities solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Objects solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if(gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if(player) {
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public int checkStew(Entity entity, boolean player) {

        int index = 999;
        for(int i = 0; i < gp.stewPots.length; i++){
            if(gp.stewPots[i] != null) {
                // Get entities solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Objects solid area position
                gp.stewPots[i].solidArea.x = gp.stewPots[i].worldX + gp.stewPots[i].solidArea.x;
                gp.stewPots[i].solidArea.y = gp.stewPots[i].worldY + gp.stewPots[i].solidArea.y;

                if (entity.solidArea.intersects(gp.stewPots[i].solidArea)) {
                    if(gp.stewPots[i].collision) {
                        entity.collisionOn = true;
                    }
                    if(player) {
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.stewPots[i].solidArea.x = gp.stewPots[i].solidAreaDefaultX;
                gp.stewPots[i].solidArea.y = gp.stewPots[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
