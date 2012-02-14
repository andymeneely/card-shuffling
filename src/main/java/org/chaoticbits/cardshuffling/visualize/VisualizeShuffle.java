package org.chaoticbits.cardshuffling.visualize;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.chaoticbits.cardshuffling.IShuffle;
import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.shuffles.EmpiricalShuffle;
import org.chaoticbits.cardshuffling.shuffles.ShuffleType;

/**
 * Given a set of shuffles, make a color visualization showing how one card moves to the next
 * @author andy
 * 
 */
public class VisualizeShuffle {

	public void run(List<IShuffle> shuffles) throws IOException {
		visualizeShuffles(ShuffleType.RIFLE, select(shuffles, ShuffleType.RIFLE), true);
		visualizeShuffles(ShuffleType.RIFLE, select(shuffles, ShuffleType.RIFLE), false);
		visualizeShuffles(ShuffleType.OVERHAND, select(shuffles, ShuffleType.OVERHAND), true);
		visualizeShuffles(ShuffleType.OVERHAND, select(shuffles, ShuffleType.OVERHAND), false);
	}

	private List<IShuffle> select(List<IShuffle> shuffles, ShuffleType type) {
		List<IShuffle> list = new ArrayList<IShuffle>(shuffles.size());
		for (IShuffle iShuffle : shuffles) {
			if (iShuffle.type() == type)
				list.add(iShuffle);
		}
		return list;
	}

	private void visualizeShuffles(ShuffleType type, List<IShuffle> shuffles, boolean singles) throws IOException {
		// add reference deck
		if (singles) {
			shuffles.add(0, new EmpiricalShuffle("Ordered", PlayingCard.newDeck(), PlayingCard.newDeck()));
		}
		Map<PlayingCard, Color> card2Color = initPlayingCardMap();
		BufferedImage bi = new BufferedImage(600, 900, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		int cardWidth = 10;
		int cardHeight = 800 / shuffles.size();
		List<PlayingCard> newDeck = PlayingCard.newDeck();
		for (int deckSlot = 0; deckSlot < shuffles.size(); deckSlot++) {
			if (singles)
				newDeck = PlayingCard.newDeck();
			newDeck = shuffles.get(deckSlot).shuffle(newDeck);
			outputDeckLine(card2Color, g2d, cardWidth, cardHeight, newDeck, deckSlot);
		}
		ImageIO.write(bi, "PNG", new File("output/visualize" + (singles ? "Single" : "Successive") + type + ".png"));
	}

	private void outputDeckLine(Map<PlayingCard, Color> card2Color, Graphics2D g2d, int cardWidth, int cardHeight, List<PlayingCard> newDeck,
			int deckSlot) {
		for (int cardSlot = 0; cardSlot < newDeck.size(); cardSlot++) {
			g2d.setColor(card2Color.get(newDeck.get(cardSlot)));
			g2d.fillRect(50 + cardSlot * cardWidth, 50 + deckSlot * cardHeight, cardWidth, cardHeight);
		}
	}

	private Map<PlayingCard, Color> initPlayingCardMap() {
		Map<PlayingCard, Color> map = new HashMap<PlayingCard, Color>();
		List<PlayingCard> deck = PlayingCard.newDeck();
		for (PlayingCard playingCard : deck) {
			float rank = (float) playingCard.getRank();
			map.put(playingCard, new Color(Color.HSBtoRGB(rank / 60.0f, 0.9f, 0.9f)));
		}
		return map;
	}

}
