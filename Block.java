import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle{
    static final int UP = 0;
    static final int RIGHT = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;

    int Position_X;
    int Position_Y;
    int olderPosition_X;
    int olderPosition_Y;

    Block previous;

    int direction = LEFT;

    int maxX;
    int maxY;


    public Block(int x,  int y, Block p, Field field){
        super(App.BlockSize, App.BlockSize);
        Position_X = x;
        Position_Y=y;
        setTranslateX(Position_X*App.BlockSize);
        setTranslateY(Position_Y*App.BlockSize);
        previous = p;
        maxX= field.getW();
        maxY = field.getH();

        setFill(Color.DARKSALMON);
    }

    public void update(){
        olderPosition_X = Position_X;
        olderPosition_Y = Position_Y;
        if(previous == null){
            switch(direction){
                case UP: moveUp(); break;
                case RIGHT: moveRight(); break;
                case DOWN: moveDown(); break;
                case LEFT: moveLeft(); break;
            }
        }else{
            Position_X = previous.olderPosition_X;
            Position_Y = previous.olderPosition_Y;
        }
        updatePosition();
    }

    private void moveUp() {
        Position_Y--;
        if(Position_Y<0){
            Position_Y=maxY-1;
        }
    }
    private void moveDown() {
        Position_Y++;
        if(Position_Y>= maxY){
            Position_Y=0;
        }
    }
    private void moveRight() {
        Position_X++;
        if(Position_X>= maxX){
            Position_X=0;
        }
    }
    private void moveLeft() {
        Position_X--;
        if(Position_X<0){
            Position_X=maxX-1;
        }
    }

    public void updatePosition(){
        setTranslateX(Position_X*App.BlockSize);
        setTranslateY(Position_Y*App.BlockSize);
    }
}