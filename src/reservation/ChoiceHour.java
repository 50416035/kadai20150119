package reservation;

import java.awt.Choice;

public class ChoiceHour extends Choice{
ChoiceHour(){
        //æå»ã®ç¯?å²ã®åæå¤ã¯ 9æã??21æã¨ãã
        resetRange(9,21);
    }

    public void resetRange( int start, int end){
        // ç¯?å²ã«å«ã¾ããæå»ã®æ°ãæ±ãã?
        int number = end - start +1;
        // é¸æã?ãã?ã¯ã¹ã®å?å®¹ãã¯ãªã¢ãã
        removeAll();
        // æ?å®ã¦ãããç¯?å²ã®æå»ãè¨­å®ãã?
        while (start<=end){
            String h = String.valueOf(start);
            //ä¸?æ¡ã?®å ´å?,åã«0ãä»ãã?
            if ( h.length()==1){
                h = "0" + h;
            }
            // é¸æã?ãã?ã¯ã¹ã«è¿½å? (ãã¡ãã?¯æ?å­å??)
            add(h);
            // startã?1å¢ãã?
            start++;
        }
    }
    // æ?å¾ã«è¨­å®ããã¦ã?ãæå»ãè¿ã
    public String getLast(){
        return getItem( getItemCount()-1 );

    }



}
