package cl.cutiko.estresless.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import cl.cutiko.estresless.R;
import cl.cutiko.estresless.data.Queries;
import cl.cutiko.estresless.models.Pending;

/**
 * Created by cutiko on 15-02-17.
 */

public class PendingsAdapter extends RecyclerView.Adapter<PendingsAdapter.ViewHolder> {

    private List<Pending> pendings = new Queries().notDone();
    private PendingClickListener listener;

    public PendingsAdapter(PendingClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pending, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Pending pending = pendings.get(position);
        holder.textView.setText(pending.getName());
        holder.checkBox.setChecked(pending.isDone());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    final int adapterPosition = holder.getAdapterPosition();
                    Pending auxPending = pendings.get(adapterPosition);
                    auxPending.setDone(true);
                    auxPending.save();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pendings.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition);
                        }
                    }, 200);
                }
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pending auxPending = pendings.get(holder.getAdapterPosition());
                //listener.clickedId(auxPending.getId());
                listener.clickedPending(auxPending);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pendings.size();
    }

    public void addPending(Pending pending) {
        pendings.add(pending);
        notifyDataSetChanged();
    }

    public void resetEverything() {
        List<Pending> done = new Queries().done();
        int listSize = pendings.size();

        for (int i = 0; i < done.size(); i++) {
            Pending pending = done.get(i);
            pending.setDone(false);
            pending.save();
            pendings.add(pending);
        }

        notifyItemRangeInserted(listSize, pendings.size());
    }

    public void updateByName(String name) {
        List<Pending> byName = new Queries().byName(name);
        pendings.clear();
        pendings.addAll(byName);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;
        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.pendingCb);
            textView = (TextView) itemView.findViewById(R.id.pendingTv);
        }
    }

}
