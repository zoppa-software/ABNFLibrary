package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 連接コンパイル済み表現式クラス。
 */
public final class ConcatenationCompiledExpression implements ICompiledExpression {

    /** コンパイル済みの連接する式（左辺） */
    private List<ICompiledExpression> leftExper;

    /** コンパイル済みの連接する式（右辺） */
    private List<ICompiledExpression> rightExper;

    /** コンパイル済みの連接する式（オプション） */
    private ICompiledExpression optExper;

    /**
     * コンストラクタ
     * @param expressions コンパイル済みの連接する式。
     */
    public ConcatenationCompiledExpression(List<ICompiledExpression> expressions) {
        createExpressTree(expressions);
    }

    /**
     * 連接表現のツリー構造を作成する。
     * @param expressions コンパイル済みの連接する式。
     */
    private void createExpressTree(List<ICompiledExpression> expressions) {
        // オプション表現が含まれている場合は取得
        ICompiledExpression hitOpt = null;
        for (ICompiledExpression expr : expressions) {
            if (expr instanceof OptionCompiledExpression) {
                hitOpt = expr;
                break;
            }
        }
        if (hitOpt == null) {
            this.leftExper = expressions;
            this.rightExper = null;
            this.optExper = null;
            return;
        }

        // オプション表現の前後で分割してツリー構造に変換
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

        // ツリー構造を作成
        this.leftExper = prevExprs;
        this.rightExper = afterOptExprs;
        this.optExper = hitOpt;
    }

    @Override
    public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        List<ABNFAnalyzeItem> leftAnswers = new ArrayList<>();
        List<ABNFAnalyzeItem> rightAnswers = new ArrayList<>();

        // 連結要素を順番にマッチさせる
        for (ICompiledExpression expr : leftExper) {
            // マッチしなかった場合は元の位置に戻す
            if (!expr.analyze(rules, accesser, leftAnswers)) {
                mark.restore();
                return false;
            }
        }

        // オプションが存在しない場合は終了
        if (optExper == null) {
            answer.addAll(leftAnswers);
            return true;
        }

        // オプションと次の式が全てマッチした場合は真を返す
        boolean match = true;
        if (optExper.analyze(rules, accesser, rightAnswers)) {
            for (ICompiledExpression expr : rightExper) {
                // マッチしなかった場合は元の位置に戻す
                if (!expr.analyze(rules, accesser, rightAnswers)) {
                    mark.restore();
                    match = false;
                    break;
                }
            }
        }
        if (match) {
            answer.addAll(leftAnswers);
            answer.addAll(rightAnswers);
            return true;
        }

        // オプションを除いて次の式をマッチさせる
        mark.restore();
        rightAnswers.clear();

        for (ICompiledExpression expr : rightExper) {
            // マッチしなかった場合は元の位置に戻す
            if (!expr.analyze(rules, accesser, rightAnswers)) {
                mark.restore();
                return false;
            }
        }

        // 全ての要素がマッチした場合は結果を追加する
        answer.addAll(leftAnswers);
        answer.addAll(rightAnswers);
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < leftExper.size(); i++) {
            if (i > 0) {
                sb.append(" + ");
            }
            sb.append(leftExper.get(i).toString());
        }
        if (optExper != null) {
            sb.append(" + ");
            sb.append(optExper.toString());
            for (int i = 0; i < rightExper.size(); i++) {
                sb.append(" + ");
                sb.append(rightExper.get(i).toString());
            }
        }
        return sb.toString();
    }

}
