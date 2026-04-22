package buff;

import main.GamePanel;

public class BUFF_speed_boost extends SuperBuff {
    GamePanel gp;

    public BUFF_speed_boost(GamePanel gp) {
        this.gp = gp;
        buffID = "BUFF_speed_boost";
        valueToChange = "moveSpeed";
        targetEntity = "Player";
        howMuchToChange = 0.1;
        duration = 10;
    }

    public void applyBuff(int objIndex, int buffIndex, GamePanel gp) {
        gp.player.moveSpeed = gp.player.moveSpeed - gp.obj[objIndex].buffsApplied[buffIndex].howMuchToChange;
    }

}
