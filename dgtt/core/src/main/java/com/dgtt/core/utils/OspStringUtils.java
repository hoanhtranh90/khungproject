/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.core.utils;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author namtv
 */
public class OspStringUtils {

    public static String DATA_SPACE = " ";
    public static String DATA_UNDERSCORE = "_";
    public static String DATA_AND = "&";
    public static String DATA_ENCODE_AND = "&amp;";
    public static String DATA_ENCODE_UNDERSCORE = "&#95;";
    public static Map<Character, Character> VIETNAMESE_CHARACTER_DICT = null;
    public static Map<Character, Character> VIETNAMESE_CHARACTER_UPPERCASE_DICT = null;
    public static final Pattern characterFinder = Pattern.compile("[ăâđêôơưàảãáạăằẳẵắặâầẩẫấậèẻẽéẹêềểễếệìỉĩíịòỏõóọôồổỗốộơờởỡớợùủũúụưừửữứựỳỷỹýỵĂÂĐÊÔƠƯÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬÈẺẼÉẸÊỀỂỄẾỆÌỈĨÍỊÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢÙỦŨÚỤƯỪỬỮỨỰỲỶỸÝỴ]");
    public static final Pattern numberFinder = Pattern.compile("^[0-9]+$");

    static {
        String VIETNAMESE_1 = "aăâbcdđeêghiklmnoôơpqrstuưvxyaàảãáạăằẳẵắặâầẩẫấậeèẻẽéẹêềểễếệiìỉĩíịoòỏõóọôồổỗốộơờởỡớợuùủũúụưừửữứựyỳỷỹýỵAĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXYAÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬEÈẺẼÉẸÊỀỂỄẾỆIÌỈĨÍỊOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢUÙỦŨÚỤƯỪỬỮỨỰYỲỶỸÝỴ";
        String VIETNAMESE_2 = "aaabcddeeghiklmnooopqrstuuvxyaaaaaaaaaaaaaaaaaaeeeeeeeeeeeeiiiiiioooooooooooooooooouuuuuuuuuuuuyyyyyyAAABCDDEEGHIKLMNOOOPQRSTUUVXYAAAAAAAAAAAAAAAAAAEEEEEEEEEEEEIIIIIIOOOOOOOOOOOOOOOOOOUUUUUUUUUUUUYYYYYY";
        VIETNAMESE_CHARACTER_DICT = makeDictCharacter(VIETNAMESE_1, VIETNAMESE_2);

        VIETNAMESE_1 = "aăâbcdđeêghiklmnoôơpqrstuưvxyaàảãáạăằẳẵắặâầẩẫấậeèẻẽéẹêềểễếệiìỉĩíịoòỏõóọôồổỗốộơờởỡớợuùủũúụưừửữứựyỳỷỹýỵaăâbcdđeêghiklmnoôơpqrstuưvxyaàảãáạăằẳẵắặâầẩẫấậeèẻẽéẹêềểễếệiìỉĩíịoòỏõóọôồổỗốộơờởỡớợuùủũúụưừửữứựyỳỷỹýỵ";
        VIETNAMESE_2 = "AĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXYAÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬEÈẺẼÉẸÊỀỂỄẾỆIÌỈĨÍỊOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢUÙỦŨÚỤƯỪỬỮỨỰYỲỶỸÝỴAĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXYAÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬEÈẺẼÉẸÊỀỂỄẾỆIÌỈĨÍỊOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢUÙỦŨÚỤƯỪỬỮỨỰYỲỶỸÝỴ";
        VIETNAMESE_CHARACTER_UPPERCASE_DICT = makeDictCharacter(VIETNAMESE_1, VIETNAMESE_2);
    }

    public static Map<Character, Character> makeDictCharacter(String sFrom, String sTo) {
        Map<Character, Character> result = new HashMap<>();
        char[] arr1 = sFrom.toCharArray();
        char[] arr2 = sTo.toCharArray();
        for (int i = 0; i < arr1.length; i++) {
            result.put(arr1[i], arr2[i]);
        }
        return result;
    }

    public static final String unescapeHtml3(final String input) {
        StringWriter writer = null;
        int len = input.length();
        int i = 1;
        int st = 0;
        while (true) {
            // look for '&'
            while (i < len && input.charAt(i - 1) != '&') {
                i++;
            }
            if (i >= len) {
                break;
            }

            // found '&', look for ';'
            int j = i;
            while (j < len && j < i + MAX_ESCAPE + 1 && input.charAt(j) != ';') {
                j++;
            }
            if (j == len || j < i + MIN_ESCAPE || j == i + MAX_ESCAPE + 1) {
                i++;
                continue;
            }

            // found escape 
            if (input.charAt(i) == '#') {
                // numeric escape
                int k = i + 1;
                int radix = 10;

                final char firstChar = input.charAt(k);
                if (firstChar == 'x' || firstChar == 'X') {
                    k++;
                    radix = 16;
                }

                try {
                    int entityValue = Integer.parseInt(input.substring(k, j), radix);

                    if (writer == null) {
                        writer = new StringWriter(input.length());
                    }
                    writer.append(input.substring(st, i - 1));

                    if (entityValue > 0xFFFF) {
                        final char[] chrs = Character.toChars(entityValue);
                        writer.write(chrs[0]);
                        writer.write(chrs[1]);
                    } else {
                        writer.write(entityValue);
                    }

                } catch (NumberFormatException ex) {
                    i++;
                    continue;
                }
            } else {
                // named escape
                CharSequence value = lookupMap.get(input.substring(i, j));
                if (value == null) {
                    i++;
                    continue;
                }

                if (writer == null) {
                    writer = new StringWriter(input.length());
                }
                writer.append(input.substring(st, i - 1));

                writer.append(value);
            }

            // skip escape
            st = j + 1;
            i = st;
        }

        if (writer != null) {
            writer.append(input.substring(st, len));
            return writer.toString();
        }
        return input;
    }

    private static final String[][] ESCAPES = {
        {"\"", "quot"}, // " - double-quote
        {"&", "amp"}, // & - ampersand
        {"<", "lt"}, // < - less-than
        {">", "gt"}, // > - greater-than

        // Mapping to escape ISO-8859-1 characters to their named HTML 3.x equivalents.
        {"\u00A0", "nbsp"}, // non-breaking space
        {"\u00A1", "iexcl"}, // inverted exclamation mark
        {"\u00A2", "cent"}, // cent sign
        {"\u00A3", "pound"}, // pound sign
        {"\u00A4", "curren"}, // currency sign
        {"\u00A5", "yen"}, // yen sign = yuan sign
        {"\u00A6", "brvbar"}, // broken bar = broken vertical bar
        {"\u00A7", "sect"}, // section sign
        {"\u00A8", "uml"}, // diaeresis = spacing diaeresis
        {"\u00A9", "copy"}, // © - copyright sign
        {"\u00AA", "ordf"}, // feminine ordinal indicator
        {"\u00AB", "laquo"}, // left-pointing double angle quotation mark = left pointing guillemet
        {"\u00AC", "not"}, // not sign
        {"\u00AD", "shy"}, // soft hyphen = discretionary hyphen
        {"\u00AE", "reg"}, // ® - registered trademark sign
        {"\u00AF", "macr"}, // macron = spacing macron = overline = APL overbar
        {"\u00B0", "deg"}, // degree sign
        {"\u00B1", "plusmn"}, // plus-minus sign = plus-or-minus sign
        {"\u00B2", "sup2"}, // superscript two = superscript digit two = squared
        {"\u00B3", "sup3"}, // superscript three = superscript digit three = cubed
        {"\u00B4", "acute"}, // acute accent = spacing acute
        {"\u00B5", "micro"}, // micro sign
        {"\u00B6", "para"}, // pilcrow sign = paragraph sign
        {"\u00B7", "middot"}, // middle dot = Georgian comma = Greek middle dot
        {"\u00B8", "cedil"}, // cedilla = spacing cedilla
        {"\u00B9", "sup1"}, // superscript one = superscript digit one
        {"\u00BA", "ordm"}, // masculine ordinal indicator
        {"\u00BB", "raquo"}, // right-pointing double angle quotation mark = right pointing guillemet
        {"\u00BC", "frac14"}, // vulgar fraction one quarter = fraction one quarter
        {"\u00BD", "frac12"}, // vulgar fraction one half = fraction one half
        {"\u00BE", "frac34"}, // vulgar fraction three quarters = fraction three quarters
        {"\u00BF", "iquest"}, // inverted question mark = turned question mark
        {"\u00C0", "Agrave"}, // А - uppercase A, grave accent
        {"\u00C1", "Aacute"}, // Б - uppercase A, acute accent
        {"\u00C2", "Acirc"}, // В - uppercase A, circumflex accent
        {"\u00C3", "Atilde"}, // Г - uppercase A, tilde
        {"\u00C4", "Auml"}, // Д - uppercase A, umlaut
        {"\u00C5", "Aring"}, // Е - uppercase A, ring
        {"\u00C6", "AElig"}, // Ж - uppercase AE
        {"\u00C7", "Ccedil"}, // З - uppercase C, cedilla
        {"\u00C8", "Egrave"}, // И - uppercase E, grave accent
        {"\u00C9", "Eacute"}, // Й - uppercase E, acute accent
        {"\u00CA", "Ecirc"}, // К - uppercase E, circumflex accent
        {"\u00CB", "Euml"}, // Л - uppercase E, umlaut
        {"\u00CC", "Igrave"}, // М - uppercase I, grave accent
        {"\u00CD", "Iacute"}, // Н - uppercase I, acute accent
        {"\u00CE", "Icirc"}, // О - uppercase I, circumflex accent
        {"\u00CF", "Iuml"}, // П - uppercase I, umlaut
        {"\u00D0", "ETH"}, // Р - uppercase Eth, Icelandic
        {"\u00D1", "Ntilde"}, // С - uppercase N, tilde
        {"\u00D2", "Ograve"}, // Т - uppercase O, grave accent
        {"\u00D3", "Oacute"}, // У - uppercase O, acute accent
        {"\u00D4", "Ocirc"}, // Ф - uppercase O, circumflex accent
        {"\u00D5", "Otilde"}, // Х - uppercase O, tilde
        {"\u00D6", "Ouml"}, // Ц - uppercase O, umlaut
        {"\u00D7", "times"}, // multiplication sign
        {"\u00D8", "Oslash"}, // Ш - uppercase O, slash
        {"\u00D9", "Ugrave"}, // Щ - uppercase U, grave accent
        {"\u00DA", "Uacute"}, // Ъ - uppercase U, acute accent
        {"\u00DB", "Ucirc"}, // Ы - uppercase U, circumflex accent
        {"\u00DC", "Uuml"}, // Ь - uppercase U, umlaut
        {"\u00DD", "Yacute"}, // Э - uppercase Y, acute accent
        {"\u00DE", "THORN"}, // Ю - uppercase THORN, Icelandic
        {"\u00DF", "szlig"}, // Я - lowercase sharps, German
        {"\u00E0", "agrave"}, // а - lowercase a, grave accent
        {"\u00E1", "aacute"}, // б - lowercase a, acute accent
        {"\u00E2", "acirc"}, // в - lowercase a, circumflex accent
        {"\u00E3", "atilde"}, // г - lowercase a, tilde
        {"\u00E4", "auml"}, // д - lowercase a, umlaut
        {"\u00E5", "aring"}, // е - lowercase a, ring
        {"\u00E6", "aelig"}, // ж - lowercase ae
        {"\u00E7", "ccedil"}, // з - lowercase c, cedilla
        {"\u00E8", "egrave"}, // и - lowercase e, grave accent
        {"\u00E9", "eacute"}, // й - lowercase e, acute accent
        {"\u00EA", "ecirc"}, // к - lowercase e, circumflex accent
        {"\u00EB", "euml"}, // л - lowercase e, umlaut
        {"\u00EC", "igrave"}, // м - lowercase i, grave accent
        {"\u00ED", "iacute"}, // н - lowercase i, acute accent
        {"\u00EE", "icirc"}, // о - lowercase i, circumflex accent
        {"\u00EF", "iuml"}, // п - lowercase i, umlaut
        {"\u00F0", "eth"}, // р - lowercase eth, Icelandic
        {"\u00F1", "ntilde"}, // с - lowercase n, tilde
        {"\u00F2", "ograve"}, // т - lowercase o, grave accent
        {"\u00F3", "oacute"}, // у - lowercase o, acute accent
        {"\u00F4", "ocirc"}, // ф - lowercase o, circumflex accent
        {"\u00F5", "otilde"}, // х - lowercase o, tilde
        {"\u00F6", "ouml"}, // ц - lowercase o, umlaut
        {"\u00F7", "divide"}, // division sign
        {"\u00F8", "oslash"}, // ш - lowercase o, slash
        {"\u00F9", "ugrave"}, // щ - lowercase u, grave accent
        {"\u00FA", "uacute"}, // ъ - lowercase u, acute accent
        {"\u00FB", "ucirc"}, // ы - lowercase u, circumflex accent
        {"\u00FC", "uuml"}, // ь - lowercase u, umlaut
        {"\u00FD", "yacute"}, // э - lowercase y, acute accent
        {"\u00FE", "thorn"}, // ю - lowercase thorn, Icelandic
        {"\u00FF", "yuml"}, // я - lowercase y, umlaut
    };

    private static final int MIN_ESCAPE = 2;
    private static final int MAX_ESCAPE = 6;

    private static final HashMap<String, CharSequence> lookupMap;

    static {
        lookupMap = new HashMap<>();
        for (final CharSequence[] seq : ESCAPES) {
            lookupMap.put(seq[1].toString(), seq[0]);
        }
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5.hashCode() + "";
    }

    public static String SHA265(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5.hashCode() + "";
    }

    /**
     * getDomain
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        String domain;
        int domainStartIdx = url.indexOf("//");
        if (domainStartIdx == -1) {
            domainStartIdx = 0;
        } else {
            domainStartIdx += 2;
        }
        int domainEndIdx = url.indexOf('/', domainStartIdx);
        if (domainEndIdx == -1) {
            domainEndIdx = url.length();
        }
        domainEndIdx = domainEndIdx > domainStartIdx ? domainEndIdx : url.length();
        domain = url.substring(domainStartIdx, domainEndIdx).toLowerCase();
        if (domain.startsWith("www.")) {
            domain = domain.substring(4);
        }
        if (domain.startsWith("m.")) {
            domain = domain.substring(2);
        }
        return domain;
    }

    /**
     *
     * @param arrs
     * @param delimeter
     * @return
     */
    public static String iteratorString2String(Iterator<String> arrs, String delimeter) {
        StringBuilder sb = new StringBuilder();
        while (arrs.hasNext()) {
            String val = arrs.next();
            val = val.trim();
            if (val.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(delimeter);
            }
            sb.append("'").append(val).append("'");
        }

        return sb.toString();
    }

    /**
     *
     * @param arrs
     * @param delimeter
     * @return
     */
    public static String iterator2String(Iterator<Integer> arrs, String delimeter) {
        StringBuilder sb = new StringBuilder();
        while (arrs.hasNext()) {
            Integer val = arrs.next();
            if (sb.length() > 0) {
                sb.append(delimeter);
            }
            sb.append(val);
        }

        return sb.toString();
    }

    public static String mysqlSafe(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(input.length());
        char ch;
        for (int i = 0; i < input.length(); i++) {
            // Emojis are two characters long in java, e.g. a rocket emoji is "\uD83D\uDE80";
            ch = input.charAt(i);
            if (i < (input.length() - 1)) {
                if (Character.isSurrogatePair(ch, input.charAt(i + 1))) {
                    i += 1; //also skip the second character of the emoji
                    continue;
                }
            }
            sb.append(ch);
        }

        return sb.toString();
    }

    /**
     * Joins many string to one with DELIMITER specify
     *
     * @param args
     * @param delimiter
     * @return
     */
    public static String joins(String[] args, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(arg);
        }
        return sb.toString();
    }

    /**
     * Joins many string to one with DELIMITER specify
     *
     * @param args
     * @param delimiter
     * @return
     */
    public static String joins(Iterator<String> args, String delimiter) {
        StringBuilder sb = new StringBuilder();
        while (args.hasNext()) {
            String next = args.next();
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(next);
        }
        return sb.toString();
    }

    /**
     * Joins many string to one with DELIMITER specify
     *
     * @param args
     * @param delimiter
     * @param bound
     * @return
     */
    public static String joins(String[] args, String delimiter, String bound) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(bound).append(arg).append(bound);
        }
        return sb.toString();
    }

    /**
     * Joins many string to one with DELIMITER specify
     *
     * @param args
     * @param delimiter
     * @param bound
     * @return
     */
    public static String joins(Iterator<String> args, String delimiter, String bound) {
        StringBuilder sb = new StringBuilder();
        while (args.hasNext()) {
            String next = args.next();
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(bound).append(next).append(bound);
        }
        return sb.toString();
    }

    /**
     *
     * @param in
     * @return
     */
    public static String chuyenKhongDau(String in) {
        StringBuilder sb = new StringBuilder();
        for (char ch : in.toCharArray()) {
            if (VIETNAMESE_CHARACTER_DICT.containsKey(ch)) {
                sb.append(VIETNAMESE_CHARACTER_DICT.get(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static boolean isVovelVietnamese(String in) {
        return characterFinder.matcher(in).find();
    }

    public static String makeUpperForFirstCharacter(String in) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < in.length(); i++) {
            char ch = in.charAt(i);
            if (i == 0
                    || Character.isLetter(ch) && Character.isWhitespace(in.charAt(i - 1))) {
                if (VIETNAMESE_CHARACTER_UPPERCASE_DICT.containsKey(ch)) {
                    sb.append(VIETNAMESE_CHARACTER_UPPERCASE_DICT.get(ch));
                } else {
                    sb.append(ch);
                }
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static boolean isOnlyNumber(String keyword) {
        return numberFinder.matcher(keyword).find();
    }

    /**
     *
     * @param arrs
     * @param delimeter
     * @return
     */
    public static String array2String(int[] arrs, String delimeter) {
        StringBuilder sb = new StringBuilder();
        for (int arr : arrs) {
            if (sb.length() > 0) {
                sb.append(delimeter);
            }
            sb.append(arr);
        }
        return sb.toString();
    }

    /**
     *
     * @param arrs
     * @param delimeter
     * @return
     */
    public static String array2String(Integer[] arrs, String delimeter) {
        StringBuilder sb = new StringBuilder();
        for (Integer arr : arrs) {
            if (sb.length() > 0) {
                sb.append(delimeter);
            }
            sb.append(arr);
        }
        return sb.toString();
    }

    /**
     *
     * @param arrs
     * @param delimeter
     * @return
     */
    public static <T> String arrayNumber2String(T[] arrs, String delimeter) {
        StringBuilder sb = new StringBuilder();
        for (T arr : arrs) {
            if (sb.length() > 0) {
                sb.append(delimeter);
            }
            sb.append(arr);
        }
        return sb.toString();
    }

    /**
     *
     * @param arrs
     * @param delimeter
     * @param infix
     * @return
     */
    public static String array2StringWithTextInfix(int[] arrs, String delimeter, String infix) {

        StringBuilder sb = new StringBuilder();
        for (Integer arr : arrs) {
            if (sb.length() > 0) {
                sb.append(delimeter);
            }
            sb.append("'").append(infix).append(arr).append(infix).append("'");//'T1T'
        }

        return sb.toString();
    }

    /**
     *
     * @param arrs
     * @param delimeter
     * @return
     */
    public static String arrayString2String(String[] arrs, String delimeter) {
        StringBuilder sb = new StringBuilder();
        for (String arr : arrs) {
            arr = arr.trim();
            if (arr.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(delimeter);
            }
            sb.append("'").append(arr).append("'");
        }
        return sb.toString();
    }

    /**
     *
     * @param arrs
     * @param delimeter
     * @return
     */
    public static String iteratorString2String2(Iterator<String> arrs, String delimeter) {
        StringBuilder sb = new StringBuilder();
        while (arrs.hasNext()) {
            String val = arrs.next();
            val = val.trim();
            if (val.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(delimeter);
            }
            sb.append(val);
        }

        return sb.toString();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * @param in The integer value
     * @param fill The number of digits to fill
     * @return The given value left padded with the given number of digits
     */
    public static String lPadZero(int in, int fill) {

        boolean negative = false;
        int len = 0;

        String s;

        if (in >= 0) {
            s = String.valueOf(in);
            len = s.length();
        } else {
            negative = true;
            s = String.valueOf(-in);
            len = s.length();
            len++;
        }

        StringBuilder sb = new StringBuilder(fill);

        if (negative) {
            sb.append('-');
        }

        for (int i = fill; i > len; i--) {
            sb.append('0');
        }

        sb.append(s);

        return sb.toString();
    }

    /**
     * @param in The integer value
     * @param fill The number of digits to fill
     * @return The given value left padded with the given number of digits
     */
    public static String lPadZero(long in, int fill) {

        boolean negative = false;
        int len = 0;

        String s;

        if (in >= 0) {
            s = String.valueOf(in);
            len = s.length();
        } else {
            negative = true;
            s = String.valueOf(-in);
            len = s.length();
            len++;
        }

        StringBuilder sb = new StringBuilder(fill);

        if (negative) {
            sb.append('-');
        }

        for (int i = fill; i > len; i--) {
            sb.append('0');
        }

        sb.append(s);

        return sb.toString();
    }

    public static int countNumber(String s) {
        int num = 0;
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                num++;
            }
        }
        return num;
    }

    public static String titleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    /**
     * Replaces characters that may be confused by an SQL parser with their
     * equivalent escape characters.
     * <p>
     * Any data that will be put in an SQL query should be be escaped. This is
     * especially important for data that comes from untrusted sources such as
     * Internet users.
     * <p>
     * For example if you had the following SQL query:<br>
     * <code>"SELECT * FROM addresses WHERE name='" + name + "' AND private='N'"</code><br>
     * Without this function a user could give <code>" OR 1=1 OR ''='"</code> as
     * their name causing the query to be:<br>
     * <code>"SELECT * FROM addresses WHERE name='' OR 1=1 OR ''='' AND private='N'"</code><br>
     * which will give all addresses, including private ones.<br>
     * Correct usage would be:<br>
     * <code>"SELECT * FROM addresses WHERE name='" + StringHelper.escapeSQL(name) + "' AND private='N'"</code><br>
     * <p>
     * Another way to avoid this problem is to use a PreparedStatement with
     * appropriate placeholders.
     *
     * @param s String to be escaped
     * @return escaped String
     * @throws NullPointerException if s is null.
     *
     * @since ostermillerutils 1.00.00
     */
    public static String escapeSQL(String s) {
        int length = s.length();
        int newLength = length;
    // first check for characters that might
        // be dangerous and calculate a length
        // of the string that has escapes.
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\':
                case '\"':
                case '\'':
                case '\0': {
                    newLength += 1;
                }
                break;
            }
        }
        if (length == newLength) {
            // nothing to escape in the string
            return s;
        }
        StringBuffer sb = new StringBuffer(newLength);
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\': {
                    sb.append("\\\\");
                }
                break;
                case '\"': {
                    sb.append("\\\"");
                }
                break;
                case '\'': {
                    sb.append("\\\'");
                }
                break;
                case '\0': {
                    sb.append("\\0");
                }
                break;
                default: {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static String buildFirstTimeOfDay(Date day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    public static String buildEndTimeOfDay(Date day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }
}
