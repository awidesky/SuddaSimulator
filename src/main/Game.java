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