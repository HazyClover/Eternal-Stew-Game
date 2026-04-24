package object;

import buff.BUFF_speed_boost;
import buff.SuperBuff;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_berry_bush_blackberry extends SuperObject {


    GamePanel gp;

    public OBJ_berry_bush_blackberry(GamePanel gp) {
        this.gp = gp;

        name = "Blackberry";
        type = "berry_bush_blackberry";
        value = 5;
        lastHarvested = gp.gameLifetime;
        nextHarvestableTime = 0;

        //Give Buffs
        buffsApplied = new SuperBuff[1];
        buffsApplied[0] = new BUFF_speed_boost(gp);
        //

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/BerryBushPlaceholder.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("SUCCESS: LOADED + '" + type +"'!");
    }


}
