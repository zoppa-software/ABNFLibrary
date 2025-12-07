package jp.co.zoppa.abnf.accesser;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * バイト列アクセス実装クラス（ストリーム版）。
 */
public class ByteAccesserFromStream implements IByteAccesser {

    /**
     * ストリーム。
     */
    private final InputStream stream;

    /**
     * 現在位置。
     */
    private int position = 0;

    /**
     * 読込済みバッファ。
     */
    private ArrayList<Byte> buffer = new ArrayList<>();

    /**
    * コンストラクタ。
    * @param inputStream 入力ストリーム。
    */
    public ByteAccesserFromStream(InputStream inputStream) {
        this.stream = inputStream;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public int peek() {
        try {
            if (this.position < this.buffer.size()) {
                // 既に読み込まれている場合はバッファから取得
                return this.buffer.get(this.position);
            }
            else {
                // 未読み込みの場合はストリームから読み込む
                int byteRead = this.stream.read();
                if (byteRead != -1) {
                    this.buffer.add((byte) byteRead);
                }
                return byteRead;
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int read() {
        try {
            if (this.position < this.buffer.size()) {
                // 既に読み込まれている場合はバッファから取得
                return this.buffer.get(this.position++);
            }
            else {
                // 未読み込みの場合はストリームから読み込む
                int byteRead = this.stream.read();
                if (byteRead != -1) {
                    this.buffer.add((byte) byteRead);
                    this.position++;
                }
                return byteRead;
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int read(byte[] buffer, int offset, int length) {
        if (this.position < this.buffer.size()) { 
            if (this.position + length > this.buffer.size()) {
                // 読み込み可能バイト数が要求バイト数より少ない場合
                int readLength = this.buffer.size() - this.position;
                System.arraycopy(this.buffer.subList(this.position, readLength), this.position, buffer, offset, readLength);
                this.position += readLength;

                try {
                    int bytesRead = this.stream.read(buffer, offset, length - readLength);
                    if (bytesRead != -1) {
                        for (int i = 0; i < bytesRead; i++) {
                            this.buffer.add(buffer[offset + i]);
                        }
                        this.position += bytesRead;
                    }
                    return readLength + bytesRead;
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    return readLength;
                }
            }
            else {
                // 読み込み可能バイト数が要求バイト数以上の場合
                System.arraycopy(this.buffer.subList(this.position, length), this.position, buffer, offset, length);
                this.position += length;
                return length;
            }
        }
        else {
            // すべて未読み込みの場合はストリームから直接読み込む
            try {
                int bytesRead = this.stream.read(buffer, offset, length);
                if (bytesRead != -1) {
                    for (int i = 0; i < bytesRead; i++) {
                        this.buffer.add(buffer[offset + i]);
                    }
                    this.position += bytesRead;
                }
                return bytesRead;
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

    @Override
    public Span span(int start) {
        return span(start, this.position);
    }

    @Override
    public Span span(int start, int end) {
        byte[] spanData = new byte[end - start];
        for (int i = start; i < end; i++) {
            spanData[i - start] = this.buffer.get(i);
        }
        return new Span(spanData, start, end);
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
