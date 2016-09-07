package com.example.pjez.happyhours;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    Basket basket;
    List<Towar> magazyn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        magazyn = new ArrayList<>();

        magazyn.add( new Towar(700, "BMW GS1200R", (TextView) findViewById(R.id.price_1)));
        magazyn.add( new Towar(850, "Triumph Rocket III", (TextView) findViewById(R.id.price_2)));
        magazyn.add( new Towar(300, "Suzuki VL800", (TextView) findViewById(R.id.price_3)));
        magazyn.add( new Towar(680, "KTM 950", (TextView) findViewById(R.id.price_4)));

        basket = new Basket();
        basket.setDisplay((TextView) findViewById(R.id.total));
    }

    public void buy1(View view) {
        basket.addTowar(magazyn.get(0));
    }


    public void buy2(View view) {
        basket.addTowar(magazyn.get(1));
    }

    public void buy3(View view) {
        basket.addTowar(magazyn.get(2));
    }

    public void buy4(View view) {
        basket.addTowar(magazyn.get(3));
    }

    public void toggleHappyHours(View view) {
        basket.toggleHappyHours();
    }

    public void getZamowienie(View view) {
        String trescZamowienia = basket.getZamowienie();
        displayAlert(trescZamowienia);
    }

    private void displayAlert(String msg) {

        new AlertDialog.Builder(this)
                .setTitle("Zamówienie")
                .setMessage(msg)
                .setPositiveButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                .create()
                .show();
    }


    private class Basket {

        protected TextView basketTextView = null;
        protected ArrayList<Towar> towary = null;
        protected Boolean happyHours = false;

        protected float hhDiscount = 0.2f;

        public Basket() {
            towary = new ArrayList<>();
        }

        public void setDisplay(TextView totalTextView) {
            this.basketTextView = totalTextView;
            refreshDisplay();
        }

        public void refreshDisplay() {
            if (this.basketTextView != null) {

                String total = String.format("%.2f", getTotalSum());
                this.basketTextView.setText(total);
            }
        }

        public void toggleHappyHours() {
            this.happyHours = !this.happyHours;
            refreshDisplay();
        }

        public Boolean isHappyHours() {
            return this.happyHours;
        }

        public void addTowar(Towar towar) {
            this.towary.add(towar);
            refreshDisplay();
        }

        public float getTotalSum() {
            float totalSum = 0;
            for (Towar t : towary) {
                totalSum += t.getPrice();
            }

            if (happyHours)
                totalSum = totalSum * (1 - hhDiscount);

            return totalSum;
        }

        public String getZamowienie() {
            StringBuilder sb = new StringBuilder();
            Integer x = 1;
            for (Towar t : towary) {
                sb.append(x++ + ". " + t.getName() + ": " + t.toString() + "\n");
            }
            sb.append(String.format("Razem: %.2f zl", getTotalSum()));
            return sb.toString();
        }
    }

    private class Towar {

        protected float price;
        protected String name;

        public Towar(float price, String name, TextView textView) {
            this.price = price;
            this.name = name;
            textView.setText(this.toString());
        }

        public float getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return String.format("%.2f zl", price);
        }
    }


}

