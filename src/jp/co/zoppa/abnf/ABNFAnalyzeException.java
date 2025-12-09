package jp.co.zoppa.abnf;

/**
 * ABNF解析例外クラス。
 */
public class ABNFAnalyzeException extends Exception {

    /**
     * コンストラクタ。
     * @param message 例外メッセージ。
     */
    public ABNFAnalyzeException(String message) {
        super(message);
    }

}
