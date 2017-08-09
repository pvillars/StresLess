package cl.cutiko.estresless.adapters;

import cl.cutiko.estresless.models.Pending;

/**
 * Created by cutiko on 20-02-17.
 */

public interface PendingClickListener {

    void clickedId(long id);
    void clickedPending(Pending pending);

}
