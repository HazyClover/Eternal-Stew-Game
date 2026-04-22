package buff;

import main.GamePanel;

public class BUFF_slowness extends SuperBuff {
    GamePanel gp;

    public BUFF_slowness(GamePanel gp) {
        this.gp = gp;
        buffID = "BUFF_slowness";
        valueToChange = "moveSpeed";
        targetEntity = "Player";
        howMuchToChange = 0.5;
        duration = 10;
    }


}
