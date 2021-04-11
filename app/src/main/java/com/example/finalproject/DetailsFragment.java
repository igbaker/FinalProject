package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private long id;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ReaderActivity.ITEM_ID);

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_details, container, false);

        //show the title
        TextView itemTitle = (TextView) result.findViewById(R.id.itemTitle);
        itemTitle.setText("Title: " + dataFromActivity.getString(ReaderActivity.ITEM_TITLE));

        //show the description
        TextView itemDescription = (TextView) result.findViewById(R.id.itemDescription);
        itemDescription.setText("Description: " + dataFromActivity.getString(ReaderActivity.ITEM_DESCRIPTION));

        //show the date
        TextView itemDate = (TextView) result.findViewById(R.id.itemDate);
        itemDate.setText("Date: " + dataFromActivity.getString(ReaderActivity.ITEM_DATE));

        //show the link
        TextView itemLink = (TextView) result.findViewById(R.id.itemLink);
        itemLink.setText(dataFromActivity.getString(ReaderActivity.ITEM_LINK));
        Linkify.addLinks(itemDate, Linkify.ALL);

        // get the hide button, and add a click listener:
        Button hide = (Button) result.findViewById(R.id.hide);
        hide.setOnClickListener(clk -> {
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra(com.example.finalproject.ReaderActivity.ITEM_ID, dataFromActivity.getLong(com.example.finalproject.ReaderActivity.ITEM_ID));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back

        });
        return result;
    }
    }

