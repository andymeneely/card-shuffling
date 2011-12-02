package org.chaoticbits.cardshuffling.shuffles;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.chaoticbits.cardshuffling.IShuffle;
import org.chaoticbits.cardshuffling.cards.PlayingCard;

public class RandomShuffle implements IShuffle {

	private final Random random;

	public RandomShuffle(Random random) {
		this.random = random;
	}

	/**
	 * Given an ordered deck of cards, return a new list of those same cards, but in a uniformly-distributed
	 * shuffled order according to {@link Collections#shuffle(List)}
	 */
	public List<PlayingCard> shuffle(List<PlayingCard> deck) {
		Collections.shuffle(deck, random);
		return deck;
	}

	/**
	 * Always returns "Random"
	 */
	public String name() {
		return "Random";
	}

	public ShuffleType type() {
		return ShuffleType.RANDOM;
	}

}
