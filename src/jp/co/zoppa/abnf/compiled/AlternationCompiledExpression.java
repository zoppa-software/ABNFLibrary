package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * コンパイル済み選択表現式クラス。
 */
public final class AlternationCompiledExpression implements ICompiledExpression {

    /** コンパイル済みの選択する式。 */
    private final List<ICompiledExpression> expressions;

    /**
     * コンストラクタ
     * @param compileAll コンパイル済みの選択する式。
     */
    public AlternationCompiledExpression(List<ICompiledExpression> compileAll) {
        this.expressions = compileAll;
    }

    @Override
    public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        List<ABNFAnalyzeItem> tempAnswers = new ArrayList<>();

        // それぞれの選択肢を試す
        for (ICompiledExpression expr : expressions) {
            mark.restore();
            tempAnswers.clear();

            // マッチした場合は結果を追加して真を返す
            if (expr.analyze(rules, accesser, tempAnswers)) {
                answer.addAll(tempAnswers);
                return true;
            }
        }

        // どれもマッチしなかった場合は偽を返す
        mark.restore();
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < expressions.size(); i++) {
            if (i > 0) {
                sb.append(" / ");
            }
            sb.append(expressions.get(i).toString());
        }
        return sb.toString();
    }

}
