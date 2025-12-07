package jp.co.zoppa.abnf.data;


public class Answer {

    private final boolean matched;

    private final byte[] data;

    private final int start;

    private final int end;

    public Answer(boolean matched, byte[] data, int start, int end) {
        this.matched = matched;
        this.data = data;
        this.start = start;
        this.end = end;
    }
    
    public boolean isMatched() {
        return matched;
    }


}
