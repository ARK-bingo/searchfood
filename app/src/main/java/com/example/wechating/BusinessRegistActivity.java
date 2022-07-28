package com.example.wechating;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wechating.domain.User;
import com.example.wechating.utils.UserUtil;

/**
 * @author _BiN
 * @function 商家注册界面
 */
public class BusinessRegistActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText businessNameText;
    private EditText businessEmailText;
    private EditText businessPasswordText;
    private EditText businessPhoneText;
    private Button businessRegBtn;
    private TextView error_tip_business;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_regist);
        businessRegBtn = (Button)findViewById(R.id.businessRegist);
        businessNameText = (EditText) findViewById(R.id.businessNameText);
        businessEmailText = (EditText) findViewById(R.id.businessEmailText);
        businessPasswordText = (EditText) findViewById(R.id.businessPasswordText);
        businessPhoneText = (EditText) findViewById(R.id.businessPhoneText);
        error_tip_business = (TextView) findViewById(R.id.error_tip_business);
        businessRegBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.businessRegist){
            User user = new User(businessNameText.getText().toString(), businessPasswordText.getText().toString());
            boolean res = false;
            String emailReg = "/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/";
            String phoneReg = "/^(?:(?:\\+|00)86)?1[3-9]\\d{9}$/";
            res = UserUtil.addUser(user);
            if(!res){
                error_tip_business.setText("商家名称已经存在，请重新输入");
            } else if(!businessEmailText.getText().toString().matches(emailReg)){
                error_tip_business.setText("邮箱格式有误，请重新输入");
            } else if(!businessPhoneText.getText().toString().matches(phoneReg)){
                error_tip_business.setText("手机号输入有误，请重新输入");
            } else {
                error_tip_business.setText("");
                Toast.makeText(BusinessRegistActivity.this, "注册成功，现在可以去登录了",Toast.LENGTH_SHORT).show();
                //提醒注册成功，自动跳转至登录界面
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
