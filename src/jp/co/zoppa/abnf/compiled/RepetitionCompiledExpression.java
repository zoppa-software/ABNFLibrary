package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.expression.ExpressionRange;

/**
 * 繰り返し表現のコンパイル済み表現。
 */
public final class RepetitionCompiledExpression implements ICompiledExpression {

    /** 繰り返し対象表現。 */
    private final ICompiledExpression expression;

    /** 繰り返しパターン。 */
    private final int repetitionPattern;

    /** 繰り返し回数。 */
    private final int lowCount, highCount;

    /**
     * コンストラクタ
     * @param repeatExpr 繰り返し回数表現式
     * @param compile 繰り返し対象表現のコンパイル済み表現
     */
    public RepetitionCompiledExpression(ExpressionRange repeatExpr, ICompiledExpression expression) {
        this.expression = expression;
        this.repetitionPattern = repeatExpr.getSubRanges().size();
        switch (this.repetitionPattern) {
            case 1:
                this.lowCount = Integer.parseInt(repeatExpr.getSubRanges().get(0).getSpan().toString());
                this.highCount = Integer.MAX_VALUE;
                break;
            case 2:
                this.lowCount = Integer.parseInt(repeatExpr.getSubRanges().get(0).getSpan().toString());
                this.highCount = Integer.parseInt(repeatExpr.getSubRanges().get(1).getSpan().toString());
                break;
            default:
                this.lowCount = 0;
                this.highCount = Integer.MAX_VALUE;
                break;
        }
    }

    @Override
    public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        List<ABNFAnalyzeItem> tempAnswers = new ArrayList<>();
        int matchCount = 0;

        // 繰り返しマッチ数をカウント
        while (expression.analyze(rules, accesser, tempAnswers)) {
            matchCount++;
        }

        // 繰り返し回数の範囲チェック
        if (matchCount < this.lowCount || matchCount > this.highCount) {
            mark.restore();
            return false;
        }

        answer.addAll(tempAnswers);
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        // 繰り返し部分の文字列表現を生成
        switch (this.repetitionPattern) {
            case 0:
                sb.append("repeat * : ");
                break;

            case 1:
                sb.append("repeat ");
                sb.append(this.lowCount);
                sb.append(" : ");
                break;

            case 2:
                sb.append("repeat low=");
                sb.append(this.lowCount);
                sb.append(",high=");
                sb.append(this.highCount);
                sb.append(" : ");
                break;
        
            default:
                break;
        }

        // 繰り返し対象部分の文字列表現を生成
        sb.append(this.expression.toString());
        return sb.toString();
    }

}
