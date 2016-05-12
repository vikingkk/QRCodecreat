package com.example.xiezhehao.qrcodecreat;

import java.util.EnumMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public Button button01;
    public Button button02;
    TextView txtCount;
 int ax=1;
    int i = 0;
    int asd=1;
    int xx=1;
    Thread countToTen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //QRCode的內容
        countToTen = new CountToTen();

        button01 = (Button)findViewById(R.id.button);
        //QRCode的寬度
        button02 = (Button)findViewById(R.id.button2);
        txtCount = (TextView) findViewById(R.id.txtCount);
        class CountToTen extends Thread {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cacaw();
                xx=2;

                countToTen.start();//開始thread
                    //Context context = this;
                    //String text = "顯示Toast訊息";
                    //String str = String.valueOf(ax);
                    //int duration = Toast.LENGTH_SHORT;

                    //Toast.makeText( MainActivity.this , str , duration).show();







            }
        });
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cacaw();
                xx=1;
            }
        });








    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (asd == 1)
            {
                cacaw();
                asd++;
            }
            txtCount.setText(Integer.toString(msg.getData().getInt("count", 0)));
        }
    };

    public void cacaw()
    {
        try {
            String QRCodeContent = "http://www.google.com.tw/";
            int QRCodeWidth = 120;
            //QRCode的高度
            int QRCodeHeight = 120;
            //QRCode內容的編碼
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            MultiFormatWriter writer = new MultiFormatWriter();
            //建立QRCode的資料矩陣
            BitMatrix result= writer.encode(QRCodeContent, BarcodeFormat.QR_CODE, QRCodeWidth, QRCodeHeight, hints);
            //建立點陣圖
            Bitmap bitmap = Bitmap.createBitmap(QRCodeWidth, QRCodeHeight, Bitmap.Config.ARGB_8888);
            //將QRCode資料矩陣繪製到點陣圖上
            for (int y = 0; y < QRCodeHeight; y++) {
                for (int x = 0; x < QRCodeWidth; x++) {
                    bitmap.setPixel(x, y, result.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            //建立新ImageView
            ImageView imgView = new ImageView(MainActivity.this);
            //設定為QRCode影像
            imgView.setImageBitmap(bitmap);
            //加到畫面上
            addContentView(imgView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class CountToTen extends Thread { //thread 內容

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {

                for (i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    Bundle countBundle = new Bundle();
                    countBundle.putInt("count", i+1);

                    Message msg = new Message();
                    msg.setData(countBundle);

                    mHandler.sendMessage(msg);

                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
