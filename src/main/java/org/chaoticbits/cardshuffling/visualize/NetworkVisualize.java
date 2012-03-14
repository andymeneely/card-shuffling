package org.chaoticbits.cardshuffling.visualize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.collections15.Transformer;
import org.chaoticbits.cardshuffling.IShuffle;
import org.chaoticbits.cardshuffling.cards.PlayingCard;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class NetworkVisualize {

	private static final Dimension size = new Dimension(2000, 4000);
	private static final Map<PlayingCard, Color> card2Color = initPlayingCardMap();

	public void run(List<IShuffle> shuffles) throws IOException {
		Graph<PlayingCardRowToken, Integer> graph = new SparseGraph<PlayingCardRowToken, Integer>();
		List<PlayingCard> deck = PlayingCard.newDeck();
		Layout<PlayingCardRowToken, Integer> layout = new StaticLayout<PlayingCardRowToken, Integer>(graph);
		layout.setSize(size);
		int edge = 0;
		int topMargin = 20;
		int spacing = (int) size.getWidth() / 54;
		double y = 20;
		int row = 0;
		double x = spacing;
		for (PlayingCard card : deck) {
			PlayingCardRowToken v = new PlayingCardRowToken(card, row);
			graph.addVertex(v);
			layout.setLocation(v, new Point2D.Double(x, y));
			x += spacing;
		}
		for (IShuffle shuffle : shuffles) {
			deck = shuffle.shuffle(deck);
			row++;
			y = row * 100 + topMargin;
			x = spacing;
			for (PlayingCard card : deck) {
				PlayingCardRowToken v = new PlayingCardRowToken(card, row);
				graph.addVertex(v);
				layout.setLocation(v, new Point2D.Double(x, y));
				x += spacing;
				graph.addEdge(edge++, new PlayingCardRowToken(card, row - 1), v);
			}
		}
		visualize(graph, layout);
	}

	private void visualize(Graph<PlayingCardRowToken, Integer> graph, Layout<PlayingCardRowToken, Integer> layout) throws IOException {
		VisualizationImageServer<PlayingCardRowToken, Integer> vis = new VisualizationImageServer<PlayingCardRowToken, Integer>(layout, size);
		vis.getRenderContext().setVertexFillPaintTransformer(new Transformer<PlayingCardRowToken, Paint>() {
			public Paint transform(PlayingCardRowToken token) {
				return card2Color.get(token.getCard());
			}
		});
		BufferedImage image = (BufferedImage) vis.getImage(new Point2D.Double(size.getWidth() / 2, size.getHeight() / 2), size);
		ImageIO.write(image, "PNG", new File("output/network.png"));
	}

	private static Map<PlayingCard, Color> initPlayingCardMap() {
		Map<PlayingCard, Color> map = new HashMap<PlayingCard, Color>();
		List<PlayingCard> deck = PlayingCard.newDeck();
		for (PlayingCard playingCard : deck) {
			float rank = (float) playingCard.getRank();
			map.put(playingCard, new Color(Color.HSBtoRGB(rank / 60.0f, 0.9f, 0.9f)));
		}
		return map;
	}

}
