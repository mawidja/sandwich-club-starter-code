package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    private TextView also_known_tv;
    private TextView origin_tv;
    private TextView description_tv;
    private TextView ingredients_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Sandwich sandwich = new Sandwich();

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        also_known_tv = (TextView)findViewById(R.id.also_known_tv);
        origin_tv = (TextView)findViewById(R.id.origin_tv);
        description_tv = (TextView)findViewById(R.id.description_tv);
        ingredients_tv = (TextView)findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        //Sandwich sandwich = new Sandwich();
        //setTitle(sandwich.getMainName());

        List<String> alternativeNameList = sandwich.getAlsoKnownAs();
        /*ListIterator<String> alterNamesIterator = alterNativeName.listIterator();
        while(alterNamesIterator.hasNext()){
            //also_known_tv.setText(alterNamesIterator.next());
            also_known_tv.append(alterNamesIterator.next());
        }*/
        for(int i=0;i<alternativeNameList.size();i++) {
            if((alternativeNameList.size()-1)== i){
                also_known_tv.append(alternativeNameList.get(i) + ".");
            }else{
                also_known_tv.append(alternativeNameList.get(i) + ", ");
            }
        }

        origin_tv.setText(sandwich.getPlaceOfOrigin());
        description_tv.setText(sandwich.getDescription());

        List<String> ingredientList = sandwich.getIngredients();
        /*ListIterator<String> ingredientIterator = ingredientList.listIterator();
        while(ingredientIterator.hasNext()){
            //ingredients_tv.setText(ingredientIterator.next());
            ingredients_tv.append(ingredientIterator.next());
        }*/
        for(int i=0;i<ingredientList.size();i++) {
            if((ingredientList.size()-1)== i){
                ingredients_tv.append(ingredientList.get(i) + ".");
            }else{
                ingredients_tv.append(ingredientList.get(i) + ", ");
            }
        }
    }
}
