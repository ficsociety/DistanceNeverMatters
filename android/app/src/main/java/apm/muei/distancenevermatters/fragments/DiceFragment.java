package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.activities.GameActivity;
import apm.muei.distancenevermatters.adapters.DiceGridViewAdapter;
import apm.muei.distancenevermatters.adapters.DiceResultRecyclerAdapter;
import apm.muei.distancenevermatters.entities.Dice;
import apm.muei.distancenevermatters.entities.DiceContainer;
import apm.muei.distancenevermatters.entities.dto.DiceResultDto;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiceFragment extends Fragment {


    private int itemSelected = -1;
    private int[] diceValues = new int[6];
    private List<Dice> diceList;

    @BindView(R.id.diceGridView)
    GridView gridView;

    @BindView(R.id.diceInputNumber)
    EditText inputNumber;

    @BindView(R.id.diceBtnMore)
    Button btnMore;

    @BindView(R.id.diceBtnLess)
    Button btnLess;

    @BindView(R.id.diceTextInput)
    TextView diceTextInput;

    @BindView(R.id.diceTVResult)
    TextView diceResult;

    @BindView(R.id.diceLayoutResult)
    ConstraintLayout resultLayout;

    @BindView(R.id.resultRecyclerView)
    RecyclerView resultRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dice, container, false);
        ButterKnife.bind(this, rootView);

        Toolbar toolbar = getActivity().findViewById(R.id.gameToolbar);
        AppBarLayout appBar = getActivity().findViewById(R.id.gameAppBar);
        appBar.setVisibility(View.VISIBLE);

        toolbar.setTitle("Tirada de dados");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Volviendo a Unity", Toast.LENGTH_SHORT).show();
                ((GameActivity) getActivity()).onBack();
            }
        });

        diceList = DiceContainer.getDiceList();
        diceTextInput.setText(R.string.ningundado);
        resultLayout.setVisibility(View.GONE);
        inputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    setDiceValue(getItemSelected(), Integer.parseInt(s.toString()));
                    updateSelectedDices();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        FloatingActionButton quitFab = getActivity().findViewById(R.id.quitFab);
        FloatingActionButton diceFab = getActivity().findViewById(R.id.gameFabBtnDice);
        diceFab.hide();
        quitFab.hide();
        final DiceGridViewAdapter adapter = new DiceGridViewAdapter(getActivity().getApplicationContext(), this, diceList);
        gridView.setAdapter(adapter);
        return rootView;
    }

    public int getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(int itemSelected) {
        this.itemSelected = itemSelected;
    }

    public EditText getInputNumber() {
        return inputNumber;
    }

    public Button getBtnMore() {
        return btnMore;
    }

    public Button getBtnLess() {
        return btnLess;
    }

    public int[] getDiceValues() {
        return diceValues;
    }

    public void setDiceValue(int pos, int value) {
        diceValues[pos] = value;
    }

    @OnClick(R.id.diceBtnLess)
    public void clickLess() {
        int value = Integer.parseInt(getInputNumber().getText().toString());
        if (value > 0 ) {
            value--;
            getInputNumber().setText(Integer.toString(value));
            setDiceValue(getItemSelected(), value);
        }
        updateSelectedDices();
    }

    @OnClick(R.id.diceBtnMore)
    public void clickMore() {
        int value = Integer.parseInt(getInputNumber().getText().toString());
        if (value < 20 ) {
            value++;
            getInputNumber().setText(Integer.toString(value));
            setDiceValue(getItemSelected(), value);
        }
        updateSelectedDices();
    }

    @OnClick(R.id.diceBtnRandom)
    public void random() {
        Random rand = new Random();
        List<DiceResultDto> resultList = new ArrayList<>();
        int finalValue = 0;
        for (int i = 0; i < diceValues.length; i++) {
            int count = diceValues[i];
            while (count != 0) {
                int value = rand.nextInt(diceList.get(i).getValue()) + 1;
                finalValue += value;
                DiceResultDto resultDto = new DiceResultDto(diceList.get(i).getName(), value);
                resultList.add(resultDto);
                count--;
            }
        }

        // TODO Mandar resultado al server de sincronización
        resultLayout.setVisibility(View.VISIBLE);
        final DiceResultRecyclerAdapter adapter = new DiceResultRecyclerAdapter(this, resultList, diceList);
        resultRecyclerView.setAdapter(adapter);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        diceResult.setText(Integer.toString(finalValue));
    }


    @OnClick(R.id.diceBtnClear)
    public void clear() {
        diceValues = new int[6];
        itemSelected = -1;
        updateSelectedDices();
        final DiceGridViewAdapter adapter = new DiceGridViewAdapter(getActivity().getApplicationContext(), this, diceList);
        gridView.setAdapter(adapter);
        resultLayout.setVisibility(View.GONE);
    }

    private void updateSelectedDices() {

        String text = "";
        for (int i = 0; i < diceValues.length; i++) {
            if (diceValues[i] > 0) {
                if (text == "") {
                    text = Integer.toString(diceValues[i]) + "(" + diceList.get(i).getName() + ")";
                } else {
                    text = text.concat(" + " + Integer.toString(diceValues[i]) + "(" + diceList.get(i).getName() + ")");
                }
            }
        }

        if (text == "") diceTextInput.setText(R.string.ningundado); else diceTextInput.setText(text);
    }
}
