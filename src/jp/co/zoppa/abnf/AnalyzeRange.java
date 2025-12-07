package jp.co.zoppa.abnf;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.Span;
import jp.co.zoppa.abnf.expression.ExpressionEnum;

/**
 * 解析結果の範囲を表すクラス。
 */
public class AnalyzeRange {

    /**
     * 空のマッチ結果リスト。
     */
    private static List<AnalyzeRange> emptyRanges;
    
    /**
     * 空のマッチ結果リストを取得する。
     * @return 空のマッチ結果リスト。
     */
    public static List<AnalyzeRange> getSpaceExpr() {
        if (emptyRanges == null) {
            synchronized (AnalyzeRange.class) {
                if (emptyRanges == null) {
                    emptyRanges = new ArrayList<AnalyzeRange>();
                }
            }
        }
        return emptyRanges;
    }

    private final ExpressionEnum type;

    private final Span span;

    private final String ruleName;

    private final List<AnalyzeRange> subRanges;

    public AnalyzeRange(ExpressionEnum type, String name, Span span) {
        this.type = type;
        this.ruleName = name;
        this.span = span;
        this.subRanges = getSpaceExpr();
    }

    public AnalyzeRange(ExpressionEnum type, String name, Span span, List<AnalyzeRange> subRanges) {
        this.type = type;
        this.ruleName = name;
        this.span = span;
        this.subRanges = subRanges;
    }
    
}
