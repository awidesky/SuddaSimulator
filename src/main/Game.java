package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Game {
	
	public static ArrayList<String> genealogy;
	
	public Player[] players;
	protected StringBuffer log = null;
	
	public Game(Player[] pairs) {
		players = pairs;
		if(Main.verbose) {
			log = new StringBuffer();
			log.append("\nNew Game :\n");
		}
	}
	
	
	protected void setup() {
		
		for(Player p : players) {
			if(p.a.num == p.b.num) {
				p.setHand(p.a.num + "땡");
			} else {
				p.setHand(((p.a.num + p.b.num) % 10) + "끗");
			}
		}
		
		if(Main.verbose) {
			log.append(Arrays.stream(players).map(Player::getHand).collect(Collectors.joining(", ")));
			log.append("\n");
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
		
		int myHandInt = -1;
		boolean sameHandExists = false;
		boolean result = true;
		
		for(Player p : players) {
			int index = genealogy.indexOf(p.getHand());
			if(myHandInt != -1) {
				if(myHandInt < index) {
					result = false;
					break;
				}
				if(myHandInt == index) {
					sameHandExists = true;
				}
			} else {
				myHandInt = index;
			}
		}
		if(result && sameHandExists) {
			int replayNum = (int)Arrays.stream(players).filter((p) -> p.getHand().equals(players[0].getHand())).count();
			players = Main.makePlayer(replayNum);
			if(Main.verbose) log.append("Draw. rematch.\n");
			setup();
			return play();
		}
		
		if (Main.verbose) {
			log.append("result : ");
			log.append(result ? "win" : "lose");
			Main.logger.log(log.toString());
		}
		
		return result;
	}
}

class MyGame extends Game {
	static {
		genealogy = new ArrayList<>(Arrays.asList("0끗", "1끗", "2끗", "3끗", "4끗", "5끗", "6끗", "7끗", "8끗", "9끝", "1땡", "2땡", "3땡", "4땡", "5땡", "6땡", "7땡", "8땡", "9땡", "10땡", "13광땡", "18광땡", "38광땡"));
	}
	public MyGame(Player[] pairs) { super(pairs); }
	
	@Override
	public boolean play() {
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
	public boolean play() {
		// TODO Auto-generated method stub
		return false;
	}
}