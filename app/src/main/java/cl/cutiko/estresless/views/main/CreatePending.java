package cl.cutiko.estresless.views.main;

import cl.cutiko.estresless.models.Pending;

/**
 * Created by cutiko on 15-02-17.
 */

public class CreatePending {

    private PendingCallback callback;

    public CreatePending(PendingCallback callback) {
        this.callback = callback;
    }

    public void validation(String name) {
        if (name.trim().length() > 0) {
            Pending pending = new Pending();
            pending.setName(name);
            pending.setDone(false);
            pending.save();
            callback.created(pending);
        } else {
            callback.noName();
        }
    }
}
