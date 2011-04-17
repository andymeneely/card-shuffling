package org.chaoticbits.cardshuffling.test;

import static org.junit.Assert.*;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.Suit;
import org.chaoticbits.cardshuffling.cards.Value;
import org.junit.Test;

public class CardRankTest {

	
	@Test
	public void testRanks() throws Exception {
		assertEquals(1, new PlayingCard(Value.ACE, Suit.CLUBS).getRank());
		assertEquals(52, new PlayingCard(Value.KING, Suit.SPADES).getRank());
		assertEquals(14, new PlayingCard(Value.ACE, Suit.DIAMONDS).getRank());
	}
}
