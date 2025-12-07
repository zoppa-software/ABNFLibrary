package jp.co.zoppa.abnf.accesser;

import java.io.UnsupportedEncodingException;

/**
 * 部分情報クラス。
 */
public final class Span {

    /**
     * データ。
     */
    private final byte[] data;

    /**
     * 開始位置。
     */
    private final int start;

    /**
     * 終了位置。
     */
    private final int end;

    /**
     * コンストラクタ。
     * @param data データ。
     * @param start 開始位置。
     * @param end 終了位置。
     */
    public Span(byte[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    /**
     * データを取得する。
     * @return データ。
     */
    public byte[] getData() {
        byte[] spanData = new byte[end - start];
        System.arraycopy(this.data, start, spanData, 0, end - start);
        return spanData;
    }

    /**
     * クローンを作成する。
     * @return クローン。
     */
    public Span clone() {
        byte[] spanData = new byte[end - start];
        System.arraycopy(this.data, start, spanData, 0, end - start);

        return new Span(spanData, 0, spanData.length);
    }

    /**
     * 開始位置を取得する。
     * @return 開始位置。
     */
    public int getStart() {
        return start;
    }

    /**
     * 終了位置を取得する。
     * @return 終了位置。
     */
    public int getEnd() {
        return end;
    }

    /**
     * 指定位置のバイトを取得する。
     * @param index 位置。
     * @return バイト値。
     */
    public int getByte(int index) {
        if (index >= 0 && index < end - start) {
            return data[start + index];
        }
        else {
            return -1;
        }
    }
    
    /**
     * 文字列表現を取得する。
     * @return 文字列表現。
     */
    @Override
    public String toString() {
        byte[] range = new byte[end - start];
        System.arraycopy(this.data, start, range, 0, end - start);
        try {
            return new String(range, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "<invalid>";
        }
    }

    /**
     * アクセス元のデータと範囲が等しいかを判定する。
     * @param accesser アクセス元。
     * @return 等しい場合はtrue、そうでない場合はfalse。
     */
    public boolean rangeEquals(IByteAccesser accesser) {
        for (int i = 0; i < end - start; i++) {
            if (this.getByte(i) != accesser.read()) {
                return false;
            }
        }
        return true;
    }

}
