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
        listViewItems.add(new ItemObjects(R.drawable.r01, "МЕ-Д-ВЕ-ДЬ"));
        listViewItems.add(new ItemObjects(R.drawable.r02, "ЛО-ША-ДЬ"));
        listViewItems.add(new ItemObjects(R.drawable.r03, "ТЕЛ-Е-ФОН"));
        listViewItems.add(new ItemObjects(R.drawable.r04, "СОРОК-А"));
        listViewItems.add(new ItemObjects(R.drawable.r05, "В-О-ДА"));
        listViewItems.add(new ItemObjects(R.drawable.r06, "И-СТО-РИЯ"));
        listViewItems.add(new ItemObjects(R.drawable.r07, "Б-УСЫ"));
        listViewItems.add(new ItemObjects(R.drawable.r08, "СТО-Л"));
        listViewItems.add(new ItemObjects(R.drawable.r09, "ШО-С-СЕ"));
        listViewItems.add(new ItemObjects(R.drawable.r10, "ИН-ТЕР-НЕТ"));
        listViewItems.add(new ItemObjects(R.drawable.r11, "КОМП-ЬЮ-ТЕР"));
        listViewItems.add(new ItemObjects(R.drawable.r12, "А-ИСТ"));
        listViewItems.add(new ItemObjects(R.drawable.r13, "БЛ-ОКНО-Т"));
        listViewItems.add(new ItemObjects(R.drawable.r14, "СК-РЕПКА"));
        listViewItems.add(new ItemObjects(R.drawable.r15, "В-О-ЛК"));
        listViewItems.add(new ItemObjects(R.drawable.r16, "ЛИ-МОН));));
        listViewItems.add(new ItemObjects(R.drawable.r17, "ШМ-ЕЛЬ));
        listViewItems.add(new ItemObjects(R.drawable.r18, "ВОЛ-ОСЫ));
        listViewItems.add(new ItemObjects(R.drawable.r19, "ВОЛН-А));
        listViewItems.add(new ItemObjects(R.drawable.r20, "ОСЕНЬ));
        listViewItems.add(new ItemObjects(R.drawable.r21, "И-СТО-РИ-Я));
        listViewItems.add(new ItemObjects(R.drawable.r22, "Ю-БОЧКА));
        listViewItems.add(new ItemObjects(R.drawable.r23, "САМОК-АТ));
        listViewItems.add(new ItemObjects(R.drawable.r24, "Г-РУША));
        listViewItems.add(new ItemObjects(R.drawable.r25, "В-ТОР-НИК));
        listViewItems.add(new ItemObjects(R.drawable.r26, "Ш-УТКА));
        listViewItems.add(new ItemObjects(R.drawable.r27, "ДО-РОГА));
        listViewItems.add(new ItemObjects(R.drawable.r28, "КУР-СОР));
        listViewItems.add(new ItemObjects(R.drawable.r29, "РЫБА-К"));
        listViewItems.add(new ItemObjects(R.drawable.r30, "В-Е-СНА"));
        listViewItems.add(new ItemObjects(R.drawable.r31, "СВИ-СТО-К"));
        listViewItems.add(new ItemObjects(R.drawable.r32, "СОЛН-ЦЕ"));
        listViewItems.add(new ItemObjects(R.drawable.r33, "С-ОВА"));
        listViewItems.add(new ItemObjects(R.drawable.r34, "НЕЗА-БУДКА"));
        listViewItems.add(new ItemObjects(R.drawable.r35, "БЕ-РЁЗА"));
        listViewItems.add(new ItemObjects(R.drawable.r36, "СА-ПО-ГИ"));
        listViewItems.add(new ItemObjects(R.drawable.r37, "В-О-РО-НА"));
        listViewItems.add(new ItemObjects(R.drawable.r38, "П-РО-Б-КА"));
        listViewItems.add(new ItemObjects(R.drawable.r39, "С-ОН"));
        listViewItems.add(new ItemObjects(R.drawable.r40, "ГОЛО-В-А"));
        listViewItems.add(new ItemObjects(R.drawable.r41, "В-О-Д-ИТ-ЕЛЬ"));
        listViewItems.add(new ItemObjects(R.drawable.r42, "Э-КРАН"));
        listViewItems.add(new ItemObjects(R.drawable.r43, "С-ТРИ-ЖИ"));
        listViewItems.add(new ItemObjects(R.drawable.r44, "ТРИ-О"));
        listViewItems.add(new ItemObjects(R.drawable.r45, "ТРИ-ТОН"));
        listViewItems.add(new ItemObjects(R.drawable.r46, "ТРИ-КОТА-Ж"));
        listViewItems.add(new ItemObjects(R.drawable.r47, "Т-РАМ-В-А-Й"));
        listViewItems.add(new ItemObjects(R.drawable.r48, "СТО-ЛБ"));
        listViewItems.add(new ItemObjects(R.drawable.r49, "ПО-ДВА-Л"));


        return listViewItems;
    }
}
