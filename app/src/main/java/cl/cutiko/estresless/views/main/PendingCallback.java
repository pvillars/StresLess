package cl.cutiko.estresless.views.main;

import cl.cutiko.estresless.models.Pending;

/**
 * Created by cutiko on 15-02-17.
 */

public interface PendingCallback {

    void created(Pending pending);
    void noName();

}
