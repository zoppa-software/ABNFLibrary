package jp.co.zoppa.abnf.accesser;

import java.io.UnsupportedEncodingException;

/**
 * バイト列アクセス実装クラス。
 */
public class ByteAccesser implements IByteAccesser {

    /**
     * バイト列データ。
     */
    private final byte[] data;

    /**
     * 現在の位置。
     */
    private int position = 0;

    /**
     * コンストラクタ。
     * @param data バイト列データ。
     */
    public ByteAccesser(byte[] data) {
        this.data = data;
    }

    /**
     * コンストラクタ。
     * @param data 文字列データ。
     */
    public ByteAccesser(String data) {
        byte[] tempData = new byte[] {};
        try {
            tempData = data.getBytes("UTF-8");
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  
        this.data = tempData;          
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public int peek() {
        if (this.position < this.data.length) {
            return this.data[this.position];
        }
        else {
            return -1;
        }
    }

    @Override
    public int read() {
        if (this.position < this.data.length) {
            return this.data[this.position++];
        }
        else {
            return -1;
        }
    }

    @Override
    public int read(byte[] buffer, int offset, int length) {
        if (this.position < this.data.length) { 
            if (this.position + length > this.data.length) {
                // 読み込み可能バイト数が要求バイト数より少ない場合
                int readLength = this.data.length - this.position;
                System.arraycopy(this.data, this.position, buffer, offset, readLength);
                this.position += readLength;
                return readLength;
            }
            else {
                // 読み込み可能バイト数が要求バイト数以上の場合
                System.arraycopy(this.data, this.position, buffer, offset, length);
                this.position += length;
                return length;
            }
        }
        else {
            return 0;
        }
    }

    @Override
    public Span span(int start) {
        return new Span(this.data, start, this.data.length);
    }

    @Override
    public Span span(int start, int end) {
        return new Span(this.data, start, end);
    }

    @Override
    public IPosition mark() {
        return new Position(this.position);
    }

    /**
     * 位置マーククラス。
     */
    private class Position implements IPosition {

        /**
         * マーク位置。
         */
        private final int memPosition;

        /**
         * コンストラクタ。
         * @param position マーク位置。
         */
        public Position(int position) {
            this.memPosition = position;
        }

        @Override
        public int getPosition() {
            return this.memPosition;
        }

        @Override
        public void restore() {
            position = this.memPosition;
        }
    }
    
}
