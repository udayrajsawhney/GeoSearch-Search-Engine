import java.io.*;

public class MyClass {
	public static void main(String[] args) {
		try {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter writer = new PrintWriter("result.txt", "UTF-8");
			String s = bufferRead.readLine();
			System.out.println(s);
		} catch(IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}