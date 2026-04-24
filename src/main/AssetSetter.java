package main;

import object.OBJ_berry_bush_blackberry;
import object.OBJ_berry_bush_blueberry;
import object.OBJ_Stew;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_berry_bush_blackberry(gp);
        gp.obj[0].worldX = 14 * gp.tileSize;
        gp.obj[0].worldY = 9 * gp.tileSize;

        gp.obj[1] = new OBJ_berry_bush_blueberry(gp);
        gp.obj[1].worldX = 15 * gp.tileSize;
        gp.obj[1].worldY = 10 * gp.tileSize;

        gp.obj[2] = new OBJ_Stew(gp);
        gp.obj[2].worldX = 14 * gp.tileSize;
        gp.obj[2].worldY = 15 * gp.tileSize;
    }
}
