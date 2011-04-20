package org.chaoticbits.cardshuffling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.cardshuffling.entry.DataEntryException;
import org.chaoticbits.cardshuffling.entry.EntryCorrection;
import org.chaoticbits.cardshuffling.entry.ShuffleState;
import org.chaoticbits.cardshuffling.entry.TrialReader;
import org.chaoticbits.cardshuffling.rankcheckers.BridgeHandCompare;
import org.chaoticbits.cardshuffling.rankcheckers.BridgeHandValue;
import org.chaoticbits.cardshuffling.rankcheckers.DeckDifference;
import org.chaoticbits.cardshuffling.rankcheckers.SpearmanRankCompare;
import org.chaoticbits.cardshuffling.shuffles.EmpiricalShuffle;
import org.chaoticbits.cardshuffling.shuffles.RandomShuffle;

public class RunAnalysis {

	private static final byte[] seed = new byte[] { 89, 12, 123 };
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RunAnalysis.class);

	public static void main(String[] args) throws DataEntryException, IOException {
		PropertyConfigurator.configure("log4j.properties");
		log.info("Loading trial files...");
		List<ShuffleState> states = loadTrialFiles();
		log.info("Checking data entry...");
		checkShuffleStates(states);
		log.info("Deriving shuffle transformations...");
		List<EmpiricalShuffle> shuffles = deriveShuffles(states);
		log.info("Running Random Simulations...");
		randomSimulations(new SecureRandom(seed));
		log.info("Loaded and checked " + states.size() + " deck states");
		log.info("Derived " + shuffles.size() + " shuffles");
		log.info("Done.");
	}

	private static List<ShuffleState> loadTrialFiles() throws FileNotFoundException, DataEntryException {
		List<File> trialFiles = listTrialFiles(new File("data/"));
		List<ShuffleState> shuffles = new ArrayList<ShuffleState>();
		for (File trialFile : trialFiles) {
			log.info("\tLoading file " + trialFile);
			shuffles.addAll(new TrialReader().readDeck(trialFile));
		}
		return shuffles;
	}

	private static List<File> listTrialFiles(File dir) {
		String[] names = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("trial") && name.endsWith(".txt");
			}
		});
		List<File> trialFiles = new ArrayList<File>();
		for (String fileName : names) {
			trialFiles.add(new File(dir, fileName));
		}
		return trialFiles;
	}

	private static void checkShuffleStates(List<ShuffleState> states) throws DataEntryException {
		for (ShuffleState shuffleState : states) {
			log.info("\tChecking... " + shuffleState);
			new EntryCorrection().checkDeckEntry(shuffleState.getDeck());
		}
	}

	private static List<EmpiricalShuffle> deriveShuffles(List<ShuffleState> states) {
		List<EmpiricalShuffle> shuffles = new ArrayList<EmpiricalShuffle>();
		for (int i = 0; i < states.size() - 1; i++) {
			// if consecutive shuffle state
			if (states.get(i).getSequenceNumber() < states.get(i + 1).getSequenceNumber())
				shuffles.add(new EmpiricalShuffle(states.get(i).getDeck(), states.get(i + 1).getDeck()));
		}
		return shuffles;
	}

	private static void randomSimulations(Random random) throws IOException {
		new ShuffleSimulation(new RandomShuffle(random), Arrays.asList(new SpearmanRankCompare(),
				new DeckDifference(), new BridgeHandCompare(), new BridgeHandValue()), 10000, 1, new File(
				"output/randomShuffle.txt")).run();
	}
}
