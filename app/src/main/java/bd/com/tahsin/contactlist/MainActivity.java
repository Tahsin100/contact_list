package bd.com.tahsin.contactlist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    String TAG = "TAG->";
    DBHelper dbHelper;
    RecyclerView rvContacts;
    ArrayList<String> name;
    ArrayList<String> number;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int index = 0;
    private ImageView cirIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this, null, null, 1);
        cirIv = (ImageView)findViewById(R.id.cirIv);
        Picasso.with(this).load(R.drawable.pass_pic2).transform(new CircleTransform()).into(cirIv);

        name = new ArrayList<>();
        number = new ArrayList<>();
        rvContacts = (RecyclerView)findViewById(R.id.rvContacts);
        mLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );
        rvContacts.setLayoutManager(mLayoutManager);


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            } else {
                LoadContactList();
            }
        } else {
            LoadContactList();
        }


      //  setAdapter(index);

    }


    public void LoadContactList(){

        if(dbHelper.isDBexists() == 0) {
            getAllContactNo();
        }
        List<String> getInfo = dbHelper.FetchContactList(index);
        for(int i = 0; i < getInfo.size(); i++){
            String[] separated = getInfo.get(i).split(":");
            name.add(separated[0]);
            number.add(separated[1]);

        }
        mAdapter = new CustomRecyclerViewAdapter(MainActivity.this , name, number);
        rvContacts.setAdapter(mAdapter);
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void setAdapter(){
        index+=10;
        List<String> getInfo = dbHelper.FetchContactList(index);
        for(int i = 0; i < getInfo.size(); i++){
            if(getInfo.get(i).equals("end")){
                break;
            }
            String[] separated = getInfo.get(i).split(":");
            if(i < getInfo.size()-1) {
                if (getInfo.get(i + 1).equals("end")) {
                    name.add(separated[0]);
                    number.add(separated[1] + ":" + "end");
                    continue;
                }
            }
            name.add(separated[0]);
            number.add(separated[1]);
        }

        mAdapter.notifyDataSetChanged();
    }


    public void getAllContactNo(){
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            User user = new User();
            user.setName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            user.setNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            ContactList contactList = new ContactList(user.getName().toString(),user.getNumber().toString());
            dbHelper.addContactList(contactList);
            Log.d(TAG, "Name : " + user.getName().toString());
            Log.d(TAG, "Phone : " + user.getNumber().toString());
        }
        phones.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   LoadContactList();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
                }
                return;

            }
        }
    }


}

