package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Game {
	
	public static ArrayList<String> genealogy;
	
	public Player[] players;
	protected StringBuffer log = null;
	protected boolean rematch = false;
	
	public Game(Player[] pairs) {
		players = pairs;
		if(Main.verbose) {
			log = new StringBuffer();
			log.append("\nNew Game :\n");
		}
	}
	
	
	protected void setup() {
		
		for(Player p : players) {
			if(p.getHand() != null) continue; // If hand is predefined in override method, skip it 
			
			if(p.a.num == p.b.num) {
				p.setHand(p.a.num + "땡");
			} else {
				p.setHand(((p.a.num + p.b.num) % 10) + "끗");
			}
		}
		
	}
	
	public abstract boolean play();

}


class VanillaGame extends Game {
	static {
		genealogy = new ArrayList<>(Arrays.asList("0끗", "1끗", "2끗", "3끗", "4끗", "5끗", "6끗", "7끗", "8끗", "9끗", "1땡", "2땡", "3땡", "4땡", "5땡", "6땡", "7땡", "8땡", "9땡", "10땡"));
	}
	public VanillaGame(Player[] pairs) { super(pairs); }
	
	@Override
	public boolean play() {
		
		setup();
		
		if(Main.verbose) {
			log.append(Arrays.stream(players).map(Player::getHand).collect(Collectors.joining(", ")));
			log.append("\n");
		}
		
		int replayNum = Main.PLAYER;
		boolean result = true;
		if(!rematch) { //predefined re-match hand exists
			result = showDown();
			if(rematch) replayNum = (int)Arrays.stream(players).filter((p) -> p.getHand().equals(players[0].getHand())).count();
		}
		
		if(result && rematch) {
			
			players = Main.makePlayer(replayNum);
			if(Main.verbose) {log.append("Draw. rematch.\n");}
			rematch = false;
			return play();
			
		}
		
		if (Main.verbose) {
			log.append("result : ");
			log.append(result ? "win" : "lose");
			Main.logger.log(log.toString());
		}
		
		return result;
	}
	
	/**
	 * Show down all players, and check who got highest hand
	 * */
	protected boolean showDown() {
		int myHandInt = -1;
		boolean result = true;
		
		for(Player p : players) {
			int index = genealogy.indexOf(p.getHand());
			if(myHandInt != -1) {
				if(myHandInt < index) {
					result = false;
					break;
				}
				if(myHandInt == index) {
					rematch = true;
				}
			} else {
				myHandInt = index;
			}
		}
		return result;
	}
}

class MyGame extends VanillaGame {
	static {
		genealogy = new ArrayList<>(Arrays.asList("0끗", "1끗", "2끗", "3끗", "4끗", "5끗", "6끗", "7끗", "8끗", "9끗", "1땡", "2땡", "3땡", "4땡", "5땡", "6땡", "7땡", "8땡", "9땡", "10땡", "13광땡", "18광땡", "38광땡"));
	}
	
	private Card[] gwag13 = new Card[]{ new Card(1, 1), new Card(3, 1)};
	private Card[] gwag18 = new Card[]{ new Card(1, 1), new Card(8, 0)};
	private Card[] gwag38 = new Card[]{ new Card(3, 1), new Card(8, 0)};
	
	public MyGame(Player[] pairs) { super(pairs); }
	
	@Override
	protected void setup() {
		super.setup();
		for(Player p : players) {
			if((p.equals(4, 9))) {
				p.setHand("사구");
				rematch = true;
			} else if(p.equals(gwag13)) {
				p.setHand("13광땡");
			} else if(p.equals(gwag18)) {
				p.setHand("18광땡");
			} else if(p.equals(gwag38)) {
				p.setHand("38광땡");
			}
		}
		for(Player p : players) {
			if(genealogy.indexOf(p.getHand()) >= genealogy.indexOf("10땡")) rematch = false;
		}
	}
	
}

class PMangGame extends MyGame {
	static {
		genealogy = new ArrayList<>(Arrays.asList("0끗", "1끗", "2끗", "3끗", "4끗", "5끗", "6끗", "7끗", "8끗", "9끗", "세륙", "장사", "장삥", "구삥", "독사", "알리", "1땡", "2땡", "3땡", "4땡", "5땡", "6땡", "7땡", "8땡", "9땡", "10땡", "13광땡", "18광땡", "38광땡"));
	}
	
	private Card[] mGosa = new Card[]{ new Card(4, 1), new Card(9, 1)}; //멍구사
	private Card[] tKiller = new Card[]{ new Card(3, 1), new Card(7, 1)}; // 땡잡이
	private Card[] secretAgent = new Card[]{ new Card(4, 1), new Card(7, 1)}; //암행어사
	
	public PMangGame(Player[] pairs) { super(pairs); }
	
	@Override
	protected void setup() {
		super.setup();
		for(Player p : players) {
			
		if(p.equals(mGosa)) {
			p.setHand("멍구사");
			rematch = true;
		} else if(p.equals(4, 6)) {
			p.setHand("세륙");
		} else if(p.equals(4, 10)) {
			p.setHand("장사");
		} else if(p.equals(1, 10)) {
			p.setHand("장삥");
		} else if(p.equals(1, 9)) {
			p.setHand("구삥");
		} else if(p.equals(1, 4)) {
			p.setHand("독사");
		} else if(p.equals(1, 2)) {
			p.setHand("알리");
		} else if(p.equals(tKiller)) {
			p.setHand("땡잡이");
			
		} else if(p.equals(secretAgent)) {
			p.setHand("암행어사");
			
		} else if(p.equals(mGosa)) {
				p.setHand("멍구사");
				rematch = true;
			}
		}
		for(Player p : players) {
			if(genealogy.indexOf(p.getHand()) >= genealogy.indexOf("13광땡")) rematch = false;
		}
	}

}