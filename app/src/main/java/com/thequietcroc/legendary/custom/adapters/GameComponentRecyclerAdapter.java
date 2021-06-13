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
import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;

import java.util.List;
import java.util.function.Consumer;

public class GameComponentRecyclerAdapter<T extends BaseGameComponentEntity>
        extends RecyclerView.Adapter<GameComponentRecyclerAdapter.ViewHolder> {

    private final List<T> componentEntityList;
    private Consumer<T> dbInsertConsumer;
    private Consumer<T> dbDeleteConsumer;

    public List<T> getComponentEntityList() {
        return componentEntityList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView gameComponentName;
        private final CheckBox gameComponentEnabledCheckbox;
        private final Button gameComponentDeleteButton;

        public ViewHolder(final View view) {
            super(view);

            gameComponentName = (TextView) view.findViewById(R.id.gameComponentName);
            gameComponentEnabledCheckbox = (CheckBox) view.findViewById(R.id.gameComponentEnabledCheckbox);
            gameComponentDeleteButton = (Button) view.findViewById(R.id.gameComponentDeleteButton);
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

    public GameComponentRecyclerAdapter(final List<T> componentEntityList) {
        this.componentEntityList = componentEntityList;
    }

    public void setDbInsertConsumer(final Consumer<T> dbInsertConsumer) {
        this.dbInsertConsumer = dbInsertConsumer;
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

        viewHolder.getGameComponentEnabledCheckbox().setOnCheckedChangeListener((buttonView,
                isChecked) -> {
            final T entity = componentEntityList.get(viewHolder.getAdapterPosition());

            entity.setEnabled(isChecked);
            dbInsertConsumer.accept(entity);
        });

        viewHolder.getGameComponentDeleteButton().setOnClickListener(v -> {
            final T entity = componentEntityList.get(viewHolder.getAdapterPosition());

            dbDeleteConsumer.accept(entity);
            notifyItemRemoved(viewHolder.getAdapterPosition());
        });

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final T entity = componentEntityList.get(position);

        viewHolder.getGameComponentName().setText(entity.getName());
        viewHolder.getGameComponentEnabledCheckbox().setChecked(entity.isEnabled());
        viewHolder.getGameComponentDeleteButton().setEnabled(entity.isCustom());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return componentEntityList.size();
    }

}
