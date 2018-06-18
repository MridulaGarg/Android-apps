package app.blog.my.com.blogapp.Activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import app.blog.my.com.blogapp.R;

public class PostDetActivity extends AppCompatActivity {

    private ImageView img;
    private TextView pTitle;
    private TextView pDesc;
    private TextView pDate;
    String time;
    String uid;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_det);

        img = (ImageView) findViewById(R.id.postImageDet);
        pTitle = (TextView) findViewById(R.id.postTitleDet);
        pDesc = (TextView) findViewById(R.id.postDescDet);
        pDate = (TextView) findViewById(R.id.postDateDet);
        uid = null;
        url = null;

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            url = bundle.getString("image");
            pTitle.setText(bundle.getString("title"));
            pDesc.setText(bundle.getString("desc"));
            uid = bundle.getString("userid");

            time = bundle.getString("timestamp");
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(Long.valueOf(time)));
            pDate.setText(formattedDate);

            Picasso.with(this)
                    .load(url)
                    .into(img);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.det_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        StringBuilder dataString = new StringBuilder();

        String  imagUrl = url.toString();
        String title = pTitle.getText().toString();
        String desc = pDesc.getText().toString();
        String dat = pDate.getText().toString();

        dataString.append(" Image: " + imagUrl + "\n\n");
        dataString.append(" Title: " + title + "\n");
        dataString.append(" Description: " + desc + "\n\n");
        dataString.append(" Date: " + dat);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "Shared Post");
        i.putExtra(Intent.EXTRA_EMAIL, new String[] {"recipient@example.com"});
        i.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try{

            startActivity(Intent.createChooser(i, "Send mail..."));

        }catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Please install email client before sending",
                    Toast.LENGTH_LONG).show();
        }
        
        return super.onOptionsItemSelected(item);
    }
}
