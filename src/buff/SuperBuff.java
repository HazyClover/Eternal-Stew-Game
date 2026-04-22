package buff;

import main.GamePanel;

public class SuperBuff {
    public String buffID;
    public String valueToChange;
    public String targetEntity;
    public double howMuchToChange;
    public double duration;

    public void applyBuff(int objIndex, int buffIndex, GamePanel gp) {
        switch (gp.obj[objIndex].buffsApplied[buffIndex].buffID){
            case "BUFF_slowness":
                gp.player.moveSpeed = gp.player.moveSpeed + gp.obj[objIndex].buffsApplied[buffIndex].howMuchToChange;
                break;
            case "BUFF_speed_boost":
                gp.player.moveSpeed = gp.player.moveSpeed - gp.obj[objIndex].buffsApplied[buffIndex].howMuchToChange;
                break;
        }
    }
}
