package com.example.a2davaa02.addressbook;

import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contacts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save){
            try{
                PrintWriter pw=new PrintWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath()+"/contacts.cvs"));
                int i=0;
                while(contacts[i].printSting!=null)
                {
                    pw.println(contacts[i].printString());
                }

            }
            catch (IOException e)
            {
                System.out.println("I/O Exception: " + e);
            }
            return true;
        }
        else if(item.getItemId()==R.id.load){
            try{

                FileReader fr=new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/contacts.cvs");
                BufferedReader reader=new BufferedReader(fr);
                String line;
                while((line=reader.readLine())!=null)
                {
                    String[] components=line.split(",");
                    if(components.length==4)
                    {
                        Contact currentContact = new Contact(components[0],components[1],components[2],components[3]);
                        contacts.add(currentContact);
                    }
                }
                reader.close();
            }
            catch (IOException e) {
                new AlertDialog.Builder(this).setMessage("ERROR: "+e).setPositiveButton("OK",null).show();
            }
            return true;
        }
        else if(item.getItemId()==R.id.add)
        {
            EditText firstname=(EditText)findViewById(R.id.firstname);
            EditText lastname=(EditText)findViewById(R.id.lastname);
            EditText phonenumber=(EditText)findViewById(R.id.phonenumber);
            EditText address=(EditText)findViewById(R.id.address);

            Contact currentContact=new Contact(firstname.toString(),lastname.toString(),phonenumber.toString(),address.toString());
            contacts.add(currentContact);
            return true;
        }
        else if(item.getItemId()==R.id.search)
        {
            return true;
        }
        return false;
    }


    private class Contact
    {
        private String fname;
        private String lname;
        private String fnumber;
        private String address;

        public Contact(String fname,String lname,String fnumber,String address)
        {
            this.fname=fname;
            this.lname=lname;
            this.fnumber=fnumber;
            this.address=address;
        }
        public String printString()
        {
            String string=fname+","+lname+","+fnumber+","+address;
            return string;
        }

    }
}
