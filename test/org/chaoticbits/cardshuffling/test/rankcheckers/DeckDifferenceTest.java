package org.chaoticbits.cardshuffling.test.rankcheckers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.Suit;
import org.chaoticbits.cardshuffling.cards.Value;
import org.chaoticbits.cardshuffling.rankcheckers.DeckDifference;
import org.chaoticbits.cardshuffling.rankcheckers.IRankChecker;
import org.junit.Test;

public class DeckDifferenceTest {

	private List<PlayingCard> newDeck() {
		ArrayList<PlayingCard> newDeck = new ArrayList<PlayingCard>(52);
		for (Suit suit : Suit.values()) {
			for (Value value : Value.values()) {
				newDeck.add(new PlayingCard(value, suit));
			}
		}
		return newDeck;
	}

	@Test
	public void noDifference() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		IRankChecker checker = new DeckDifference();
		assertEquals(0.0, checker.compareRanks(newDeck1, newDeck2), 0.00001);
	}
	
	@Test
	public void swapTwoCards() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, newDeck1.get(1));
		newDeck2.set(1, newDeck1.get(0));
		IRankChecker checker = new DeckDifference();
		assertEquals(2.0, checker.compareRanks(newDeck1, newDeck2), 0.00001);
	}
	@Test
	public void swapThreeCards() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, newDeck1.get(1));
		newDeck2.set(1, newDeck1.get(2));
		newDeck2.set(2, newDeck1.get(0));
		IRankChecker checker = new DeckDifference();
		assertEquals(3.0, checker.compareRanks(newDeck1, newDeck2), 0.00001);
	}
}
