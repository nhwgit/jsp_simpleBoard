package article.util;

public class StringUtil {
	public static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return -1;
		}
	}
}
