import edu.princeton.cs.introcs.StdRandom;
import java.util.*;
/**
 * Created by .Rain on 19.03.2016.
 */
public class GameCell {
    private int age;
    private boolean alive;
    private int friends;    // =neighborhood

    GameCell(){
        age=    (int)(10*Math.random()%2);

        if(age>0)       alive=true;
        else            alive=false;
    }
    GameCell(boolean value){
        age=            value?1:0;
        alive=          value;
    }

    int     getAge()        { return age;                   }
    void    increaseAge()   { if(alive) ++age; else --age;  }
    boolean isAlive()       { return alive;                 }
    void    revStatus()     { alive=!alive; age=0;          }

}
