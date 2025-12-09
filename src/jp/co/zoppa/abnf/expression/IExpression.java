package jp.co.zoppa.abnf.expression;

import jp.co.zoppa.abnf.ABNFAnalyzeException;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 表現式インターフェース。
 */
public interface IExpression {

    /**
     * マッチングを行う。
     * @param accesser バイト列アクセスインターフェース。
     * @return マッチ結果。
     * @throws ABNFAnalyzeException 解析例外。
     */
    public ExpressionRange match(IByteAccesser accesser) throws ABNFAnalyzeException;

    /**
     * 表現式番号を取得する。
     * @return 表現式番号。
     */
    public ExpressionEnum getNo();
}