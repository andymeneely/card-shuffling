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
	private final int sequenceLength;
	private final IShuffle shuffle;
	private final File outputFile;
	private final Random rnd;

	/**
	 * Given one shuffle algorithm, perform a shuffle for the sequenceLength times sequentially. After each
	 * shuffle, compare the ranks to the original deck. Do all that numTrials of times.
	 * @param shuffle
	 * @param rankCheckers
	 * @param numTrials
	 * @param sequenceLength
	 * @param output
	 * @param rnd
	 */
	public ShuffleSimulation(IShuffle shuffle, List<IRankChecker> rankCheckers, int numTrials, int sequenceLength, File output, Random rnd) {
		this.shuffle = shuffle;
		this.rankCheckers = rankCheckers;
		this.numTrials = numTrials;
		this.sequenceLength = sequenceLength;
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
		output.write("Shuffle\tTrial\tSequenceIndex\tRankChecker\tRankCheckerValue\n");
		for (int trial = 0; trial < numTrials; trial++) {
			List<PlayingCard> beforeDeck = newRandomDeck(); // fresh deck
			for (int seq = 0; seq < sequenceLength; seq++) {
				List<PlayingCard> afterDeck = new ArrayList<PlayingCard>(beforeDeck); // copy the deck
				afterDeck = shuffle.shuffle(afterDeck); // shuffle it
				for (IRankChecker checker : rankCheckers) { // compare the ranks
					Double rankCheckerValue = checker.compareRanks(beforeDeck, afterDeck);
					output.write(shuffle.name() + "\t" + trial + "\t" + seq + "\t" + checker.name() + "\t" + rankCheckerValue + "\n");
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
