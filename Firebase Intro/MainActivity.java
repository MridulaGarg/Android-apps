package app.firebase.intro.com.firebaseintro;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";

    private EditText email;
    private EditText password;
    private Button loginButton;
    private Button logoutButton;
    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.emailId);
        password = (EditText) findViewById(R.id.passId);
        loginButton = (Button) findViewById(R.id.loginButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        createAccount = (Button) findViewById(R.id.createAct);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");

        databaseReference.setValue("Hello Firebase");
        databaseReference.setValue("Another ");

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(Customer.class);
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //user is signed in
                    Log.d(TAG, "user signed in");
                    Log.d(TAG, "userName: " + user.getEmail());
                }else {
                    //user is signed out
                    Log.d(TAG, "user signed out");
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailstring = email.getText().toString();
                String pwd = password.getText().toString();

                if (!emailstring.equals("") && !pwd.equals("")) {
                    mAuth.signInWithEmailAndPassword(emailstring, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Failed Sign in...", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Signed in!!!", Toast.LENGTH_LONG).show();

                                Customer customer = new Customer("Gina", "Machaval", emailstring, 78);

                                //we can write to database
                                databaseReference.setValue(customer);
                            }
                        }
                    });
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "You signed out!!!", Toast.LENGTH_LONG).show();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailstring = email.getText().toString();
                String pwd = password.getText().toString();

                if (!emailstring.equals("") && !pwd.equals("")) {
                    mAuth.createUserWithEmailAndPassword(emailstring, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Failed to createaccount...", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
