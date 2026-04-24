package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_bowl_test extends SuperObject {
    GamePanel gp;

    public OBJ_bowl_test(GamePanel gp) {
        this.gp = gp;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("item/BowlEmpty.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        name = "Bowl";
        type = "bowl_test";
    }
}
