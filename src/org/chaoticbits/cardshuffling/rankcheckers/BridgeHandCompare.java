package org.chaoticbits.cardshuffling.rankcheckers;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public class BridgeHandCompare implements IRankChecker {

	/**
	 * Deals the hand out as you would in a Bridge game (one by one to each hand in succession). Compares
	 * each player's hands, where order doesn't matter within each player's hand. The number of cards
	 * different in total is the total score. Ranges from 0 to 52.
	 * 
	 * @param List
	 *            <PlayingCard> before - the ordered deck before shuffling
	 * @param List
	 *            <PlayingCard> after - the ordered deck after shuffling
	 */
	@Override
	public Double compareRanks(List<PlayingCard> before, List<PlayingCard> after) {
		if (before.size() != after.size() && before.size() % 4 != 0)
			throw new IllegalArgumentException("Decks are not equals and divisible by 4.");
		Double numDiffs = 0.0;// assume they're the same initially

		@SuppressWarnings("unchecked")
		Set<PlayingCard>[] beforeHands = (Set<PlayingCard>[]) Array.newInstance(Set.class, 4);
		@SuppressWarnings("unchecked")
		Set<PlayingCard>[] afterHands = (Set<PlayingCard>[]) Array.newInstance(Set.class, 4);
		for (int player = 0; player < 4; player++) {
			beforeHands[player] = new HashSet<PlayingCard>();
			afterHands[player] = new HashSet<PlayingCard>();
		}
		// Deal it out
		for (int i = 0; i < before.size(); i++) {
			beforeHands[i % 4].add(before.get(i));
			afterHands[i % 4].add(after.get(i));
		}
		for (int player = 0; player < 4; player++) {
			for (PlayingCard beforeCard : beforeHands[player]) {
				if (!afterHands[player].contains(beforeCard))
					numDiffs++;
			}
		}
		return numDiffs;

	}

}
