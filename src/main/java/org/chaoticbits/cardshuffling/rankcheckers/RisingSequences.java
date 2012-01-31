package org.chaoticbits.cardshuffling.rankcheckers;

import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.PlayingCard.NullPlayingCard;
import org.chaoticbits.cardshuffling.entry.DataEntryException;

public class RisingSequences implements IRankChecker {

	public Double compareRanks(List<PlayingCard> before, List<PlayingCard> after) {
		return Math.abs(numRising(before) - numRising(after));
	}

	public double numRising(List<PlayingCard> deck) {
		double numRising = 1.0;
		for (int i = 1; i < 52; i++) { // All possible 52 cards
			PlayingCard cardI = fromRank(i);
			PlayingCard cardI1 = fromRank(i + 1);
			if (deck.contains(cardI) && deck.contains(cardI1) && deck.indexOf(cardI) > deck.indexOf(cardI1)) {
				numRising++; // found a pair that is decreasing, so that starts a new one
			}
		}
		return numRising;
	}

	private PlayingCard fromRank(int i) {
		try {
			return PlayingCard.fromRank(i);
		} catch (DataEntryException e) {
			e.printStackTrace();
			return new NullPlayingCard();
		}
	}

	public String name() {
		return "# of Rising Sequences";
	}

}
