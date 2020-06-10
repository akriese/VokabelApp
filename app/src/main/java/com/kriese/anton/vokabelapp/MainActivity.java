package com.kriese.anton.vokabelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.UrlQuerySanitizer;
import android.preference.Preference;
import android.print.PrintAttributes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    //region Definitionen
    // Dem Programm mitteilen, welche Elemente es gibt und wie diese hier heißen.
    private Button buttonSettings, buttonHelp, buttonBackStart, buttonShowMe, easyVocab, hardVocab, buttonStartContinue, buttonSearchVocab;
    //Elemente für Deutsch-Japanisch
    private Button buttonExercise, buttonTest, buttonStatistic,
            buttonNewVocab, buttonSpcSettings, buttonBackToLang, buttonExerciseContinue, buttonTip,
            buttonNextVocab, buttonExerciseBack, resetEasy, resetHard, resetNoRepeat, resetAllLists,
            buttonNumberTrainer, buttonCharacterTrainer, buttonContinueNumber, buttonSearch;
    private CheckBox checkBoxLang1, checkBoxLang2, checkBoxEasy,checkBoxNormal, checkBoxHard, checkBoxShowTest, checkBoxMarkAsNormal;
    private RadioButton radioButtonJap, radioButtonEng, radioButtonFra, radioButtonIta,
            radioButtonNumber1, radioButtonNumber2, radioButtonNumber3, radioButtonNumber4, radioButtonTest1,
            radioButtonTest2,radioButtonTest3, radioButtonTest4, radioButtonKeyboardGer, radioButtonKeyboardJap;
    private RadioGroup radioGroupTest, radioGroupKeyboard;

    private TextView textViewHeadline, vocabField, whichLang, textViewCounterEasy,
            textViewCounterNormal, textViewCounterHard, testTextView, numberField, vocabFieldTest, searchResult;
    private EditText editTestLength, editSearch, editFrom, editTo;
    private ScrollView searchScroll;
    //endregion
    //region Noch mehr Definitionen
    public String toast;
    public List<Integer> noRepeat = new ArrayList<>();
    boolean chooseLang1 = true;
    boolean chooseLang2 = true;
    public List<Integer> easyVocabList = new ArrayList<>();
    public int zeilenNummer;
    public List<Integer> hardVocabList = new ArrayList<>();
    public List<String> characterList = new ArrayList<>();

    public List<String> vocabList = new ArrayList<>();
    public int languageNumber;
    public int vocabNumber;
    public int charNumber;
    public boolean chooseExercise = false;
    public boolean chooseNumbers = false;
    public boolean chooseCharacters = false;
    public boolean chooseTest = false;
    public boolean testStarted = false;
    public boolean testOver = false;
    public boolean messageOver = false;
    int randomNumber;
    int numberTested = 0;
    int numberTestAmount;
    int rightNumber;
    int rightAmount;
    public boolean testMod1 = false;
    public boolean testMod2 = false;
    public boolean testMod3 = false;
    public boolean testMod4 = false;
    public boolean keyboardGer = false;
    public boolean keyboardJap = false;
    public boolean kanji = false;
    public List<String> foundWords = new ArrayList<String>();
    public int numberFrom = 0;
    public int numberTo = 0;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion
        //region Definition der Elemente
        //region Startoberfläche
        radioButtonJap = (RadioButton) findViewById(R.id.radioButtonJap);
        radioButtonEng = (RadioButton) findViewById(R.id.radioButtonEng);
        radioButtonFra = (RadioButton) findViewById(R.id.radioButtonFra);
        radioButtonIta = (RadioButton) findViewById(R.id.radioButtonIta);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        buttonHelp = (Button) findViewById(R.id.buttonHelp);
        buttonBackStart = (Button) findViewById(R.id.buttonBackStart);
        textViewHeadline = (TextView) findViewById(R.id.textViewHeadline);
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonStartContinue = (Button) findViewById(R.id.startContinue);
        testTextView = (TextView) findViewById(R.id.testText);
        testTextView.setVisibility(View.INVISIBLE);
        //endregion
        //region Language chosen
        buttonExercise = (Button) findViewById(R.id.buttonExercise);
        buttonExercise.setVisibility(View.INVISIBLE);
        buttonNumberTrainer = (Button) findViewById(R.id.buttonNumberTrainer);
        buttonNumberTrainer.setVisibility(View.INVISIBLE);
        buttonTest = (Button) findViewById(R.id.buttonTest);
        buttonTest.setVisibility(View.INVISIBLE);
        buttonStatistic = (Button) findViewById(R.id.buttonStatistic);
        buttonStatistic.setVisibility(View.INVISIBLE);
        buttonNewVocab = (Button) findViewById(R.id.buttonNewVocab);
        buttonNewVocab.setVisibility(View.INVISIBLE);
        buttonSpcSettings = (Button) findViewById(R.id.buttonSpcSettings);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        buttonBackToLang = (Button) findViewById(R.id.buttonBackToLang);
        buttonBackToLang.setVisibility(View.INVISIBLE);
        buttonCharacterTrainer = (Button) findViewById(R.id.buttonCharacterTrainer);
        buttonCharacterTrainer.setVisibility(View.INVISIBLE);
        //endregion

        //region Exercise Settings
        buttonExerciseContinue = (Button) findViewById(R.id.buttonExerciseContinue);
        buttonExerciseContinue.setVisibility(View.INVISIBLE);
        checkBoxLang1 = (CheckBox) findViewById(R.id.checkBoxLang1);
        checkBoxLang1.setVisibility(View.INVISIBLE);
        checkBoxLang2 = (CheckBox) findViewById(R.id.checkBoxLang2);
        checkBoxLang2.setVisibility(View.INVISIBLE);
        checkBoxEasy = (CheckBox) findViewById(R.id.checkBoxEasy);
        checkBoxNormal = (CheckBox) findViewById(R.id.checkBoxNormal);
        checkBoxHard = (CheckBox) findViewById(R.id.checkBoxHard);
        checkBoxEasy.setVisibility(View.INVISIBLE);
        checkBoxNormal.setVisibility(View.INVISIBLE);
        checkBoxHard.setVisibility(View.INVISIBLE);
        textViewCounterEasy = (TextView) findViewById(R.id.textViewCounterEasy);
        textViewCounterNormal = (TextView) findViewById(R.id.textViewCounterNormal);
        textViewCounterHard = (TextView) findViewById(R.id.textViewCounterHard);
        textViewCounterEasy.setVisibility(View.INVISIBLE);
        textViewCounterNormal.setVisibility(View.INVISIBLE);
        textViewCounterHard.setVisibility(View.INVISIBLE);
        whichLang = (TextView) findViewById(R.id.whichLang);
        whichLang.setVisibility(View.INVISIBLE);
        editFrom = (EditText) findViewById(R.id.editTextFrom);
        editFrom.setVisibility(View.INVISIBLE);
        editTo = (EditText) findViewById(R.id.editTextTo);
        editTo.setVisibility(View.INVISIBLE);
        //region Search
        buttonSearchVocab = (Button) findViewById(R.id.searchVocab);
        buttonSearchVocab.setVisibility(View.INVISIBLE);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setVisibility(View.INVISIBLE);
        editSearch = (EditText) findViewById(R.id.editSearch);
        editSearch.setVisibility(View.INVISIBLE);
        radioGroupKeyboard = (RadioGroup) findViewById(R.id.radioGroupKeyboard);
        radioGroupKeyboard.setVisibility(View.INVISIBLE);
        radioButtonKeyboardGer = (RadioButton) findViewById(R.id.keyboardGer);
        radioButtonKeyboardGer.setChecked(true);
        radioButtonKeyboardJap = (RadioButton) findViewById(R.id.keyboardJap);
        searchResult = (TextView) findViewById(R.id.searchResult);
        searchResult.setVisibility(View.INVISIBLE);
        searchScroll = (ScrollView) findViewById(R.id.searchScroll);
        searchScroll.setVisibility(View.INVISIBLE);
        //endregion
        //endregion
        //region NumberTrainer
        radioButtonNumber1 = (RadioButton) findViewById(R.id.radioButtonNumber1);
        radioButtonNumber2 = (RadioButton) findViewById(R.id.radioButtonNumber2);
        radioButtonNumber3 = (RadioButton) findViewById(R.id.radioButtonNumber3);
        radioButtonNumber4 = (RadioButton) findViewById(R.id.radioButtonNumber4);
        radioButtonNumber1.setVisibility(View.INVISIBLE);
        radioButtonNumber2.setVisibility(View.INVISIBLE);
        radioButtonNumber3.setVisibility(View.INVISIBLE);
        radioButtonNumber4.setVisibility(View.INVISIBLE);
        numberField = (TextView) findViewById(R.id.numberField);
        numberField.setVisibility(View.INVISIBLE);
        buttonContinueNumber = (Button) findViewById(R.id.continueNumber);
        buttonContinueNumber.setVisibility(View.INVISIBLE);
        //endregion
        //region Spc Settings
        resetEasy = (Button) findViewById(R.id.resetEasy);
        resetEasy.setVisibility(View.INVISIBLE);
        resetHard = (Button) findViewById(R.id.resetHard);
        resetHard.setVisibility(View.INVISIBLE);
        resetNoRepeat = (Button) findViewById(R.id.resetNoRepeat);
        resetNoRepeat.setVisibility(View.INVISIBLE);
        //endregion
        //region Exercise
        buttonExerciseBack = (Button) findViewById(R.id.buttonExerciseBack);
        buttonTip = (Button) findViewById(R.id.buttonJapTip);
        buttonNextVocab = (Button) findViewById(R.id.buttonNextVocab);
        buttonExerciseBack.setVisibility(View.INVISIBLE);
        buttonTip.setVisibility(View.INVISIBLE);
        buttonNextVocab.setVisibility(View.INVISIBLE);
        vocabField = (TextView) findViewById(R.id.vocabField);
        vocabField.setVisibility(View.INVISIBLE);
        buttonShowMe = (Button) findViewById(R.id.buttonShowMe);
        buttonShowMe.setVisibility(View.INVISIBLE);
        hardVocab = (Button) findViewById(R.id.hardVocab);
        hardVocab.setVisibility(View.INVISIBLE);
        easyVocab = (Button) findViewById(R.id.easyVocab);
        easyVocab.setVisibility(View.INVISIBLE);
        checkBoxMarkAsNormal = (CheckBox) findViewById(R.id.checkBoxMarkAsNormal);
        checkBoxMarkAsNormal.setVisibility(View.INVISIBLE);
        //endregion
        //region Settings
        resetAllLists = (Button) findViewById(R.id.resetAllLists);
        resetAllLists.setVisibility(View.INVISIBLE);
        checkBoxShowTest = (CheckBox) findViewById(R.id.checkBoxShowTest);
        checkBoxShowTest.setVisibility(View.INVISIBLE);
        if (prefs.getBoolean("SHOW_TEST",false)){
            checkBoxShowTest.setChecked(true);
            testTextView.setVisibility(View.VISIBLE);
        }
        else if (!prefs.getBoolean("SHOW_TEST",false)){
            checkBoxShowTest.setChecked(false);
            testTextView.setVisibility(View.INVISIBLE);
        }
        //endregion
        //region Test
        editTestLength = (EditText) findViewById(R.id.editTestLength);
        editTestLength.setVisibility(View.INVISIBLE);
        radioGroupTest = (RadioGroup) findViewById(R.id.radioGroupTest);
        radioGroupTest.setVisibility(View.INVISIBLE);
        radioButtonTest1 = (RadioButton) findViewById(R.id.radioButtonTest1);
        radioButtonTest2 = (RadioButton) findViewById(R.id.radioButtonTest2);
        radioButtonTest3 = (RadioButton) findViewById(R.id.radioButtonTest3);
        radioButtonTest4 = (RadioButton) findViewById(R.id.radioButtonTest4);
        vocabFieldTest = (TextView) findViewById(R.id.vocabFieldTest);
        vocabFieldTest.setVisibility(View.INVISIBLE);
        //endregion
        //endregion
        //region load difficulty lists
        if (prefs.getInt("CHOSEN_LANGUAGE",0) == 1) {
            radioButtonJap.setChecked(true);
        }
        else if (prefs.getInt("CHOSEN_LANGUAGE",0) == 2){
            radioButtonEng.setChecked(true);
        }
        else if (prefs.getInt("CHOSEN_LANGUAGE",0) == 3){
            radioButtonFra.setChecked(true);
        }
        else if (prefs.getInt("CHOSEN_LANGUAGE",0) == 4) {
            radioButtonIta.setChecked(true);
        }

        //endregion
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        editor.commit();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editedText = editSearch.getText().toString();
                char[] editedTextToArray = editedText.toCharArray();
                for (int q = 0; q < 30; q++){

                }
                if (editedText.equals("a")){
                    editSearch.setText("あ");

                    int position = editSearch.getText().length();
                    editSearch.setSelection(position);
                }
            }
        });
    }

    //region Aktionen beim Clicken der Buttons des Startbildschirms:
    public void onStartContinueClick(View view) throws IOException {
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion
        //region Headline Change
        if (radioButtonJap.isChecked()) {
            textViewHeadline.setText("Deutsch - Japanisch");
        }
        else if (radioButtonEng.isChecked()){
            textViewHeadline.setText("Deutsch - Englisch");
        }
        else if (radioButtonFra.isChecked()){
            textViewHeadline.setText("Deutsch - Französisch");
        }
        else if (radioButtonIta.isChecked()) {
            textViewHeadline.setText("Deutsch - Italienisch");
        }
        //endregion
        //region Visibility Buttons
        radioButtonJap.setVisibility(view.INVISIBLE);
        radioButtonEng.setVisibility(view.INVISIBLE);
        radioButtonFra.setVisibility(view.INVISIBLE);
        radioButtonIta.setVisibility(view.INVISIBLE);
        buttonStartContinue.setVisibility(View.INVISIBLE);
        buttonBackStart.setVisibility(view.VISIBLE);
        buttonExercise.setVisibility(View.VISIBLE);
        buttonNumberTrainer.setVisibility(View.VISIBLE);
        buttonCharacterTrainer.setVisibility(View.VISIBLE);
        buttonTest.setVisibility(View.VISIBLE);
        buttonStatistic.setVisibility(View.VISIBLE);
        buttonNewVocab.setVisibility(View.VISIBLE);
        buttonSpcSettings.setVisibility(View.VISIBLE);
        if (!radioButtonJap.isChecked()){
            buttonCharacterTrainer.setClickable(false);
        }
        else if (radioButtonJap.isChecked()){
            buttonCharacterTrainer.setClickable(true);
        }

        //endregion
        //region Language Button save
        if (radioButtonJap.isChecked()) {
            editor.putInt("CHOSEN_LANGUAGE",1);
        }
        else if (radioButtonEng.isChecked()){
            editor.putInt("CHOSEN_LANGUAGE",2);
        }
        else if (radioButtonFra.isChecked()){
            editor.putInt("CHOSEN_LANGUAGE", 3);
        }
        else if (radioButtonIta.isChecked()) {
            editor.putInt("CHOSEN_LANGUAGE", 4);
        }
        //endregion
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        editor.commit();
        buttonNewVocab.setClickable(false);
        buttonStatistic.setClickable(false);
        buttonSettings.setClickable(false);
    }
    public void onSettingsClick(View view) {
        checkBoxShowTest.setVisibility(View.VISIBLE);
        resetAllLists.setVisibility(View.VISIBLE);
        radioButtonJap.setVisibility(View.INVISIBLE);
        radioButtonEng.setVisibility(View.INVISIBLE);
        radioButtonFra.setVisibility(View.INVISIBLE);
        radioButtonIta.setVisibility(View.INVISIBLE);
        buttonStartContinue.setVisibility(View.INVISIBLE);
        buttonBackStart.setVisibility(View.VISIBLE);
    }
    //endregion
    //region Language chosen
    public void onBackStartClick(View view) {
        radioButtonJap.setVisibility(view.VISIBLE);
        radioButtonEng.setVisibility(view.VISIBLE);
        radioButtonFra.setVisibility(view.VISIBLE);
        radioButtonIta.setVisibility(view.VISIBLE);
        buttonStartContinue.setVisibility(View.VISIBLE);
        buttonBackStart.setVisibility(view.INVISIBLE);
        buttonExercise.setVisibility(View.INVISIBLE);
        buttonNumberTrainer.setVisibility(View.INVISIBLE);
        buttonCharacterTrainer.setVisibility(View.INVISIBLE);
        buttonTest.setVisibility(View.INVISIBLE);
        buttonStatistic.setVisibility(View.INVISIBLE);
        buttonNewVocab.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        textViewHeadline.setText("Sprachauswahl");
        checkBoxShowTest.setVisibility(View.INVISIBLE);
        resetAllLists.setVisibility(View.INVISIBLE);
        testTextView.setText(".." + easyVocabList + hardVocabList);
        buttonSettings.setClickable(true);
    }
    public void onExerciseClick(View view) throws IOException {
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion
        //region reset diff + repeat lists + norepeatList
        easyVocabList.clear();
        vocabList.clear();
        noRepeat.clear();
        //endregion
        //region Vocab lists .txt into List + vocabNumber counted
        if (radioButtonJap.isChecked()) {
            InputStream inputstream1 = getAssets().open("JapVocab.txt");
            InputStreamReader reader1 = new InputStreamReader(inputstream1);
            BufferedReader bff1 = new BufferedReader(reader1);

            for (int i = 0; bff1.readLine() != null; i++) {
                vocabNumber = i;
            }
            vocabNumber = vocabNumber + 1;
            bff1.close();
            InputStream inputstream12 = getAssets().open("JapVocab.txt");
            InputStreamReader reader12 = new InputStreamReader(inputstream12);
            BufferedReader bff12 = new BufferedReader(reader12);
            for (int i = 0; i < vocabNumber; i++) {
                vocabList.add(bff12.readLine());
            }
            bff12.close();
        }
        else if (radioButtonEng.isChecked()) {
            InputStream inputstream2 = getAssets().open("EngVocab.txt");
            InputStreamReader reader2 = new InputStreamReader(inputstream2);
            BufferedReader bff2 = new BufferedReader(reader2);
            for (int i = 0; bff2.readLine() != null; i++) {
                vocabNumber = i;
            }
            vocabNumber = vocabNumber + 1;
            bff2.close();
            InputStream inputstream22 = getAssets().open("EngVocab.txt");
            InputStreamReader reader22 = new InputStreamReader(inputstream22);
            BufferedReader bff22 = new BufferedReader(reader22);
            for (int i = 0; i < vocabNumber; i++) {
                vocabList.add(bff22.readLine());
            }
            bff22.close();
        }
        else if (radioButtonFra.isChecked()) {
            InputStream inputstream3 = getAssets().open("FraVocab.txt");
            InputStreamReader reader3 = new InputStreamReader(inputstream3);
            BufferedReader bff3 = new BufferedReader(reader3);
            for (int i = 0; bff3.readLine() != null; i++) {
                vocabNumber = i;
            }
            vocabNumber = vocabNumber + 1;
            bff3.close();
            InputStream inputstream32 = getAssets().open("FraVocab.txt");
            InputStreamReader reader32 = new InputStreamReader(inputstream32);
            BufferedReader bff32 = new BufferedReader(reader32);
            for (int i = 0; i < vocabNumber; i++) {
                vocabList.add(bff32.readLine());
            }
            bff32.close();
        }
        else if (radioButtonIta.isChecked()) {
            InputStream inputstream4 = getAssets().open("ItaVocab.txt");
            InputStreamReader reader4 = new InputStreamReader(inputstream4);
            BufferedReader bff4 = new BufferedReader(reader4);
            for (int i = 0; bff4.readLine() != null; i++) {
                vocabNumber = i;
            }
            vocabNumber = vocabNumber + 1;
            bff4.close();
            InputStream inputstream42 = getAssets().open("ItaVocab.txt");
            InputStreamReader reader42 = new InputStreamReader(inputstream42);
            BufferedReader bff42 = new BufferedReader(reader42);
            for (int i = 0; i < vocabNumber; i++) {
                vocabList.add(bff42.readLine());
            }
            bff42.close();
        }

        //endregion
        //region Lang check box name
        if (radioButtonJap.isChecked()) {
            checkBoxLang1.setText("Deutsch");
            checkBoxLang2.setText("Japanisch");
        }
        else if (radioButtonEng.isChecked()){
            checkBoxLang1.setText("Deutsch");
            checkBoxLang2.setText("Englisch");
        }
        else if (radioButtonFra.isChecked()){
            checkBoxLang1.setText("Deutsch");
            checkBoxLang2.setText("Französisch");
        }
        else if (radioButtonIta.isChecked()) {
            checkBoxLang1.setText("Deutsch");
            checkBoxLang2.setText("Italienisch");
        }
        //endregion
        //region exercise settings + MarkAsNormal
        //region load preferences Jap
        if (radioButtonJap.isChecked()) {
            if (prefsJap.getBoolean("CHOOSE_GERj", true)) {
                checkBoxLang1.setChecked(true);
            } else if (prefsJap.getBoolean("CHOOSE_GERj", false)) {
                checkBoxLang1.setChecked(false);
            }

            if (prefsJap.getBoolean("CHOOSE_JAP", true)) {
                checkBoxLang2.setChecked(true);
            } else if (prefsJap.getBoolean("CHOOSE_JAP", false)) {
                checkBoxLang2.setChecked(false);
            }

            if (prefsJap.getBoolean("CHOOSE_JAP_EASY", true)) {
                checkBoxEasy.setChecked(true);
            } else if (prefsJap.getBoolean("CHOOSE_JAP_EASY", false)) {
                checkBoxEasy.setChecked(false);
            }

            if (prefsJap.getBoolean("CHOOSE_JAP_NORMAL", true)) {
                checkBoxNormal.setChecked(true);
            } else if (prefsJap.getBoolean("CHOOSE_JAP_NORMAL", false)) {
                checkBoxNormal.setChecked(false);
            }

            if (prefsJap.getBoolean("CHOOSE_JAP_HARD", true)) {
                checkBoxHard.setChecked(true);
            } else if (prefsJap.getBoolean("CHOOSE_JAP_HARD", false)) {
                checkBoxHard.setChecked(false);
            }

            if (prefsJap.getBoolean("MARK_AS_NORMAL_JAP",true)){
                checkBoxMarkAsNormal.setChecked(true);
            }
            else if (prefsJap.getBoolean("MARK_AS_NORMAL_JAP",false)){
                checkBoxMarkAsNormal.setChecked(false);
            }

        }
        //endregion
        //region load preferences Eng
        else if (radioButtonEng.isChecked()) {
            if (prefsEng.getBoolean("CHOOSE_GERe", true)) {
                checkBoxLang1.setChecked(true);
            } else if (prefsEng.getBoolean("CHOOSE_GERe", false)) {
                checkBoxLang1.setChecked(false);
            }

            if (prefsEng.getBoolean("CHOOSE_ENG", true)) {
                checkBoxLang2.setChecked(true);
            } else if (prefsEng.getBoolean("CHOOSE_ENG", false)) {
                checkBoxLang2.setChecked(false);
            }

            if (prefsEng.getBoolean("CHOOSE_ENG_EASY", true)) {
                checkBoxEasy.setChecked(true);
            } else if (prefsEng.getBoolean("CHOOSE_ENG_EASY", false)) {
                checkBoxEasy.setChecked(false);
            }
            if (prefsEng.getBoolean("CHOOSE_ENG_NORMAL", true)) {
                checkBoxNormal.setChecked(true);
            } else if (prefsEng.getBoolean("CHOOSE_ENG_NORMAL", false)) {
                checkBoxNormal.setChecked(false);
            }
            if (prefsEng.getBoolean("CHOOSE_ENG_HARD", true)) {
                checkBoxHard.setChecked(true);
            } else if (prefsEng.getBoolean("CHOOSE_ENG_HARD", false)) {
                checkBoxHard.setChecked(false);
            }

            if (prefsEng.getBoolean("MARK_AS_NORMAL_ENG",true)){
                checkBoxMarkAsNormal.setChecked(true);
            }
            else if (prefsEng.getBoolean("MARK_AS_NORMAL_ENG",false)){
                checkBoxMarkAsNormal.setChecked(false);
            }
        }
        //endregion
        //region load preferences Fra
        else if (radioButtonFra.isChecked()) {
            if (prefsFra.getBoolean("CHOOSE_GERf", true)) {
                checkBoxLang1.setChecked(true);
            } else if (prefsFra.getBoolean("CHOOSE_GERf", false)) {
                checkBoxLang1.setChecked(false);
            }

            if (prefsFra.getBoolean("CHOOSE_FRA", true)) {
                checkBoxLang2.setChecked(true);
            } else if (prefsFra.getBoolean("CHOOSE_FRA", false)) {
                checkBoxLang2.setChecked(false);
            }

            if (prefsFra.getBoolean("CHOOSE_FRA_EASY", true)) {
                checkBoxEasy.setChecked(true);
            } else if (prefsFra.getBoolean("CHOOSE_FRA_EASY", false)) {
                checkBoxEasy.setChecked(false);
            }
            if (prefsFra.getBoolean("CHOOSE_FRA_NORMAL", true)) {
                checkBoxNormal.setChecked(true);
            } else if (prefsFra.getBoolean("CHOOSE_FRA_NORMAL", false)) {
                checkBoxNormal.setChecked(false);
            }
            if (prefsFra.getBoolean("CHOOSE_FRA_HARD", true)) {
                checkBoxHard.setChecked(true);
            } else if (prefsFra.getBoolean("CHOOSE_FRA_HARD", false)) {
                checkBoxHard.setChecked(false);
            }

            if (prefsFra.getBoolean("MARK_AS_NORMAL_FRA",true)){
                checkBoxMarkAsNormal.setChecked(true);
            }
            else if (prefsFra.getBoolean("MARK_AS_NORMAL_FRA",false)){
                checkBoxMarkAsNormal.setChecked(false);
            }
        }
        //endregion
        //region load preferences Ita
        else if(radioButtonIta.isChecked()) {
            if (prefsIta.getBoolean("CHOOSE_GERi", true)) {
                checkBoxLang1.setChecked(true);
            } else if (prefsIta.getBoolean("CHOOSE_GERi", false)) {
                checkBoxLang1.setChecked(false);
            }

            if (prefsIta.getBoolean("CHOOSE_ITA", true)) {
                checkBoxLang2.setChecked(true);
            } else if (prefsIta.getBoolean("CHOOSE_ITA", false)) {
                checkBoxLang2.setChecked(false);
            }

            if (prefsIta.getBoolean("CHOOSE_ITA_EASY", true)) {
                checkBoxEasy.setChecked(true);
            } else if (prefsIta.getBoolean("CHOOSE_ITA_EASY", false)) {
                checkBoxEasy.setChecked(false);
            }
            if (prefsIta.getBoolean("CHOOSE_ITA_NORMAL", true)) {
                checkBoxNormal.setChecked(true);
            } else if (prefsIta.getBoolean("CHOOSE_ITA_NORMAL", false)) {
                checkBoxNormal.setChecked(false);
            }
            if (prefsIta.getBoolean("CHOOSE_ITA_HARD", true)) {
                checkBoxHard.setChecked(true);
            } else if (prefsIta.getBoolean("CHOOSE_ITA_HARD", false)) {
                checkBoxHard.setChecked(false);
            }

            if (prefsIta.getBoolean("MARK_AS_NORMAL_ITA",true)){
                checkBoxMarkAsNormal.setChecked(true);
            }
            else if (prefsIta.getBoolean("MARK_AS_NORMAL_ITA",false)){
                checkBoxMarkAsNormal.setChecked(false);
            }
        }
        //endregion
        //endregion
        //region load easy/hard lists + noRepeatList
        if (radioButtonJap.isChecked()){
            String noRepeatString = prefsJap.getString("NO_REPEAT_JAP", "0");
            noRepeatString = noRepeatString.replace(" ","");
            noRepeatString = noRepeatString.replace("[","");
            noRepeatString = noRepeatString.replace("]","");
            String[] backToIntNR = noRepeatString.split(",");
            int backToIntLengthNR = backToIntNR.length;
            for (int i = 0; i < backToIntLengthNR; i++) {
                noRepeat.add(Integer.parseInt(backToIntNR[i]));
            }

            String easyVocabJap = prefsJap.getString("EASY_VOCAB_LIST_JAP", "0");
            easyVocabJap = easyVocabJap.replace(" ","");
            easyVocabJap = easyVocabJap.replace("[","");
            easyVocabJap = easyVocabJap.replace("]","");
            String[] backToIntEasy = easyVocabJap.split(",");
            int backToIntLengthEasy = backToIntEasy.length;
            for (int i = 0; i < backToIntLengthEasy; i++) {
                easyVocabList.add(Integer.parseInt(backToIntEasy[i]));
            }

            String hardVocabJap = prefsJap.getString("HARD_VOCAB_LIST_JAP", "0");
            hardVocabJap = hardVocabJap.replace(" ","");
            hardVocabJap = hardVocabJap.replace("[","");
            hardVocabJap = hardVocabJap.replace("]","");
            String[] backToIntHard = hardVocabJap.split(",");
            int backToIntLengthHard = backToIntHard.length;
            for (int i = 0; i < backToIntLengthHard; i++) {
                hardVocabList.add(Integer.parseInt(backToIntHard[i]));
            }


            vocabField.setText(prefs.getString("LAST_VOCAB_JAP", ""));
            toast = prefs.getString("LAST_VOCAB_SHOW_JAP","");
            languageNumber = prefs.getInt("TIP_LANGUAGE_JAP",0);
        }
        else if (radioButtonEng.isChecked()){
            String noRepeatString = prefsEng.getString("NO_REPEAT_ENG", "0");
            noRepeatString = noRepeatString.replace(" ","");
            noRepeatString = noRepeatString.replace("[","");
            noRepeatString = noRepeatString.replace("]","");
            String[] backToIntNR = noRepeatString.split(",");
            int backToIntLengthNR = backToIntNR.length;
            for (int i = 0; i < backToIntLengthNR; i++) {
                noRepeat.add(Integer.parseInt(backToIntNR[i]));
            }

            String easyVocabEng = prefsEng.getString("EASY_VOCAB_LIST_ENG", "0");
            easyVocabEng = easyVocabEng.replace(" ","");
            easyVocabEng = easyVocabEng.replace("[","");
            easyVocabEng = easyVocabEng.replace("]","");
            String[] backToIntEasy = easyVocabEng.split(",");
            int backToIntLengthEasy = backToIntEasy.length;
            for (int i = 0; i < backToIntLengthEasy; i++) {
                easyVocabList.add(Integer.parseInt(backToIntEasy[i]));
            }

            String hardVocabEng = prefsEng.getString("HARD_VOCAB_LIST_ENG", "0");
            hardVocabEng = hardVocabEng.replace(" ","");
            hardVocabEng = hardVocabEng.replace("[","");
            hardVocabEng = hardVocabEng.replace("]","");
            String[] backToIntHard = hardVocabEng.split(",");
            int backToIntLengthHard = backToIntHard.length;
            for (int i = 0; i < backToIntLengthHard; i++) {
                hardVocabList.add(Integer.parseInt(backToIntHard[i]));
            }


            vocabField.setText(prefs.getString("LAST_VOCAB_ENG", ""));
            toast = prefs.getString("LAST_VOCAB_SHOW_ENG","");
            languageNumber = prefs.getInt("TIP_LANGUAGE_ENG",0);
        }
        else if (radioButtonFra.isChecked()){
            String noRepeatString = prefsFra.getString("NO_REPEAT_FRA", "0");
            noRepeatString = noRepeatString.replace(" ","");
            noRepeatString = noRepeatString.replace("[","");
            noRepeatString = noRepeatString.replace("]","");
            String[] backToIntNR = noRepeatString.split(",");
            int backToIntLengthNR = backToIntNR.length;
            for (int i = 0; i < backToIntLengthNR; i++) {
                noRepeat.add(Integer.parseInt(backToIntNR[i]));
            }

            String easyVocabFra = prefsFra.getString("EASY_VOCAB_LIST_FRA", "0");
            easyVocabFra = easyVocabFra.replace(" ","");
            easyVocabFra = easyVocabFra.replace("[","");
            easyVocabFra = easyVocabFra.replace("]","");
            String[] backToIntEasy = easyVocabFra.split(",");
            int backToIntLengthEasy = backToIntEasy.length;
            for (int i = 0; i < backToIntLengthEasy; i++) {
                easyVocabList.add(Integer.parseInt(backToIntEasy[i]));
            }

            String hardVocabFra = prefsFra.getString("HARD_VOCAB_LIST_FRA", "0");
            hardVocabFra = hardVocabFra.replace(" ","");
            hardVocabFra = hardVocabFra.replace("[","");
            hardVocabFra = hardVocabFra.replace("]","");
            String[] backToIntHard = hardVocabFra.split(",");
            int backToIntLengthHard = backToIntHard.length;
            for (int i = 0; i < backToIntLengthHard; i++) {
                hardVocabList.add(Integer.parseInt(backToIntHard[i]));
            }


            vocabField.setText(prefs.getString("LAST_VOCAB_FRA", ""));
            toast = prefs.getString("LAST_VOCAB_SHOW_FRA","");
            languageNumber = prefs.getInt("TIP_LANGUAGE_FRA",0);
        }
        else if (radioButtonIta.isChecked()){
            String noRepeatString = prefsIta.getString("NO_REPEAT_ITA", "0");
            noRepeatString = noRepeatString.replace(" ","");
            noRepeatString = noRepeatString.replace("[","");
            noRepeatString = noRepeatString.replace("]","");
            String[] backToIntNR = noRepeatString.split(",");
            int backToIntLengthNR = backToIntNR.length;
            for (int i = 0; i < backToIntLengthNR; i++) {
                noRepeat.add(Integer.parseInt(backToIntNR[i]));
            }

            String easyVocabIta = prefsIta.getString("EASY_VOCAB_LIST_ITA", "0");
            easyVocabIta = easyVocabIta.replace(" ","");
            easyVocabIta = easyVocabIta.replace("[","");
            easyVocabIta = easyVocabIta.replace("]","");
            String[] backToIntEasy = easyVocabIta.split(",");
            int backToIntLengthEasy = backToIntEasy.length;
            for (int i = 0; i < backToIntLengthEasy; i++) {
                easyVocabList.add(Integer.parseInt(backToIntEasy[i]));
            }

            String hardVocabIta = prefsIta.getString("HARD_VOCAB_LIST_ITA", "0");
            hardVocabIta = hardVocabIta.replace(" ","");
            hardVocabIta = hardVocabIta.replace("[","");
            hardVocabIta = hardVocabIta.replace("]","");
            String[] backToIntHard = hardVocabIta.split(",");
            int backToIntLengthHard = backToIntHard.length;
            for (int i = 0; i < backToIntLengthHard; i++) {
                hardVocabList.add(Integer.parseInt(backToIntHard[i]));
            }


            vocabField.setText(prefs.getString("LAST_VOCAB_ITA", ""));
            toast = prefs.getString("LAST_VOCAB_SHOW_ITA","");
            languageNumber = prefs.getInt("TIP_LANGUAGE_ITA",0);
        }
        //endregion
        //region choose: lists + vocabNumber + last zeilenNummer + languageNumber
        if (radioButtonJap.isChecked()) {
            zeilenNummer = prefsJap.getInt("LAST_VOCAB_NUMBER_JAP",0);
            languageNumber = prefsJap.getInt("LANGUAGE_NUMBER_JAP",0);
        }
        else if (radioButtonEng.isChecked()){
            zeilenNummer = prefsEng.getInt("LAST_VOCAB_NUMBER_ENG",0);
            languageNumber = prefsEng.getInt("LANGUAGE_NUMBER_ENG",0);
        }
        else if (radioButtonFra.isChecked()){
            zeilenNummer = prefsFra.getInt("LAST_VOCAB_NUMBER_FRA",0);
            languageNumber = prefsFra.getInt("LANGUAGE_NUMBER_FRA",0);
        }
        else if (radioButtonIta.isChecked()) {
            zeilenNummer = prefsIta.getInt("LAST_VOCAB_NUMBER_ITA",0);
            languageNumber = prefsIta.getInt("LANGUAGE_NUMBER_ITA",0);
        }
        //endregion
        if (easyVocabList.size() == 1){
            easyVocabList.remove(Integer.valueOf(0));
        }
        if (hardVocabList.size() == 1){
            hardVocabList.remove(Integer.valueOf(0));
        }
        if (noRepeat.size() == 1){
            noRepeat.remove(Integer.valueOf(0));
        }


        testTextView.setText(".." + easyVocabList+hardVocabList+noRepeat);
        int size1 = easyVocabList.size();
        int size2 = hardVocabList.size();
        int size3 = vocabNumber-size1-size2;
        chooseExercise = true;
        chooseNumbers = false;
        chooseCharacters = false;
        chooseTest = false;
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonExercise.setVisibility(View.INVISIBLE);
        buttonNumberTrainer.setVisibility(View.INVISIBLE);
        buttonCharacterTrainer.setVisibility(View.INVISIBLE);
        buttonTest.setVisibility(View.INVISIBLE);
        buttonStatistic.setVisibility(View.INVISIBLE);
        buttonNewVocab.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        buttonSettings.setVisibility(View.INVISIBLE);
        textViewHeadline.setText("Übungseinstellungen");
        buttonBackToLang.setVisibility(View.VISIBLE);
        buttonExerciseContinue.setVisibility(View.VISIBLE);
        checkBoxLang1.setVisibility(View.VISIBLE);
        checkBoxLang2.setVisibility(View.VISIBLE);
        whichLang.setVisibility(View.VISIBLE);
        whichLang.setText("In welcher Sprache sollen die Vokabeln ausgegeben werden?");
        checkBoxEasy.setVisibility(View.VISIBLE);
        checkBoxNormal.setVisibility(View.VISIBLE);
        checkBoxHard.setVisibility(View.VISIBLE);
        checkBoxEasy.setText("Leicht");
        checkBoxNormal.setText("Normal");
        checkBoxHard.setText("Schwer");
        textViewCounterEasy.setVisibility(View.VISIBLE);
        textViewCounterNormal.setVisibility(View.VISIBLE);
        textViewCounterHard.setVisibility(View.VISIBLE);
        textViewCounterEasy.setText(String.valueOf(size1));
        textViewCounterNormal.setText(String.valueOf(size3));
        textViewCounterHard.setText(String.valueOf(size2));
        testTextView.setText(".." + easyVocabList+hardVocabList+noRepeat);
        vocabField.setTextSize(30);
        buttonSearchVocab.setVisibility(View.VISIBLE); buttonSearchVocab.setText("Suchen");
        buttonNextVocab.setText("Nächste");
        editFrom.setVisibility(View.VISIBLE);
        editTo.setVisibility(View.VISIBLE);
        //region check/uncheck diff lists
        if (easyVocabList.size() == 0){
            checkBoxEasy.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_EASY",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_EASY",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_EASY",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_EASY",false);
            }
        }
        if (hardVocabList.size() == 0){
            checkBoxHard.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_HARD",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_HARD",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_HARD",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_HARD",false);
            }
        }
        if (vocabNumber - easyVocabList.size() - hardVocabList.size() == 0){
            checkBoxNormal.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_NORMAL",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_NORMAL",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_NORMAL",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_NORMAL",false);
            }
        }
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        editor.commit();
        //endregion

    }
    public void onCharactersClick(View view) throws IOException {
        //region CharacterListAdd
        InputStream inputstreamChar = getAssets().open("Characters.txt");
        InputStreamReader readerChar = new InputStreamReader(inputstreamChar);
        BufferedReader bffChar = new BufferedReader(readerChar);

        for (int i = 0; bffChar.readLine() != null; i++) {
            charNumber = i;
        }
        charNumber = charNumber + 1;
        bffChar.close();
        InputStream inputstreamChar2 = getAssets().open("Characters.txt");
        InputStreamReader readerChar2 = new InputStreamReader(inputstreamChar2);
        BufferedReader bffChar2 = new BufferedReader(readerChar2);
        for (int i = 0; i < charNumber; i++) {
            characterList.add(bffChar2.readLine());
        }
        bffChar2.close();
        //endregion
        chooseExercise = false;
        chooseNumbers = false;
        chooseCharacters = true;
        chooseTest = false;
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonExercise.setVisibility(View.INVISIBLE);
        buttonNumberTrainer.setVisibility(View.INVISIBLE);
        buttonCharacterTrainer.setVisibility(View.INVISIBLE);
        buttonTest.setVisibility(View.INVISIBLE);
        buttonStatistic.setVisibility(View.INVISIBLE);
        buttonNewVocab.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        buttonSettings.setVisibility(View.INVISIBLE);
        buttonBackToLang.setVisibility(View.VISIBLE);
        textViewHeadline.setText("Übungseinstellungen");
        vocabField.setText("");
        vocabField.setTextSize(60);
        checkBoxLang1.setVisibility(View.VISIBLE); checkBoxLang1.setText("Deutsch");
        checkBoxLang2.setVisibility(View.VISIBLE); checkBoxLang2.setText("Japanisch");
        whichLang.setVisibility(View.VISIBLE);
        whichLang.setText("In welcher Sprache sollen die Zeichen ausgegeben werden?");
        checkBoxEasy.setVisibility(View.VISIBLE); checkBoxEasy.setText("Hiragana"); checkBoxEasy.setChecked(false);
        checkBoxNormal.setVisibility(View.VISIBLE); checkBoxNormal.setText("Katakana"); checkBoxNormal.setChecked(false);
        checkBoxHard.setVisibility(View.VISIBLE); checkBoxHard.setText("erweitert"); checkBoxHard.setChecked(false);
        buttonSearchVocab.setVisibility(View.VISIBLE); buttonSearchVocab.setText("Kanji");
        buttonExerciseContinue.setVisibility(View.VISIBLE);
        buttonNextVocab.setText("Nächste");

    }
    public void onNumberTrainerClick(View view) {
        chooseExercise = false;
        chooseNumbers = true;
        chooseCharacters = false;
        chooseTest = false;
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonExercise.setVisibility(View.INVISIBLE);
        buttonNumberTrainer.setVisibility(View.INVISIBLE);
        buttonCharacterTrainer.setVisibility(View.INVISIBLE);
        buttonTest.setVisibility(View.INVISIBLE);
        buttonStatistic.setVisibility(View.INVISIBLE);
        buttonNewVocab.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        buttonSettings.setVisibility(View.INVISIBLE);
        buttonBackToLang.setVisibility(View.VISIBLE);
        radioButtonNumber1.setVisibility(View.VISIBLE);
        radioButtonNumber2.setVisibility(View.VISIBLE);
        radioButtonNumber3.setVisibility(View.VISIBLE);
        radioButtonNumber4.setVisibility(View.VISIBLE);
        buttonContinueNumber.setVisibility(View.VISIBLE);
        textViewHeadline.setText("Übungseinstellungen");
        if (!radioButtonNumber1.isChecked() && !radioButtonNumber2.isChecked() &&
                !radioButtonNumber3.isChecked() && !radioButtonNumber4.isChecked()){
            radioButtonNumber1.setChecked(true);
        }
        buttonNextVocab.setText("Nächste");
        vocabField.setText("");
    }
    public void onTestClick(View view) throws IOException {
        //region Vocab lists .txt into List + vocabNumber counted
        if (radioButtonJap.isChecked()) {
            InputStream inputstream1 = getAssets().open("JapVocab.txt");
            InputStreamReader reader1 = new InputStreamReader(inputstream1);
            BufferedReader bff1 = new BufferedReader(reader1);

            for (int i = 0; bff1.readLine() != null; i++) {
                vocabNumber = i;
            }
            vocabNumber = vocabNumber + 1;
            bff1.close();
            InputStream inputstream12 = getAssets().open("JapVocab.txt");
            InputStreamReader reader12 = new InputStreamReader(inputstream12);
            BufferedReader bff12 = new BufferedReader(reader12);
            for (int i = 0; i < vocabNumber; i++) {
                vocabList.add(bff12.readLine());
            }
            bff12.close();
        }
        else if (radioButtonEng.isChecked()) {
            InputStream inputstream2 = getAssets().open("EngVocab.txt");
            InputStreamReader reader2 = new InputStreamReader(inputstream2);
            BufferedReader bff2 = new BufferedReader(reader2);
            for (int i = 0; bff2.readLine() != null; i++) {
                vocabNumber = i;
            }
            vocabNumber = vocabNumber + 1;
            bff2.close();
            InputStream inputstream22 = getAssets().open("EngVocab.txt");
            InputStreamReader reader22 = new InputStreamReader(inputstream22);
            BufferedReader bff22 = new BufferedReader(reader22);
            for (int i = 0; i < vocabNumber; i++) {
                vocabList.add(bff22.readLine());
            }
            bff22.close();
        }
        else if (radioButtonFra.isChecked()) {
            InputStream inputstream3 = getAssets().open("FraVocab.txt");
            InputStreamReader reader3 = new InputStreamReader(inputstream3);
            BufferedReader bff3 = new BufferedReader(reader3);
            for (int i = 0; bff3.readLine() != null; i++) {
                vocabNumber = i;
            }
            vocabNumber = vocabNumber + 1;
            bff3.close();
            InputStream inputstream32 = getAssets().open("FraVocab.txt");
            InputStreamReader reader32 = new InputStreamReader(inputstream32);
            BufferedReader bff32 = new BufferedReader(reader32);
            for (int i = 0; i < vocabNumber; i++) {
                vocabList.add(bff32.readLine());
            }
            bff32.close();
        }
        else if (radioButtonIta.isChecked()) {
            InputStream inputstream4 = getAssets().open("ItaVocab.txt");
            InputStreamReader reader4 = new InputStreamReader(inputstream4);
            BufferedReader bff4 = new BufferedReader(reader4);
            for (int i = 0; bff4.readLine() != null; i++) {
                vocabNumber = i;
            }
            vocabNumber = vocabNumber + 1;
            bff4.close();
            InputStream inputstream42 = getAssets().open("ItaVocab.txt");
            InputStreamReader reader42 = new InputStreamReader(inputstream42);
            BufferedReader bff42 = new BufferedReader(reader42);
            for (int i = 0; i < vocabNumber; i++) {
                vocabList.add(bff42.readLine());
            }
            bff42.close();
        }

        //endregion
        chooseExercise = false;
        chooseNumbers = false;
        chooseCharacters = false;
        chooseTest = true;
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonExercise.setVisibility(View.INVISIBLE);
        buttonNumberTrainer.setVisibility(View.INVISIBLE);
        buttonCharacterTrainer.setVisibility(View.INVISIBLE);
        buttonTest.setVisibility(View.INVISIBLE);
        buttonStatistic.setVisibility(View.INVISIBLE);
        buttonNewVocab.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
        buttonBackToLang.setVisibility(View.VISIBLE);
        buttonSettings.setVisibility(View.INVISIBLE);
        editTestLength.setVisibility(View.VISIBLE);
        radioGroupTest.setVisibility(View.VISIBLE);
        radioButtonTest2.setClickable(false);
        radioButtonTest3.setClickable(false);
        radioButtonTest4.setClickable(false);
        buttonExerciseContinue.setVisibility(View.VISIBLE);
        radioButtonTest1.setChecked(true);
        textViewHeadline.setText("Testeinstellungen");
    }
    public void onStatisticClick(View view) {
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonBackToLang.setVisibility(View.VISIBLE);
    }
    public void onNewVocabClick(View view) {
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonBackToLang.setVisibility(View.VISIBLE);
    }
    public void onSpcSettingsClick(View view) {
        textViewHeadline.setText("Spez. Einstellungen");
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonBackToLang.setVisibility(View.VISIBLE);
        resetEasy.setVisibility(View.VISIBLE);
        resetHard.setVisibility(View.VISIBLE);
        resetNoRepeat.setVisibility(View.VISIBLE);
        buttonBackStart.setVisibility(View.INVISIBLE);
        buttonExercise.setVisibility(View.INVISIBLE);
        buttonNumberTrainer.setVisibility(View.INVISIBLE);
        buttonCharacterTrainer.setVisibility(View.INVISIBLE);
        buttonTest.setVisibility(View.INVISIBLE);
        buttonStatistic.setVisibility(View.INVISIBLE);
        buttonNewVocab.setVisibility(View.INVISIBLE);
        buttonSpcSettings.setVisibility(View.INVISIBLE);
    }
    public void onBackToLangClick(View view) {
        buttonBackStart.setVisibility(view.VISIBLE);
        buttonExercise.setVisibility(View.VISIBLE);
        buttonNumberTrainer.setVisibility(View.VISIBLE);
        buttonCharacterTrainer.setVisibility(View.VISIBLE);
        buttonTest.setVisibility(View.VISIBLE);
        buttonStatistic.setVisibility(View.VISIBLE);
        buttonNewVocab.setVisibility(View.VISIBLE);
        buttonSpcSettings.setVisibility(View.VISIBLE);
        buttonBackToLang.setVisibility(View.INVISIBLE);
        buttonSettings.setVisibility(View.VISIBLE);
        buttonExerciseContinue.setVisibility(View.INVISIBLE);
        checkBoxLang1.setVisibility(View.INVISIBLE);
        checkBoxLang2.setVisibility(View.INVISIBLE);
        whichLang.setVisibility(View.INVISIBLE);
        buttonSearch.setVisibility(View.INVISIBLE);
        resetEasy.setVisibility(View.INVISIBLE);
        resetHard.setVisibility(View.INVISIBLE);
        resetNoRepeat.setVisibility(View.INVISIBLE);
        checkBoxEasy.setVisibility(View.INVISIBLE);
        checkBoxNormal.setVisibility(View.INVISIBLE);
        checkBoxHard.setVisibility(View.INVISIBLE);
        textViewCounterEasy.setVisibility(View.INVISIBLE);
        textViewCounterNormal.setVisibility(View.INVISIBLE);
        textViewCounterHard.setVisibility(View.INVISIBLE);
        buttonBackToLang.setVisibility(View.INVISIBLE);
        radioButtonNumber1.setVisibility(View.INVISIBLE);
        radioButtonNumber2.setVisibility(View.INVISIBLE);
        radioButtonNumber3.setVisibility(View.INVISIBLE);
        radioButtonNumber4.setVisibility(View.INVISIBLE);
        buttonContinueNumber.setVisibility(View.INVISIBLE);
        //region Headline Change
        if (radioButtonJap.isChecked()) {
            textViewHeadline.setText("Deutsch - Japanisch");
        }
        else if (radioButtonEng.isChecked()){
            textViewHeadline.setText("Deutsch - Englisch");
        }
        else if (radioButtonFra.isChecked()){
            textViewHeadline.setText("Deutsch - Französisch");
        }
        else if (radioButtonIta.isChecked()) {
            textViewHeadline.setText("Deutsch - Italienisch");
        }
        //endregion
        testTextView.setText(".." + easyVocabList + hardVocabList + noRepeat);
        editTestLength.setVisibility(View.INVISIBLE);
        radioGroupTest.setVisibility(View.INVISIBLE);
        buttonSearchVocab.setVisibility(View.INVISIBLE);
        editFrom.setVisibility(View.INVISIBLE);
        editTo.setVisibility(View.INVISIBLE);
    }
    //endregion
    //region Exercise Settings
    public void onExerciseContinueClick(View view)  {
        //region Exercise
        if (chooseExercise) {
            buttonExerciseContinue.setVisibility(View.INVISIBLE);
            buttonBackToLang.setVisibility(View.INVISIBLE);
            buttonExerciseBack.setVisibility(View.VISIBLE);
            buttonHelp.setVisibility(View.INVISIBLE);
            buttonSearchVocab.setVisibility(View.INVISIBLE);
            buttonSettings.setVisibility(View.INVISIBLE);
            textViewHeadline.setVisibility(View.INVISIBLE);
            checkBoxLang1.setVisibility(View.INVISIBLE);
            checkBoxLang2.setVisibility(View.INVISIBLE);
            whichLang.setVisibility(View.INVISIBLE);
            buttonShowMe.setVisibility(View.VISIBLE);
            checkBoxEasy.setVisibility(View.INVISIBLE);
            checkBoxNormal.setVisibility(View.INVISIBLE);
            checkBoxHard.setVisibility(View.INVISIBLE);
            textViewCounterEasy.setVisibility(View.INVISIBLE);
            textViewCounterNormal.setVisibility(View.INVISIBLE);
            textViewCounterHard.setVisibility(View.INVISIBLE);
            vocabField.setVisibility(View.VISIBLE);
            buttonTip.setVisibility(View.VISIBLE);
            buttonNextVocab.setVisibility(View.VISIBLE);
            easyVocab.setVisibility(View.VISIBLE);
            hardVocab.setVisibility(View.VISIBLE);
            checkBoxMarkAsNormal.setVisibility(View.VISIBLE);
            testTextView.setText(".." + easyVocabList + hardVocabList + noRepeat);
            editFrom.setVisibility(View.INVISIBLE);
            editTo.setVisibility(View.INVISIBLE);

            int size1 = easyVocabList.size() - 1;
            int size2 = hardVocabList.size() - 1;
            int size3 = vocabNumber - size1 - size2;
            int zähler = 0;
            if (checkBoxEasy.isChecked()) {
                zähler = zähler + size1;
            }
            if (checkBoxNormal.isChecked()) {
                zähler = zähler + size3;
            }
            if (checkBoxHard.isChecked()) {
                zähler = zähler + size2;
            }
            if (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
                zähler = zähler + size1 + size2 + size3;
            }

            if (zähler / 2 <= noRepeat.size()) {
                noRepeat.remove(0);
            }
            //region from to
            String editedFrom = editFrom.getText().toString().trim();
            String editedTo = editTo.getText().toString().trim();

            if (editedFrom.equals("")){
                numberFrom = 0;
                editedFrom="0";
            }
            if (editedTo.equals("")){
                numberTo = vocabNumber;
                editedTo = vocabNumber+"";
            }
            numberFrom = Integer.parseInt(editedFrom);
            numberTo = Integer.parseInt(editedTo);

            if ((numberFrom > -1) && (numberFrom<vocabNumber+1)){
            }
            else {numberFrom=0;}

            if ((numberTo > -1) && (numberTo<vocabNumber+1)){
            }
            else {numberTo=vocabNumber;}

            if (numberTo<numberFrom){
                numberTo = Integer.parseInt(editedFrom);
                numberFrom = Integer.parseInt(editedTo);
            }
            editTo.setText(numberTo+"");
            editFrom.setText(numberFrom+"");
            //endregion
        }
        //endregion
        //region Characters
        if (chooseCharacters){
            kanji=false;
            buttonNextVocab.setVisibility(View.VISIBLE);
            buttonExerciseBack.setVisibility(View.VISIBLE);
            textViewHeadline.setText("Zeichen üben");
            checkBoxEasy.setVisibility(View.INVISIBLE);
            checkBoxNormal.setVisibility(View.INVISIBLE);
            checkBoxHard.setVisibility(View.INVISIBLE);
            checkBoxLang1.setVisibility(View.INVISIBLE);
            checkBoxLang2.setVisibility(View.INVISIBLE);
            buttonHelp.setVisibility(View.INVISIBLE);
            whichLang.setVisibility(View.INVISIBLE);
            buttonBackToLang.setVisibility(View.INVISIBLE);
            buttonExerciseContinue.setVisibility(View.INVISIBLE);
            vocabField.setVisibility(View.VISIBLE);
        }
        //endregion
        //region Test
        if (chooseTest){
            radioButtonTest2.setClickable(true);
            radioButtonTest3.setClickable(true);
            radioButtonTest4.setClickable(true);
            String editedNumber = editTestLength.getText().toString().trim();
            int editedDigits = editedNumber.length();

            if (editedNumber.equals("")){
                Toast.makeText(getApplicationContext(), "Gib eine Zahl größer gleich 1 an!", Toast.LENGTH_LONG).show();
            }
            else if (!editedNumber.equals("")){
                numberTestAmount = Integer.parseInt(editedNumber);
                if (numberTestAmount > 100){
                    numberTestAmount = 100;
                    editTestLength.setText("100");
                }
            }
            if (editedDigits > 0) {
                if (numberTestAmount > 0) {
                    editTestLength.setVisibility(View.INVISIBLE);
                    radioGroupTest.setVisibility(View.INVISIBLE);
                    buttonBackToLang.setVisibility(View.INVISIBLE);
                    buttonExerciseContinue.setVisibility(View.INVISIBLE);
                    buttonHelp.setVisibility(View.INVISIBLE);
                    textViewHeadline.setTextSize(22);
                    textViewHeadline.setText(numberTested + "/" + numberTestAmount + " Aufgaben geschafft");
                    buttonExerciseBack.setVisibility(View.VISIBLE);
                    buttonNextVocab.setVisibility(View.VISIBLE);
                    buttonNextVocab.setText("Los geht's!");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Gib eine Zahl größer gleich 1 an!", Toast.LENGTH_LONG).show();
                }
            }

        }
        //endregion
    }
    public void onCheckboxLang1Click(View view) {
        //region Exercise
        if (chooseExercise) {

            //region USER PREFERENCES DEFINE
            SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
            SharedPreferences.Editor editorJap = prefsJap.edit();
            SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
            SharedPreferences.Editor editorEng = prefsEng.edit();
            SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
            SharedPreferences.Editor editorFra = prefsFra.edit();
            SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
            SharedPreferences.Editor editorIta = prefsIta.edit();
            SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            //endregion
            if (radioButtonJap.isChecked()) {
                if (checkBoxLang1.isChecked()) {
                    editorJap.putBoolean("CHOOSE_GERj", true);
                } else if (!checkBoxLang1.isChecked()) {
                    editorJap.putBoolean("CHOOSE_GERj", false);
                }
            } else if (radioButtonEng.isChecked()) {
                if (checkBoxLang1.isChecked()) {
                    editorEng.putBoolean("CHOOSE_GERe", true);
                } else if (!checkBoxLang1.isChecked()) {
                    editorEng.putBoolean("CHOOSE_GERe", false);
                }
            } else if (radioButtonFra.isChecked()) {
                if (checkBoxLang1.isChecked()) {
                    editorFra.putBoolean("CHOOSE_GERf", true);
                } else if (!checkBoxLang1.isChecked()) {
                    editorFra.putBoolean("CHOOSE_GERf", false);
                }
            } else if (radioButtonIta.isChecked()) {
                if (checkBoxLang1.isChecked()) {
                    editorIta.putBoolean("CHOOSE_GERi", true);
                } else if (!checkBoxLang1.isChecked()) {
                    editorIta.putBoolean("CHOOSE_GERi", false);
                }
            }
            editorJap.commit();
            editorEng.commit();
            editorFra.commit();
            editorIta.commit();
            editor.commit();
        }
        //endregion
        //region Characters

        //endregion
    }
    public void onCheckBoxLang2Click(View view) {
        //region Exercise
        if (chooseExercise) {
            //region USER PREFERENCES DEFINE
            SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
            SharedPreferences.Editor editorJap = prefsJap.edit();
            SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
            SharedPreferences.Editor editorEng = prefsEng.edit();
            SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
            SharedPreferences.Editor editorFra = prefsFra.edit();
            SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
            SharedPreferences.Editor editorIta = prefsIta.edit();
            SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            //endregion
            if (radioButtonJap.isChecked()) {
                if (checkBoxLang2.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP", true);
                } else if (!checkBoxLang2.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP", false);
                }
            } else if (radioButtonEng.isChecked()) {
                if (checkBoxLang2.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG", true);
                } else if (!checkBoxLang2.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG", false);
                }
            } else if (radioButtonFra.isChecked()) {
                if (checkBoxLang2.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA", true);
                } else if (!checkBoxLang2.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA", false);
                }
            } else if (radioButtonIta.isChecked()) {
                if (checkBoxLang2.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA", true);
                } else if (!checkBoxLang2.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA", false);
                }
            }
            editorJap.commit();
            editorEng.commit();
            editorFra.commit();
            editorIta.commit();
            editor.commit();
        }
        //endregion
        //region Characters
        if (chooseCharacters){
        }
        //endregion
    }
    public void chooseEasy(View view) {
        //region Exercise
        if (chooseExercise) {
            //region USER PREFERENCES DEFINE
            SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
            SharedPreferences.Editor editorJap = prefsJap.edit();
            SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
            SharedPreferences.Editor editorEng = prefsEng.edit();
            SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
            SharedPreferences.Editor editorFra = prefsFra.edit();
            SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
            SharedPreferences.Editor editorIta = prefsIta.edit();
            SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            //endregion
            if (radioButtonJap.isChecked()) {
                if (checkBoxEasy.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_EASY", true);
                } else if (!checkBoxEasy.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_EASY", false);
                }

                if ((easyVocabList.size()) == 0) {
                    checkBoxEasy.setChecked(false);
                    editorJap.putBoolean("CHOOSE_JAP_EASY", false);
                }
            } else if (radioButtonEng.isChecked()) {
                if (checkBoxEasy.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_EASY", true);
                } else if (!checkBoxEasy.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_EASY", false);
                }

                if ((easyVocabList.size()) == 0) {
                    checkBoxEasy.setChecked(false);
                    editorEng.putBoolean("CHOOSE_ENG_EASY", false);
                }
            } else if (radioButtonFra.isChecked()) {
                if (checkBoxEasy.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_EASY", true);
                } else if (!checkBoxEasy.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_EASY", false);
                }

                if ((easyVocabList.size()) == 0) {
                    checkBoxEasy.setChecked(false);
                    editorFra.putBoolean("CHOOSE_FRA_EASY", false);
                }
            } else if (radioButtonIta.isChecked()) {
                if (checkBoxEasy.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_EASY", true);
                } else if (!checkBoxEasy.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_EASY", false);
                }

                if ((easyVocabList.size()) == 0) {
                    checkBoxEasy.setChecked(false);
                    editorIta.putBoolean("CHOOSE_ITA_EASY", false);
                }
            }

        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        editor.commit();
        }
        //endregion
        //region Characters
        if (chooseCharacters){
        }
        //endregion
    }
    public void chooseNormal(View view) {
        //region Exercise
        if (chooseExercise) {
            //region USER PREFERENCES DEFINE
            SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
            SharedPreferences.Editor editorJap = prefsJap.edit();
            SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
            SharedPreferences.Editor editorEng = prefsEng.edit();
            SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
            SharedPreferences.Editor editorFra = prefsFra.edit();
            SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
            SharedPreferences.Editor editorIta = prefsIta.edit();
            SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            //endregion
            if (radioButtonJap.isChecked()) {
                if (checkBoxNormal.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_NORMAL", true);
                } else if (!checkBoxNormal.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_NORMAL", false);
                }

                if ((vocabList.size() - easyVocabList.size() - hardVocabList.size()) == 0) {
                    checkBoxNormal.setChecked(false);
                    editorJap.putBoolean("CHOOSE_JAP_NORMAL", false);
                }
            } else if (radioButtonEng.isChecked()) {
                if (checkBoxNormal.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_NORMAL", true);
                } else if (!checkBoxNormal.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_NORMAL", false);
                }

                if ((vocabList.size() - easyVocabList.size() - hardVocabList.size()) == 0) {
                    checkBoxNormal.setChecked(false);
                    editorEng.putBoolean("CHOOSE_ENG_NORMAL", false);
                }
            } else if (radioButtonFra.isChecked()) {
                if (checkBoxNormal.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_NORMAL", true);
                } else if (!checkBoxNormal.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_NORMAL", false);
                }

                if ((vocabList.size() - easyVocabList.size() - hardVocabList.size()) == 0) {
                    checkBoxNormal.setChecked(false);
                    editorFra.putBoolean("CHOOSE_FRA_NORMAL", false);
                }
            } else if (radioButtonIta.isChecked()) {
                if (checkBoxNormal.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_NORMAL", true);
                } else if (!checkBoxNormal.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_NORMAL", false);
                }

                if ((vocabList.size() - easyVocabList.size() - hardVocabList.size()) == 0) {
                    checkBoxNormal.setChecked(false);
                    editorIta.putBoolean("CHOOSE_ITA_NORMAL", false);
                }
            }
            editorJap.commit();
            editorEng.commit();
            editorFra.commit();
            editorIta.commit();
            editor.commit();
        }
        //endregion
        //region Characters
        if (chooseCharacters){
        }
        //endregion
    }
    public void chooseHard(View view) {
        //region Exercise
        if (chooseExercise) {
            //region USER PREFERENCES DEFINE
            SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
            SharedPreferences.Editor editorJap = prefsJap.edit();
            SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
            SharedPreferences.Editor editorEng = prefsEng.edit();
            SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
            SharedPreferences.Editor editorFra = prefsFra.edit();
            SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
            SharedPreferences.Editor editorIta = prefsIta.edit();
            SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            //endregion
            if (radioButtonJap.isChecked()) {
                if (checkBoxHard.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_HARD", true);
                } else if (!checkBoxHard.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_HARD", false);
                }

                if ((easyVocabList.size()) == 0) {
                    checkBoxHard.setChecked(false);
                    editorJap.putBoolean("CHOOSE_JAP_HARD", false);
                }
            } else if (radioButtonEng.isChecked()) {
                if (checkBoxHard.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_HARD", true);
                } else if (!checkBoxHard.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_HARD", false);
                }

                if ((hardVocabList.size()) == 0) {
                    checkBoxHard.setChecked(false);
                    editorEng.putBoolean("CHOOSE_ENG_HARD", false);
                }
            } else if (radioButtonFra.isChecked()) {
                if (checkBoxHard.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_HARD", true);
                } else if (!checkBoxHard.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_HARD", false);
                }

                if ((hardVocabList.size()) == 0) {
                    checkBoxHard.setChecked(false);
                    editorFra.putBoolean("CHOOSE_FRA_HARD", false);
                }
            } else if (radioButtonIta.isChecked()) {
                if (checkBoxHard.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_HARD", true);
                } else if (!checkBoxHard.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_HARD", false);
                }

                if ((hardVocabList.size()) == 0) {
                    checkBoxHard.setChecked(false);
                    editorIta.putBoolean("CHOOSE_ITA_HARD", false);
                }
            }
            editorJap.commit();
            editorEng.commit();
            editorFra.commit();
            editorIta.commit();
            editor.commit();
        }
        //endregion
        //region Characters
        if (chooseCharacters){
        }
        //endregion
    }
    public void onSearchVocabClick(View view) {
        if (chooseExercise) {
            buttonExerciseBack.setVisibility(View.VISIBLE);
            buttonExerciseContinue.setVisibility(View.INVISIBLE);
            buttonBackToLang.setVisibility(View.INVISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
            buttonSearchVocab.setVisibility(View.INVISIBLE);
            editSearch.setVisibility(View.VISIBLE);
            searchResult.setVisibility(View.VISIBLE);
            searchScroll.setVisibility(View.VISIBLE);
            radioGroupKeyboard.setVisibility(View.VISIBLE);
            buttonSettings.setVisibility(View.INVISIBLE);
            checkBoxLang1.setVisibility(View.INVISIBLE);
            checkBoxLang2.setVisibility(View.INVISIBLE);
            whichLang.setVisibility(View.INVISIBLE);
            checkBoxEasy.setVisibility(View.INVISIBLE);
            checkBoxNormal.setVisibility(View.INVISIBLE);
            checkBoxHard.setVisibility(View.INVISIBLE);
            textViewCounterEasy.setVisibility(View.INVISIBLE);
            textViewCounterNormal.setVisibility(View.INVISIBLE);
            textViewCounterHard.setVisibility(View.INVISIBLE);
            textViewHeadline.setText("Wörter suchen");
            editFrom.setVisibility(View.INVISIBLE);
            editTo.setVisibility(View.INVISIBLE);
        }
        if (chooseCharacters){
            kanji = true;
            checkBoxLang1.setVisibility(View.INVISIBLE);
            checkBoxLang2.setVisibility(View.INVISIBLE);
            whichLang.setVisibility(View.INVISIBLE);
            checkBoxEasy.setVisibility(View.INVISIBLE);
            checkBoxNormal.setVisibility(View.INVISIBLE);
            checkBoxHard.setVisibility(View.INVISIBLE);
            buttonHelp.setVisibility(View.INVISIBLE);
            buttonBackToLang.setVisibility(View.INVISIBLE);
            buttonExerciseContinue.setVisibility(View.INVISIBLE);
            buttonSearchVocab.setVisibility(View.INVISIBLE);
            textViewHeadline.setText("Kanji üben");
            buttonExerciseBack.setVisibility(View.VISIBLE);
            vocabField.setVisibility(View.VISIBLE);
            buttonNextVocab.setVisibility(View.VISIBLE);
        }
    }
    //endregion
    //region Exercise
    public void onExerciseBackClick(View view) {
        //region Exercise
        if (chooseExercise) {
            int size1 = easyVocabList.size();
            int size2 = hardVocabList.size();
            int size3 = vocabNumber - size1 - size2;
            buttonExerciseContinue.setVisibility(View.VISIBLE);
            buttonBackToLang.setVisibility(View.VISIBLE);
            buttonExerciseBack.setVisibility(View.INVISIBLE);
            buttonTip.setVisibility(View.INVISIBLE);
            buttonNextVocab.setVisibility(View.INVISIBLE);
            buttonHelp.setVisibility(View.VISIBLE);
            textViewHeadline.setText("Übungseinstellungen");
            vocabField.setVisibility(View.INVISIBLE);
            textViewHeadline.setVisibility(View.VISIBLE);
            buttonShowMe.setVisibility(View.INVISIBLE);
            checkBoxMarkAsNormal.setVisibility(View.INVISIBLE);
            checkBoxLang1.setVisibility(View.VISIBLE);
            checkBoxLang2.setVisibility(View.VISIBLE);
            whichLang.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
            easyVocab.setVisibility(View.INVISIBLE);
            hardVocab.setVisibility(View.INVISIBLE);
            checkBoxEasy.setVisibility(View.VISIBLE);
            checkBoxNormal.setVisibility(View.VISIBLE);
            checkBoxHard.setVisibility(View.VISIBLE);
            textViewCounterEasy.setVisibility(View.VISIBLE);
            textViewCounterNormal.setVisibility(View.VISIBLE);
            textViewCounterHard.setVisibility(View.VISIBLE);
            textViewCounterEasy.setText(String.valueOf(size1));
            textViewCounterNormal.setText(String.valueOf(size3));
            textViewCounterHard.setText(String.valueOf(size2));
            testTextView.setText(".." + easyVocabList + hardVocabList + noRepeat);
            buttonSearchVocab.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.INVISIBLE);
            editSearch.setVisibility(View.INVISIBLE);
            searchResult.setVisibility(View.INVISIBLE);
            radioGroupKeyboard.setVisibility(View.INVISIBLE);
            searchScroll.setVisibility(View.INVISIBLE);
            editFrom.setVisibility(View.VISIBLE);
            editTo.setVisibility(View.VISIBLE);
        }
        //endregion
        //region NumberTrainer
        if (chooseNumbers){
            buttonBackToLang.setVisibility(View.VISIBLE);
            radioButtonNumber1.setVisibility(View.VISIBLE);
            radioButtonNumber2.setVisibility(View.VISIBLE);
            radioButtonNumber3.setVisibility(View.VISIBLE);
            radioButtonNumber4.setVisibility(View.VISIBLE);
            buttonContinueNumber.setVisibility(View.VISIBLE);
            textViewHeadline.setText("Übungseinstellungen");
            if (!radioButtonNumber1.isChecked() && !radioButtonNumber2.isChecked() &&
                    !radioButtonNumber3.isChecked() && !radioButtonNumber4.isChecked()){
                radioButtonNumber1.setChecked(true);
            }
            buttonExerciseBack.setVisibility(View.INVISIBLE);
            buttonNextVocab.setVisibility(View.INVISIBLE);
            vocabField.setVisibility(View.INVISIBLE);
            buttonHelp.setVisibility(View.VISIBLE);
        }
        //endregion
        //region Characters
        if (chooseCharacters){
            kanji=false;
            buttonBackToLang.setVisibility(View.VISIBLE);
            checkBoxLang1.setVisibility(View.VISIBLE);
            checkBoxLang2.setVisibility(View.VISIBLE);
            whichLang.setVisibility(View.VISIBLE);
            checkBoxEasy.setVisibility(View.VISIBLE);
            checkBoxNormal.setVisibility(View.VISIBLE);
            checkBoxHard.setVisibility(View.VISIBLE);
            whichLang.setVisibility(View.VISIBLE);
            buttonExerciseContinue.setVisibility(View.VISIBLE);
            buttonHelp.setVisibility(View.VISIBLE);
            buttonSearchVocab.setVisibility(View.VISIBLE);
            buttonExerciseBack.setVisibility(View.INVISIBLE);
            buttonNextVocab.setVisibility(View.INVISIBLE);
            vocabField.setVisibility(View.INVISIBLE);
            textViewHeadline.setText("Zeichen üben");
        }
        //endregion
        //region Test
        if (chooseTest){
            radioButtonTest2.setClickable(false);
            radioButtonTest3.setClickable(false);
            radioButtonTest4.setClickable(false);
            radioButtonTest1.setChecked(true);
            buttonNextVocab.setVisibility(View.INVISIBLE);
            buttonExerciseBack.setVisibility(View.INVISIBLE);
            editTestLength.setVisibility(View.VISIBLE);
            radioGroupTest.setVisibility(View.VISIBLE);
            buttonBackToLang.setVisibility(View.VISIBLE);
            buttonExerciseContinue.setVisibility(View.VISIBLE);
            buttonHelp.setVisibility(View.VISIBLE);
            if (messageOver) {
                Toast.makeText(getApplicationContext(), "Test beendet!", Toast.LENGTH_LONG).show();
            }
            else if (!messageOver){
                Toast.makeText(getApplicationContext(), "Test abgebrochen!", Toast.LENGTH_LONG).show();
            }

            numberTested = 0;
            rightAmount=0;
            vocabFieldTest.setVisibility(View.INVISIBLE);
            textViewHeadline.setText("Testeinstellungen");
            textViewHeadline.setTextSize(28);
            testStarted = false;
            testOver = false;
            messageOver=false;
            radioButtonTest1.setText("Multiple Choice");
            radioButtonTest2.setText("Schreiben");
            radioButtonTest3.setText("Bla");
            radioButtonTest4.setText("Gemischt");
            testMod1 = false;
            testMod2 = false;
            testMod3 = false;
            testMod4 = false;
        }
        //endregion
    }
    public void onExerciseTipClick(View view) throws IOException {
        //region tip1
        int randomVocab1 = (int) (Math.random()*vocabNumber);
        String word1 = "";
        String tip1;
        for (int i = 0; i < randomVocab1; i++){
            word1 = vocabList.get(randomVocab1);
        }
        String[] wordList1 = word1.split("=");
        tip1 = wordList1[languageNumber];
        //endregion
        //region tip2

        int randomVocab2 = (int) (Math.random()*vocabNumber);
        String word2 = "";
        String tip2;
        for (int i = 0; i < randomVocab2; i++){
            word2 = vocabList.get(randomVocab2);
        }
        String[] wordList2 = word2.split("=");
        tip2 = wordList2[languageNumber];
        //endregion

        //region Reihenfolge
        String tipFull1 = (tip1+"---"+tip2+"---"+toast);
        String tipFull2 = (tip2+"---"+tip1+"---"+toast);
        String tipFull3 = (toast+"---"+tip1+"---"+tip2);
        String tipFull4 = (tip1+"---"+toast+"---"+tip2);
        String tipFull5 = (tip2+"---"+toast+"---"+tip1);
        String tipFull6 = (toast+"---"+tip2+"---"+tip1);

        int randomChooser = (int) (Math.random()*6)+1;
        String tipFinal = "";

        if (randomChooser == 1)
            tipFinal = tipFull1;
        else if (randomChooser == 2)
            tipFinal = tipFull2;
        else if (randomChooser == 3)
            tipFinal = tipFull3;
        else if (randomChooser == 4)
            tipFinal = tipFull4;
        else if (randomChooser == 5)
            tipFinal = tipFull5;
        else if (randomChooser == 6)
            tipFinal = tipFull6;
        //endregion

        Toast.makeText(getApplicationContext(), tipFinal, Toast.LENGTH_LONG).show();
    }
    public void onEasyVocabClick(View view) throws IOException {
        if (easyVocabList.size() == 1){
            easyVocabList.remove(Integer.valueOf(0));
        }
        if (hardVocabList.size() == 1){
            hardVocabList.remove(Integer.valueOf(0));
        }
        if (hardVocabList.contains(zeilenNummer)) {
            easyVocabList.add(zeilenNummer);
            while (hardVocabList.contains(zeilenNummer)){
                hardVocabList.remove(Integer.valueOf(zeilenNummer));
            }

        }
        else if (!easyVocabList.contains(zeilenNummer) && !hardVocabList.contains(zeilenNummer)){
            easyVocabList.add(zeilenNummer);
        }

        //region check/uncheck diff lists
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion
        if (easyVocabList.size() == 0){
            checkBoxEasy.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_EASY",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_EASY",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_EASY",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_EASY",false);
            }
        }
        if (hardVocabList.size() == 0){
            checkBoxHard.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_HARD",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_HARD",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_HARD",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_HARD",false);
            }
        }
        if (vocabNumber - easyVocabList.size() - hardVocabList.size() == 0){
            checkBoxNormal.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_NORMAL",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_NORMAL",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_NORMAL",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_NORMAL",false);
            }
        }


        if (easyVocabList.size() > 0){
            checkBoxEasy.setChecked(true);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_EASY",true);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_EASY",true);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_EASY",true);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_EASY",true);
            }
        }
        if (hardVocabList.size() > 0){
            checkBoxEasy.setChecked(true);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_HARD",true);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_HARD",true);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_HARD",true);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_HARD",true);
            }
        }
        if (vocabNumber - easyVocabList.size() - hardVocabList.size() > 0){
            checkBoxNormal.setChecked(true);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_NORMAL",true);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_NORMAL",true);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_NORMAL",true);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_NORMAL",true);
            }
        }
        //endregion



        //region noRepeat und Random und Easy Vocab
        //region noRepeat
        int size1 = easyVocabList.size()-1;
        int size2 = hardVocabList.size()-1;
        int size3 = vocabNumber-size1-size2;
        int zähler = 0;
        if (checkBoxEasy.isChecked()){
            zähler = zähler + size1;
        }
        if (checkBoxNormal.isChecked()){
            zähler = zähler + size3;
        }
        if (checkBoxHard.isChecked()){
            zähler = zähler + size2;
        }
        if (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked()){
            zähler = zähler + size1 + size2 + size3;
        }

        while (zähler/2 <= noRepeat.size()||(numberTo-numberFrom)/2 <= noRepeat.size())
        {
            noRepeat.remove(0);
        }
        //endregion
        zeilenNummer = (int) ((Math.random()*(vocabNumber+1)));
        //region difficulty-Level
        if ((checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && checkBoxHard.isChecked()) ||
                (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked())) {
            while ((noRepeat.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo){
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (!easyVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (!easyVocabList.contains(zeilenNummer))
                    || (!hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (!checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (easyVocabList.contains(zeilenNummer))
                    || zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (!hardVocabList.contains(zeilenNummer))
                    || zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (!checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (easyVocabList.contains(zeilenNummer))
                    || (hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        }
        noRepeat.add(zeilenNummer);
        //endregion

        //endregion
        //region boolean für checkBoxes
        if (checkBoxLang1.isChecked())
        {
            chooseLang1 = true;
        } else
        {
            chooseLang1 = false;
        }

        if (checkBoxLang2.isChecked())
        {
            chooseLang2 = true;
        } else
        {
            chooseLang2 = false;
        }
        //endregion
        //region split
        String vokabelZeile;
        String vokabel = "";
        vokabelZeile = vocabList.get(zeilenNummer);
        String[] words = vokabelZeile.split("=");
        //endregion
        //region Welche Sprache
        if (chooseLang1 && !chooseLang2)
        {
            vokabel = words[0];
            toast = words[1];
            languageNumber = 1;
        }
        else if (chooseLang2 && !chooseLang1)
        {

            vokabel = words[1];
            toast = words[0];
            languageNumber = 0;
        }
        else if ((chooseLang2 && chooseLang1) || (!chooseLang2 && !chooseLang1)) {
            int auswahl = (int) ((Math.random()*10)+3);
            if (auswahl%2 == 0)
            {
                vokabel = words[0];
                toast = words[1];
                languageNumber = 1;
            }
            else if (auswahl%2 == 1)
            {
                vokabel = words[1];
                toast = words[0];
                languageNumber = 0;
            }
        }
        //endregion
        //region vocab saves

        String eVL = easyVocabList.toString();
        String hVL = hardVocabList.toString();

        String noRepeatToString = noRepeat.toString();

        if (radioButtonJap.isChecked())
        {
            editorJap.putString("LAST_VOCAB_JAP",vokabel);
            editorJap.putString("LAST_VOCAB_SHOW_JAP", toast);
            editorJap.putInt("TIP_LANGUAGE_JAP", languageNumber);
            editorJap.putString("EASY_VOCAB_LIST_JAP", eVL);
            editorJap.putString("HARD_VOCAB_LIST_JAP", hVL);
            editorJap.putInt("LAST_VOCAB_NUMBER_JAP", zeilenNummer);
            editorJap.putInt("LANGUAGE_NUMBER_JAP",languageNumber);
            editorJap.putString("NO_REPEAT_JAP",noRepeatToString);
        }
        else if (radioButtonEng.isChecked())
        {
            editorEng.putString("LAST_VOCAB_ENG",vokabel);
            editorEng.putString("LAST_VOCAB_SHOW_ENG", toast);
            editorEng.putInt("TIP_LANGUAGE_ENG", languageNumber);
            editorEng.putString("EASY_VOCAB_LIST_ENG", eVL);
            editorEng.putString("HARD_VOCAB_LIST_ENG", hVL);
            editorEng.putInt("LAST_VOCAB_NUMBER_ENG", zeilenNummer);
            editorEng.putInt("LANGUAGE_NUMBER_ENG", languageNumber);
            editorEng.putString("NO_REPEAT_ENG",noRepeatToString);
        }
        else if (radioButtonFra.isChecked())
        {
            editorFra.putString("LAST_VOCAB_FRA",vokabel);
            editorFra.putString("LAST_VOCAB_SHOW_FRA", toast);
            editorFra.putInt("TIP_LANGUAGE_FRA", languageNumber);
            editorFra.putString("EASY_VOCAB_LIST_FRA", eVL);
            editorFra.putString("HARD_VOCAB_LIST_FRA", hVL);
            editorFra.putInt("LAST_VOCAB_NUMBER_FRA", zeilenNummer);
            editorFra.putInt("LANGUAGE_NUMBER_FRA", languageNumber);
            editorFra.putString("NO_REPEAT_FRA",noRepeatToString);
        }
        else if (radioButtonIta.isChecked())
        {
            editorIta.putString("LAST_VOCAB_ITA",vokabel);
            editorIta.putString("LAST_VOCAB_SHOW_ITA", toast);
            editorIta.putInt("TIP_LANGUAGE_ITA", languageNumber);
            editorIta.putString("EASY_VOCAB_LIST_ITA", eVL);
            editorIta.putString("HARD_VOCAB_LIST_ITA", hVL);
            editorIta.putInt("LAST_VOCAB_NUMBER_ITA",zeilenNummer);
            editorIta.putInt("LANGUAGE_NUMBER_ITA",languageNumber);
            editorIta.putString("NO_REPEAT_ITA",noRepeatToString);
        }
        //region Fehlerbehebung
        if (easyVocabList.size() == 0) {
            if (radioButtonJap.isChecked()){
                editorJap.putString("EASY_VOCAB_LIST_JAP", "0");
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putString("EASY_VOCAB_LIST_ENG", "0");
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putString("EASY_VOCAB_LIST_FRA", "0");
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putString("EASY_VOCAB_LIST_ITA", "0");
            }
        }
        if (easyVocabList.size() == 0) {
            if (radioButtonJap.isChecked()){
                editorJap.putString("HARD_VOCAB_LIST_JAP", "0");
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putString("HARD_VOCAB_LIST_ENG", "0");
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putString("HARD_VOCAB_LIST_FRA", "0");
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putString("HARD_VOCAB_LIST_ITA", "0");
            }
        }
        if (noRepeat.size() == 0) {
            if (radioButtonJap.isChecked()){
                editorJap.putString("NO_REPEAT_JAP", "0");
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putString("NO_REPEAT_ENG", "0");
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putString("NO_REPEAT_FRA", "0");
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putString("NO_REPEAT_ITA", "0");
            }
        }
        //endregion
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        editor.commit();
        //endregion
        vocabField.setText(vokabel);

        testTextView.setText(".." + easyVocabList+hardVocabList+noRepeat);

    }
    public void onHardVocabClick(View view) throws IOException {
        if (easyVocabList.size() == 1){
            easyVocabList.remove(Integer.valueOf(0));
        }
        if (hardVocabList.size() == 1){
            hardVocabList.remove(Integer.valueOf(0));
        }

        if (easyVocabList.contains(zeilenNummer)) {
            while (easyVocabList.contains(zeilenNummer)){
                easyVocabList.remove(Integer.valueOf(zeilenNummer));
            }
            hardVocabList.add(zeilenNummer);
        }
        else if (!hardVocabList.contains(zeilenNummer) && !easyVocabList.contains(zeilenNummer)) {
            hardVocabList.add(zeilenNummer);
        }
        //region check/uncheck diff lists
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion
        if (easyVocabList.size() == 0){
            checkBoxEasy.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_EASY",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_EASY",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_EASY",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_EASY",false);
            }
        }
        if (hardVocabList.size() == 0){
            checkBoxHard.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_HARD",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_HARD",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_HARD",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_HARD",false);
            }
        }
        if (vocabNumber - easyVocabList.size() - hardVocabList.size() == 0){
            checkBoxNormal.setChecked(false);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_NORMAL",false);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_NORMAL",false);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_NORMAL",false);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_NORMAL",false);
            }
        }


        if (easyVocabList.size() > 0){
            checkBoxEasy.setChecked(true);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_EASY",true);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_EASY",true);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_EASY",true);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_EASY",true);
            }
        }
        if (hardVocabList.size() > 0){
            checkBoxEasy.setChecked(true);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_HARD",true);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_HARD",true);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_HARD",true);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_HARD",true);
            }
        }
        if (vocabNumber - easyVocabList.size() - hardVocabList.size() > 0){
            checkBoxNormal.setChecked(true);
            if (radioButtonJap.isChecked()){
                editorJap.putBoolean("CHOOSE_JAP_NORMAL",true);
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putBoolean("CHOOSE_ENG_NORMAL",true);
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putBoolean("CHOOSE_FRA_NORMAL",true);
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putBoolean("CHOOSE_ITA_NORMAL",true);
            }
        }
        //endregion



        //region noRepeat und Random und Easy Vocab
        //region noRepeat

        int size1 = easyVocabList.size()-1;
        int size2 = hardVocabList.size()-1;
        int size3 = vocabNumber-size1-size2;
        int zähler = 0;
        if (checkBoxEasy.isChecked()){
            zähler = zähler + size1;
        }
        if (checkBoxNormal.isChecked()){
            zähler = zähler + size3;
        }
        if (checkBoxHard.isChecked()){
            zähler = zähler + size2;
        }
        if (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked()){
            zähler = zähler + size1 + size2 + size3;
        }

        while (zähler/2 <= noRepeat.size()||(numberTo-numberFrom)/2 <= noRepeat.size())
        {
            noRepeat.remove(0);
        }
        //endregion
        zeilenNummer = (int) ((Math.random()*(vocabNumber+1)));
        //region difficulty-Level
        if ((checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && checkBoxHard.isChecked()) ||
                (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked())) {
            while ((noRepeat.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo){
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (!easyVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (!easyVocabList.contains(zeilenNummer))
                    || (!hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (!checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (easyVocabList.contains(zeilenNummer))
                    || zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (!hardVocabList.contains(zeilenNummer))
                    || zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        } else if (!checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
            while ((noRepeat.contains(zeilenNummer)) || (easyVocabList.contains(zeilenNummer))
                    || (hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                zeilenNummer = (int) (Math.random() * (vocabNumber));
            }
        }


        //endregion
        //endregion
        noRepeat.add(zeilenNummer);

        String vokabelZeile;
        String vokabel = "";

        vokabelZeile = vocabList.get(zeilenNummer);
        //region boolean für checkBoxes
        if (checkBoxLang1.isChecked())
        {
            chooseLang1 = true;
        } else
        {
            chooseLang1 = false;
        };
        if (checkBoxLang2.isChecked())
        {
            chooseLang2 = true;
        } else
        {
            chooseLang2 = false;
        }
        //endregion
        //region split

        String[] words = vokabelZeile.split("=");
        //endregion
        //region Welche Sprache
        if (chooseLang1 && !chooseLang2)
        {
            vokabel = words[0];
            toast = words[1];
            languageNumber = 1;
        }
        else if (chooseLang2 && !chooseLang1)
        {

            vokabel = words[1];
            toast = words[0];
            languageNumber = 0;
        }
        else if ((chooseLang2 && chooseLang1) || (!chooseLang2 && !chooseLang1)) {
            int auswahl = (int) ((Math.random()*10)+3);
            if (auswahl%2 == 0)
            {
                vokabel = words[0];
                toast = words[1];
                languageNumber = 1;
            }
            else if (auswahl%2 == 1)
            {
                vokabel = words[1];
                toast = words[0];
                languageNumber = 0;
            }
        }
        //endregion
        //region vocab saves

        String eVL = easyVocabList.toString();
        String hVL = hardVocabList.toString();

        String noRepeatToString = noRepeat.toString();

        if (radioButtonJap.isChecked())
        {
            editorJap.putString("LAST_VOCAB_JAP",vokabel);
            editorJap.putString("LAST_VOCAB_SHOW_JAP", toast);
            editorJap.putInt("TIP_LANGUAGE_JAP", languageNumber);
            editorJap.putString("EASY_VOCAB_LIST_JAP", eVL);
            editorJap.putString("HARD_VOCAB_LIST_JAP", hVL);
            editorJap.putInt("LAST_VOCAB_NUMBER_JAP", zeilenNummer);
            editorJap.putInt("LANGUAGE_NUMBER_JAP",languageNumber);
            editorJap.putString("NO_REPEAT_JAP",noRepeatToString);
        }
        else if (radioButtonEng.isChecked())
        {
            editorEng.putString("LAST_VOCAB_ENG",vokabel);
            editorEng.putString("LAST_VOCAB_SHOW_ENG", toast);
            editorEng.putInt("TIP_LANGUAGE_ENG", languageNumber);
            editorEng.putString("EASY_VOCAB_LIST_ENG", eVL);
            editorEng.putString("HARD_VOCAB_LIST_ENG", hVL);
            editorEng.putInt("LAST_VOCAB_NUMBER_ENG", zeilenNummer);
            editorEng.putInt("LANGUAGE_NUMBER_ENG", languageNumber);
            editorEng.putString("NO_REPEAT_ENG",noRepeatToString);
        }
        else if (radioButtonFra.isChecked())
        {
            editorFra.putString("LAST_VOCAB_FRA",vokabel);
            editorFra.putString("LAST_VOCAB_SHOW_FRA", toast);
            editorFra.putInt("TIP_LANGUAGE_FRA", languageNumber);
            editorFra.putString("EASY_VOCAB_LIST_FRA", eVL);
            editorFra.putString("HARD_VOCAB_LIST_FRA", hVL);
            editorFra.putInt("LAST_VOCAB_NUMBER_FRA", zeilenNummer);
            editorFra.putInt("LANGUAGE_NUMBER_FRA", languageNumber);
            editorFra.putString("NO_REPEAT_FRA",noRepeatToString);
        }
        else if (radioButtonIta.isChecked())
        {
            editorIta.putString("LAST_VOCAB_ITA",vokabel);
            editorIta.putString("LAST_VOCAB_SHOW_ITA", toast);
            editorIta.putInt("TIP_LANGUAGE_ITA", languageNumber);
            editorIta.putString("EASY_VOCAB_LIST_ITA", eVL);
            editorIta.putString("HARD_VOCAB_LIST_ITA", hVL);
            editorIta.putInt("LAST_VOCAB_NUMBER_ITA",zeilenNummer);
            editorIta.putInt("LANGUAGE_NUMBER_ITA",languageNumber);
            editorIta.putString("NO_REPEAT_ITA",noRepeatToString);
        }
        //region Fehlerbehebung
        if (easyVocabList.size() == 0) {
            if (radioButtonJap.isChecked()){
                editorJap.putString("EASY_VOCAB_LIST_JAP", "0");
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putString("EASY_VOCAB_LIST_ENG", "0");
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putString("EASY_VOCAB_LIST_FRA", "0");
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putString("EASY_VOCAB_LIST_ITA", "0");
            }
        }
        if (easyVocabList.size() == 0) {
            if (radioButtonJap.isChecked()){
                editorJap.putString("HARD_VOCAB_LIST_JAP", "0");
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putString("HARD_VOCAB_LIST_ENG", "0");
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putString("HARD_VOCAB_LIST_FRA", "0");
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putString("HARD_VOCAB_LIST_ITA", "0");
            }
        }
        if (noRepeat.size() == 0) {
            if (radioButtonJap.isChecked()){
                editorJap.putString("NO_REPEAT_JAP", "0");
            }
            else if (radioButtonEng.isChecked()){
                editorEng.putString("NO_REPEAT_ENG", "0");
            }
            else if (radioButtonFra.isChecked()){
                editorFra.putString("NO_REPEAT_FRA", "0");
            }
            else if (radioButtonIta.isChecked()){
                editorIta.putString("NO_REPEAT_ITA", "0");
            }
        }
        //endregion
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        editor.commit();
        //endregion
        vocabField.setText(vokabel);
        testTextView.setText(".." + easyVocabList+hardVocabList+noRepeat);

    }
    public void onExerciseNextClick(View view) throws IOException {
        //region NextExercise
        if (chooseExercise) {
            if (easyVocabList.size() == 1) {
                easyVocabList.remove(Integer.valueOf(0));
            }
            if (hardVocabList.size() == 1) {
                hardVocabList.remove(Integer.valueOf(0));
            }
            if (checkBoxMarkAsNormal.isChecked()) {
                if (easyVocabList.contains(zeilenNummer)) {
                    easyVocabList.remove(Integer.valueOf(zeilenNummer));
                }
                if (hardVocabList.contains(zeilenNummer)) {
                    hardVocabList.remove(Integer.valueOf(zeilenNummer));
                }
            }

            //region check/uncheck diff lists
            //region USER PREFERENCES DEFINE
            SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
            SharedPreferences.Editor editorJap = prefsJap.edit();
            SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
            SharedPreferences.Editor editorEng = prefsEng.edit();
            SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
            SharedPreferences.Editor editorFra = prefsFra.edit();
            SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
            SharedPreferences.Editor editorIta = prefsIta.edit();
            SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            //endregion
            if (easyVocabList.size() == 0) {
                checkBoxEasy.setChecked(false);
                if (radioButtonJap.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_EASY", false);
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_EASY", false);
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_EASY", false);
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_EASY", false);
                }
            }
            if (hardVocabList.size() == 0) {
                checkBoxHard.setChecked(false);
                if (radioButtonJap.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_HARD", false);
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_HARD", false);
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_HARD", false);
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_HARD", false);
                }
            }
            if (vocabNumber - easyVocabList.size() - hardVocabList.size() == 0) {
                checkBoxNormal.setChecked(false);
                if (radioButtonJap.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_NORMAL", false);
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_NORMAL", false);
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_NORMAL", false);
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_NORMAL", false);
                }
            }


            if (easyVocabList.size() > 0) {
                checkBoxEasy.setChecked(true);
                if (radioButtonJap.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_EASY", true);
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_EASY", true);
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_EASY", true);
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_EASY", true);
                }
            }
            if (hardVocabList.size() > 0) {
                checkBoxEasy.setChecked(true);
                if (radioButtonJap.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_HARD", true);
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_HARD", true);
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_HARD", true);
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_HARD", true);
                }
            }
            if (vocabNumber - easyVocabList.size() - hardVocabList.size() > 0) {
                checkBoxNormal.setChecked(true);
                if (radioButtonJap.isChecked()) {
                    editorJap.putBoolean("CHOOSE_JAP_NORMAL", true);
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putBoolean("CHOOSE_ENG_NORMAL", true);
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putBoolean("CHOOSE_FRA_NORMAL", true);
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putBoolean("CHOOSE_ITA_NORMAL", true);
                }
            }
            //endregion

            //region noRepeat
            int size1 = easyVocabList.size();
            int size2 = hardVocabList.size();
            int size3 = vocabNumber - size1 - size2;
            int zähler = 0;
            if (checkBoxEasy.isChecked()) {
                zähler = zähler + size1;
            }
            if (checkBoxNormal.isChecked()) {
                zähler = zähler + size3;
            }
            if (checkBoxHard.isChecked()) {
                zähler = zähler + size2;
            }
            if (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
                zähler = zähler + size1 + size2 + size3;
            }

            while ((zähler / 2 <= noRepeat.size())||(numberTo-numberFrom)/2 <= noRepeat.size()) {
                noRepeat.remove(0);
            }
            //endregion
            zeilenNummer = (int) (Math.random() * (vocabNumber));
            //region difficulty-Level
            if ((checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && checkBoxHard.isChecked()) ||
                    (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked())) {
                while ((noRepeat.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo){
                    zeilenNummer = (int) (Math.random() * (vocabNumber));
                }
            } else if (checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
                while ((noRepeat.contains(zeilenNummer)) || (hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                    zeilenNummer = (int) (Math.random() * (vocabNumber));
                }
            } else if (checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
                while ((noRepeat.contains(zeilenNummer)) || (!easyVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                    zeilenNummer = (int) (Math.random() * (vocabNumber));
                }
            } else if (checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
                while ((noRepeat.contains(zeilenNummer)) || (!easyVocabList.contains(zeilenNummer))
                        || (!hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                    zeilenNummer = (int) (Math.random() * (vocabNumber));
                }
            } else if (!checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
                while ((noRepeat.contains(zeilenNummer)) || (easyVocabList.contains(zeilenNummer))
                        || zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                    zeilenNummer = (int) (Math.random() * (vocabNumber));
                }
            } else if (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked() && checkBoxHard.isChecked()) {
                while ((noRepeat.contains(zeilenNummer)) || (!hardVocabList.contains(zeilenNummer))
                        || zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                    zeilenNummer = (int) (Math.random() * (vocabNumber));
                }
            } else if (!checkBoxEasy.isChecked() && checkBoxNormal.isChecked() && !checkBoxHard.isChecked()) {
                while ((noRepeat.contains(zeilenNummer)) || (easyVocabList.contains(zeilenNummer))
                        || (hardVocabList.contains(zeilenNummer))|| zeilenNummer<numberFrom || zeilenNummer>numberTo) {
                    zeilenNummer = (int) (Math.random() * (vocabNumber));
                }
            }
            noRepeat.add(zeilenNummer);
            //endregion


            //region boolean für checkBoxes
            if (checkBoxLang1.isChecked()) {
                chooseLang1 = true;
            } else {
                chooseLang1 = false;
            }

            if (checkBoxLang2.isChecked()) {
                chooseLang2 = true;
            } else {
                chooseLang2 = false;
            }
            //endregion
            //region split
            String vokabelZeile;
            String vokabel = "";
            vokabelZeile = vocabList.get(zeilenNummer);
            String[] words = vokabelZeile.split("=");
            //endregion
            //region Welche Sprache
            if (chooseLang1 && !chooseLang2) {
                vokabel = words[0];
                toast = words[1];
                languageNumber = 1;
            } else if (chooseLang2 && !chooseLang1) {
                vokabel = words[1];
                toast = words[0];
                languageNumber = 0;
            } else if ((chooseLang2 && chooseLang1) || (!chooseLang2 && !chooseLang1)) {
                int auswahl = (int) ((Math.random() * 10) + 3);
                if (auswahl % 2 == 0) {
                    vokabel = words[0];
                    toast = words[1];
                    languageNumber = 1;
                } else if (auswahl % 2 == 1) {
                    vokabel = words[1];
                    toast = words[0];
                    languageNumber = 0;
                }
            }

            //region was hier passiert trifft nur auf die Verbenübung zu
            if (zeilenNummer>2 && zeilenNummer<112){
                if (chooseLang1 && !chooseLang2) {
                    vokabel = words[0];
                    toast = words[1];
                    languageNumber = 1;
                } else if (chooseLang2 && !chooseLang1) {
                    int auswahl = (int) ((Math.random() * 3) + 1);
                    vokabel = words[auswahl];
                    toast = words[0];
                    languageNumber = 0;
                } else if ((chooseLang2 && chooseLang1)||(!chooseLang2&&!chooseLang1)){
                    int auswahl = (int) ((Math.random() * 4));
                    switch (auswahl){
                        case 0: vokabel=words[0]; toast = words[1]+" + "+words[2]+" + "+words[3]; languageNumber = 1; break;
                        case 1: vokabel=words[1]; toast = words[0]; languageNumber = 0; break;
                        case 2: vokabel=words[2]; toast = words[0]+" + "+words[1]; languageNumber = 0; break;
                        case 3: vokabel=words[3]; toast = words[0]+" + "+words[1]; languageNumber = 0; break;
                    }


                }
            }
            //endregion
            //region was hier passiert trifft nur auf die Adjektivübung zu
            if (zeilenNummer>115 && zeilenNummer<196){
                if (chooseLang1 && !chooseLang2) {
                    vokabel = words[0];
                    toast = words[1];
                    languageNumber = 1;
                } else if (chooseLang2 && !chooseLang1) {
                    int auswahl = (int) ((Math.random() * 2) + 1);
                    vokabel = words[auswahl];
                    toast = words[0];
                    languageNumber = 0;
                } else if ((chooseLang2 && chooseLang1)||(!chooseLang2&&!chooseLang1)){
                    int auswahl = (int) ((Math.random() * 3));
                    switch (auswahl){
                        case 0: vokabel=words[0]; toast = words[1]+" + "+words[2]; languageNumber = 1; break;
                        case 1: vokabel=words[1]; toast = words[0]; languageNumber = 0; break;
                        case 2: vokabel=words[2]; toast = words[0]+" + "+words[1]; languageNumber = 0; break;
                    }


                }
            }
            //endregion
            //endregion
            //region vocab saves

            String eVL = easyVocabList.toString();
            String hVL = hardVocabList.toString();

            String noRepeatToString = noRepeat.toString();

            if (radioButtonJap.isChecked()) {
                editorJap.putString("LAST_VOCAB_JAP", vokabel);
                editorJap.putString("LAST_VOCAB_SHOW_JAP", toast);
                editorJap.putInt("TIP_LANGUAGE_JAP", languageNumber);
                editorJap.putString("EASY_VOCAB_LIST_JAP", eVL);
                editorJap.putString("HARD_VOCAB_LIST_JAP", hVL);
                editorJap.putInt("LAST_VOCAB_NUMBER_JAP", zeilenNummer);
                editorJap.putInt("LANGUAGE_NUMBER_JAP", languageNumber);
                editorJap.putString("NO_REPEAT_JAP", noRepeatToString);
            } else if (radioButtonEng.isChecked()) {
                editorEng.putString("LAST_VOCAB_ENG", vokabel);
                editorEng.putString("LAST_VOCAB_SHOW_ENG", toast);
                editorEng.putInt("TIP_LANGUAGE_ENG", languageNumber);
                editorEng.putString("EASY_VOCAB_LIST_ENG", eVL);
                editorEng.putString("HARD_VOCAB_LIST_ENG", hVL);
                editorEng.putInt("LAST_VOCAB_NUMBER_ENG", zeilenNummer);
                editorEng.putInt("LANGUAGE_NUMBER_ENG", languageNumber);
                editorEng.putString("NO_REPEAT_ENG", noRepeatToString);
            } else if (radioButtonFra.isChecked()) {
                editorFra.putString("LAST_VOCAB_FRA", vokabel);
                editorFra.putString("LAST_VOCAB_SHOW_FRA", toast);
                editorFra.putInt("TIP_LANGUAGE_FRA", languageNumber);
                editorFra.putString("EASY_VOCAB_LIST_FRA", eVL);
                editorFra.putString("HARD_VOCAB_LIST_FRA", hVL);
                editorFra.putInt("LAST_VOCAB_NUMBER_FRA", zeilenNummer);
                editorFra.putInt("LANGUAGE_NUMBER_FRA", languageNumber);
                editorFra.putString("NO_REPEAT_FRA", noRepeatToString);
            } else if (radioButtonIta.isChecked()) {
                editorIta.putString("LAST_VOCAB_ITA", vokabel);
                editorIta.putString("LAST_VOCAB_SHOW_ITA", toast);
                editorIta.putInt("TIP_LANGUAGE_ITA", languageNumber);
                editorIta.putString("EASY_VOCAB_LIST_ITA", eVL);
                editorIta.putString("HARD_VOCAB_LIST_ITA", hVL);
                editorIta.putInt("LAST_VOCAB_NUMBER_ITA", zeilenNummer);
                editorIta.putInt("LANGUAGE_NUMBER_ITA", languageNumber);
                editorIta.putString("NO_REPEAT_ITA", noRepeatToString);
            }
            //region Fehlerbehebung
            if (easyVocabList.size() == 0) {
                if (radioButtonJap.isChecked()) {
                    editorJap.putString("EASY_VOCAB_LIST_JAP", "0");
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putString("EASY_VOCAB_LIST_ENG", "0");
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putString("EASY_VOCAB_LIST_FRA", "0");
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putString("EASY_VOCAB_LIST_ITA", "0");
                }
            }
            if (easyVocabList.size() == 0) {
                if (radioButtonJap.isChecked()) {
                    editorJap.putString("HARD_VOCAB_LIST_JAP", "0");
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putString("HARD_VOCAB_LIST_ENG", "0");
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putString("HARD_VOCAB_LIST_FRA", "0");
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putString("HARD_VOCAB_LIST_ITA", "0");
                }
            }
            if (noRepeat.size() == 0) {
                if (radioButtonJap.isChecked()) {
                    editorJap.putString("NO_REPEAT_JAP", "0");
                } else if (radioButtonEng.isChecked()) {
                    editorEng.putString("NO_REPEAT_ENG", "0");
                } else if (radioButtonFra.isChecked()) {
                    editorFra.putString("NO_REPEAT_FRA", "0");
                } else if (radioButtonIta.isChecked()) {
                    editorIta.putString("NO_REPEAT_ITA", "0");
                }
            }
            //endregion
            editorJap.commit();
            editorEng.commit();
            editorFra.commit();
            editorIta.commit();
            editor.commit();
            //endregion
            vocabField.setText(vokabel);

            testTextView.setText(".." + easyVocabList + hardVocabList + noRepeat);


        }
        //endregion
        //region NextNumber
        if (chooseNumbers){
            if (radioButtonNumber1.isChecked()){
                randomNumber = (int) (Math.random() * 100 + 1);
            }
            if (radioButtonNumber2.isChecked()){
                randomNumber = (int) (Math.random() * 1000 + 1);
            }
            if (radioButtonNumber3.isChecked()){
                randomNumber = (int) (Math.random() * 1000000 + 1);
            }
            if (radioButtonNumber4.isChecked()){
                randomNumber = (int) (Math.random() * 1000000000 + 1);
            }
            String numberString = String.valueOf(randomNumber);
            //region Punkte zwischen Ziffern
            int digitCounter = numberString.length();

            char[] c = numberString.toCharArray();
            if (digitCounter >= 4){
                if (digitCounter%3 == 0){
                    if (digitCounter == 9){
                        numberString = c[0] + c[1] + c[2] + "." + c[3] + c[4] + c[5] + "." + c[6] + c[7] + c[8];
                    }
                    else if (digitCounter == 6){
                        numberString = c[0] + c[1] + c[2] + "." + c[3] + c[4] + c[5];
                    }
                }
                else if (digitCounter%3 == 1){
                    if (digitCounter == 4){
                        numberString = c[0] + "."+ c[1] + c[2] + c[3];
                    }
                    else if (digitCounter == 7){
                        numberString = c[0] + "."+ c[1] + c[2] + c[3] + "." + c[4] + c[5] + c[6];
                    }
                    else if (digitCounter == 10){
                        numberString = c[0] + "."+ c[1] + c[2] + c[3] + "." + c[4] + c[5] + c[6] + "." + c[7] + c[8] + c[9];
                    }
                }
                else if (digitCounter%3 == 2){
                    if (digitCounter == 8){
                        numberString = c[0] + c[1] + "." + c[2] + c[3] + c[4] + "." + c[5] + c[6] + c[7] + c[8];
                    }
                    else if (digitCounter == 5){
                        numberString = c[0] + c[1] + "." + c[2] + c[3] + c[4];
                    }
                }
            }

            //endregion
            vocabField.setText(numberString);
        }
        //endregion
        //region NextCharacter
        if (chooseCharacters){
            if (!kanji) {
                int numberChar;
                numberChar = (int) (Math.random() * charNumber);
                // Deutsch und Japanisch
                if ((checkBoxLang1.isChecked() && checkBoxLang2.isChecked()) ||
                        (!checkBoxLang1.isChecked() && !checkBoxLang2.isChecked())) {
                    // Kana
                    if (((checkBoxEasy.isChecked() && checkBoxNormal.isChecked()) ||
                            (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked()))) {
                        //grundlegend
                        if (!checkBoxHard.isChecked()) {
                            while ((numberChar > 242)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                    }
                    //nur Hiragana
                    else if (checkBoxEasy.isChecked() && !checkBoxNormal.isChecked()) {
                        //erweitert
                        if (checkBoxHard.isChecked()) {
                            while ((numberChar < 243 || numberChar > 275) && (numberChar > 171)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                        //grundlegend
                        else if (!checkBoxHard.isChecked()) {
                            while ((numberChar < 101) || (numberChar > 171) && (numberChar > 70)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                    }
                    //nur Katakana
                    else if (!checkBoxEasy.isChecked() && checkBoxNormal.isChecked()) {
                        //erweitert
                        if (checkBoxHard.isChecked()) {
                            while ((numberChar > 100 && numberChar < 172) || (numberChar > 242 && numberChar < 276)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                        //grundlegend
                        else if (!checkBoxHard.isChecked()) {
                            while (((numberChar < 172) && (numberChar > 70)) || (numberChar > 242)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                    }
                }
                // Nur deutsch
                else if (!checkBoxLang2.isChecked() && checkBoxLang1.isChecked()) {
                    if (checkBoxHard.isChecked()) {
                        while (numberChar > 100) {
                            numberChar = (int) (Math.random() * charNumber);
                        }
                    } else if (!checkBoxHard.isChecked()) {
                        while (numberChar > 70) {
                            numberChar = (int) (Math.random() * charNumber);
                        }
                    }
                }
                // Nur japanisch
                else if (!checkBoxLang1.isChecked() && checkBoxLang2.isChecked()) {
                    // Kana
                    if (((checkBoxEasy.isChecked() && checkBoxNormal.isChecked()) ||
                            (!checkBoxEasy.isChecked() && !checkBoxNormal.isChecked()))) {
                        //erweitert
                        if (checkBoxHard.isChecked()) {
                            while (numberChar < 101) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                        //grundlegend
                        else if (!checkBoxHard.isChecked()) {
                            while ((numberChar < 101) || (numberChar > 242)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                    }
                    //nur Hiragana
                    else if (checkBoxEasy.isChecked() && !checkBoxNormal.isChecked()) {
                        //erweitert
                        if (checkBoxHard.isChecked()) {
                            while ((numberChar < 243 || numberChar > 275) && (numberChar < 101 || numberChar > 171)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                        //grundlegend
                        else if (!checkBoxHard.isChecked()) {
                            while ((numberChar < 101) || (numberChar > 171)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                    }
                    //nur Katakana
                    else if (!checkBoxEasy.isChecked() && checkBoxNormal.isChecked()) {
                        //erweitert
                        if (checkBoxHard.isChecked()) {
                            while ((numberChar < 276) && (numberChar < 172) || (numberChar > 242)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                        //grundlegend
                        else if (!checkBoxHard.isChecked()) {
                            while ((numberChar < 172) || (numberChar > 242)) {
                                numberChar = (int) (Math.random() * charNumber);
                            }
                        }
                    }
                }


                vocabField.setText(characterList.get(numberChar));
            }
            if (kanji){

            }
        }
        //endregion
        //region NextTest
        if (chooseTest){
            if (testOver){
                textViewHeadline.setText("Test beendet!");
                buttonNextVocab.setVisibility(View.INVISIBLE);
                radioGroupTest.setVisibility(View.INVISIBLE);
                vocabFieldTest.setVisibility(View.VISIBLE);
                vocabFieldTest.setText(rightAmount + "/" + numberTestAmount + " richtig! (" + ((rightAmount * 100) / numberTestAmount) + "%)");
                messageOver = true;
            }
            if(!testStarted){
                vocabFieldTest.setVisibility(View.VISIBLE);
                buttonNextVocab.setText("Weiter!");
                testStarted = true;
                testMod1 = false;
                testMod2 = false;
                testMod3 = false;
                testMod4 = false;
                if (radioButtonTest1.isChecked()){
                    testMod1=true;
                }
                else if (radioButtonTest2.isChecked()){
                    testMod2=true;
                }
                else if (radioButtonTest3.isChecked()){
                    testMod3=true;
                }
                else if (radioButtonTest4.isChecked()){
                    testMod4=true;
                }
                //region choose 4 words
                if (radioButtonTest1.isChecked()){
                    int langNumberTest = (int) (Math.random()*2);
                    radioGroupTest.setVisibility(View.VISIBLE);
                    int randomTest1 = (int) (Math.random() * vocabNumber);
                    int randomTest2 = (int) (Math.random() * vocabNumber);
                    while (randomTest1 == randomTest2) {
                        randomTest2 = (int) (Math.random() * vocabNumber);
                    }
                    int randomTest3 = (int) (Math.random() * vocabNumber);
                    while ((randomTest3 == randomTest1) || (randomTest3 == randomTest2)){
                        randomTest3 = (int) (Math.random() * vocabNumber);
                    }
                    int randomTest4 = (int) (Math.random() * vocabNumber);
                    while ((randomTest4 == randomTest1) || (randomTest4 == randomTest2) || (randomTest4 == randomTest3)){
                        randomTest4 = (int) (Math.random() * vocabNumber);
                    }
                    String random1="";
                    String random2="";
                    String random3="";
                    String random4="";
                    for (int i=0;i<randomTest1;i++) {
                        random1 = vocabList.get(randomTest1);
                    }
                    for (int i=0;i<randomTest2;i++){
                        random2 = vocabList.get(randomTest2);
                    }
                    for (int i=0;i<randomTest3;i++) {
                        random3 = vocabList.get(randomTest3);
                    }
                    for (int i=0;i<randomTest4;i++){
                        random4 = vocabList.get(randomTest4);
                    }
                    String[] randomSplit1 = random1.split("=");
                    String[] randomSplit2 = random2.split("=");
                    String[] randomSplit3 = random3.split("=");
                    String[] randomSplit4 = random4.split("=");

                    String a1 = randomSplit1[langNumberTest];
                    String a2 = randomSplit2[langNumberTest];
                    String a3 = randomSplit3[langNumberTest];
                    String a4 = randomSplit4[langNumberTest];

                    List<String> listOfChoices = new ArrayList<>();
                    listOfChoices.add(a1);
                    listOfChoices.add(a2);
                    listOfChoices.add(a3);
                    listOfChoices.add(a4);
                    Collections.shuffle(listOfChoices);

                    radioButtonTest1.setText(listOfChoices.get(0));
                    radioButtonTest2.setText(listOfChoices.get(1));
                    radioButtonTest3.setText(listOfChoices.get(2));
                    radioButtonTest4.setText(listOfChoices.get(3));



                    if (langNumberTest == 0){
                        vocabFieldTest.setText(randomSplit1[1]);
                    }
                    else if (langNumberTest == 1){
                        vocabFieldTest.setText(randomSplit1[0]);
                    }

                    if (listOfChoices.get(0).equals(a1)){
                        rightNumber = 1;
                    }
                    else if (listOfChoices.get(1).equals(a1)){
                        rightNumber = 2;
                    }
                    else if (listOfChoices.get(2).equals(a1)){
                        rightNumber = 3;
                    }
                    else if (listOfChoices.get(3).equals(a1)){
                        rightNumber = 4;
                    }

                    messageOver = false;
                }
                //endregion

            }
            else if (testStarted && !testOver) {
                ++numberTested;
                textViewHeadline.setText(numberTested + "/" + editTestLength.getText() + " Aufgaben geschafft");
                if ((radioButtonTest1.isChecked() && (rightNumber==1)) || (radioButtonTest2.isChecked() && (rightNumber==2))
                        || (radioButtonTest3.isChecked() && (rightNumber==3)) || (radioButtonTest4.isChecked() && (rightNumber==4))){
                    rightAmount = rightAmount + 1;
                }
                //region choose 4 words
                    int langNumberTest = (int) (Math.random()*2);
                    radioGroupTest.setVisibility(View.VISIBLE);
                    int randomTest1 = (int) (Math.random() * vocabNumber);
                    int randomTest2 = (int) (Math.random() * vocabNumber);
                    while (randomTest1 == randomTest2) {
                        randomTest2 = (int) (Math.random() * vocabNumber);
                    }
                    int randomTest3 = (int) (Math.random() * vocabNumber);
                    while ((randomTest3 == randomTest1) || (randomTest3 == randomTest2)){
                        randomTest3 = (int) (Math.random() * vocabNumber);
                    }
                    int randomTest4 = (int) (Math.random() * vocabNumber);
                    while ((randomTest4 == randomTest1) || (randomTest4 == randomTest2) || (randomTest4 == randomTest3)){
                        randomTest4 = (int) (Math.random() * vocabNumber);
                    }
                    String random1="";
                    String random2="";
                    String random3="";
                    String random4="";
                    for (int i=0;i<randomTest1;i++) {
                        random1 = vocabList.get(randomTest1);
                    }
                    for (int i=0;i<randomTest2;i++){
                        random2 = vocabList.get(randomTest2);
                    }
                    for (int i=0;i<randomTest3;i++) {
                        random3 = vocabList.get(randomTest3);
                    }
                    for (int i=0;i<randomTest4;i++){
                        random4 = vocabList.get(randomTest4);
                    }
                    String[] randomSplit1 = random1.split("=");
                    String[] randomSplit2 = random2.split("=");
                    String[] randomSplit3 = random3.split("=");
                    String[] randomSplit4 = random4.split("=");

                    String a1 = randomSplit1[langNumberTest];
                    String a2 = randomSplit2[langNumberTest];
                    String a3 = randomSplit3[langNumberTest];
                    String a4 = randomSplit4[langNumberTest];

                    List<String> listOfChoices = new ArrayList<>();
                    listOfChoices.add(a1);
                    listOfChoices.add(a2);
                    listOfChoices.add(a3);
                    listOfChoices.add(a4);
                    Collections.shuffle(listOfChoices);

                    radioButtonTest1.setText(listOfChoices.get(0));
                    radioButtonTest2.setText(listOfChoices.get(1));
                    radioButtonTest3.setText(listOfChoices.get(2));
                    radioButtonTest4.setText(listOfChoices.get(3));

                    if (langNumberTest == 0){
                        vocabFieldTest.setText(randomSplit1[1]);
                    }
                    else if (langNumberTest == 1){
                        vocabFieldTest.setText(randomSplit1[0]);
                    }

                    if (listOfChoices.get(0).equals(a1)){
                        rightNumber = 1;
                    }
                    else if (listOfChoices.get(1).equals(a1)){
                        rightNumber = 2;
                    }
                    else if (listOfChoices.get(2).equals(a1)){
                        rightNumber = 3;
                    }
                    else if (listOfChoices.get(3).equals(a1)){
                        rightNumber = 4;
                    }
                messageOver = false;
                //endregion


                if (numberTested == numberTestAmount){
                    buttonNextVocab.setText("Test beenden");
                    testOver = true;
                    messageOver = false;
                }
            }
        }
        //endregion
    }

    public void onShowMeClick(View view) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }
    public void markAsNormalClick(View view) {
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion

        if (radioButtonJap.isChecked()) {
            if (checkBoxMarkAsNormal.isChecked()) {
                editorJap.putBoolean("MARK_AS_NORMAL_JAP",true);
            }
            else if (!checkBoxMarkAsNormal.isChecked()){
                editorJap.putBoolean("MARK_AS_NORMAL_JAP",false);
            }
        }

        if (radioButtonEng.isChecked()) {
            if (checkBoxMarkAsNormal.isChecked()) {
                editorEng.putBoolean("MARK_AS_NORMAL_ENG",true);
            }
            else if (!checkBoxMarkAsNormal.isChecked()){
                editorEng.putBoolean("MARK_AS_NORMAL_ENG",false);
            }
        }

        if (radioButtonFra.isChecked()) {
            if (checkBoxMarkAsNormal.isChecked()) {
                editorFra.putBoolean("MARK_AS_NORMAL_FRA",true);
            }
            else if (!checkBoxMarkAsNormal.isChecked()){
                editorFra.putBoolean("MARK_AS_NORMAL_FRA",false);
            }
        }

        if (radioButtonIta.isChecked()) {
            if (checkBoxMarkAsNormal.isChecked()) {
                editorIta.putBoolean("MARK_AS_NORMAL_ITA",true);
            }
            else if (!checkBoxMarkAsNormal.isChecked()){
                editorIta.putBoolean("MARK_AS_NORMAL_ITA",false);
            }
        }
        editor.commit();
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
    }
    //endregion
    //region spec Settings
    public void onResetEasy(View view) {
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion
        easyVocabList.clear();
        if (radioButtonJap.isChecked()){
            editorJap.putString("EASY_VOCAB_LIST_JAP", "0");
        }
        else if (radioButtonEng.isChecked()){
            editorEng.putString("EASY_VOCAB_LIST_ENG", "0");
        }
        else if (radioButtonFra.isChecked()){
            editorFra.putString("EASY_VOCAB_LIST_FRA", "0");
        }
        else if (radioButtonIta.isChecked()){
            editorIta.putString("EASY_VOCAB_LIST_ITA", "0");
        }

        editor.commit();
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        Toast.makeText(getApplicationContext(), "Die Vokabelliste 'Einfach' wurde gelöscht!", Toast.LENGTH_SHORT).show();
        testTextView.setText(".." + easyVocabList+hardVocabList+noRepeat);
    }
    public void onResetHard(View view) {
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion
        hardVocabList.clear();
        if (radioButtonJap.isChecked()){
            editorJap.putString("HARD_VOCAB_LIST_JAP", "0");
        }
        else if (radioButtonEng.isChecked()){
            editorEng.putString("HARD_VOCAB_LIST_ENG", "0");
        }
        else if (radioButtonFra.isChecked()){
            editorFra.putString("HARD_VOCAB_LIST_FRA", "0");
        }
        else if (radioButtonIta.isChecked()){
            editorIta.putString("HARD_VOCAB_LIST_ITA", "0");
        }
        editor.commit();
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        Toast.makeText(getApplicationContext(), "Die Vokabelliste 'Schwer' wurde gelöscht!", Toast.LENGTH_SHORT).show();
        testTextView.setText(".." + easyVocabList+hardVocabList+noRepeat);
    }
    public void onResetNoRepeat (View view) {
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion
        noRepeat.clear();
        if (radioButtonJap.isChecked()){
            editorJap.putString("NO_REPEAT_JAP", "0");
        }
        else if (radioButtonEng.isChecked()){
            editorEng.putString("NO_REPEAT_ENG", "0");
        }
        else if (radioButtonFra.isChecked()){
            editorFra.putString("NO_REPEAT_FRA", "0");
        }
        else if (radioButtonIta.isChecked()){
            editorIta.putString("NO_REPEAT_ITA", "0");
        }
        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        Toast.makeText(getApplicationContext(), "Die Liste 'noRepeat' wurde gelöscht!", Toast.LENGTH_SHORT).show();
        testTextView.setText(".." + easyVocabList + hardVocabList + noRepeat);
    }
    //endregion
    //region Settings
    public void showTestClick(View view) {
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (checkBoxShowTest.isChecked()) {
            testTextView.setVisibility(View.VISIBLE);
            editor.putBoolean("SHOW_TEST",true);
        }
        else if (!checkBoxShowTest.isChecked()){
            testTextView.setVisibility(View.INVISIBLE);
            editor.putBoolean("SHOW_TEST",false);
        }
        editor.commit();
    }
    public void resetAllLists(View view) {
        //region USER PREFERENCES DEFINE
        SharedPreferences prefsJap = getSharedPreferences("USER_PREFERENCES_JAP", MODE_PRIVATE);
        SharedPreferences.Editor editorJap = prefsJap.edit();
        SharedPreferences prefsEng = getSharedPreferences("USER_PREFERENCES_ENG", MODE_PRIVATE);
        SharedPreferences.Editor editorEng = prefsEng.edit();
        SharedPreferences prefsFra = getSharedPreferences("USER_PREFERENCES_FRA", MODE_PRIVATE);
        SharedPreferences.Editor editorFra = prefsFra.edit();
        SharedPreferences prefsIta = getSharedPreferences("USER_PREFERENCES_ITA", MODE_PRIVATE);
        SharedPreferences.Editor editorIta = prefsIta.edit();
        SharedPreferences prefs = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //endregion

        editorJap.putString("NO_REPEAT_JAP", "0");
        editorJap.putString("EASY_VOCAB_LIST_JAP", "0");
        editorJap.putString("HARD_VOCAB_LIST_JAP", "0");
        editorEng.putString("NO_REPEAT_ENG", "0");
        editorEng.putString("EASY_VOCAB_LIST_ENG", "0");
        editorEng.putString("HARD_VOCAB_LIST_ENG", "0");
        editorFra.putString("NO_REPEAT_FRA", "0");
        editorFra.putString("EASY_VOCAB_LIST_FRA", "0");
        editorFra.putString("HARD_VOCAB_LIST_FRA", "0");
        editorIta.putString("NO_REPEAT_ITA", "0");
        editorIta.putString("EASY_VOCAB_LIST_ITA", "0");
        editorIta.putString("HARD_VOCAB_LIST_ITA", "0");

        editorJap.commit();
        editorEng.commit();
        editorFra.commit();
        editorIta.commit();
        editor.commit();
    }


    //endregion
    //region Search
    public void chooseKeyboardGer(View view) {
        keyboardGer = true;
        keyboardJap = false;
        radioButtonKeyboardJap.setChecked(false);
    }
    public void chooseKeyboardJap(View view) {
        keyboardGer = false;
        keyboardJap = true;
        radioButtonKeyboardGer.setChecked(false);
    }

    public void onButtonSearchClick(View view) {
        searchResult.setSingleLine(false);
        foundWords.clear();
        //region hide keyboard
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        //endregion
        String searchedWord = editSearch.getText().toString().toLowerCase();
        for (int i = 0; i < vocabNumber; i++) {
            String takenWord = vocabList.get(i).toLowerCase();
            if (takenWord.contains(searchedWord)) {
                    foundWords.add(vocabList.get(i));
            }
        }

        int foundWordsAmount = foundWords.size();
        if (foundWordsAmount == 0){
            foundWords.add("Keine Treffer!");
        }
        for (int i = 0; i<40; i++){
            foundWords.add(" ");
        }
        searchResult.setText("Gesucht: " + "'" +searchedWord + "'"+ "\n" +
                foundWords.get(0)+"\n"+foundWords.get(1)+"\n"+foundWords.get(2)+"\n"+foundWords.get(3)+"\n"+
                foundWords.get(4)+"\n"+foundWords.get(5)+"\n"+foundWords.get(6)+"\n"+foundWords.get(7)+"\n"+
                foundWords.get(8)+"\n"+foundWords.get(9)+"\n"+foundWords.get(10)+"\n"+foundWords.get(11)+"\n"+
                foundWords.get(12)+"\n"+foundWords.get(13)+"\n"+foundWords.get(14)+"\n"+foundWords.get(15)+"\n"+
                foundWords.get(16)+"\n"+foundWords.get(17)+"\n"+foundWords.get(18)+"\n"+foundWords.get(19)+
                foundWords.get(20)+"\n"+foundWords.get(21)+"\n"+foundWords.get(22)+"\n"+foundWords.get(23)+"\n"+
                foundWords.get(24)+"\n"+foundWords.get(25)+"\n"+foundWords.get(26)+"\n"+foundWords.get(27)+"\n"+
                foundWords.get(28)+"\n"+foundWords.get(29)+"\n"+foundWords.get(30)+"\n"+foundWords.get(31)+"\n"+
                foundWords.get(32)+"\n"+foundWords.get(33)+"\n"+foundWords.get(34)+"\n"+foundWords.get(35)+"\n"+
                foundWords.get(36)+"\n"+foundWords.get(37)+"\n"+foundWords.get(38)+"\n"+foundWords.get(39));
        editSearch.setText("");
    }
    //endregion
    //region NumberTrainer
    public void continueNumber(View view) {
        buttonBackToLang.setVisibility(View.INVISIBLE);
        radioButtonNumber1.setVisibility(View.INVISIBLE);
        radioButtonNumber2.setVisibility(View.INVISIBLE);
        radioButtonNumber3.setVisibility(View.INVISIBLE);
        radioButtonNumber4.setVisibility(View.INVISIBLE);
        buttonContinueNumber.setVisibility(View.INVISIBLE);
        vocabField.setVisibility(View.VISIBLE);
        buttonHelp.setVisibility(View.INVISIBLE);
        buttonNextVocab.setVisibility(View.VISIBLE);
        buttonExerciseBack.setVisibility(View.VISIBLE);
        textViewHeadline.setText("Zahlen üben");
    }


    //endregion

}
