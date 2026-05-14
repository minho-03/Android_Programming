package com.cookandroid.helloandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView text1, text2;
    CheckBox chkAgree;
    RadioGroup rGroup;
    RadioButton rdoDog, rdoCat, rdoRabbit;
    Button btnOK;
    ImageView imgPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("애완동물 사진 보기");

        chkAgree = (CheckBox) findViewById(R.id.chkAgree);
        text2 = (TextView) findViewById(R.id.text2);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        rdoDog = (RadioButton) findViewById(R.id.rdoDog);
        rdoCat = (RadioButton) findViewById(R.id.rdoCat);
        rdoRabbit = (RadioButton) findViewById(R.id.rdoRabbit);
        btnOK = (Button) findViewById(R.id.btnOK);
        imgPet = (ImageView) findViewById(R.id.imgPet);

        chkAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    text2.setVisibility(View.VISIBLE);
                    rGroup.setVisibility(View.VISIBLE);
                    btnOK.setVisibility(View.VISIBLE);
                    imgPet.setVisibility(View.VISIBLE);
                } else {
                    text2.setVisibility(View.INVISIBLE);
                    rGroup.setVisibility(View.INVISIBLE);
                    btnOK.setVisibility(View.INVISIBLE);
                    imgPet.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = rGroup.getCheckedRadioButtonId();

                if (checkedId == R.id.rdoDog) {
                    imgPet.setImageResource(R.drawable.dog2);
                } else if (checkedId == R.id.rdoCat) {
                    imgPet.setImageResource(R.drawable.cat2);
                } else if (checkedId == R.id.rdoRabbit) {
                    imgPet.setImageResource(R.drawable.rabbit);
                } else {
                    Toast.makeText(getApplicationContext(), "동물을 먼저 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}