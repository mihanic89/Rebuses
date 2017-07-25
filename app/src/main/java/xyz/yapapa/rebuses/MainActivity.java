package xyz.yapapa.rebuses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {




    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<ItemObjects> gaggeredList = getListItemData();

        RebusRecyclerViewAdapter rcAdapter = new RebusRecyclerViewAdapter(MainActivity.this, gaggeredList);
        recyclerView.setAdapter(rcAdapter);
    }

    private List<ItemObjects> getListItemData(){
        List<ItemObjects> listViewItems = new ArrayList<ItemObjects>();
        listViewItems.add(new ItemObjects(R.drawable.r01, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r02, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r03, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r04, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r05, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r06, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r07, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r08, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r09, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r10, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r11, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r12, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r13, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r14, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r15, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r16, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r17, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r18, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r19, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r20, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r21, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r22, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r23, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r24, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r25, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r26, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r27, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r28, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r29, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r30, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r31, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r32, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r33, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r34, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r35, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r36, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r37, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r38, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r39, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r40, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r41, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r42, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r43, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r44, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r45, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r46, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r47, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r48, "ответ"));
        listViewItems.add(new ItemObjects(R.drawable.r49, "ответ"));


        return listViewItems;
    }
}
