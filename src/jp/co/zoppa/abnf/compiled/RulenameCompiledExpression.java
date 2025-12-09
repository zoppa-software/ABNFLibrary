package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
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
    public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        ArrayList<ABNFAnalyzeItem> tempAnswers = new ArrayList<ABNFAnalyzeItem>();
        int start = accesser.getPosition();

        // ルール名に対応するルールをマッチさせる
        if (rules.getRule(this.ruleName).getExpression().analyze(rules, accesser, tempAnswers)) {
            ABNFAnalyzeItem range = new ABNFAnalyzeItem(
                ExpressionEnum.RULE, 
                ruleName, 
                accesser.span(start, accesser.getPosition()), 
                tempAnswers
            );
            answer.add(range);
            return true;
        }
        else {
            // マッチしなかった場合は元の位置に戻す
            rules.setUnmatchedRule(this.ruleName);
            mark.restore();
            return false;
        }
    }

    @Override
    public String toString() {
        return ruleName;
    }

}
