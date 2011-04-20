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

	@Override
	public List<PlayingCard> shuffle(List<PlayingCard> deck) {
		Collections.shuffle(deck, random);
		return deck;
	}

	@Override
	public String name() {
		return "Random";
	}

}
