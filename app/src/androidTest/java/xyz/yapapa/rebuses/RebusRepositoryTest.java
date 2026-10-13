package xyz.yapapa.rebuses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RebusRepositoryTest {

    private final Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void everyRebusHasAnImageAndAnAnswer() {
        List<Rebus> rebuses = RebusRepository.getAll(context);

        assertFalse(rebuses.isEmpty());
        for (Rebus rebus : rebuses) {
            assertNotEquals(0, rebus.imageRes());
            assertFalse(rebus.answer().isBlank());
        }
    }

    @Test
    public void rebusesFollowTheOrderOfTheResourceArrays() {
        String[] answers = context.getResources().getStringArray(R.array.rebus_answers);
        List<Rebus> rebuses = RebusRepository.getAll(context);

        assertEquals(answers.length, rebuses.size());
        for (int i = 0; i < answers.length; i++) {
            assertEquals(answers[i], rebuses.get(i).answer());
        }
    }

    @Test
    public void imagesAreDistinctDrawables() {
        Resources resources = context.getResources();
        List<Rebus> rebuses = RebusRepository.getAll(context);

        assertEquals(rebuses.size(),
                rebuses.stream().mapToInt(Rebus::imageRes).distinct().count());
        for (Rebus rebus : rebuses) {
            assertTrue(resources.getResourceTypeName(rebus.imageRes()).equals("drawable"));
        }
    }

    @Test
    public void getReturnsNullForOutOfRangeIndex() {
        assertNull(RebusRepository.get(context, -1));
        assertNull(RebusRepository.get(context, RebusRepository.getAll(context).size()));
    }
}
