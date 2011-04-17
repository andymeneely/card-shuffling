package org.chaoticbits.cardshuffling.test.rankcheckers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.Suit;
import org.chaoticbits.cardshuffling.cards.Value;
import org.chaoticbits.cardshuffling.rankcheckers.BridgeHandCompare;
import org.chaoticbits.cardshuffling.rankcheckers.IRankChecker;
import org.junit.Test;

public class BridgeHandTest {

	private IRankChecker checker = new BridgeHandCompare();

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
		assertEquals(0.0, checker.compareRanks(newDeck1, newDeck2), 0.00001);
	}

	@Test
	public void swapFirstTwoCards() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, newDeck1.get(1));
		newDeck2.set(1, newDeck1.get(0));
		assertEquals(2.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void swapThreeCards() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, newDeck1.get(1));
		newDeck2.set(1, newDeck1.get(2));
		newDeck2.set(2, newDeck1.get(0));
		assertEquals(3.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void swapFirstLast() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, new PlayingCard(Value.KING, Suit.SPADES));
		newDeck2.set(51, new PlayingCard(Value.ACE, Suit.CLUBS));
		assertEquals(2.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void reverseAll() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		Collections.reverse(newDeck2);
		// All players get new hands
		assertEquals(52.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void longerExample() throws Exception {
		List<PlayingCard> newDeck1 = new ArrayList<PlayingCard>();
		newDeck1.add(new PlayingCard(Value.SEVEN, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.ACE, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.FOUR, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.FIVE, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.THREE, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.SIX, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.TWO, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.TEN, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.NINE, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.EIGHT, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.JACK, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.QUEEN, Suit.CLUBS));
		// Hands are: (7,3,9) (A,6,8) (4,2,J) (5,10,Q)

		List<PlayingCard> newDeck2 = new ArrayList<PlayingCard>();
		newDeck2.add(new PlayingCard(Value.THREE, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.ACE, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.SEVEN, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.TEN, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.EIGHT, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.NINE, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.SIX, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.FOUR, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.TWO, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.FIVE, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.JACK, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.QUEEN, Suit.CLUBS));
		// Hands are: (3,8,2) (A,9,5) (7,6,J) (10,4,Q)

		assertEquals(2 + 2 + 2 + 1, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}
}
