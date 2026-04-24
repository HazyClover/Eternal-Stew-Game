package object;

import buff.BUFF_slowness;
import buff.SuperBuff;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_berry_bush_blueberry extends SuperObject {


    GamePanel gp;

    public OBJ_berry_bush_blueberry(GamePanel gp) {
        this.gp = gp;

        name = "Blueberry";
        type = "berry_bush_blueberry";
        value = 2;
        lastHarvested = gp.gameLifetime;
        nextHarvestableTime = 0;
        buffsApplied = new SuperBuff[1];
        buffsApplied[0] = new BUFF_slowness(gp);
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/BerryBushPlaceholder.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("SUCCESS: LOADED + '" + type +"'!");
    }

}
