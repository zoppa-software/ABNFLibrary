package jp.co.zoppa.abnf.compiled;

import java.util.List;

import jp.co.zoppa.abnf.AnalyzeRange;
import jp.co.zoppa.abnf.CompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * コンパイル済み表現式インターフェース。
 */
public interface ICompiledExpression {

    public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer);

}
