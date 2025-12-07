package jp.co.zoppa.abnf.accesser;

/**
 * バイト列アクセスインターフェース。
 */
public interface IByteAccesser {

    /**
     * 現在の位置を取得する。
     * @return 現在の位置。
     */
    public int getPosition();

    /**
     * 1バイト先読みする。
     * @return 先読みしたバイト。
     */
    public int peek();

    /**
    * 1バイト読み込む。
    * @return 読み込んだバイト。
    */
    public int read();

    /**
     * 複数バイト読み込む。
     * @param buffer 読み込み先バッファ。
     * @param offset バッファオフセット。
     * @param length 読み込みバイト数。
     * @return 実際に読み込んだバイト数。
     */
    public int read(byte[] buffer, int offset, int length);

    /**
     * 指定位置から終端までのバイト列を取得する。
     * @param start 開始位置。
     * @return 部分情報。
     */
    public Span span(int start);

    /**
     * 指定位置から指定位置までのバイト列を取得する。
     * @param start 開始位置。
     * @param end 終了位置。
     * @return 部分情報。
     */
    public Span span(int start, int end);

    /**
     * 現在の位置をマークする。
     * @return マーク位置。
     */
    public IPosition mark();
    
    /**
     * 位置マークインターフェース。
     */
    public interface IPosition {
    
        /**
         * マーク位置を取得する。
         * @return マーク位置。
         */
        public int getPosition();

        /**
         * マーク位置に復帰する。
         */
        public void restore();
    }
}