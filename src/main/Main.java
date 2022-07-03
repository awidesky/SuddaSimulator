package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class Main {

	
	public static final long GAMEPERLOOP = 40000L; 
	public static final int NUMOFLOOP = 100; 
	public static final boolean verbose = false;
	public static final int PLAYER = 5; 
	
	private static final Card[] deck = new Card[] { new Card(1, 0), new Card(1, 1), new Card(2, 0), new Card(2, 1), new Card(3, 0), new Card(3, 1), new Card(4, 0), new Card(4, 1), new Card(5, 0), new Card(5, 1), new Card(6, 0), new Card(6, 1), new Card(7, 0), new Card(7, 1), new Card(8, 0), new Card(8, 1), new Card(9, 0), new Card(9, 1), new Card(10, 0), new Card(10, 1)};
	
	
	public static LoggerThread logger = null;
	
	public static void main(String[] args) {

		if (verbose) {
			try {
				new File(".\\logs").mkdir();
				FileOutputStream fo = new FileOutputStream(new File(".\\logs\\" + new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss").format(new Date()) + ".txt"));
				logger = new LoggerThread(fo);
				logger.start();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		long total = 0L;
		long timeSum = 0L;
		for (int i = 0; i < NUMOFLOOP; i++) {
			long t = System.currentTimeMillis();

			long win = Stream.generate(Main::makeGame).limit(GAMEPERLOOP).parallel().map(Game::play).filter(Boolean::booleanValue)
					.count();

			t = System.currentTimeMillis() - t;

			total += win;
			timeSum += t;
			String result = String.format("#%0" + String.valueOf(NUMOFLOOP).length() + "d %" + String.valueOf(GAMEPERLOOP).length() + "d",i + 1 , win) + " / " + GAMEPERLOOP + "  " + String.format("(%6.3f)%%  in %5dms", 100.0 * win / GAMEPERLOOP, t);
			System.out.println(result);
			if (verbose) logger.log(result);
		}
		
		System.out.println();
		System.out.println(100.0 * total / (NUMOFLOOP * GAMEPERLOOP) + "% avg when " + PLAYER + " players.");
		System.out.println("avg " + (timeSum / NUMOFLOOP) + "ms");
		
		if (verbose) logger.kill(1000);
		
	}
	
	
	public static Game makeGame() {
		return new VanillaGame(makePlayer());
	}
	
	public static Player[] makePlayer() {
		return makePlayer(PLAYER);
	}
	public static Player[] makePlayer(int playerNum) {
		Player[] pairs = new Player[playerNum];
		for (int i = 0; i < playerNum; i++) {
			int[] arr = new Random().ints(0, 20).distinct().limit(2).toArray();
			pairs[i] = new Player(deck[arr[0]], deck[arr[1]]);
		}
		return pairs;
	}

}

class Card {
	
	public final int num;
	public final int type;
	
	public Card(int n, int t) { num = n; type = t; }
	public String getGenealogy() {
		return "";
	}
	
	@Override
	public int hashCode() {
		return num * 10 + type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Card)) {
			return false;
		}
		return obj.hashCode() == hashCode();
	}
}

class Player {
	public Card a;
	public Card b;
	private String myHand = null;
	
	public Player(Card a, Card b) {
		this.a = a;
		this.b = b;
	}

	public void setHand(String hand) {
		myHand = hand;
	}
	public String getHand() {
		return myHand;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(a, b, myHand);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Player) {
			Player other = (Player) obj;
			return Objects.equals(a, other.a) && Objects.equals(b, other.b) && Objects.equals(myHand, other.myHand);
		} else if (obj instanceof String) {
			return obj.equals(myHand);
		} else {
			return false;
		}
	}
	
	
	
}

