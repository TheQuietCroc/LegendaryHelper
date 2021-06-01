package com.thequietcroc.legendary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.database.entities.gamecomponents.cards.BaseCardEntity;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final private Context context;
    final private List<? extends BaseCardEntity> cardList;

    public CardsAdapter(final Context context, final List<? extends BaseCardEntity> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return getViewHolder(parent, inflater);
    }

    private RecyclerView.ViewHolder getViewHolder(final ViewGroup parent, final LayoutInflater inflater) {
        final View view = inflater.inflate(R.layout.card, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final CardViewHolder cardVH = (CardViewHolder) holder;

        cardVH.cardName.setText(cardList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView cardName;

        public CardViewHolder(final View view) {
            super(view);

            cardName = view.findViewById(R.id.cardName);
        }
    }
}
