package reservation;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ReservationDialog extends Dialog implements ActionListener, WindowListener,ItemListener{
	
	boolean canceled;        //キャンセルされたら true 予�?実行�?�タンのとき�?� false
    // パネル
    	Panel panelNorth;
    	Panel panelMid;
    	Panel panelSouth;

    // 入力用コンポ�?�ネン�?
    	ChoiceFacility choiceFacility;    // 施設選択用ボックス
    	TextField tfYear,tfMonth,tfDay;    // 年月日の�?キストフィール�?
    	ChoiceHour startHour;            // 予�?開始時刻�?時�?�選択用ボックス
    	ChoiceMinute startMinute;        // 予�?開始時刻�?�?の選択用ボックス
    	ChoiceHour endHour;                // 予�?終�?時刻 �?時�?�選択用ボックス
    	ChoiceMinute endMinute;            // 予�?終�?時刻�?�?の選択用ボックス

    // ボタン
    	Button buttonOK;
    	Button buttonCancel;
    	
	public ReservationDialog(Frame owner) {
		// TODO Auto-generated constructor stub

		//基底クラス(Dialog)のコンストラクタを呼び出�?
		        super(owner, "新規予約", true);
		        //キャンセルは初期値ではtrueとしておく
		        canceled = true;
		        //施設選択�?�ックスの生�??
		        choiceFacility = new ChoiceFacility();
		        //�?キストフィールド�?�生�?��??年月日
		        tfYear = new TextField("",4);
		        tfMonth = new TextField("",2);
		        tfDay = new TextField("",2);
		        //開始時刻�?時�?選択�?�ックスの生�??
		        startHour = new ChoiceHour();
		        startMinute = new ChoiceMinute();
		        //終�?時刻�?自�?選択�?�ックスの生�??
		        endHour = new ChoiceHour();
		        endMinute = new ChoiceMinute();

		        //ボタンの生�??
		        buttonOK = new Button("予約実行");
		        buttonCancel = new Button("キャンセル");

		        //パネルの生�??
		        panelNorth = new Panel();
		        panelMid = new Panel();
		        panelSouth = new Panel();

		        //上部パネルに施設選択�?�ックス?��年月日入力�?を追�?
		        panelNorth.add( new Label("施設"));
		        panelNorth.add(choiceFacility);
		        panelNorth.add( new Label("予約日 "));
		        panelNorth.add(tfYear);
		        panelNorth.add(new Label("年"));
		        panelNorth.add(tfMonth);
		        panelNorth.add(new Label("月"));
		        panelNorth.add(tfDay);

		        panelNorth.add(new Label("日"));


		        //中央パネルに予�?�?開始時刻?��終�?時刻入力用選択�?�ックスを追�?
		        panelMid.add( new Label("予約時間"));
		        panelMid.add( startHour);
		        panelMid.add( new Label("時"));
		        panelMid.add( startMinute);
		        panelMid.add( new Label("分　~　"));
		        panelMid.add( endHour);
		        panelMid.add( new Label("時"));
		        panelMid.add( endMinute);
		        panelMid.add( new Label("分"));


		        //下部パネルに2つのボタンを追�?
		        panelSouth.add(buttonCancel);
		        panelSouth.add( new Label("   "));
		        panelSouth.add(buttonOK);

		        // ReservationDialogをBorderLayoutに設定し?�?3つのパネルを追�?
		        setLayout(new BorderLayout());
		        add(panelNorth, BorderLayout.NORTH);
		        add(panelMid,BorderLayout.CENTER);
		        add(panelSouth, BorderLayout.SOUTH);

		        // ウィンドウリスナを追�?
		        addWindowListener(this);
		        // ボタンにアクションリスナを追�?
		        buttonOK.addActionListener(this);
		        buttonCancel.addActionListener(this);
		        //施設選択�?�ックス?��時・�?選択�?�ックスそれぞれに�?目リスナを追�?
		        choiceFacility.addItemListener(this);
		        startHour.addItemListener(this);
		        startMinute.addItemListener(this);
		        endHour.addItemListener(this);
		        endMinute.addItemListener(this);
		        // 選択されて�?る施設によって?��時刻の�?囲を設定する�?
		        resetTimeRange();
		        // 大きさの設定，ウィンドウのサイズ変更不可の設�?
		        this.setBounds( 100, 100, 500, 120);
		        setResizable(false);

		    }
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
        setVisible(false);
        dispose();
    }
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==buttonCancel){
			setVisible(false);
			dispose();
	 } else if(e.getSource()==buttonOK){
		 canceled=false;
		 setVisible(false);
		 dispose();
		}
	}

    private void resetTimeRange() {
        // 選択されて�?る施設によって?��時刻の�?囲を設定する�?
        if ( choiceFacility.getSelectedIndex()==0){
            // �?初�?�施設(小�?��?�ルのと�?)の設�?
            startHour.resetRange(10, 20);
            endHour.resetRange(10, 21);
        } else {
            // 小�?��?�ル以外�?�設�?
            startHour.resetRange(9, 19);
            endHour.resetRange(9, 20);
        }
    }
    

    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        if ( e.getSource()==choiceFacility){
            // 施設が変更されたら?��施設に応じた�?囲を設�?
            resetTimeRange();
        } else if ( e.getSource()==startHour){
            //開始時刻が変更されたら?��終�?時刻入力�?の時を開始時刻に合わせる
            int start = Integer.parseInt( startHour.getSelectedItem());
            endHour.resetRange(start, Integer.parseInt( endHour.getLast()));
        } else if ( e.getSource()==endHour){
            //終�?時刻が変更され?��最後�?�時刻の場合，�?は 00�?に設�?
            if ( endHour.getSelectedIndex()==endHour.getItemCount()-1){
                endMinute.select(0);
            }
        } if( e.getSource()==endMinute){
            //終�?時刻?���??��が変更され?��時が最後�?�場合，�?は 00�?に設�?
            if ( endHour.getSelectedIndex()==endHour.getItemCount()-1){
                endMinute.select(0);
            }
        }
    }
}
    


