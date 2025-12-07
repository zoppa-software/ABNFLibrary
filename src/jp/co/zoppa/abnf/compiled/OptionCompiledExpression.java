package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.AnalyzeRange;
import jp.co.zoppa.abnf.CompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * オプションのコンパイル済み表現式クラス。
 */
public final class OptionCompiledExpression implements ICompiledExpression {

    /** コンパイル済み式。 */
    private final ICompiledExpression expression;

    /** オプション以降の式。 */
    private ICompiledExpression nextOptions = null;

    /**
     * コンストラクタ
     * @param expression コンパイル済み式。
     */
    public OptionCompiledExpression(ICompiledExpression expression) {
        this.expression = expression;
    }

    /**
     * オプション以降の式を追加する。
     * @param nextExprs オプション以降の式。
     */
    public void addNextExper(List<ICompiledExpression> nextExprs) {
        this.nextOptions = new ConcatenationCompiledExpression(nextExprs);
    }

    @Override
    public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        List<AnalyzeRange> tempAnswers = new ArrayList<>();

        // オプションと次の式が全てマッチした場合は真を返す
        if (expression.analyze(rules, accesser, tempAnswers)) {
            if (nextOptions != null) {
                if (nextOptions.analyze(rules, accesser, tempAnswers)) {
                    answer.addAll(tempAnswers);
                    return true;
                }
            }
        }

        // オプションが一致しなかった場合は位置を戻す
        mark.restore();
        tempAnswers.clear();

        // 次の式を試し、一致すれば真を返す
        if (nextOptions != null) {
            if (nextOptions.analyze(rules, accesser, tempAnswers)) {
                answer.addAll(tempAnswers);
                return true;
            }
            else {
                mark.restore();
                return false;
            }
        }

        // 一致しなかった場合でも真を返す
        return true;
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
