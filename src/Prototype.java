import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Prototype {
	public static void main(String[] args) throws Exception {
		
		Process dump = new ProcessBuilder("windump", "-i", "1", "-n", "-l")
				.start();
		
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(dump.getInputStream()));
		
		String s;
		
		Scanner portScanner = new Scanner(br);
		
		while ((s = br.readLine()) != null) {
			
			System.out.println(s);
			
		}
		
	}
}
