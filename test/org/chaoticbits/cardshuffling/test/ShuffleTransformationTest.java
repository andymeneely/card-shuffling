package org.chaoticbits.cardshuffling.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.chaoticbits.cardshuffling.IShuffle;
import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.Suit;
import org.chaoticbits.cardshuffling.cards.Value;
import org.chaoticbits.cardshuffling.shuffles.EmpiricalShuffle;
import org.junit.Test;

public class ShuffleTransformationTest {

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
	public void doesNothing() throws Exception {
		List<PlayingCard> deck1 = newDeck();
		List<PlayingCard> deck2 = newDeck();
		IShuffle trans = new EmpiricalShuffle("", deck1, deck2);
		List<PlayingCard> expDeckInput = newDeck();
		List<PlayingCard> expDeckOutput = newDeck();
		Collections.reverse(expDeckInput);
		Collections.reverse(expDeckOutput);
		assertArrayEquals(expDeckOutput.toArray(), trans.shuffle(expDeckInput).toArray());
	}

	@Test
	public void swapFourCards() throws Exception {
		List<PlayingCard> deck1 = newDeck();
		List<PlayingCard> deck2 = newDeck();
		Collections.swap(deck2, 0, 51);
		Collections.swap(deck2, 1, 50);
		Collections.swap(deck2, 2, 49);
		Collections.swap(deck2, 3, 48);
		IShuffle trans = new EmpiricalShuffle("", deck1, deck2);
		List<PlayingCard> expDeckInput = newDeck();
		List<PlayingCard> expDeckOutput = newDeck();
		Collections.reverse(expDeckInput);
		Collections.reverse(expDeckOutput);
		Collections.swap(expDeckOutput, 0, 51);
		Collections.swap(expDeckOutput, 1, 50);
		Collections.swap(expDeckOutput, 2, 49);
		Collections.swap(expDeckOutput, 3, 48);
		assertArrayEquals(expDeckOutput.toArray(), trans.shuffle(expDeckInput).toArray());
	}
	
	@Test
	public void extractType() throws Exception {
		assertEquals("Rifle", "  \t Rifle 59".replaceAll("[0123456789]", "").trim());
	}

}
