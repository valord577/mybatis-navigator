package com.valord577.mybatis.navigator.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>based on jdk11</p>
 *
 * {@code copy from 'org.apache.commons.lang3.StringUtils' @ 3.9}
 */
public class Strings {

    private Strings() { }

    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StrKit.isEmpty(null)      = true
     * StrKit.isEmpty("")        = true
     * StrKit.isEmpty(" ")       = false
     * StrKit.isEmpty("bob")     = false
     * StrKit.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final String cs) {
        return cs == null || cs.isEmpty();
    }

    /**
     * <p>Checks if a CharSequence is not empty ("") and not null.</p>
     *
     * <pre>
     * StrKit.isNotEmpty(null)      = false
     * StrKit.isNotEmpty("")        = false
     * StrKit.isNotEmpty(" ")       = true
     * StrKit.isNotEmpty("bob")     = true
     * StrKit.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     */
    public static boolean isNotEmpty(final String cs) {
        return !isEmpty(cs);
    }

    /**
     * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StrKit.isBlank(null)      = true
     * StrKit.isBlank("")        = true
     * StrKit.isBlank(" ")       = true
     * StrKit.isBlank("bob")     = false
     * StrKit.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    public static boolean isBlank(final String cs) {
        return cs == null || cs.isBlank();
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StrKit.isNotBlank(null)      = false
     * StrKit.isNotBlank("")        = false
     * StrKit.isNotBlank(" ")       = false
     * StrKit.isNotBlank("bob")     = true
     * StrKit.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     *  not empty and not null and not whitespace only
     */
    public static boolean isNotBlank(final String cs) {
        return !isBlank(cs);
    }

    /**
     * <p>Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is <strong>case sensitive</strong>.</p>
     *
     * <pre>
     * StrKit.equals(null, null)   = true
     * StrKit.equals(null, "abc")  = false
     * StrKit.equals("abc", null)  = false
     * StrKit.equals("abc", "abc") = true
     * StrKit.equals("abc", "ABC") = false
     * </pre>
     *
     * @param cs1  the first CharSequence, may be {@code null}
     * @param cs2  the second CharSequence, may be {@code null}
     * @return {@code true} if the CharSequences are equal (case-sensitive), or both {@code null}
     */
    public static boolean equals(final String cs1, final String cs2) {
        if (cs1 == null && cs2 == null) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return cs1.equals(cs2);
    }

    /**
     * <p>Compares two CharSequences, returning {@code true} if they represent
     * not equal sequences of characters.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is <strong>case sensitive</strong>.</p>
     *
     *
     * <pre>
     * StrKit.isNotEquals(null, null)   = false
     * StrKit.isNotEquals(null, "abc")  = true
     * StrKit.isNotEquals("abc", null)  = true
     * StrKit.isNotEquals("abc", "abc") = false
     * StrKit.isNotEquals("abc", "ABC") = true
     * </pre>
     *
     * @param cs1  the first CharSequence, may be {@code null}
     * @param cs2  the second CharSequence, may be {@code null}
     * @return {@code true} if the CharSequences are not equal (case-sensitive), or both {@code null}
     */
    public static boolean isNotEquals(final String cs1, final String cs2) {
        return !equals(cs1, cs2);
    }

    /**
     * <p>Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters, ignoring case.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered equal. The comparison is <strong>case insensitive</strong>.</p>
     *
     * <pre>
     * StrKit.equalsIgnoreCase(null, null)   = true
     * StrKit.equalsIgnoreCase(null, "abc")  = false
     * StrKit.equalsIgnoreCase("abc", null)  = false
     * StrKit.equalsIgnoreCase("abc", "abc") = true
     * StrKit.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param cs1  the first CharSequence, may be {@code null}
     * @param cs2  the second CharSequence, may be {@code null}
     * @return {@code true} if the CharSequences are equal (case-insensitive), or both {@code null}
     */
    public static boolean equalsIgnoreCase(final String cs1, final String cs2) {
        if (cs1 == null && cs2 == null) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return cs1.equalsIgnoreCase(cs2);
    }

    /**
     * <p>Check if a CharSequence starts with a specified prefix.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is case sensitive.</p>
     *
     * <pre>
     * StrKit.startsWith(null, null)      = true
     * StrKit.startsWith(null, "abc")     = false
     * StrKit.startsWith("abcdef", null)  = false
     * StrKit.startsWith("abcdef", "abc") = true
     * StrKit.startsWith("ABCDEF", "abc") = false
     * </pre>
     *
     * @see String#startsWith(String)
     * @param str  the CharSequence to check, may be null
     * @param prefix the prefix to find, may be null
     * @return {@code true} if the CharSequence starts with the prefix, case sensitive, or
     *  both {@code null}
     */
    public static boolean startsWith(final String str, final String prefix) {
        if (null == str && prefix == null) {
            return true;
        }
        if (null == str || prefix == null) {
            return false;
        }
        return str.startsWith(prefix);
    }

    /**
     * <p>Check if a CharSequence ends with a specified suffix (optionally case insensitive).</p>
     *
     * @see String#endsWith(String)
     * @param str  the CharSequence to check, may be null
     * @param suffix the suffix to find, may be null
     * @return {@code true} if the CharSequence starts with the prefix or
     *  both {@code null}
     */
    public static boolean endsWith(final String str, final String suffix) {
        if (null == str && suffix == null) {
            return true;
        }
        if (null == str || suffix == null) {
            return false;
        }
        return str.endsWith(suffix);
    }

    /**
     * Performs the logic for the {@code split} and
     * {@code splitPreserveAllTokens} methods that do not return a
     * maximum array length.
     *
     * @param str  the String to parse, may be {@code null}
     * @param separatorChar the separate character
     * @return an list of parsed Strings, {@code null} if null String input
     */
    public static List<String> split(final String str, final char separatorChar) {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return Collections.emptyList();
        }
        final List<String> list = new ArrayList<>();
        int i = 0, start = 0;
        boolean match = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
                continue;
            }
            match = true;
            i++;
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return list;
    }

    /**
     * Performs the logic for the {@code split} and
     * {@code splitPreserveAllTokens} methods that return a maximum array
     * length.
     *
     * @param str  the String to parse, may be {@code null}
     * @param separatorChars the separate character
     * @return an list of parsed Strings, {@code null} if null String input
     */
    public static List<String> split(final String str, final String separatorChars) {
        // Performance tuned for 2.0 (JDK1.4)
        // Direct code is quicker than StringTokenizer.
        // Also, StringTokenizer uses isSpace() not isWhitespace()

        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return Collections.emptyList();
        }
        final List<String> list = new ArrayList<>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;

        final int max = -1;

        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = len;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = len;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                match = true;
                i++;
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = len;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                match = true;
                i++;
            }
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return list;
    }

}
