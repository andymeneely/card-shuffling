package org.chaoticbits.cardshuffling.test;

import static org.junit.Assert.assertEquals;

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
		assertEquals(16, new PlayingCard(Value.THREE, Suit.DIAMONDS).getRank());
	}

	@Test
	public void fromRank() throws Exception {
		assertEquals(new PlayingCard(Value.ACE, Suit.CLUBS), PlayingCard.fromRank(1));
		assertEquals(new PlayingCard(Value.TWO, Suit.CLUBS), PlayingCard.fromRank(2));
		assertEquals(new PlayingCard(Value.KING, Suit.CLUBS), PlayingCard.fromRank(13));
		assertEquals(new PlayingCard(Value.ACE, Suit.DIAMONDS), PlayingCard.fromRank(14));
		assertEquals(new PlayingCard(Value.THREE, Suit.DIAMONDS), PlayingCard.fromRank(16));
		assertEquals(new PlayingCard(Value.KING, Suit.SPADES), PlayingCard.fromRank(52));
	}
}
