package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.Span;

/**
 * 表現式のマッチ結果クラス。
 */
public final class ExpressionRange {

    /**
     * 空のマッチ結果リスト。
     */
    private static List<ExpressionRange> emptyRanges;
    
    /**
     * 空のマッチ結果リストを取得する。
     * @return 空のマッチ結果リスト。
     */
    public static List<ExpressionRange> getSpaceExpr() {
        if (emptyRanges == null) {
            synchronized (ExpressionRange.class) {
                if (emptyRanges == null) {
                    emptyRanges = new ArrayList<ExpressionRange>();
                }
            }
        }
        return emptyRanges;
    }

    /**
     * 無効なマッチ結果。
     */
    private static ExpressionRange invalidRange;
    
    /**
     * 無効なマッチ結果を取得する。
     * @return 無効なマッチ結果。
     */
    public static ExpressionRange getInvalid() {
        if (invalidRange == null) {
            synchronized (ExpressionRange.class) {
                if (invalidRange == null) {
                    invalidRange = new ExpressionRange();
                }
            }
        }
        return invalidRange;
    }

    /** マッチに使用された表現式。 */
    private final IExpression expr;

    /** マッチした文字列。 */
    private final Span span;

    /** サブマッチ結果のリスト。 */
    private final List<ExpressionRange> subRanges;

    /** マッチ結果の有効フラグ。 */
    private final boolean valid;

    /**
     * 無効なマッチ結果を作成するコンストラクタ。
     */
    public ExpressionRange() {
        this.expr = null;
        this.span = new Span(new byte[] {}, 0, 0);
        this.subRanges = getSpaceExpr();
        this.valid = false;
    }

    /**
     * コンストラクタ。
     * @param expr 
     * @param span
     */
    public ExpressionRange(IExpression expr, Span span) {
        this.expr = expr;
        this.span = span;
        this.subRanges = getSpaceExpr();
        this.valid = true;
    }

    /**
     * コンストラクタ。
     * @param expr 
     * @param span
     * @param subRanges
     */
    public ExpressionRange(IExpression expr, Span span, List<ExpressionRange> subRanges) {
        this.expr = expr;
        this.span = span;
        this.subRanges = subRanges;
        this.valid = true;
    }

    /**
     * マッチ結果が有効かどうかを取得する。
     * @return マッチ結果が有効な場合は true、無効な場合は false。
     */
    public boolean isEnable() {
        return this.valid;
    }

    /**
     * マッチに使用された表現式を取得する。
     * @return 表現式。
     */
    public IExpression getExpression() {
        return this.expr;
    }

    /**
     * マッチした文字列を取得する。 
     * @return マッチした文字列。
     */
    public Span getSpan() {
        return this.span;
    }

    /**
     * サブマッチ結果のリストを取得する。
     * @return サブマッチ結果のリスト。
     */
    public List<ExpressionRange> getSubRanges() {
        return this.subRanges;
    }
}
