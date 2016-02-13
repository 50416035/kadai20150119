package reservation;

import java.awt.Choice;

public class ChoiceHour extends Choice{
ChoiceHour(){
        //時刻の�?囲の初期値は 9時�??21時とする
        resetRange(9,21);
    }

    public void resetRange( int start, int end){
        // �?囲に含まれる時刻の数を求め�?
        int number = end - start +1;
        // 選択�?�゙�?クスの�?容をクリアする
        removeAll();
        // �?定できる�?囲の時刻を設定す�?
        while (start<=end){
            String h = String.valueOf(start);
            //�?桁�?�場�?,前に0を付け�?
            if ( h.length()==1){
                h = "0" + h;
            }
            // 選択�?�゙�?クスに追�? (こちら�?��?字�??)
            add(h);
            // start�?1増や�?
            start++;
        }
    }
    // �?後に設定されて�?る時刻を返す
    public String getLast(){
        return getItem( getItemCount()-1 );

    }



}
