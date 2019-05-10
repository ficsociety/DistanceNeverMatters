package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.adapters.DiceGridViewAdapter;
import apm.muei.distancenevermatters.entities.Dice;
import apm.muei.distancenevermatters.entities.DiceContainer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiceFragment extends Fragment {


    private int itemSelected = -1;

    @BindView(R.id.diceGridView)
    GridView gridView;

    @BindView(R.id.diceInputNumber)
    EditText inputNumber;

    @BindView(R.id.diceBtnMore)
    Button btnMore;

    @BindView(R.id.diceBtnLess)
    Button btnLess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dice, container, false);
        ButterKnife.bind(this, rootView);

        List<Dice> diceList = DiceContainer.getDiceList();
        System.out.println(diceList.size());

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
}
