package org.chaoticbits.cardshuffling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.cards.Suit;
import org.chaoticbits.cardshuffling.cards.Value;
import org.chaoticbits.cardshuffling.rankcheckers.IRankChecker;

public class ShuffleSimulation {
	private final List<IRankChecker> rankCheckers;
	private final int numTrials;
	private final IShuffle shuffle;

	private SecureRandom rnd = new SecureRandom(new byte[] { 89, 12, 123, 40, 57 });
	private final File outputFile;
	private int maxSubtrials;

	public ShuffleSimulation(IShuffle shuffle, List<IRankChecker> rankCheckers, int numTrials,
			int maxSubtrials, File output) {
		this.shuffle = shuffle;
		this.rankCheckers = rankCheckers;
		this.numTrials = numTrials;
		this.maxSubtrials = maxSubtrials;
		this.outputFile = output;
	}

	public void run() throws IOException {
		FileWriter output = new FileWriter(outputFile);
		output.write("Shuffle\tTrial\tSubTrial\tRankChecker\tRankCheckerValue\n");
		for (int trial = 0; trial < numTrials; trial++) {
			for (int subTrial = 0; subTrial < maxSubtrials; subTrial++) {
				List<PlayingCard> beforeDeck = newRandomDeck();
				List<PlayingCard> afterDeck = new ArrayList<PlayingCard>(beforeDeck);
				shuffle.shuffle(afterDeck);
				for (IRankChecker checker : rankCheckers) {
					Double rankCheckerValue = checker.compareRanks(beforeDeck, afterDeck);
					output.write(shuffle.name() + "\t" + trial + "\t" + subTrial + "\t" + checker.name()
							+ "\t" + rankCheckerValue + "\n");
				}
			}
		}
		output.close();
	}

	private List<PlayingCard> newRandomDeck() {
		ArrayList<PlayingCard> newDeck = new ArrayList<PlayingCard>(52);
		for (Suit suit : Suit.values()) {
			for (Value value : Value.values()) {
				newDeck.add(new PlayingCard(value, suit));
			}
		}
		Collections.shuffle(newDeck, rnd);
		return newDeck;
	}

}
