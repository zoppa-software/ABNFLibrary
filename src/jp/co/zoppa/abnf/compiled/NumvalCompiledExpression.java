package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.expression.ExpressionEnum;
import jp.co.zoppa.abnf.expression.ExpressionRange;

/**
 * 数値値式のコンパイル済み表現。
 */
public final class NumvalCompiledExpression implements ICompiledExpression {

    /** 比較文字。 */
    private final String spanStr;

    /** 数値値の種類。 */
    private final int type;

    /** 表現式の種類。 */
    private final ExpressionEnum exprem;

    /** 数値値のリスト。 */
    private final List<NumvalValue> values;

    /**
     * コンストラクタ。
     * @param expression 表現式。
     */
    public NumvalCompiledExpression(ExpressionRange expression) {
        this.spanStr = expression.getSpan().toString();

        // 数値種別を取得
        this.type = expression.getSpan().getByte(1);

        // 表現式の種類を取得
        List<ExpressionRange> subRanges = expression.getSubRanges().get(0).getSubRanges();
        if (subRanges.size() >= 2) {
            this.exprem = subRanges.get(1).getExpression().getNo();
        }
        else {
            this.exprem = ExpressionEnum.NUMVAL;
        }

        // 数値リストを取得
        this.values = new ArrayList<>();
        for (ExpressionRange valueExpr : expression.getSubRanges().get(0).getSubRanges()) {
            String v = valueExpr.getSpan().toString();
            switch (this.type) {
                case 'd':
                    this.values.add(new NumvalValue(Integer.parseInt(v), v));
                    break;
                case 'x':
                    this.values.add(new NumvalValue(Integer.parseInt(v, 16), v));
                    break;
                case 'b':
                    this.values.add(new NumvalValue(Integer.parseInt(v, 2), v));
                default:
                    break;
            }
        }
    }

    @Override
    public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();

        switch (this.exprem) {
            case ExpressionEnum.NUMVAL:
                if (values.get(0).num == accesser.read()) {
                    return true;
                }
                break;
            case ExpressionEnum.NUMVAL_RANGE:
                int rval = accesser.read();
                if (values.get(0).num <= rval && rval <= values.get(1).num) {
                    return true;
                }
                break;
            case ExpressionEnum.NUMVAL_CONCAT:
                boolean conf = true;
                for (NumvalValue val : values) {
                    if (val.num != accesser.read()) {
                        conf = false;
                        break;
                    }
                }
                if (conf) {
                    return true;
                } 
                break;
            default:
                break;
        }

        // マッチしなかった場合は元の位置に戻す
        rules.setUnmatchedTerminal(this.spanStr, accesser.span(start));
        mark.restore();
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("type : ");
        switch (this.type) {
            case 'd':
                sb.append("dec ");
                break;
            case 'x':
                sb.append("hex ");
                break;
            case 'b':
                sb.append("bin ");
            default:
                break;
        }

        switch (this.exprem) {
            case NUMVAL:
                sb.append("value : ");
                sb.append(values.get(0).str);
                break;
            case NUMVAL_RANGE:
                sb.append("range : ");
                sb.append(values.get(0).str);
                sb.append(" - ");
                sb.append(values.get(1).str);
                break;
            case NUMVAL_CONCAT:
                sb.append("concat : ");
                for (int i = 0; i < values.size(); i++) {
                    if (i > 0) {
                        sb.append(" . ");
                    }
                    sb.append(values.get(i).str);
                }
                break;
            default:
                break;
        }

        return sb.toString();
    }

    /**
     * 数値値。
     */
    private static class NumvalValue {

        /** 数値を取得する。 */
        public final int num;

        /** 文字列表現を取得する。 */
        public final String str;

        /**
         * コンストラクタ
         * @param num 数値。
         * @param str 文字列表現。
         */
        public NumvalValue(int num, String str) {
            this.num = num;
            this.str = str;
        }
    }

}
