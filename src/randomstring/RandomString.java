package randomstring;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomString {
	

	  private static final char[] CHARR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?-+=_"
	      .toCharArray();
	  private static final String PASSWORD_REGEX = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,16}$";
	  private static final String NO_CHINESE_REGEX = "^[^\\u4e00-\\u9fa5]+$";
	  private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
	  private static final Pattern NO_CHINESE_PATTERN = Pattern.compile(NO_CHINESE_REGEX);
	 
	  public static String generatePassword(int length) {
	    length = length < 8 ? 8 : length;
	    length = length > 16 ? 16 : length;
	    String result = getRandomPassword(length);
	    Matcher m = PASSWORD_PATTERN.matcher(result);
	    Matcher m1 = NO_CHINESE_PATTERN.matcher(result);
	    if (m.matches() && m1.matches()) {
	      return result;
	    }
	    return generatePassword(length);
	  }
	 
	  private static String getRandomPassword(int length) {
	    StringBuilder sb = new StringBuilder();
	    ThreadLocalRandom r = ThreadLocalRandom.current();
	    for (int x = 0; x < length; ++x) {
	      sb.append(CHARR[r.nextInt(CHARR.length)]);
	    }
	    return sb.toString();
	  }
	  
}


