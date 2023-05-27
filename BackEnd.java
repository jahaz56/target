import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.ArrayList;

public class BackEnd {
	NineLetterWords allNineLetterWords;
	Random random;
	String nineLetterWord;
	char[] shuffledLetters;
	ArrayList<String> possibleWords;
	ArrayList<String> enteredWords;
	int correctScore;
	int incorrectScore;
	int totalScore;

	public BackEnd() {
		this.allNineLetterWords = new NineLetterWords();
		this.random = new Random();
		this.nineLetterWord = null;
		this.shuffledLetters = new char[9];
		this.possibleWords = new ArrayList<String>();
		this.enteredWords = new ArrayList<String>();
		this.correctScore = 0;
		this.incorrectScore = 0;
		this.totalScore = 0;
	}

	public void createTargetData() {
		
		// Gets a random nine letter word.
		int randInt = this.random.nextInt(this.allNineLetterWords.nineLetterWords.length);
		this.nineLetterWord = this.allNineLetterWords.nineLetterWords[randInt];
		
		// Divides the nine letter word into a char array of letters.
		char[] nineLetterArray = new char[this.nineLetterWord.length()];

		int i = 0;
		while (i < this.nineLetterWord.length()) {
			nineLetterArray[i] = this.nineLetterWord.charAt(i);
			i += 1;
		}

		// Shuffles the array.
		// Look up Fischer-Yates shuffle on wikipedia for more information.
		int j = nineLetterArray.length - 1;
		while (j > 0) {
			int randomIndex = this.random.nextInt(j + 1);
			char randomLetter = nineLetterArray[randomIndex];
			nineLetterArray[randomIndex] = nineLetterArray[j];
			nineLetterArray[j] = randomLetter;
			j -= 1;
		}
		this.shuffledLetters = nineLetterArray;
		
		// Creates an ArrayList with all the possible words you can get from the nine letter word.
		try {
			BufferedReader inFile = new BufferedReader(new FileReader("C:\\Users\\James\\Documents\\Java\\workspace\\Target\\src\\dictionary2.txt"));
			String currentLine = inFile.readLine();

			while (currentLine != null) {
				if (checkValidWord(currentLine)) {
					this.possibleWords.add(currentLine);
				}
				currentLine = inFile.readLine();
			}
			inFile.close();

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			System.out.println(errorMessage);
		}
		this.totalScore = this.possibleWords.size();
	}
	
	public boolean checkValidWord(String word) {
		boolean isValid = true;
		
		if (word.length() < 4 || word.length() > 9 || word.indexOf(this.shuffledLetters[4]) == -1)
			isValid = false;
		else {
			StringBuilder sb1 = new StringBuilder(word);
			StringBuilder sb2 = new StringBuilder(this.nineLetterWord);

			for (int i = 0; i < sb1.length(); i += 1) {
				String letter = Character.toString(sb1.charAt(i));
				int indexOfLetter = sb2.indexOf(letter);
				if (indexOfLetter != -1) {
					sb2.deleteCharAt(indexOfLetter);
				} else
					isValid = false;
			}
		}
		return isValid;
	}

	public void checkUserEnteredWord(String word) {
		if (possibleWords.contains(word)) {
			this.enteredWords.add(word);
			this.possibleWords.remove(word);
			this.correctScore += 1;
		} else {
			this.enteredWords.add(word);
			this.incorrectScore += 1;
		}
	}
	
	// Get methods.
	public char[] getShuffledLetters() {
		return this.shuffledLetters;
	}
	
	public ArrayList<String> getEnteredWords() {
		return this.enteredWords;
	}
	
	// This method resets the data that needs to be reset at a new game.
	public void resetData() {
		this.correctScore = 0;
		this.incorrectScore = 0;
		this.possibleWords.clear();
		this.enteredWords.clear();
		this.totalScore = 0;
	}
	
	public String correctScoreToString() {
		return "Correct: " + this.correctScore + "/" + this.totalScore;
	}
	
	public String incorrectScoreToString() {
		return "Incorrect: " + this.incorrectScore;
	}
	
	public String possibleWordsToString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < this.possibleWords.size(); i += 1) {
			if (this.possibleWords.get(i).length() < 9)
				sb.append(this.possibleWords.get(i).toLowerCase() + " ");
			else 
				sb.append(this.possibleWords.get(i) + " ");
		}
		
		// Text wrapping code.
		int i = 0;
		while ((i = sb.indexOf(" ", i + 50)) != -1) {
			sb.replace(i, i + 1, "\n");
		}
		
		return sb.toString();
	}
}
