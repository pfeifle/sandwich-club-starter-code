package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        //show the description
        if(sandwich.getDescription().length() > 0){
            TextView description = findViewById(R.id.description_tv);
            findViewById(R.id.description_tv).setVisibility(View.VISIBLE);
            description.setText(sandwich.getDescription());
            description.setVisibility(View.VISIBLE);
        }

        //show the ingredients
        if(!sandwich.getIngredients().isEmpty()){
            TextView ingredients = findViewById(R.id.ingredients_tv);
            findViewById(R.id.ingredients_tv).setVisibility(View.VISIBLE);
            List<String> incredients = sandwich.getIngredients();
            for(String s: incredients)
                ingredients.append("* "+ s +"\n");
            ingredients.setVisibility(View.VISIBLE);
        }


        //Show origin
        if(sandwich.getPlaceOfOrigin().length() > 0){
            findViewById(R.id.origin_label).setVisibility(View.VISIBLE);
            TextView origin = findViewById(R.id.origin_tv);
            origin.setVisibility(View.VISIBLE);
            origin.setText(sandwich.getPlaceOfOrigin());
        }


        // show other names
        if(!sandwich.getAlsoKnownAs().isEmpty()){
            findViewById(R.id.also_known_label).setVisibility(View.VISIBLE);
            TextView knownAs = findViewById(R.id.also_known_tv);
            knownAs.setVisibility(View.VISIBLE);
            List<String> otherNames = sandwich.getAlsoKnownAs();
            for(String s : otherNames){
                knownAs.append("* "+ s +"\n");
            }
        }
    }
}
