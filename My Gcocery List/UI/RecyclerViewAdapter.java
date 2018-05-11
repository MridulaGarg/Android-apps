package com.example.grocery.grocerylist.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grocery.grocerylist.Activities.DetailsActivity;
import com.example.grocery.grocerylist.Data.DatabaseHandler;
import com.example.grocery.grocerylist.Model.Grocery;
import com.example.grocery.grocerylist.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Grocery> groceryItems;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view, context);

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        Grocery grocery = groceryItems.get(position);

        holder.groceryItemName.setText(grocery.getName());
        holder.quantity.setText(grocery.getQuantity());
        holder.dateAdded.setText(grocery.getDateItemadded());

    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView groceryItemName;
        public TextView quantity;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id;

        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);

            context = ctx;

            groceryItemName = (TextView) itemView.findViewById(R.id.name);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            dateAdded = (TextView) itemView.findViewById(R.id.dateAdded);
            editButton = (Button) itemView.findViewById(R.id.editButton);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //go to next screen/DetailsActivity
                    int position = getAdapterPosition();

                    Grocery grocery = groceryItems.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("name", grocery.getName());
                    intent.putExtra("quantity", grocery.getQuantity());
                    intent.putExtra("id", grocery.getId());
                    intent.putExtra("date", grocery.getDateItemadded());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.editButton:
                    int pos = getAdapterPosition();
                    Grocery grocery = groceryItems.get(pos);
                    ediItem(grocery);
                    break;

                case R.id.deleteButton:
                    pos = getAdapterPosition();
                    grocery = groceryItems.get(pos);
                    deleteItem(grocery.getId());
                    break;

            }
        }

        public void deleteItem(final int id) {

            //cretae an alert dialog
            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog, null);

            Button noButton = (Button) view.findViewById(R.id.noButton);
            Button yesButton = (Button) view.findViewById(R.id.yesButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //delete the item
                    DatabaseHandler db = new DatabaseHandler(context);

                    db.deleteGrocery(id);
                    groceryItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });

        }

        public void ediItem(final Grocery gr) {

            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup, null);

            final EditText grItem = (EditText) view.findViewById(R.id.groceryItem);
            final EditText grQuantity = (EditText) view.findViewById(R.id.groceryQty);
            TextView title = (TextView) view.findViewById(R.id.tile);
            Button grSaveButton = (Button) view.findViewById(R.id.saveButton);

            title.setText("Edit Grocery Item");
            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            grSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler db = new DatabaseHandler(context);

                    //update item
                    gr.setName(grItem.getText().toString());
                    gr.setQuantity(grQuantity.getText().toString());

                    if (!grItem.getText().toString().isEmpty() && !grQuantity.getText().toString().isEmpty()) {
                        db.updateGrocery(gr);
                        notifyItemChanged(getAdapterPosition(), gr);
                    } else {
                        Snackbar.make(v, "Add Grocery and Quantity", Snackbar.LENGTH_LONG).show();
                    }

                    dialog.dismiss();

                }
            });

        }
    }

}