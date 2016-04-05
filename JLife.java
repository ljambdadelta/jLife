import edu.princeton.cs.introcs.StdDraw;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by .Rain on 19.03.2016.
 */
public class JLife {
    //-------------------------------------
    public static void main(String[] args){
        //---------------------------------
        double          xMin=       0.000;              // global field scale
        double          yMin=       0.000;
        double          xMax=       1.000;
        double          yMax=       1.000;
        double          xGrid=      7.00;              // number of gridlines
        double          yGrid=      7.00;
        double          xCellSize=  xMax/xGrid;
        double          yCellSize=  yMax/yGrid;
        int             xWinSize=   500;                // window size
        int             yWinSize=   500;
        int             maxGen=     50;
        GameField       game=       new GameField((int)xGrid,(int)yGrid,"ddddaddddddadaddddaaddddddddddddddddddddddddddddd");

        //---------------------------------
        // Initialisation
        StdDraw.setXscale(xMin,xMax);
        StdDraw.setYscale(yMin,yMax);
        StdDraw.setCanvasSize(xWinSize,yWinSize);
        StdDraw.setPenRadius(0.0025);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        //---------------------------------
        game.drawGrid(xMin,yMin,xMax,yMax);
        game.drawFirstField(xCellSize,yCellSize);
        StdDraw.save("Pic"+""+".png");

       /* JLife.doSleep();
        StdDraw.save("Pic1.png");
        game.reDraw();
        JLife.doSleep();
        StdDraw.save("Pic2.png");
        game.reDraw();*/

        for(int i=0;i<maxGen;i++){
            JLife.doSleep();
            game.reDraw();
            StdDraw.save("Pic"+String.valueOf(i)+".png");
        }
    }

    //-------------------------------------
    // Simply draws a grid
static void doSleep(){
    try{TimeUnit.MILLISECONDS.sleep(500);}
    catch(InterruptedException e) {}
}

}
