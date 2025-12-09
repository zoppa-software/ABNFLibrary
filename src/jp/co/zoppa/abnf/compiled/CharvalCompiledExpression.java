package jp.co.zoppa.abnf.compiled;

import java.util.List;

import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.accesser.Span;
import jp.co.zoppa.abnf.expression.ExpressionEnum;
import jp.co.zoppa.abnf.expression.ExpressionRange;

/**
 * 文字値式のコンパイル済み表現式クラス。
 */
public final class CharvalCompiledExpression implements ICompiledExpression {

    /** 比較文字。 */
    private final String spanStr;

    /** 文字範囲。 */
    private final Span span;

    /**
     * コンストラクタ
     * @param expression 表現式。
     */
    public CharvalCompiledExpression(ExpressionRange expression) {
        this.spanStr = expression.getSubRanges().get(0).getSpan().toString();
        this.span = expression.getSubRanges().get(0).getSpan();
    }

    @Override
    public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();

        // 文字範囲と比較する
        if (this.span.rangeEquals(accesser)) {
            // マッチした場合は結果を追加する
            ABNFAnalyzeItem range = new ABNFAnalyzeItem(
                ExpressionEnum.CHARVAL, 
                this.spanStr, 
                accesser.span(start, accesser.getPosition())
            );
            answer.add(range);
            return true;
        }
        else {
            // マッチしなかった場合は元の位置に戻す
            rules.setUnmatchedTerminal(this.spanStr, accesser.span(start));
            mark.restore();
            return false;
        }
    }

    @Override
    public String toString() {
        return this.spanStr;
    }

}
