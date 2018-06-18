package app.blog.my.com.blogapp.Data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import app.blog.my.com.blogapp.Activities.PostDetActivity;
import app.blog.my.com.blogapp.Model.Blog;
import app.blog.my.com.blogapp.R;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Blog> blogList;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Blog blog = blogList.get(position);

        holder.title.setText(blog.getTitle());
        holder.desc.setText(blog.getDesc());
        holder.userid = blog.getUserid();
        holder.time = blog.getTimestamp();

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())).getTime());
        holder.timestamp.setText(formattedDate);

        String imageUrl = null;
        imageUrl = blog.getImage();
         Picasso.with(context)
                .load(imageUrl)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public TextView timestamp;
        public ImageView image;
        public ImageButton delButton;
        String userid;
        String time;


        public ViewHolder(View view, final Context ctx) {
            super(view);

            context = ctx;

            title = (TextView) view.findViewById(R.id.postTitleList);
            desc = (TextView) view.findViewById(R.id.postDesc);
            image = (ImageView) view.findViewById(R.id.postImageList);
            timestamp = (TextView) view.findViewById(R.id.timestampList);
            delButton = (ImageButton) view.findViewById(R.id.delButton);

            userid = null;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    // we can go to the next activity...
                    Blog blog = blogList.get(position);

                    Intent intent = new Intent(context, PostDetActivity.class);
                    intent.putExtra("title", blog.getTitle());
                    intent.putExtra("image", blog.getImage());
                    intent.putExtra("desc", blog.getDesc());
                    intent.putExtra("userid", blog.getUserid());
                    intent.putExtra("timestamp", blog.getTimestamp());
                    context.startActivity(intent);
                }
            });

            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mAuth = FirebaseAuth.getInstance();
                    mUser = mAuth.getCurrentUser();

                    if (mUser.getUid().equals(userid)) {
                        alertDialogBuilder = new AlertDialog.Builder(ctx);

                        inflater = LayoutInflater.from(ctx);
                        View view1 = inflater.inflate(R.layout.delete_confirm, null);

                        TextView confirm = (TextView) view1.findViewById(R.id.confirmAlert);
                        TextView question = (TextView) view1.findViewById(R.id.questionAlert);
                        Button yes = (Button) view1.findViewById(R.id.yesButton);
                        Button no = (Button) view1.findViewById(R.id.noButton);

                        alertDialogBuilder.setView(view1);
                        dialog = alertDialogBuilder.create();
                        dialog.show();

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteBlog();
                                dialog.dismiss();
                            }
                        });

                    } else {
                        Toast.makeText(ctx, "You are not authorized to delete this post...", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        private void deleteBlog() {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            //StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);

            Query query= ref.child("MBlog").orderByChild("timestamp").equalTo(String.valueOf(time));

            //delete post from firebase realtime database
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue();
                        Toast.makeText(context, "Post deleted Successfully!!!", Toast.LENGTH_LONG).show();
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("Error: ", databaseError.getMessage().toString());
                }
            });

            //delete photo related to the respective post
            /*photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                    Log.d("", "onSuccess: deleted file");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                    Log.d("", "onFailure: did not delete file");
                }
            });*/
        }
    }
}
