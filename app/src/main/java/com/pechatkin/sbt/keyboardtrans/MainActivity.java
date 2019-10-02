package com.pechatkin.sbt.keyboardtrans;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final int PASS_BAD = 0;
    public static final int PASS_NOT_BAD = 1;
    public static final int PASS_GOOD = 2;
    public static final int PASS_PENTAGON = 3;

    private EditText sourseEditText;

    private TextView subEditText;
    private TextView resultTextView;
    private TextView resultTextViewGenerate;
    private TextView textSeek;

    private ImageView checkPass;

    private String[] russians;
    private String[] latin;
    private int seekProgress;
    private PasswordsHelper helper;

    private View coppyButton;
    private View generateCopyButton;
    private View generateButton;
    private CheckBox checkCaps;
    private CheckBox checkNums;
    private CheckBox checkSimvs;

    private SeekBar seekBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourseEditText = findViewById(R.id.edit_source);
        subEditText = findViewById(R.id.sub_edit_sourse);
        resultTextView = findViewById(R.id.text_result);
        resultTextViewGenerate = findViewById(R.id.text_result_bottom);
        textSeek = findViewById(R.id.text_undo_seekBar);

        russians = getResources().getStringArray(R.array.russians);
        latin = getResources().getStringArray(R.array.latin);

        coppyButton = findViewById(R.id.button_copy_password);
        coppyButton.setEnabled(false);
        generateCopyButton = findViewById(R.id.button_copy_password_bottom);
        generateCopyButton.setEnabled(false);
        checkPass = findViewById(R.id.check_pass_view);
        generateButton = findViewById(R.id.button_generate);

        helper = new PasswordsHelper(russians, latin);

        checkCaps = findViewById(R.id.check_caps);
        checkNums = findViewById(R.id.check_nums);
        checkSimvs = findViewById(R.id.check_simvs);

        seekBar = findViewById(R.id.seekBar);


        coppyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ClipboardManager manager =(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
               if(manager != null) {
                   manager.setPrimaryClip(ClipData.newPlainText
                           (getString(R.string.clipBoard_title), resultTextView.getText().toString()));
               }
                Toast.makeText(MainActivity.this, R.string.toast_copied,Toast.LENGTH_SHORT).show();
            }
        });

        generateCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager =(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if(manager != null) {
                    manager.setPrimaryClip(ClipData.newPlainText
                            (getString(R.string.clipBoard_title), resultTextViewGenerate.getText().toString()));
                }
                Toast.makeText(MainActivity.this, R.string.toast_copied,Toast.LENGTH_SHORT).show();
            }
        });

        sourseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultTextView.setText(helper.convert(s));
                int lenghtPass = s.length();
                coppyButton.setEnabled(lenghtPass > 0);
                subEditText.setText(helper.check(resultTextView.getText()));
                if(lenghtPass < 4) {
                    checkPass.setImageLevel(PASS_BAD);
                } else if((lenghtPass >= 4) && (lenghtPass < 8)) {
                    checkPass.setImageLevel(PASS_NOT_BAD);
                } else if((lenghtPass >= 8) && (lenghtPass < 12)) {
                    checkPass.setImageLevel(PASS_GOOD);
                } else {
                    checkPass.setImageLevel(PASS_PENTAGON);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSeek.setText("Символов: " + progress);
                seekProgress = progress;
                generateButton.setEnabled(progress > 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder genResult = new StringBuilder();
                for(int i = 0; i< seekProgress; i++) {
                    genResult.append("a");
                }
                if(checkCaps.isChecked() && genResult.indexOf("a") != -1) {
                    genResult.replace(genResult.indexOf("a"), genResult.indexOf("a")+1, "A");
                }
                if(checkNums.isChecked() && genResult.indexOf("a") != -1) {
                    genResult.replace(genResult.indexOf("a"), genResult.indexOf("a")+1, "1");
                }
                if(checkSimvs.isChecked() && genResult.indexOf("a") != -1) {
                    genResult.replace(genResult.indexOf("a"), genResult.indexOf("a")+1, "%");
                }
                resultTextViewGenerate.setText(genResult);
                generateCopyButton.setEnabled(resultTextViewGenerate.length() > 0);
            }
        });

    }
}
