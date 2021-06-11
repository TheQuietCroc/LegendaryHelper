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

import java.util.ArrayList;
import java.util.List;

public class GameComponentRecyclerAdapter<T extends BaseGameComponentEntity> extends RecyclerView.Adapter<GameComponentRecyclerAdapter.ViewHolder> {

    private final List<T> componentEntityList;
    private final List<T> componentsToDeleteList = new ArrayList<>();

    public List<T> getComponentEntityList() {
        return componentEntityList;
    }

    public List<T> getComponentsToDeleteList() {
        return componentsToDeleteList;
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

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        // Create a new view, which defines the UI of the list item
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_component_list_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.getGameComponentEnabledCheckbox().setOnCheckedChangeListener((buttonView,
                isChecked) -> componentEntityList.get(viewHolder.getAdapterPosition()).setEnabled(isChecked));

        viewHolder.getGameComponentDeleteButton().setOnClickListener(v -> {
            componentsToDeleteList.add(componentEntityList.remove(viewHolder.getAdapterPosition()));
            notifyItemRemoved(viewHolder.getAdapterPosition());
        });

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getGameComponentName().setText(componentEntityList.get(position).getName());
        viewHolder.getGameComponentEnabledCheckbox().setChecked(componentEntityList.get(position).isEnabled());
        viewHolder.getGameComponentDeleteButton().setEnabled(componentEntityList.get(position).isCustom());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return componentEntityList.size();
    }

}
