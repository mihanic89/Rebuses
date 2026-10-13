package xyz.yapapa.rebuses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Shows one rebus at a time; swipe left/right to move between rebuses. */
public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_INDEX = "xyz.yapapa.rebuses.extra.INDEX";
    private static final String STATE_REVEALED = "revealed";

    private final Set<Integer> revealed = new HashSet<>();

    public static Intent newIntent(Context context, int index) {
        return new Intent(context, DetailActivity.class).putExtra(EXTRA_INDEX, index);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Rebus> rebuses = RebusRepository.getAll(this);
        int startIndex = getIntent().getIntExtra(EXTRA_INDEX, -1);
        if (startIndex < 0 || startIndex >= rebuses.size()) {
            finish();
            return;
        }

        setContentView(R.layout.detail_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            int[] saved = savedInstanceState.getIntArray(STATE_REVEALED);
            if (saved != null) {
                for (int index : saved) {
                    revealed.add(index);
                }
            }
        }

        ViewPager2 pager = findViewById(R.id.pager);
        pager.setAdapter(new RebusPagerAdapter(rebuses, revealed));
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setTitle(getString(R.string.rebus_counter, position + 1, rebuses.size()));
            }
        });
        // After recreation ViewPager2 restores the page the user was on by itself.
        if (savedInstanceState == null) {
            pager.setCurrentItem(startIndex, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_help) {
            startActivity(new Intent(this, HelpActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int[] saved = new int[revealed.size()];
        int i = 0;
        for (int index : revealed) {
            saved[i++] = index;
        }
        outState.putIntArray(STATE_REVEALED, saved);
    }
}
