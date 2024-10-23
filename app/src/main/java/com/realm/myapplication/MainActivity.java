package com.realm.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.realm.myapplication.Adapters.Adapter;
import com.realm.myapplication.Models.Food;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Button buton,clear,deleteOne;
    EditText nameBox;
    Realm realm;
    ListView listView;
    Adapter adapter;
    RealmChangeListener realmChangeListener;
    RealmResults<Food> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tanimla();
    }

    private void tanimla() {
        buton = findViewById(R.id.buton);
        clear = findViewById(R.id.clear);
        deleteOne = findViewById(R.id.deleteOne);
        nameBox = findViewById(R.id.nameBox);
        realm = Realm.getDefaultInstance();
        listView = findViewById(R.id.liswItem);
        results = realm.where(Food.class).findAll(); //Select all rows from DB.

        refreshList();

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ekle();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //results.deleteAllFromRealm();
                        // refreshList();
                        new BgWork(getApplicationContext()).execute();

                    }
                });
            }
        });

        deleteOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Food> singleItem = realm.where(Food.class)
                                .equalTo("food_name", nameBox.getText().toString())
                                //.or().contains("name",nameBox.getText().toString())
                                .findAll();
                        try {
                                singleItem.deleteFromRealm(0);
                                //singleItem.deleteAllFromRealm();
                                refreshList();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Değer Yok!!! "+e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
/* ------------------------------------ this is change listener
    private void refreshList() {
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                adapter = new com.realm.myapplication.Adapters.Adapter(MainActivity.this, createList(results));
                listView.setAdapter((ListAdapter) adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }
*/ //change Listener
    private void ekle() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Number maxId =bgRealm.where(Food.class).max("food_id");
                int newId = (maxId==null)?1: maxId.intValue()+1;
                Food oFood = bgRealm.createObject(Food.class,newId);//if newId has @PrimaryKey annotaion in modelClass: can be set like this.
                oFood.setFood_name(nameBox.getText().toString());
                //oFood.setId(newId);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"Ekleme başarılı...",Toast.LENGTH_SHORT).show();
                refreshList(); // Refresh list after INSERTION
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(),"Başarısız ekleme !!! Error: "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Food> createList(RealmResults<Food> results){
        ArrayList<Food> list=new ArrayList<>();

        for (Food f:results){
            list.add(f);
        }
        return list;
    }

    private void refreshList(){
       adapter = new Adapter(this,createList(results));
        listView.setAdapter( adapter);
    }
}