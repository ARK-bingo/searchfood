package com.example.wechating;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.wechating.AddFriendPage;
import com.example.wechating.Index;
import com.example.wechating.R;
import com.example.wechating.ui.friends.FriendsFragment;

import java.util.ArrayList;
import java.util.List;

public class contact extends AppCompatActivity{
    ListView contactsView;
    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        contactsView=(ListView) findViewById(R.id.contest_view);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
                ,contactsList);
        contactsView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    contact.this,new String[]{Manifest.permission.READ_CONTACTS},1);
        } else {
            readContacts();
        }


    }

    private void readContacts()
    {
        Cursor cursor=null;
        try {
            cursor =getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            while (cursor.moveToNext()) {
                int i_name=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String displayName = cursor.getString(i_name);
                int i_number=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(i_number);
                contactsList.add(displayName + " " + number);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if (cursor!=null)
            {
                cursor.close();
            }
        }
    }


//    @Override
//    public void onClick(View view) {
//        Toast.makeText(contact.this,"恭喜，添加成功!",Toast.LENGTH_LONG).show();
//        Intent intent=new Intent(contact.this, Index.class);
//        startActivity(intent);
//        contact.this.finish();
//    }
}
