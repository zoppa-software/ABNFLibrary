package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * オプションのコンパイル済み表現式クラス。
 */
public final class OptionCompiledExpression implements ICompiledExpression {

    /** コンパイル済み式。 */
    private final ICompiledExpression expression;

    /**
     * コンストラクタ
     * @param expression コンパイル済み式。
     */
    public OptionCompiledExpression(ICompiledExpression expression) {
        this.expression = expression;
    }

    @Override
    public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        List<ABNFAnalyzeItem> tempAnswers = new ArrayList<>();

        if (expression.analyze(rules, accesser, tempAnswers)) {
            answer.addAll(tempAnswers);
            return true;
        }
        else {
            mark.restore();
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(expression.toString());
        sb.append("]");
        return sb.toString();
    }

}
