package jp.co.zoppa.abnf.compiled;

import java.util.List;

import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * コンパイル済み式インターフェース。
 */
public interface ICompiledExpression {

    /**
     * 解析を行う。
     * @param rules コンパイル済みルール群。 
     * @param accesser バイトアクセス。
     * @param answer マッチ結果リスト。
     * @return マッチした場合は真、しなかった場合は偽。
     */
    public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer);

}
