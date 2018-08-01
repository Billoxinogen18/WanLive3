package com.ogalo.partympakache.wanlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Payments extends AppCompatActivity {
    Double clickcount=0.0;
    Double tickets=1.0;
    private Button change;
    private ImageButton add;
    private Double buyer;
    private ImageButton minus;
    private TextView prices;

    private TextView ticketsi;
    private TextView ticcounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Intent i = getIntent();

        String pricer= i.getStringExtra("price");
        buyer=Double.parseDouble(pricer);
        add=(ImageButton) findViewById(R.id.add);
        minus=(ImageButton) findViewById(R.id.minus);
        change=(Button)findViewById(R.id.payment);
        prices=(TextView)findViewById(R.id.prices);
        ticketsi=(TextView)findViewById(R.id.tickets);
        ticcounter=(TextView)findViewById(R.id.tic_counter);

        change.setText("Ksh. "+buyer.toString());
        ticketsi.setText(tickets.toString());
        prices.setText(buyer.toString());

        setTics(1.0);

        setNum(buyer);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               tickets=tickets+tickets;

                clickcount=clickcount+clickcount;
                if(clickcount==1.0)
                {
                    //first time clicked to do this
                    Toast.makeText(getApplicationContext(),"Raila is too old, he should just quit politics!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    ticcounter.setText("Tickets");
                    //check how many times clicked and so on
//                    Toast.makeText(getApplicationContext(),"Button clicked count is"+clickcount, Toast.LENGTH_LONG).show();
                    ticketsi.setText(tickets.toString());
                    change.setText(clickcount.toString());
                    prices.setText(clickcount.toString());

                }





            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tickets>1.0)
                {

                    ticcounter.setText("Tickets");



                    tickets=tickets-1.0;

                    clickcount=clickcount-buyer;


                    ticketsi.setText(tickets.toString());
                    change.setText(clickcount.toString());
                    prices.setText(clickcount.toString());







                }
                else
                {
                    ticcounter.setText("Ticket");
                    Toast.makeText(Payments.this, "Please buy more tickets", Toast.LENGTH_SHORT).show();
                }





            }
        });



        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //WIll implement alertDialog where users can choose paypal or mpesa
                Toast.makeText(Payments.this, "Choose Mpesa or Paypal", Toast.LENGTH_SHORT).show();






            }
        });




    }

    public void setNum(Double num)
    {
        this.clickcount=num;

    }
    public void setTics(Double tics)
    {
        this.tickets=tickets;

    }
}
