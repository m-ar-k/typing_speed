import java.util.Scanner;
import java.util.Timer;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class typing {
	public static void main(String[] args) {
		Scanner filescanner = null;
		File infile = null;
		int errors = 0;
		int words_count = 0;
		
		Random rand = new Random();
		int n = rand.nextInt(2); // Set this to however many test files are in the folder
		String[] test_cases = {"test1.txt", "test2.txt"}; // Add possible test files to here
		
		try {infile = new File(test_cases[n]); filescanner = new Scanner(infile);}
		catch(FileNotFoundException e) {System.out.println("No file found"); System.exit(0);}
		
		for (int i=5; i>0; i--) {
			System.out.print("Get ready in "+i);
			try{Thread.sleep(1000);}
			catch(InterruptedException e){}
			System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
		}
		Scanner sc = new Scanner(System.in);
		
		long start = System.nanoTime();
		while (true) {
			String file_line = filescanner.nextLine();
			System.out.println(file_line);
			String[] line_breakdown = sc.nextLine().split(" ");
			String[] testcase_breakdown = file_line.split(" ");
			words_count += testcase_breakdown.length;
			
			for (int i=0; i<testcase_breakdown.length; i++) {
				if (i==line_breakdown.length) {errors += (testcase_breakdown.length-i); break;} // Case where less words are inputted than to be tested
				if (!(line_breakdown[i].equals(testcase_breakdown[i]))) errors++;
			}
			if (!filescanner.hasNextLine()) break;
		}

		double wpm = Math.round(((words_count-errors)/(((System.nanoTime() - start)/1000000000.0)/60.0))*100.0)/100.0;
		print_results(wpm);
	}
	
	static void print_results(double wpm) {
		String wpm_string = String.valueOf(wpm);
		String[] wpm_seperated = wpm_string.split("\\.");
		File song = new File("music.wav");
		
		print_string("Your typing speed was ");
		print_number(wpm_seperated[0], song);
		
		System.out.print(".");
		play_song(song, 850);
		
		print_number(wpm_seperated[1], song);
		print_string(" words per minute.");
	}
	
	static void print_number(String number, File song) {
		for (int i=0; i<number.length(); i++) {
			System.out.print(number.charAt(i)); 
			play_song(song, 850);
		}
		if (number.length()==0) {System.out.print("0"); play_song(song, 850);}
	}
	
	static void print_string(String segment) {
		for (int i=0; i<segment.length(); i++) {
			System.out.print(segment.charAt(i)); 
			try{Thread.sleep(50);}
			catch(InterruptedException e){}
		}
	}
	
	static void play_song(File sound, int length) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
			try{Thread.sleep(length);}
			catch(InterruptedException e){}
		}
		catch(Exception e) {}
	}
}
