package org.chaoticbits.cardshuffling.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.Suit;
import org.chaoticbits.cardshuffling.cards.Value;
import org.chaoticbits.cardshuffling.entry.DataEntryException;
import org.junit.Test;

public class EnterPlayingCardData {
	@Test
	public void testGood() throws Exception {
		assertEquals(new PlayingCard(Value.ACE, Suit.SPADES), PlayingCard.fromText("as"));
		assertEquals(new PlayingCard(Value.ACE, Suit.SPADES), PlayingCard.fromText("As"));
		assertEquals(new PlayingCard(Value.ACE, Suit.SPADES), PlayingCard.fromText("AS"));
		assertEquals(new PlayingCard(Value.ACE, Suit.SPADES), PlayingCard.fromText("As\t \n\r\n"));
	}

	@Test
	public void testNotGoodSuit() throws Exception {
		try {
			PlayingCard.fromText("aq");
			fail("Exception should have been thrown");
		} catch (DataEntryException e) {
			assertEquals("No suit found for q", e.getMessage());
		}
	}

	@Test
	public void testNotGoodValue() throws Exception {
		try {
			PlayingCard.fromText("bs");
			fail("Exception should have been thrown");
		} catch (DataEntryException e) {
			assertEquals("No card value found for b", e.getMessage());
		}
	}
}
