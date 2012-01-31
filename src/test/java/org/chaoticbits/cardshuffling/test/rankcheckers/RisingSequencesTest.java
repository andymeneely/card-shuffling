package org.chaoticbits.cardshuffling.test.rankcheckers;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.Suit;
import org.chaoticbits.cardshuffling.cards.Value;
import org.chaoticbits.cardshuffling.rankcheckers.BridgeHandCompare;
import org.chaoticbits.cardshuffling.rankcheckers.IRankChecker;
import org.chaoticbits.cardshuffling.rankcheckers.RisingSequences;
import org.junit.Test;

public class RisingSequencesTest {

	private RisingSequences checker = new RisingSequences();

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
	public void basicRisingSequences() throws Exception {
		PlayingCard threeClubs = new PlayingCard(Value.THREE, Suit.CLUBS);
		PlayingCard fourClubs = new PlayingCard(Value.FOUR, Suit.CLUBS);
		PlayingCard fiveClubs = new PlayingCard(Value.FIVE, Suit.CLUBS);
		PlayingCard sixClubs = new PlayingCard(Value.SIX, Suit.CLUBS);
		PlayingCard sevenClubs = new PlayingCard(Value.SEVEN, Suit.CLUBS);
		assertEquals("one rising sequence", 1.0, checker.numRising(Arrays.asList(threeClubs, fourClubs, fiveClubs, sixClubs, sevenClubs)), .01);
		assertEquals("decreasing singletons", 5.0, checker.numRising(Arrays.asList(sevenClubs, sixClubs, fiveClubs, fourClubs, threeClubs)), .01);
		assertEquals("a little of both", 2.0, checker.numRising(Arrays.asList(threeClubs, sixClubs, fourClubs, sevenClubs, fiveClubs)), .01);
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
		assertEquals("added one rising sequence here", 1.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void swapThreeCards() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, newDeck1.get(1));
		newDeck2.set(1, newDeck1.get(2));
		newDeck2.set(2, newDeck1.get(0));
		assertEquals("swap 3 means one rising sequence", 1.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void reverseFirstThree() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, newDeck1.get(2));
		newDeck2.set(1, newDeck1.get(1));
		newDeck2.set(2, newDeck1.get(0));
		assertEquals("reverse first three means two more", 2.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void swapFirstLast() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		newDeck2.set(0, new PlayingCard(Value.KING, Suit.SPADES));
		newDeck2.set(51, new PlayingCard(Value.ACE, Suit.CLUBS));
		assertEquals("swap endpoints means two breaks", 2.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}

	@Test
	public void reverseAll() throws Exception {
		List<PlayingCard> newDeck1 = newDeck();
		List<PlayingCard> newDeck2 = newDeck();
		Collections.reverse(newDeck2);
		// All players get new hands
		assertEquals("all singletons now", 51.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
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
		// Rising sequences are: A 2 | 3 | 4 5 6 | 7 8 | 9 | 10 J Q

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
		// Hands are: A 2 | 3 4 5 | 6 | 7 8 9 | 10 J Q

		assertEquals("6 - 5", 1.0, checker.compareRanks(newDeck1, newDeck2), 0.000001);
	}
}
