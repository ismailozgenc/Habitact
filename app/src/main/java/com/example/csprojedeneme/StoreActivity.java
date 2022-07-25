package com.example.csprojedeneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StoreActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView item1, item2, item3, item4, item5, item6;
    private Item storeItem1, storeItem2, storeItem3, storeItem4, storeItem5, storeItem6;
    private Item[] list = {storeItem1, storeItem2, storeItem3, storeItem4, storeItem5, storeItem6};

    private int userGold;
    private int count = 0;

    //User user = new User("te","te");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);

        item1 = (ImageView)findViewById(R.id.marketItem1);
        item2 = (ImageView)findViewById(R.id.marketItem2);
        item3 = (ImageView)findViewById(R.id.marketItem3);
        item4 = (ImageView)findViewById(R.id.marketItem4);
        item5 = (ImageView)findViewById(R.id.marketItem5);
        item6 = (ImageView)findViewById(R.id.marketItem6);

        item1.setOnClickListener((View.OnClickListener) this);
        item2.setOnClickListener((View.OnClickListener) this);
        item3.setOnClickListener((View.OnClickListener) this);
        item4.setOnClickListener((View.OnClickListener) this);
        item5.setOnClickListener((View.OnClickListener) this);
        item6.setOnClickListener((View.OnClickListener) this);
        //dayPassed();
        placeItems();

    }
    public void getUserGold(){
        MainActivity.userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userGold = documentSnapshot.toObject(User.class).getGold();
            }
        });
    }

    @Override
    public void onClick(View view) {

        MainActivity.userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                switch (view.getId()){
                    case R.id.marketItem1:
                        buy(user, list[0]);
                        break;
                    case R.id.marketItem2:
                        buy(user, list[1]);
                        break;
                    case R.id.marketItem3:
                        buy(user, list[2]);
                        break;
                    case R.id.marketItem4:
                        buy(user, list[3]);
                        break;
                    case R.id.marketItem5:
                        buy(user, list[4]);
                        break;
                    case R.id.marketItem6:
                        buy(user, list[5]);
                        break;
                }
            }
        });


    }

    public void placeItems(){
        ArrayList<Item> randomItems = new ArrayList<>();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        imageViews.add(item1);
        imageViews.add(item2);
        imageViews.add(item3);
        imageViews.add(item4);
        imageViews.add(item5);
        imageViews.add(item6);

        MainActivity.itemsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    if(count == 6){
                        break;
                    }
                    Item item = documentSnapshot.toObject(Item.class);
                    list[count] = new Item(item.getId(), item.getCost(), item.getCategory());
                    randomItems.add(item);
                    drawItem(randomItems.get(count).getId(), imageViews.get(count));
                    count++;
                }
                for (int i = 0; i < randomItems.size(); i++){
                    drawItem(randomItems.get(i).getId(), imageViews.get(i));
                }
            }
        });

        /*String[] linkList = {"https://firebasestorage.googleapis.com/v0/b/cs-proje-deneme.appspot.com/o/itemsImage%2Fyellow%20shirt.png?alt=media&token=4d571083-a394-4ae5-bef0-bd3c020d7723"};
        for (int i = 0; i < 2; i++) {
            omermethod(linkList[0], 200, "Dress");
        }*/



    }

    public void drawItem(String id, ImageView imageView) {

        Glide.with(getBaseContext())
                .load(id)
                .into(imageView);
    }

    public void dayPassed(){

        Calendar calendar;
        SimpleDateFormat simpleDateFormat;
        String date;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm::ss");
        refreshItems();

    }

    public void refreshItems(){

    }

    public void buy(User user, Item item){
        if (user.getGold() >= item.getCost()){
            user.setGold(user.getGold() - item.getCost());
            user.addCollection(item);
            MainActivity.userRef.update("gold", user.getGold() - item.getCost()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(StoreActivity.this,"purchased",
                            Toast.LENGTH_SHORT).show();
                }
            });
            MainActivity.itemRef.update("category", item.getCategory()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(StoreActivity.this,"purchased",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(StoreActivity.this, "not enough gold", Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<String> randomChooser(){
        ArrayList<String> itemList = new ArrayList<>();
        //itemList.add(Math.random() * (databaseItemList + 1));
        itemList.add("https://firebasestorage.googleapis.com/v0/b/quiz-app-2acee.appspot.com/o/fox.png?alt=media&token=1ba4b54a-3d2f-4ea6-8e70-0e8880469c2c");
        itemList.add("https://tr.depositphotos.com/vector-images/imagenes-en-png.html");
        itemList.add("https://www.pngwing.com/tr");
        itemList.add("https://tr.depositphotos.com/vector-images/imagenes-en-png.html");
        itemList.add("https://www.pngwing.com/tr");
        itemList.add("https://tr.depositphotos.com/vector-images/imagenes-en-png.html");
        return  itemList;
    }
    public void omermethod(String id, int cost, String category){
        Item item = new Item(id, cost, category);
        MainActivity.itemsRef.add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(StoreActivity.this,"item added",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}