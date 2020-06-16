package ru.gushchin.exchangerateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Document doc;
    private Thread secondThread;
    private Runnable runnable;
    List<Currency> currencyList;
    private RecyclerView recyclerView;
    private TextView textViewNameValuta;
    CurrencyAdapter currencyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencyList = generateCurrency();
        init();
        //textViewNameValuta = findViewById(R.id.valutaName);
        currencyAdapter = new CurrencyAdapter(currencyList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(currencyAdapter);
        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlinearLayoutManager);
    }
    private void getWeb(){
        try {
            doc = Jsoup.connect("https://www.banki.ru/products/currency/cb/").get();
            Elements tables = doc.getElementsByTag("tbody");
            Element ourTable = tables.get(0);
            Elements elementsFromTables = ourTable.children(); //это все валюты которые мы получили
            Element dollar = elementsFromTables.get(0);
            Elements dollar_elements = dollar.children();
            Log.d("Mylog", "Size : " +  ourTable.children().get(2).child(0).text());
            Log.d("Mylog", "valuta : " + ourTable.childrenSize());
            for(int i = 0; i < ourTable.childrenSize()-5; i++){
                if (i != 2){
                //currencyList.add(new Currency("code","name", "number", "price","moving fast"));
                currencyList.add(new Currency(ourTable.children().get(i).child(0).text(),ourTable.children().get(i).child(1).text(), ourTable.children().get(i).child(2).text().substring(0,3), ourTable.children().get(i).child(3).text().substring(0,4), ourTable.children().get(i).child(4).text()));
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        }).start();
    }

    List<Currency> generateCurrency(){
        List<Currency> currencyList = new ArrayList<>();

//
        return  currencyList;
    };
}