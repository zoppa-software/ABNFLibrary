package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.AnalyzeRange;
import jp.co.zoppa.abnf.CompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.expression.ExpressionEnum;
import jp.co.zoppa.abnf.expression.ExpressionRange;

/**
 * ルール名式のコンパイル済み表現
 */
public final class RulenameCompiledExpression implements ICompiledExpression {

    /** ルール名。 */
    private final String ruleName;

    /**
     * コンストラクタ。
     * @param expression ルール名式。
     */
    public RulenameCompiledExpression(ExpressionRange expression) {
        this.ruleName = expression.getSpan().toString();
    }

    @Override
    public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        List<AnalyzeRange> tempAnswers = new ArrayList<>();
        int start = accesser.getPosition();

        // ルール名に対応するルールをマッチさせる
        if (rules.getRule(this.ruleName).getExpression().analyze(rules, accesser, tempAnswers)) {
            AnalyzeRange range = new AnalyzeRange(ExpressionEnum.RULE, ruleName, accesser.span(start, accesser.getPosition()), tempAnswers);
            answer.add(range);
            return true;
        }
        else {
            mark.restore();
            return false;
        }
    }

    @Override
    public String toString() {
        return ruleName;
    }

}
