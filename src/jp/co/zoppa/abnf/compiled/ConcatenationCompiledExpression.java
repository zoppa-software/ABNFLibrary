package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.AnalyzeRange;
import jp.co.zoppa.abnf.CompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 連接コンパイル済み表現式クラス。
 */
public final class ConcatenationCompiledExpression implements ICompiledExpression {

    /** コンパイル済みの連接する式。 */
    private final List<ICompiledExpression> expressions;

    /**
     * コンストラクタ
     * @param expressions コンパイル済みの連接する式。
     */
    public ConcatenationCompiledExpression(List<ICompiledExpression> expressions) {
        this.expressions = createExpressTree(expressions);
    }

    /**
     * 連接式の中にオプション表現が含まれている場合はツリー構造に変換する。
     * @param expressions 連接式のリスト。
     * @return ツリー構造に変換された連接式のリスト。
     */
    private static List<ICompiledExpression> createExpressTree(List<ICompiledExpression> expressions) {
        // オプション表現が含まれている場合は取得
        ICompiledExpression hitOpt = null;
        for (ICompiledExpression expr : expressions) {
            if (expr instanceof OptionCompiledExpression) {
                hitOpt = expr;
                break;
            }
        }
        if (hitOpt == null) {
            return expressions;
        }

        List<ICompiledExpression> prevExprs = new ArrayList<>();
        List<ICompiledExpression> afterOptExprs = new ArrayList<>();
        boolean hit = false;

        // オプションの前はそのまま追加
        for (ICompiledExpression expr : expressions) {
            if (expr == hitOpt) {
                hit = true;
            }
            else if (!hit) {
                prevExprs.add(expr);
            }
            else {
                afterOptExprs.add(expr);
            }
        }

        // オプション式を追加
        OptionCompiledExpression optExpr = (OptionCompiledExpression)hitOpt;
        optExpr.addNextExper(createExpressTree(afterOptExprs));
        prevExprs.add(optExpr);

        return prevExprs;
    }

    @Override
    public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        List<AnalyzeRange> tempAnswers = new ArrayList<>();

        // 連結要素を順番にマッチさせる
        for (ICompiledExpression expr : expressions) {
            // マッチしなかった場合は元の位置に戻す
            if (!expr.analyze(rules, accesser, tempAnswers)) {
                mark.restore();
                return false;
            }
        }

        // 全ての要素がマッチした場合は結果を追加する
        answer.addAll(tempAnswers);
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < expressions.size(); i++) {
            if (i > 0) {
                sb.append(" + ");
            }
            sb.append(expressions.get(i).toString());
        }
        return sb.toString();
    }

}
