package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

import static com.example.myapplication.Keys.getPrivateKey;
import static com.example.myapplication.Keys.getPublicKey;
import static com.example.myapplication.Keys.initKey;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText et2;
    private EditText et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.btn1);
//        Button btn2 = (Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
//        btn2.setOnClickListener(this);

//        EditText et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            // 加密
            case R.id.btn1:
                Map<String, Object> keyMap;
                try {
                    keyMap = Keys.initKey();
                    String publicKey = Keys.getPublicKey(keyMap);
                    System.out.println("公钥" + publicKey);
                    et2.setText("公钥 \n" + publicKey);
                    String privateKey = Keys.getPrivateKey(keyMap);
                    System.out.println("私钥" + privateKey);
                    et3.setText("私钥\n" + privateKey);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

}
