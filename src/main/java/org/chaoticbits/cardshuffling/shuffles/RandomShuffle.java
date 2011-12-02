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

	public List<PlayingCard> shuffle(List<PlayingCard> deck) {
		Collections.shuffle(deck, random);
		return deck;
	}

	public String name() {
		return "Random";
	}

	public ShuffleType type() {
		return ShuffleType.RANDOM;
	}

}
