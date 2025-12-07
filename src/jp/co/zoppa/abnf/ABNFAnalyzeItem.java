package jp.co.zoppa.abnf;

import java.util.ArrayList;

import jp.co.zoppa.abnf.accesser.Span;
import jp.co.zoppa.abnf.expression.ExpressionEnum;

/**
 * 解析結果の範囲を表すクラス。
 */
public final class ABNFAnalyzeItem {

    /**
     * 空のマッチ結果リスト。
     */
    private static ArrayList<ABNFAnalyzeItem> emptyRanges;
    
    /**
     * 空のマッチ結果リストを取得する。
     * @return 空のマッチ結果リスト。
     */
    public static ArrayList<ABNFAnalyzeItem> getSpaceExpr() {
        if (emptyRanges == null) {
            synchronized (ABNFAnalyzeItem.class) {
                if (emptyRanges == null) {
                    emptyRanges = new ArrayList<ABNFAnalyzeItem>();
                }
            }
        }
        return emptyRanges;
    }

    private final ExpressionEnum type;

    private final Span span;

    private final String ruleName;

    private final ArrayList<ABNFAnalyzeItem> subRanges;

    public ABNFAnalyzeItem(ExpressionEnum type, String name, Span span) {
        this.type = type;
        this.ruleName = name;
        this.span = span;
        this.subRanges = getSpaceExpr();
    }

    public ABNFAnalyzeItem(ExpressionEnum type, String name, Span span, ArrayList<ABNFAnalyzeItem> subRanges) {
        this.type = type;
        this.ruleName = name;
        this.span = span;
        this.subRanges = subRanges;
    }

    public ExpressionEnum getType() {
        return type;
    }

    public Span getSpan() {
        return span;
    }

    public String getRuleName() {
        return ruleName;
    }

    public ArrayList<ABNFAnalyzeItem> getSubRanges() {
        return subRanges;
    }
    
    public ArrayList<ABNFAnalyzeItem> searchByRuleName(String ruleName) {
        ArrayList<ABNFAnalyzeItem> answer = new ArrayList<ABNFAnalyzeItem>();
        if (ruleName != null) {
            for (ABNFAnalyzeItem range : subRanges) {
                if (ruleName.equals(range.getRuleName())) {
                    answer.add(range);
                }
            }
        }
        return answer;
    }

}
