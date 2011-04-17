package org.chaoticbits.cardshuffling.rankcheckers;

import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public class DeckDifference implements IRankChecker {

	@Override
	public Double compareRanks(List<PlayingCard> before, List<PlayingCard> after) {
		if (before.size() != 52 && after.size() != 52)
			throw new IllegalArgumentException("Decks are not complete.");
		Double difference = 0.0; // assume they're the same
		for (int i = 0; i < 52; i++) {
			difference += (before.get(i).equals(after.get(i)) ? 0.0 : 1.0);
		}
		return difference;
	}
}
