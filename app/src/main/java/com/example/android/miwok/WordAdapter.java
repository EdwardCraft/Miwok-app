package com.example.android.miwok;



import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PEDRO on 15/09/2017.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    public static final String TAG = WordAdapter.class.getName();

    private String name;

    /*
    * The context is used to inflate the layout file, and the list is the data
    * we want to populate into the lists.
    *
    *
    *
    *   note: Activity context
    * */

    public WordAdapter(Activity context, ArrayList<Word> words) {
        super(context, 0 , words);
        name = " ";
    }

    public WordAdapter(Activity context, ArrayList<Word> words, String name) {
        super(context, 0 , words);
        this.name = name;
    }

    /*
    *Provides a view for an AdapterView(ListView GridView, etc)
    * @param position. The position in the list of data that should be display in hte
    * list item
    * @param convertView The recycle view to populate
    * @param parent. The parent view group use for inflation
    * @return The view for the position in the Adapter
    */


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check if the  existing view is being reused, otherwise inflate the view
        View numberItemView = convertView;
        if(numberItemView == null){
            numberItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent , false
            );
        }

        //Get the object located at this position in the list
        Word currentWord = getItem(position);

        //Find the TextView in the list_item.xml , with the ID version_name
        TextView miwok = (TextView)numberItemView.findViewById(R.id.miwok_text_view);
        miwok.setText(currentWord.getMiwokTranslation());

        TextView defaultWord = (TextView)numberItemView.findViewById(R.id.default_text_view);
        defaultWord.setText(currentWord.getDefaultTranslation());

        ImageView imageView = (ImageView)numberItemView.findViewById(R.id.image_id);

        if(currentWord.hasImage())
            imageView.setImageResource(currentWord.getImageResourceId());
        else
            imageView.setVisibility(View.GONE);


        View view = numberItemView.findViewById(R.id.text_list_id);

        switch (name){
            case Utils.NUMBER_FRAGMENT_NAME:
                view.setBackgroundResource(R.color.category_numbers);
                break;
            case Utils.COLORS_FRAGMENT_NAME:
                view.setBackgroundResource(R.color.category_colors);
                break;
            case Utils.FAMILY_FRAGMENT_NAME:
                view.setBackgroundResource(R.color.category_family);
                break;
            case Utils.PHRASES_FRAGMENT_NAME:
                view.setBackgroundResource(R.color.category_phrases);
                break;
            default:
                break;
        }








        return numberItemView;
    }



}
