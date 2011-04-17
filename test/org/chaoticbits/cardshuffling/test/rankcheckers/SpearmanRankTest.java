package org.chaoticbits.cardshuffling.test.rankcheckers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.Suit;
import org.chaoticbits.cardshuffling.cards.Value;
import org.chaoticbits.cardshuffling.rankcheckers.IRankChecker;
import org.chaoticbits.cardshuffling.rankcheckers.SpearmanRankCompare;
import org.junit.Test;

public class SpearmanRankTest {

	private IRankChecker checker = new SpearmanRankCompare();

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

		assertEquals(1.0, checker.compareRanks(newDeck1, newDeck2), 0.00001);
	}

	@Test
	public void swapFirstTwoCards() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, newDeck1.get(1));
		newDeck2.set(1, newDeck1.get(0));
		assertEquals(0.9999146, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void swapThreeCards() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, newDeck1.get(1));
		newDeck2.set(1, newDeck1.get(2));
		newDeck2.set(2, newDeck1.get(0));
		assertEquals(0.9997439, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void swapFirstLast() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, new PlayingCard(Value.KING, Suit.SPADES));
		newDeck2.set(51, new PlayingCard(Value.ACE, Suit.CLUBS));
		assertEquals(0.777939, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void reverseAll() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		Collections.reverse(newDeck2);
		assertEquals(-1.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void fourSimpleCards() throws Exception {
		List<PlayingCard> newDeck1 = new ArrayList<PlayingCard>();
		newDeck1.add(new PlayingCard(Value.ACE, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.FOUR, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.SIX, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.THREE, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.TWO, Suit.CLUBS));
		newDeck1.add(new PlayingCard(Value.FIVE, Suit.CLUBS));
		List<PlayingCard> newDeck2 = new ArrayList<PlayingCard>();
		newDeck2.add(new PlayingCard(Value.FOUR, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.TWO, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.ACE, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.SIX, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.THREE, Suit.CLUBS));
		newDeck2.add(new PlayingCard(Value.FIVE, Suit.CLUBS));
		assertEquals(-0.3714286, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void longerExample() throws Exception {
		// Adapted from the Wikipedia example:
		// http://en.wikipedia.org/wiki/Spearman%27s_rank_correlation_coefficient#Example
		List<PlayingCard> newDeck1 = new ArrayList<PlayingCard>();
		newDeck1.add(new PlayingCard(Value.SEVEN, Suit.CLUBS)); // 106
		newDeck1.add(new PlayingCard(Value.ACE, Suit.CLUBS)); // 86
		newDeck1.add(new PlayingCard(Value.FOUR, Suit.CLUBS)); // 100
		newDeck1.add(new PlayingCard(Value.FIVE, Suit.CLUBS)); // 101
		newDeck1.add(new PlayingCard(Value.THREE, Suit.CLUBS)); // 99
		newDeck1.add(new PlayingCard(Value.SIX, Suit.CLUBS)); // 103
		newDeck1.add(new PlayingCard(Value.TWO, Suit.CLUBS)); // 97
		newDeck1.add(new PlayingCard(Value.TEN, Suit.CLUBS)); // 113
		newDeck1.add(new PlayingCard(Value.NINE, Suit.CLUBS)); // 112
		newDeck1.add(new PlayingCard(Value.EIGHT, Suit.CLUBS)); // 110

		List<PlayingCard> newDeck2 = new ArrayList<PlayingCard>();
		newDeck2.add(new PlayingCard(Value.THREE, Suit.CLUBS)); // 7
		newDeck2.add(new PlayingCard(Value.ACE, Suit.CLUBS)); // 0
		newDeck2.add(new PlayingCard(Value.SEVEN, Suit.CLUBS)); // 27
		newDeck2.add(new PlayingCard(Value.TEN, Suit.CLUBS)); // 50
		newDeck2.add(new PlayingCard(Value.EIGHT, Suit.CLUBS)); // 28
		newDeck2.add(new PlayingCard(Value.NINE, Suit.CLUBS)); // 29
		newDeck2.add(new PlayingCard(Value.SIX, Suit.CLUBS)); // 20
		newDeck2.add(new PlayingCard(Value.FOUR, Suit.CLUBS)); // 12
		newDeck2.add(new PlayingCard(Value.TWO, Suit.CLUBS)); // 6
		newDeck2.add(new PlayingCard(Value.FIVE, Suit.CLUBS)); // 17
		
		assertEquals(-0.1757575, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}
}
