package com.example.administrator.forimm5.DB;

import com.example.administrator.forimm5.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-08-10.
 */

public class LanguageLab {

    private static LanguageLab sLanguageLab;
    private List<Language> languageList;

    public static LanguageLab getInstance(){
        if(sLanguageLab == null){
            sLanguageLab = new LanguageLab();
        }
        return sLanguageLab;
    }

    public List<Language> getData(){
        languageList = new ArrayList<>();
        languageList.add(new Language("한국어", R.drawable.language_selection_uneffect_korean, R.drawable.language_selection_effect_korean));
        languageList.add(new Language("English", R.drawable.language_selection_uneffect_english, R.drawable.language_selection_effect_english));
        languageList.add(new Language("नेपाली", R.drawable.language_selection_uneffect_nepali, R.drawable.language_selection_effect_nepali));
        return languageList;
    }

}
