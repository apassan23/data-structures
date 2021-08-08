public class KMPSearch {
    private String pattern;

    public KMPSearch(String pattern) {
        this.pattern = pattern;
    }

    public int search(String text) {
        int t = 0, p = 0;
        int[] lps = new int[pattern.length()];
        fillLPSArray(lps);
        while (t < text.length()) {
            if (text.charAt(t) == pattern.charAt(p)) {
                t += 1;
                p += 1;
            } else {
                if (p != 0)
                    p = lps[p - 1];
                else
                    t += 1;
            }
            if (p == pattern.length())
                return t - p;
        }

        return -1;
    }

    private void fillLPSArray(int[] lps) {
        int p = 1;
        int len = 0;
        lps[0] = 0;
        while (p < pattern.length()) {
            if (pattern.charAt(p) == pattern.charAt(len)) {
                lps[p] = len + 1;
                len += 1;
                p += 1;
            }

            else {
                if (len != 0)
                    len = lps[len - 1];
                else {
                    lps[p] = 0;
                    p += 1;
                }
            }
        }
    }
}
