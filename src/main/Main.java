package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	
	public static final long N = 40; 
	public static final boolean verbose = true;
	
	public static int player = 5; 
	private static final Card[] deck = new Card[] { new Card(1, 0), new Card(1, 1), new Card(2, 0), new Card(2, 1), new Card(3, 0), new Card(3, 1), new Card(4, 0), new Card(4, 1), new Card(5, 0), new Card(5, 1), new Card(6, 0), new Card(6, 1), new Card(7, 0), new Card(7, 1), new Card(8, 0), new Card(8, 1), new Card(9, 0), new Card(9, 1), new Card(10, 0), new Card(10, 1)};
	
	public static ConcurrentHashMap<String, Integer> cnt = null;
	
	public static LoggerThread logger = null;
	
	public static void main(String[] args) {

		try {
			new File(".\\logs").mkdir();
			logger = new LoggerThread(new FileOutputStream(new File(".\\logs\\" + new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss").format(new Date()) + ".txt")));
			logger.start();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(verbose) {
			cnt = new ConcurrentHashMap<>();
		}
		
		long t = System.currentTimeMillis();
		
		long win = Stream.generate(Main::makeGame).limit(N).parallel().map(Game::play).filter(Boolean::booleanValue).count();

		t = System.currentTimeMillis() - t;

		String result = win + " / " + N + "  (" + (100.0 * win/N) +"%)\nIn " + t + "ms";
		System.out.println(result);
		
		logger.log(result);
		logger.kill(1000);
		
	}
	
	
	public static Game makeGame() {
		return new VanillaGame(makePlayer());
	}
	
	public static Player[] makePlayer() {
		Player[] pairs = new Player[player];
		for (int i = 0; i < player; i++) {
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
	public String myHand = null;
	
	public Player(Card a, Card b) {
		this.a = a;
		this.b = b;
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


abstract class Game {
	
	protected static ArrayList<String> genealogy;
	
	public Player[] players;
	protected StringBuffer log = null;
	
	public Game(Player[] pairs) {
		players = pairs;
		if(Main.verbose) {
			log = new StringBuffer();
			log.append("\nNew Game :\n");
		}
	}
	
	public boolean play() {
		
		for(Player p : players) {
			if(p.a.num == p.b.num) {
				p.myHand = p.a.num + "땡";
			} else {
				p.myHand = ((p.a.num + p.b.num) % 10) + "끗";
			}
		}
		
		if(Main.verbose) {
			log.append(Arrays.stream(players).map(Player::getHand).collect(Collectors.joining(", ")));
			log.append("\n");
			boolean result = start();
			log.append("result : ");
			log.append(result ? "win" : "lose");
			//log.append("\n");
			Main.logger.log(log.toString());
			return result;
		} else {
			return start();
		}
			
	}
	
	public abstract boolean start();

}


class VanillaGame extends Game {
	static {
		genealogy = new ArrayList<>(Arrays.asList("0끗", "1끗", "2끗", "3끗", "4끗", "5끗", "6끗", "7끗", "8끗", "9끗", "1땡", "2땡", "3땡", "4땡", "5땡", "6땡", "7땡", "8땡", "9땡", "10땡"));
	}
	public VanillaGame(Player[] pairs) { super(pairs); }
	
	@Override
	public boolean start() {
		
		int myHandInt = -1;
		boolean sameHandExists = false;
		
		for(Player p : players) {
			int index = genealogy.indexOf(p.myHand);
			if(Main.verbose) {
				Main.cnt.putIfAbsent(p.myHand, 0);
			}
			if(myHandInt != -1) {
				if(myHandInt < index) {
					return false;
				}
				if(myHandInt == index) {
					sameHandExists = true;
				}
			} else {
				myHandInt = index;
			}
		}
		if(sameHandExists) {
			players = Main.makePlayer();
			if(Main.verbose) log.append("Draw. rematch.\n");
			return play();
		}
		
		return true;
	}
}

class MyGame extends Game {
	static {
		genealogy = new ArrayList<>(Arrays.asList("0끗", "1끗", "2끗", "3끗", "4끗", "5끗", "6끗", "7끗", "8끗", "9끝", "1땡", "2땡", "3땡", "4땡", "5땡", "6땡", "7땡", "8땡", "9땡", "10땡", "13광땡", "18광땡", "38광땡"));
	}
	public MyGame(Player[] pairs) { super(pairs); }
	
	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}
}

class PMangGame extends Game {
	static {
		genealogy = new ArrayList<>(Arrays.asList("0끗", "1끗", "2끗", "3끗", "4끗", "5끗", "6끗", "7끗", "8끗", "9끝", "세륙", "장사", "장삥", "구삥", "독사", "알리", "1땡", "2땡", "3땡", "4땡", "5땡", "6땡", "7땡", "8땡", "9땡", "10땡", "13광땡", "18광땡", "38광땡"));
	}
	public PMangGame(Player[] pairs) { super(pairs); }
	
	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}
}
