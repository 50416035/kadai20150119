package reservation;

import java.awt.Dialog;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class ReservationControl	{


	//�?定した日,施設の 空き状�?(と�?�?か予�?状�?)

	    public String getReservationOn( String facility, String ryear_str, String rmonth_str, String rday_str){
	        String res = "";
	        // 年月日が数字かどうかををチェ�?クする処�?
	        try {
	            int ryear = Integer.parseInt( ryear_str);
	            int rmonth = Integer.parseInt( rmonth_str);
	            int rday = Integer.parseInt( rday_str);
	        } catch(NumberFormatException e){
	            res ="年月日には数字を指定してください?";
	            return res;
	        }
	        res = facility + " 予約状況\n\n";

	        // 月と日が�?桁だったら,前に0をつける処�?
	        if (rmonth_str.length()==1) {
	            rmonth_str = "0" + rmonth_str;
	        }
	        if ( rday_str.length()==1){
	            rday_str = "0" + rday_str;
	        }
	        //SQL で検索するための年月日のフォーマット�?��?字�?�を作�?�する�?��?
	        String rdate = ryear_str + "-" + rmonth_str + "-" + rday_str;

	        //(1) MySQL を使用する準備
	        //connectDB();
	        MySQL mysql = new MySQL();

	        //(2) MySQLの操�?(SELECT�?の実�?)
	        try {
	            // 予�?�?報を取得するクエリ
	            ResultSet rs = mysql.getReservation(rdate, facility);
	            boolean exist = false;
	            while(rs.next()){
	                String start = rs.getString("start_time");
	                String end = rs.getString("end_time");
	                res += " " + start + " -- " + end + "\n";
	                exist = true;
	            }

	            if ( !exist){ //予�?が1つも存在しな�?場合�?�処�?
	                res = "予約はありません";	            
	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return res;
	    }

	    String reservation_userid;
	    private boolean flagLogin;

	    ReservationControl(){
	        flagLogin = false;

	    }
	  //ログイン・ログアウト�?�゙タンの処�?
	     public String loginLogout(MainFrame frame){
	          String res=""; //結果を�?�れる変数
	          if ( flagLogin){ //ログアウトを行う処�?
	        	  flagLogin = false;
	              frame.buttonLog.setLabel(" ログイン "); //ログインを行う処�?
	          } else {
	              //ログインダイアログ�?�生�?�と表示
	              LoginDialog ld = new LoginDialog(frame);
	              ld.setVisible(true);
	              ld.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
	              //IDとパスワードの入力がキャンセルされたら,空�?字�?�を結果として終�?
	              if ( ld.canceled){
	                  return "";
	              }
	              //ユーザIDとパスワードが入力された場合�?�処�?
	              //ユーザIDは他�?�機�?�のときに使用するのでメンバー変数に代入
	              reservation_userid = ld.tfUserID.getText();
	              //パスワードはここでしか使わな�?のて�?,ローカル変数に代入
	              String password = ld.tfPassword.getText();
	            //(2) MySQLの操�?(SELECT�?の実�?)
	                        try { // userの�?報を取得するクエリ
	                            MySQL mysql = new MySQL();
	                            ResultSet rs = mysql.getLogin(reservation_userid); 
	                            if (rs.next()){
	                                rs.getString("password");
	                                String password_from_db = rs.getString("password");
	                                if ( password_from_db.equals(password)){ //認証成功:�?゙�?�タベースのIDとパスワードに�?致
	                                    flagLogin = true;
	                                    frame.buttonLog.setLabel("ログアウト");
	                                    res = "";
	                                }else {
	                                    //認証失�?:パスワードが不�?致
	                                    res = "ログインできません.IDとパスワードが一致しません";
	                                }
	                            } else { //認証失�?;ユーザIDが�?゙�?�タベースに存在しな�?
	                                res = "ログインできません.ID パスワードが違います";
	                            }

	                        } catch (Exception e) {
	                            e.printStackTrace();
	
	          }
	         return res;
	      }
			return res;
	   }

private boolean checkReservationDate( int y, int m, int d){
        // 予�?日
        Calendar dateR = Calendar.getInstance();
        dateR.set( y, m-1, d);    // 月か�?1引かなければならな�?ことに注意�?
        // 今日の?��日�?
        Calendar date1 = Calendar.getInstance();
        date1.add(Calendar.DATE, 1);
        // 今日の?��ヶ月後�?90日�?)
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.DATE, 90);
        if ( dateR.after(date1) && dateR.before(date2)){
            return true;
        }
        return false;

 }

public String makeReservation(MainFrame frame){
        String res="";        //結果を�?�れる変数

        if ( flagLogin){ // ログインして�?た�?��?
            //新規予�?画面作�??
            ReservationDialog rd = new ReservationDialog(frame);


            // 新規予�?画面の予�?日に?��メイン画面に設定されて�?る年月日を設定す�?
            rd.tfYear.setText(frame.tfYear.getText());
            rd.tfMonth.setText(frame.tfMonth.getText());
            rd.tfDay.setText(frame.tfDay.getText());

            // 新規予�?画面を可視化
            rd.setVisible(true);
            if ( rd.canceled){
                return res;
            }

            try {
                //新規予�?画面から年月日を取�?
                String ryear_str = rd.tfYear.getText();
                String rmonth_str = rd.tfMonth.getText();
                String rday_str = rd.tfDay.getText();

                // 年月日が数字かど�?かををチェ�?クする処�?
                int ryear = Integer.parseInt( ryear_str);
                int rmonth = Integer.parseInt( rmonth_str);
                int rday = Integer.parseInt( rday_str);

                if ( checkReservationDate( ryear, rmonth, rday)){    // 期間の条件を�?たして�?る�?��?
                    // 新規予�?画面から施設名，開始時刻?��終�?時刻を取�?
                    String facility = rd.choiceFacility.getSelectedItem();
                    String st = rd.startHour.getSelectedItem()+":" + rd.startMinute.getSelectedItem() +":00";
                    String et = rd.endHour.getSelectedItem() + ":" + rd.endMinute.getSelectedItem() +":00";

                    if( st.equals(et)){        //開始時刻と終�?時刻が等し�?
                        res = "開始時刻と終了時刻が同じです";
                    } else {

                        try {
                            // 月と日が�?桁だったら?��前に0をつける処�?
                            if (rmonth_str.length()==1) {
                                rmonth_str = "0" + rmonth_str;
                            }
                            if ( rday_str.length()==1){
                                rday_str = "0" + rday_str;
                            }
                            //(2) MySQLの操�?(SELECT�?の実�?)
                            String rdate = ryear_str + "-" + rmonth_str + "-" + rday_str;

                            MySQL mysql = new MySQL();
                            ResultSet rs = mysql.selectReservation(rdate, facility);
                              // 検索結果に対して重なりチェ�?クの処�?
                              boolean ng = false;    //重なりチェ�?クの結果の初期値?��重なって�?な�?=false?��を設�?
                              // 取得したレコード�?つ�?つに対して確�?
                              while(rs.next()){
                                      //レコード�?�開始時刻と終�?時刻をそれぞれstartとendに設�?
                                    String start = rs.getString("start_time");
                                    String end = rs.getString("end_time");
                                    if ( (start.compareTo(st)<0 && st.compareTo(end)<0) ||        //レコード�?�開始時刻?��新規�?�開始時刻�?AND�?新規�?�開始時刻?��レコード�?�終�?時刻
                                         (st.compareTo(start)<0 && start.compareTo(et)<0)){        //新規�?�開始時刻?��レコード�?�開始時刻�?AND�?レコード�?�開始時刻?��新規�?�開始時刻
                                             // 重�?有りの場合に ng をtrueに設�?
                                        ng = true; break;
                                    }
                              }
                              /// 重なりチェ�?クの処�?�?ここまで  ///////

                              if (!ng){    //重なって�?な�?場�?
                                  int rs_int = mysql.setReservation(rdate, st, et, res, facility);
                                  res ="予約されました";

                              }else {    //重なって�?た�?��?
                                  res = "既にある予約に重なっています";
                              }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    res = "予約日が無効です";
                }
            } catch(NumberFormatException e){
                res ="予約日には数字を指定してください";
            }
        } else { // ログインして�?な�?場�?
            res = "ログインしてください";
        }
        return res;
    }
}








