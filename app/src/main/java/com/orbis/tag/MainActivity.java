package com.orbis.tag;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orbis.tag.model.entity.TagEntity;
import com.orbis.tag.util.TagGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TagGroup.OnTagClickListener,
        View.OnClickListener {

    private TagGroup tagGroup;
    private List<TagEntity> list = new ArrayList<>();
    private LinearLayout linear;
    private AppCompatButton btn;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagGroup = (TagGroup) findViewById(R.id.tagGroup);
        btn = (AppCompatButton) findViewById(R.id.btn);
        linear = (LinearLayout) findViewById(R.id.linear);

        list.add(new TagEntity("1", "Android"));
        list.add(new TagEntity("2", "Dota 2"));
        list.add(new TagEntity("3", "Counter Strike"));

        showTags();

        dialog = new Dialog(this);
        dialog.getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tag);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);


        tagGroup.setOnTagClickListener(this);
        btn.setOnClickListener(this);
    }

    private void showTags(){
        if (list != null && list.size() > 0) {
            tagGroup.setTags(list);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                showDialog(null);
                break;
        }
    }

    @Override
    public void onTagClick(String description) {

        for(TagEntity tag : list){
            if(tag.getDescription().equals(description)){
                showDialog(tag);
            }
        }

    }

    private  void showDialog(final TagEntity tagEntity){

        final AppCompatEditText txt = (AppCompatEditText) dialog.findViewById(R.id.txt);

        final AppCompatButton btnSave = (AppCompatButton) dialog.findViewById(R.id.btnSave);
        AppCompatButton btnDelete = (AppCompatButton) dialog.findViewById(R.id.btnDelete);

        if(tagEntity!=null){
            txt.setText(tagEntity.getDescription());
            btnDelete.setEnabled(true);
        }else{
            txt.setText("");
            btnDelete.setEnabled(false);
        }

        txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    btnSave.performClick();
                    return true;
                }

                return false;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = txt.getText().toString().trim();

                if (description.length() > 0) {

                    if(tagEntity!=null){

                        for(int i=0; i< list.size(); i++){
                            if(list.get(i).getId().equals(tagEntity.getId())){
                                list.set(i, new TagEntity(tagEntity.getId(), description));
                            }
                        }

                    }else{
                        list.add(new TagEntity(String.valueOf(list.get(list.size()-1).getId()+1), description));
                    }
                    showTags();
                    dialog.dismiss();


                } else {
                    Snackbar.make(linear, getString(R.string.enter_text_title), Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i< list.size(); i++){
                    if(list.get(i).getId().equals(tagEntity.getId())){
                        list.remove(i);
                    }
                }
                showTags();
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}