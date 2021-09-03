package com.example.miwok;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class  WordAdapter extends ArrayAdapter<Word> {

    private int mColor;

    public WordAdapter(Activity contect, ArrayList<Word> words, int color){

        super(contect, 0, words);
        mColor = color;
    }

    @Override
   public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word currentWord = getItem(position);

        TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);

        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        defaultTextView.setText(currentWord.getDefaultTranslation());

        ImageView image = listItemView.findViewById(R.id.image);

        if(currentWord.check()){
            image.setImageResource(currentWord.getImageResourceId());
            image.setVisibility(View.VISIBLE);
        }else{
            image.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColor);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
