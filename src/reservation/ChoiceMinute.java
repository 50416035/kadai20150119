package reservation;

import java.awt.Choice;

public class ChoiceMinute extends Choice {

public ChoiceMinute(){
        // 00�?と30�?のみを登録
        add("00");
        add("30");
    }

    //選択されて�?る�?を整数で返す
    public int getMinute(int index){
        if ( index==0){
            return 0;
        } else {
            return 30;
        }
    }



}

