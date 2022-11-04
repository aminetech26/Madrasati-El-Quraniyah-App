package com.example.madrasatielquraniyah;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText firstname_input, lastname_input, email_input;
    Button update_button, delete_button, presence_button,absence_button;

    String id, firstname, lastname, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        firstname_input = findViewById(R.id.input_firstname2);
        lastname_input = findViewById(R.id.input_lastname2);
        email_input = findViewById(R.id.input_email2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        presence_button = findViewById(R.id.presence_button);
        absence_button = findViewById(R.id.absence_button);
        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(firstname);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                firstname = firstname_input.getText().toString().trim();
                lastname = lastname_input.getText().toString().trim();
                email = email_input.getText().toString().trim();
                myDB.updateData(id, firstname, lastname, email);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
        presence_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstname = firstname_input.getText().toString().trim();
                lastname = lastname_input.getText().toString().trim();
                email = email_input.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {email});
                String subject ="تسجيل حضور الطالب " + lastname + " " + firstname ;
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                String message = "السلام عليكم و رحمة الله تعالى و بركاته، " +
                        "يسر إدارة المدرسة القرآنية إعلامكم أن ابنكم " + firstname+ " " + lastname + " قد سجل حضوره اليوم بتاريخ " + dateFormat.format(date) + "، خالص التحية .";
                intent.putExtra(Intent.EXTRA_TEXT, message);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select email"));
            }
        });
        absence_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstname = firstname_input.getText().toString().trim();
                lastname = lastname_input.getText().toString().trim();
                email = email_input.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
                String subject ="تسجيل غياب الطالب " + lastname + " " + firstname ;
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                String message = "السلام عليكم و رحمة الله تعالى و بركاته، " +
                        "تعلمكم إدارة المدرسة القرآنية أن ابنكم " + firstname+ " " + lastname + " قد تغيب اليوم بتاريخ " + dateFormat.format(date) + "، خالص التحية .";
                intent.putExtra(Intent.EXTRA_TEXT, message);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select email"));
            }
        });
    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("firstname") &&
                getIntent().hasExtra("lastname") && getIntent().hasExtra("email")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            firstname = getIntent().getStringExtra("firstname");
            lastname = getIntent().getStringExtra("lastname");
            email = getIntent().getStringExtra("email");

            //Setting Intent Data
            firstname_input.setText(firstname);
            lastname_input.setText(lastname);
            email_input.setText(email);
            Log.d("amine", firstname+" "+lastname+" "+email);
        }else{
            Toast.makeText(this, "لا يوجد بيانات", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" حذف " + firstname + " ؟ ");
        builder.setMessage(" هل أنت متأكد من أنك تريد حذف " + firstname + " ؟ ");
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}

