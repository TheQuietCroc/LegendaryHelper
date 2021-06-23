package com.thequietcroc.legendary.custom.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;

import java.util.List;
import java.util.function.Consumer;

public class GameComponentRecyclerAdapter<T extends BaseGameComponent>
        extends RecyclerView.Adapter<GameComponentRecyclerAdapter.ViewHolder> {

    private final List<T> componentList;
    private Consumer<T> dbUpdateConsumer;
    private Consumer<T> dbDeleteConsumer;

    public List<T> getComponentList() {
        return componentList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView gameComponentName;
        private final CheckBox gameComponentEnabledCheckbox;
        private final Button gameComponentDeleteButton;

        public ViewHolder(final View view) {
            super(view);

            gameComponentName = view.findViewById(R.id.gameComponentName);
            gameComponentEnabledCheckbox = view.findViewById(R.id.gameComponentEnabledCheckbox);
            gameComponentDeleteButton = view.findViewById(R.id.gameComponentDeleteButton);
        }

        public TextView getGameComponentName() {
            return gameComponentName;
        }

        public CheckBox getGameComponentEnabledCheckbox() {
            return gameComponentEnabledCheckbox;
        }

        public Button getGameComponentDeleteButton() {
            return gameComponentDeleteButton;
        }
    }

    public GameComponentRecyclerAdapter(final List<T> componentList) {
        this.componentList = componentList;
    }

    public void setDbUpdateConsumer(final Consumer<T> dbUpdateConsumer) {
        this.dbUpdateConsumer = dbUpdateConsumer;
    }

    public void setDbDeleteConsumer(final Consumer<T> dbDeleteConsumer) {
        this.dbDeleteConsumer = dbDeleteConsumer;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        // Create a new view, which defines the UI of the list item
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_component_list_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.getGameComponentEnabledCheckbox().setOnClickListener(v -> {
            final T entity = componentList.get(viewHolder.getAdapterPosition());

            entity.setEnabled(((CheckBox)v).isChecked());
            dbUpdateConsumer.accept(entity);
        });

        viewHolder.getGameComponentDeleteButton().setOnClickListener(v -> {
            final T entity = componentList.get(viewHolder.getAdapterPosition());

            dbDeleteConsumer.accept(entity);
            notifyItemRemoved(viewHolder.getAdapterPosition());
        });

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final T entity = componentList.get(position);

        viewHolder.getGameComponentName().setText(entity.getName());
        viewHolder.getGameComponentEnabledCheckbox().setChecked(entity.isEnabled());
        viewHolder.getGameComponentDeleteButton().setEnabled(entity.isCustom());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return componentList.size();
    }

}
