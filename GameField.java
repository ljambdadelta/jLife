import edu.princeton.cs.introcs.StdDraw;
import javafx.scene.paint.Color;

public class GameField {
    //-----------------------
    int             xGridSize,
                    yGridSize;
    double          xMin,
                    yMin,
                    xMax,
                    yMax,
                    xCellSize,
                    yCellSize;
    GameCell[][]    aField,
                    bField;
    //-----------------------
    // Constructor
    GameField(int x, int y){
        xGridSize=      x;
        yGridSize=      y;
        aField=         new GameCell[xGridSize][yGridSize]; // active/analyzed field
        bField=         new GameCell[xGridSize][yGridSize]; // buffer gamefield

        //-----------------------
        // Init every cell
        for(int i=0;i<xGridSize;i++)
            for(int j=0;j<yGridSize;j++)
                aField[i][j] = new GameCell();
    }
    GameField(int x, int y, String s){
        xGridSize=      x;
        yGridSize=      y;
        aField=         new GameCell[xGridSize][yGridSize]; // active/analyzed field
        bField=         new GameCell[xGridSize][yGridSize]; // buffer gamefield
        //
        //-----------------------
        // Init every cell
        for(int i=0;i<xGridSize;i++)
            for(int j=0;j<yGridSize;j++)
                aField[i][j] = new GameCell((s.charAt(i*yGridSize+j))=='a'?true:false);
    }
    //-----------------------
    //draw random cell
    public void drawFirstField(double xCell, double yCell){
        xCellSize= xCell; yCellSize= yCell;
        for(int i=0;i<xGridSize;i++)                                    // x is vertical, y is horizontal
            for(int j=0;j<yGridSize;j++)                                // left-bottom is 0;0, x is cycle-inside
                if(aField[i][j].isAlive()) {                            // draw alive (>0)
                    StdDraw.setPenColor(0, 0, 0);
                    StdDraw.filledRectangle(xCellSize * (double) i + (xCellSize / 2),  // center by x
                            yCellSize * (double) j + (yCellSize / 2),  // center by y
                            xCellSize / 2, yCellSize / 2);              // half-sizes x/y
                }
    }
    //-----------------------
    //
    private void countFriends(){
        int[]   counter=        new int[xGridSize*yGridSize];
        int[][]   pAlive=           new int[xGridSize][yGridSize];
        for (int y=0;y<yGridSize;y++){
            for (int x=0;x<xGridSize;x++){
                //----------------------- Vars
                int     wrap=           y*xGridSize+x;          // Cell ID. Called 'wrap' due fixed logical error.
                        counter[wrap]=  0;                      // Counter of alive 'friends'
                // x-ID's, tor realisation
                int     xMinus2=        x-2>=0?x-2:(x-1>0?xGridSize-1:xGridSize-2);
                int     xMinus1=        x-1>=0?x-1:xGridSize-1;
                int     xPlus1=         x+1<xGridSize?x+1:0;
                //int     xPlus2=         x+2<xGridSize?x+2:(x+1<xGridSize?0:1);
                // y-ID's tor realisation
                int     yMinus2=        y-2>=0?y-2:(y-1>0?yGridSize-1:yGridSize-2);
                int     yMinus1=        y-1>=0?y-1:yGridSize-1;
                int     yPlus1=         y+1<yGridSize?y+1:0;
                //int     yPlus2=         y+2<yGridSize?y+2:(y+1<yGridSize?0:1);
                // aField[x-2][]
                int     xM2yM2=         aField[xMinus2][yMinus2].isAlive()?1:0;
                int     xM2yM1=         aField[xMinus2][yMinus1].isAlive()?1:0;
                int     xM2y=           aField[xMinus2][y].isAlive()?1:0;
                int     xM2yP1=         aField[xMinus2][yPlus1].isAlive()?1:0;
                // aField[x-1][]
                int     xM1yM2=         aField[xMinus1][yMinus2].isAlive()?1:0;
                int     xM1yM1=         aField[xMinus1][yMinus1].isAlive()?1:0;
                int     xM1y=           aField[xMinus1][y].isAlive()?1:0;
                int     xM1yP1=         aField[xMinus1][yPlus1].isAlive()?1:0;
                // aField[x][]
                int     xyM2=           aField[x][yMinus2].isAlive()?1:0;
                int     xyM1=           aField[x][yMinus1].isAlive()?1:0;
                int     xy=             aField[x][y].isAlive()?1:0;
                int     xyP1=           aField[x][yPlus1].isAlive()?1:0;
                // aField[x+1][]
                int     xP1yM2=         aField[xPlus1][yMinus2].isAlive()?1:0;
                int     xP1yM1=         aField[xPlus1][yMinus1].isAlive()?1:0;
                int     xP1y=           aField[xPlus1][y].isAlive()?1:0;
                int     xP1yP1=         aField[xPlus1][yPlus1].isAlive()?1:0;

                int     yM2Sum=             xM1yM2+xyM2+xP1yM2;
                int     yM1Sum=             xM1yM1+xyM1+xP1yM1;
                int     ySum=               xM1y+xy+xP1y;
                int     yP1Sum=             xM1yP1+xyP1+xP1yP1;
                if(true) {
                    StdDraw.clear();
                    this.drawGrid(xMin,yMin,xMax,yMax);
                    StdDraw.setPenColor(0, 30, 200);
                    StdDraw.text(xMinus2 * xCellSize, yMinus1 * yCellSize, "-2;-1");
                    StdDraw.text(xMinus1 * xCellSize, yMinus1 * yCellSize, "-1;-1");
                    StdDraw.text(x * xCellSize, yMinus1 * yCellSize, "0;-1 "+String.valueOf(yM1Sum));
                    StdDraw.text(xPlus1 * xCellSize, yMinus1 * yCellSize, "1;-1");

                    StdDraw.text(xMinus2 * xCellSize, y * yCellSize, "-2;0");
                    StdDraw.text(xMinus1 * xCellSize, y * yCellSize, "-1;0");
                    StdDraw.text(x * xCellSize, y * yCellSize, "0;0 "+String.valueOf(ySum));
                    StdDraw.text(xPlus1 * xCellSize, y * yCellSize, "1;0");

                    StdDraw.text(xMinus2 * xCellSize, yPlus1 * yCellSize, "-2;1");
                    StdDraw.text(xMinus1 * xCellSize, yPlus1 * yCellSize, "-1;1");
                    StdDraw.text(x * xCellSize, yPlus1 * yCellSize, "0;1"+String.valueOf(yP1Sum));
                    StdDraw.text(xPlus1 * xCellSize, yPlus1 * yCellSize, "1;1");

                    StdDraw.text(xPlus1 * xCellSize, yMinus2 * yCellSize, "1;-2");
                    StdDraw.filledRectangle(xCellSize * (double) xPlus1 + (xCellSize / 2),  // center by x
                            yCellSize * (double) yMinus2 + (yCellSize / 2),  // center by y
                            xCellSize / 2, yCellSize / 2);              // half-sizes x/y
                    StdDraw.filledRectangle(xCellSize * (double) x + (xCellSize / 2),  // center by x
                            yCellSize * (double) yMinus2 + (yCellSize / 2),  // center by y
                            xCellSize / 2, yCellSize / 2);              // half-sizes x/y
                    StdDraw.filledRectangle(xCellSize * (double) xMinus1 + (xCellSize / 2),  // center by x
                            yCellSize * (double) yMinus2 + (yCellSize / 2),  // center by y
                            xCellSize / 2, yCellSize / 2);              // half-sizes x/y
                    JLife.doSleep();
                    StdDraw.save("Pic" + "MAP"+String.valueOf(x)+"%"+String.valueOf(y) + ".png");
                }
                //----------------------- Logic
                if(x==0){
                    counter[wrap]=yM1Sum+yP1Sum+ySum-xy;
                }
                else{
                    counter[wrap]+=(counter[wrap-1]-yM2Sum+xyM1-xy+yP1Sum);
                }
                //------------------ Processing by the rules
                bField[x][y]=aField[x][y];
                if(aField[x][y].isAlive()){
                    if((counter[wrap]==2)||(counter[wrap]==3))      bField[x][y].increaseAge();
                    else                                            bField[x][y].revStatus();
                }
                else{
                    if(counter[wrap]==3)                            bField[x][y].revStatus();
                    else                                            bField[x][y].increaseAge();
                }
            }
        }
        aField=bField;
    }

    public void reDraw(){
        countFriends();
        StdDraw.clear();
        this.drawGrid(xMin,yMin,xMax,yMax);
        for(int i=0;i<xGridSize;i++){
            for(int j=0;j<yGridSize;j++){
                if(aField[i][j].isAlive()){                                               // draw alive (>0)
                    try{ StdDraw.setPenColor(aField[i][j].getAge()*20,aField[i][j].getAge()*10,0);}
                    catch(IllegalArgumentException a){
                        try{ StdDraw.setPenColor(255,aField[i][j].getAge()*20,0);}
                        catch(IllegalArgumentException b){
                            StdDraw.setPenColor(255,255,0);
                        }
                    }
                    StdDraw.filledRectangle(xMax/xGridSize*(double)i+(xMax/xGridSize/2),  // center by x
                            yMax/yGridSize*(double)j+(yMax/yGridSize/2),                  // center by y
                            xMax/xGridSize/2,yMax/yGridSize/2);                           // half-sizes x/y
                }
            }
        }

    }

    public void drawGrid(double xMi, double yMi, double xMa, double yMa){
        xMin=xMi;yMin=yMi;xMax=xMa;yMax=yMa;
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        for(int i=1;xMax!=xMin+xMax/xGridSize*i;i++)
            StdDraw.line(xMin+xMax/xGridSize*i,yMin,xMin+xMax/xGridSize*i,yMax);
        for(int i=1;yMax!=yMin+yMax/yGridSize*i;i++)
            StdDraw.line(xMin,yMin+yMax/yGridSize*i,xMax,yMin+yMax/yGridSize*i);
    }
}