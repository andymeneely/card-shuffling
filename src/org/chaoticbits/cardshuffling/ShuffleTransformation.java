package org.chaoticbits.cardshuffling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public class ShuffleTransformation {
	private final int[] positionReloc;

	public ShuffleTransformation(List<PlayingCard> deck1, List<PlayingCard> deck2) {
		positionReloc = new int[deck1.size()];
		for (int i = 0; i < deck1.size(); i++) {
			positionReloc[i] = deck2.indexOf(deck1.get(i));
		}
	}

	public List<PlayingCard> shuffle(List<PlayingCard> deck) {
		List<PlayingCard> newDeck = new ArrayList<PlayingCard>(deck.size());
		newDeck.addAll(deck);
		for (int i = 0; i < positionReloc.length / 2; i++) {
			Collections.swap(newDeck, i, positionReloc[i]);
		}
		return newDeck;
	}

}
