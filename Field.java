import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;

public class Field extends Pane{
    private int w; 
    private int h;

    ArrayList<Block> blocks = new ArrayList<Block>();
    int Score = 0;
    double curTime = 8;
    Food tamak;
    Snake snake;


    public Field(int width, int height){
        w= width;
        h= height;
        setMinSize(width*App.BlockSize, height*App.BlockSize);
        Image backgroundImage = new Image("images/BG.jpg");
        int targetHeight = 800;
        int targetWidth = 700;

        BackgroundSize backgroundSize = new BackgroundSize(targetWidth, targetHeight, false, false, false, false);
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            backgroundSize
        );

        setBackground(new Background(background));

        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null,new BorderWidths(3))));
        addFood();
    }
    public void addSnake(Snake s){
        snake = s;
        for(Block b:s.blocks){
            addBlock(b);
        }
    }

    public void update(){
        for(Block b:blocks){
            b.update();
        }
        if(isEaten(tamak)){
            Score +=1;
            addFood();
            addNewBlock();
            curTime=curTime+0.2;
        }
    }

    public boolean isDead(){
        for(Block b:blocks){
            if(b!=snake.headSnake){
                if(b.Position_X==snake.headSnake.Position_X && b.Position_Y == snake.headSnake.Position_Y){
                    return true;
                }
            }
        }
        return false;
    }

    public void addNewBlock(){
        Block b = new Block(snake.tail.olderPosition_X, snake.tail.olderPosition_Y, snake.tail, this);
        snake.tail = b;
        addBlock(b);
    }

    private void addBlock(Block b){
        getChildren().add(b);
        blocks.add(b);
    }

    public void addFood() {
        ArrayList<Integer> freeSpots = new ArrayList<>();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (!isSnakeBlock(i, j)) {
                    freeSpots.add(i * h + j);
                }
            }
        }
    
        if (!freeSpots.isEmpty()) {
            int randomIndex = (int)(Math.random() * freeSpots.size());
            int randomPosition = freeSpots.get(randomIndex);
            int randomX = randomPosition / h;
            int randomY = randomPosition % h;
    
            Food food = new Food(randomX, randomY);
            getChildren().add(food);
            getChildren().remove(tamak);
            tamak = food;
        }
    }
    
    private boolean isSnakeBlock(int x, int y) {
        for (Block block : blocks) {
            if (block.Position_X == x && block.Position_Y == y) {
                return true;
            }
        }
        return false;
    }
    

    public boolean isEaten(Food f){
        if(f==null){
            return false;
        }
        return f.getPositionX() == snake.headSnake.Position_X && f.getPositionY() == snake.headSnake.Position_Y;
    }

    public int getW(){
        return w;
    }
    public int getH(){
        return h;
    }
}