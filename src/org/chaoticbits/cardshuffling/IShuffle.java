package org.chaoticbits.cardshuffling;

import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public interface IShuffle {

	public abstract List<PlayingCard> shuffle(List<PlayingCard> deck);

	public String name();
	
	public String type();
}