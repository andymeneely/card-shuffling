package org.chaoticbits.cardshuffling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.rankcheckers.IRankChecker;

/**
 * Performs the given shuffles on a deck and
 * @author andy
 * 
 */
public class ShuffleSimulation {
	private final List<IRankChecker> rankCheckers;
	private final int numTrials;
	private final int maxSubtrials;
	private final IShuffle shuffle;
	private final File outputFile;
	private final Random rnd;


	/**
	 * Given one shuffle algorithm, perform a shuffle for
	 * @param shuffle
	 * @param rankCheckers
	 * @param numTrials
	 * @param maxSubtrials
	 * @param output
	 * @param rnd
	 */
	public ShuffleSimulation(IShuffle shuffle, List<IRankChecker> rankCheckers, int numTrials, int maxSubtrials, File output, Random rnd) {
		this.shuffle = shuffle;
		this.rankCheckers = rankCheckers;
		this.numTrials = numTrials;
		this.maxSubtrials = maxSubtrials;
		this.outputFile = output;
		this.rnd = rnd;
	}

	/**
	 * @see ShuffleSimulation#ShuffleSimulation(IShuffle, List, int, int, File, Random)
	 * 
	 * @param shuffle
	 * @param rankCheckers
	 * @param numTrials
	 * @param maxSubtrials
	 * @param output
	 */
	public ShuffleSimulation(IShuffle shuffle, List<IRankChecker> rankCheckers, int numTrials, int maxSubtrials, File output) {
		this(shuffle, rankCheckers, numTrials, maxSubtrials, output, new SecureRandom(new byte[] { 89, 12, 123, 40, 57 }));
	}

	public void run() throws IOException {
		FileWriter output = new FileWriter(outputFile);
		output.write("Shuffle\tTrial\tSubTrial\tRankChecker\tRankCheckerValue\n");
		for (int trial = 0; trial < numTrials; trial++) {
			for (int subTrial = 0; subTrial < maxSubtrials; subTrial++) {
				List<PlayingCard> beforeDeck = newRandomDeck();
				List<PlayingCard> afterDeck = new ArrayList<PlayingCard>(beforeDeck);
				afterDeck = shuffle.shuffle(afterDeck);
				for (IRankChecker checker : rankCheckers) {
					Double rankCheckerValue = checker.compareRanks(beforeDeck, afterDeck);
					output.write(shuffle.name() + "\t" + trial + "\t" + subTrial + "\t" + checker.name() + "\t" + rankCheckerValue + "\n");
				}
			}
		}
		output.close();
	}

	private List<PlayingCard> newRandomDeck() {
		List<PlayingCard> newDeck = PlayingCard.newDeck();
		Collections.shuffle(newDeck, rnd);
		return newDeck;
	}

}
