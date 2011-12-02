package org.chaoticbits.cardshuffling.shuffles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.chaoticbits.cardshuffling.IShuffle;
import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.entry.ShuffleState;

/**
 * This class represents a shuffle we observed that was done by hand. It is derived by giving a before and
 * after {@link ShuffleState}, and diff'ing them. A real shuffle.
 * 
 * @author apmeneel
 * 
 */
public class EmpiricalShuffle implements IShuffle {
	private final int[] positionReloc;
	private final String shuffleName;
	private ShuffleType type;

	public EmpiricalShuffle(String shuffleName, List<PlayingCard> before, List<PlayingCard> after) {
		this.shuffleName = shuffleName;
		this.type = ShuffleType.fromString(shuffleName);
		positionReloc = new int[before.size()];
		for (int i = 0; i < before.size(); i++) {
			positionReloc[i] = after.indexOf(before.get(i));
		}
	}

	/**
	 * Given an ordered deck of cards, return a new list of those same cards, but in the observed shuffled order.
	 * @return List<PlayingCard>
	 */
	public List<PlayingCard> shuffle(List<PlayingCard> deck) {
		List<PlayingCard> newDeck = new ArrayList<PlayingCard>(deck.size());
		newDeck.addAll(deck);
		for (int i = 0; i < positionReloc.length / 2; i++) {
			Collections.swap(newDeck, i, positionReloc[i]);
		}
		return newDeck;
	}

	public String name() {
		return shuffleName + " (Empirical)";
	}

	public ShuffleType type() {
		return type;
	}

}
