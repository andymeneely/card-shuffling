package org.chaoticbits.cardshuffling.rankcheckers;

import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public interface IRankChecker {
	public Double compareRanks(List<PlayingCard> before, List<PlayingCard> after);
	
	public String name();
}
