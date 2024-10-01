import gpdraw.*;
import java.awt.Color;
/*interface forHangMan {
static void numberOfTimesWrong();
static void drawTheHangingMan();
}*/
public class HangingManDrawing /*implements forHangMan*/ {
    static int lengthOfPaper = 800;
    static int widthOfPaper = 600;
    /*
     * int lengthOfPaper;
     * int widthOfPaper;
     */
    SketchPad paper = new SketchPad(lengthOfPaper,widthOfPaper);
    DrawingTool pencil = new DrawingTool(paper);
    /*
     * SketchPad paper;
     * DrawingTool pencil;
     */
    double saveOfLastX;
    double saveOfLastY;
    double saveOfLastDir;
    double saveForXLeg;
    double saveForYLeg;
    double saveForDirLeg;
    /*public HangingManDrawing(DrawingTool pen, SketchPad sheet, int length, int width) {
    sheet = paper;
    pencil = pen;
    lengthOfPaper = length;
    widthOfPaper = width;
    }*/

    public HangingManDrawing() { //restart the instance variables in constructor
        /*saveOfLastX = 0.0;
        saveOfLastY = 0.0;
        saveOfLastDir = 0.0;
        saveForXLeg = 0.0;
        saveForYLeg = 0.0;
        saveForDirLeg = 0.0;
        pencil.up();
        pencil.move(0.0, 0.0);
        pencil.backward(widthOfPaper/4);
        pencil.down();*/
    }

    public void hangManPole() {
        saveOfLastX = 0.0;
        saveOfLastY = 0.0;
        saveOfLastDir = 0.0;
        saveForXLeg = 0.0;
        saveForYLeg = 0.0;
        saveForDirLeg = 0.0;
        pencil.up();
        pencil.move(saveOfLastX, saveOfLastY);
        pencil.setDirection(90.0);
        pencil.down();
        pencil.up();
        pencil.backward(widthOfPaper/4);
        double x = pencil.getXPos();
        double y = pencil.getYPos();
        //pencil.setDirection(90.0);
        double dir = pencil.getDirection();
        pencil.setDirection(180.0);
        pencil.setWidth(15);
        pencil.down();
        pencil.forward(lengthOfPaper/4);
        pencil.up();
        pencil.setDirection(0.0);
        pencil.move(x,y);
        pencil.down();
        pencil.forward(lengthOfPaper/15);
        pencil.up();
        pencil.move(x,y);
        pencil.setDirection(dir);
        pencil.down();
        pencil.forward(widthOfPaper/2);
        x = pencil.getXPos();
        y = pencil.getYPos();
        dir = pencil.getDirection();
        pencil.forward(widthOfPaper/12);
        pencil.up();
        pencil.move(x,y);
        pencil.setDirection(dir);
        pencil.turnLeft(90.0);
        pencil.setWidth(12);
        pencil.down();
        pencil.forward(lengthOfPaper/6);
        x = pencil.getXPos();
        y = pencil.getYPos();
        dir = pencil.getDirection();
        pencil.forward(lengthOfPaper/18);
        pencil.move(x,y);
        pencil.setDirection(dir);
        pencil.down();
        pencil.turnLeft(90.0);
        pencil.setWidth(10);
        pencil.forward(widthOfPaper/13);
        saveOfLastX = pencil.getXPos();
        saveOfLastY = pencil.getYPos();
        saveOfLastDir = pencil.getDirection();
        pencil.up();
    }

    public void drawFace(boolean state) {
        if(state) {
            double radius = widthOfPaper/20;
            pencil.move(saveOfLastX, saveOfLastY);
            pencil.forward(radius + 2);
            pencil.setWidth(8);
            pencil.down();
            pencil.drawCircle(radius);
            pencil.up();
            pencil.forward(radius);
            saveOfLastX = pencil.getXPos();
            saveOfLastY = pencil.getYPos();
            saveOfLastDir = pencil.getDirection();
        }
    }

    public void drawBody(boolean state) {
        if(state) {
            pencil.down();
            pencil.forward(widthOfPaper/16);
            saveOfLastX = pencil.getXPos();
            saveOfLastY = pencil.getYPos();
            saveOfLastDir = pencil.getDirection();
            pencil.forward(widthOfPaper/10);
            saveForXLeg = pencil.getXPos();
            saveForYLeg = pencil.getYPos();
            saveForDirLeg = pencil.getDirection();
            pencil.up();
        }
    }

    public void drawLeftArm(boolean state) {
        if(state) {
            pencil.move(saveOfLastX,saveOfLastY);
            pencil.setDirection(saveOfLastDir);
            pencil.turnRight(135.0);
            pencil.down();
            pencil.setWidth(6);
            pencil.forward(widthOfPaper/10);
            pencil.up();
        }
    }

    public void drawRightArm(boolean state) {
        if(state) {
            pencil.move(saveOfLastX,saveOfLastY);
            pencil.setDirection(saveOfLastDir);
            pencil.turnLeft(135.0);
            pencil.down();
            pencil.setWidth(6);
            pencil.forward(widthOfPaper/10);
            pencil.up();
        }
    }

    public void drawLeftLeg(boolean state) {
        if(state) {
            pencil.move(saveForXLeg, saveForYLeg);
            pencil.setDirection(saveForDirLeg);
            pencil.turnRight(35.0);
            pencil.down();
            pencil.setWidth(7);
            pencil.forward(widthOfPaper/8);
            pencil.up();
        }
    }

    public void drawRightLeg(boolean state) {
        if(state) {
            pencil.move(saveForXLeg, saveForYLeg);
            pencil.setDirection(saveForDirLeg);
            pencil.turnLeft(35.0);
            pencil.down();
            pencil.setWidth(7);
            pencil.forward(widthOfPaper/8);
            pencil.up();
        }
    }

    //public static void main(String[] args) {
    /*SketchPad paper = new SketchPad(lengthOfPaper,widthOfPaper);
    DrawingTool pencil = new DrawingTool(paper);
    HangingManDrawing draw = new HangingManDrawing(pencil, paper);*/
    //HangingManDrawing draw = new HangingManDrawing();
    //draw.hangManPole();
    //draw.drawFace(true);
    //draw.drawBody(true);
    //draw.drawLeftArm(true);
    //draw.drawRightArm(true);
    //draw.drawLeftLeg(true);
    //draw.drawRightLeg(true);
    //System.out.println("Can it do two?");
    //}
}   