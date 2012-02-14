package org.chaoticbits.cardshuffling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.cardshuffling.entry.DataEntryException;
import org.chaoticbits.cardshuffling.entry.EntryCorrection;
import org.chaoticbits.cardshuffling.entry.ShuffleState;
import org.chaoticbits.cardshuffling.entry.TrialReader;
import org.chaoticbits.cardshuffling.rankcheckers.BridgeHandCompare;
import org.chaoticbits.cardshuffling.rankcheckers.BridgeHandValue;
import org.chaoticbits.cardshuffling.rankcheckers.DeckDifference;
import org.chaoticbits.cardshuffling.rankcheckers.IRankChecker;
import org.chaoticbits.cardshuffling.rankcheckers.SpearmanRankCompare;
import org.chaoticbits.cardshuffling.shuffles.EmpiricalShuffle;
import org.chaoticbits.cardshuffling.shuffles.RandomAlgorithmShuffle;
import org.chaoticbits.cardshuffling.shuffles.RandomShuffle;
import org.chaoticbits.cardshuffling.shuffles.ShuffleType;
import org.chaoticbits.cardshuffling.visualize.VisualizeShuffle;

public class RunAnalysis {

	private static final Random rnd = new SecureRandom(new byte[] { 89, 12, 123 });
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RunAnalysis.class);
	private static final List<IRankChecker> RANK_CHECKERS = Arrays.asList(new SpearmanRankCompare(), new DeckDifference(),
			new BridgeHandCompare(), new BridgeHandValue());

	public static void main(String[] args) throws DataEntryException, IOException {
		PropertyConfigurator.configure("log4j.properties");
		log.info("Loading trial files...");
		List<ShuffleState> states = loadTrialFiles();
		log.info("Checking data entry...");
		checkShuffleStates(states);
		log.info("Deriving shuffle transformations...");
		List<IShuffle> shuffles = deriveShuffles(states);
		outputMetastats(states, shuffles);
		log.info("Outputting visualizations...");
		buildVisuals(shuffles);
		// log.info("Running Random Simulations...");
		// randomSimulations(rnd);
		log.info("Running Random Empirical Simulations...");
		randomEmpiricalSimulations(shuffles, rnd);
		log.info("Done.");
	}

	private static void outputMetastats(List<ShuffleState> states, List<IShuffle> shuffles) {
		log.info("Loaded and checked " + states.size() + " deck states");
		String derived = "Derived " + shuffles.size() + " shuffles; ";
		Map<ShuffleType, Integer> count = new HashMap<ShuffleType, Integer>();
		for (IShuffle shuffle : shuffles) {
			Integer thisCount = count.get(shuffle.type());
			if (thisCount == null)
				thisCount = 0;
			count.put(shuffle.type(), thisCount + 1);
		}
		Set<Entry<ShuffleType, Integer>> entrySet = count.entrySet();
		for (Entry<ShuffleType, Integer> entry : entrySet)
			derived += entry.getValue() + " " + entry.getKey() + ",";
		log.info(derived);
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

	private static List<IShuffle> deriveShuffles(List<ShuffleState> states) {
		List<IShuffle> shuffles = new ArrayList<IShuffle>();
		for (int i = 0; i < states.size() - 1; i++) {
			if (inSequence(states, i)) {
				String shuffleName = states.get(i + 1).getDescription();
				shuffles.add(new EmpiricalShuffle(shuffleName, states.get(i).getDeck(), states.get(i + 1).getDeck()));
			}
		}
		return shuffles;
	}

	private static boolean inSequence(List<ShuffleState> states, int i) {
		return states.get(i).getSequenceNumber() < states.get(i + 1).getSequenceNumber();
	}

	private static <T> void buildVisuals(List<IShuffle> shuffles) throws IOException {
		new VisualizeShuffle().run(shuffles);
	}

	private static void randomSimulations(Random random) throws IOException {
		new ShuffleSimulation(new RandomShuffle(random), RANK_CHECKERS, 1000, 10, new File("output/randomShuffle.txt")).run();
	}

	private static void randomEmpiricalSimulations(List<IShuffle> shuffles, Random random) throws IOException {
		RandomAlgorithmShuffle randomAlgorithmShuffle = new RandomAlgorithmShuffle(shuffles, random);
		new ShuffleSimulation(randomAlgorithmShuffle, RANK_CHECKERS, 1000, 10, new File("output/randomEmpiricalRifleShuffle.txt")).run();
	}
}
