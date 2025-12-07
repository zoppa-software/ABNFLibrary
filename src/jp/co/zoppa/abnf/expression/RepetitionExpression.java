package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 反復式。
 * [1*DIGIT / (*DIGIT "*" *DIGIT)] (rulename / group / option / char-val / num-val / prose-val)
 */
public final class RepetitionExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();

        // 反復回数を取得
        ExpressionRange repetRange = matchRepeat(accesser);
        if (repetRange.isEnable()) {
            ranges.add(repetRange);
        }

        // 続く式を取得
        ExpressionRange expr = selectExpression(accesser);
        if (expr.isEnable()) {
            ranges.add(expr);
            return new ExpressionRange(this, accesser.span(start, accesser.getPosition()), ranges);
        }
        else {
            mark.restore();
            return ExpressionRange.getInvalid();
        }
    }

    /**
     * 続く式を選択する。
     * @param accesser バイト列アクセスインターフェース。
     * @return 続く式のマッチ結果。
     */
    private ExpressionRange selectExpression(IByteAccesser accesser) {
        ExpressionRange expr;
        expr = ExpressionDefines.getRuleNameExpr().match(accesser);
        if (expr.isEnable()) {
            return expr;
        }
        expr = ExpressionDefines.getGroupExpr().match(accesser);
        if (expr.isEnable()) {
            return expr;
        }
        expr = ExpressionDefines.getOptionExpr().match(accesser);
        if (expr.isEnable()) {
            return expr;
        }
        expr = ExpressionDefines.getCharExpr().match(accesser);
        if (expr.isEnable()) {
            return expr;
        }
        expr = ExpressionDefines.getNumvalExpr().match(accesser);
        if (expr.isEnable()) {
            return expr;
        }
        expr = ExpressionDefines.getProseExpr().match(accesser);
        if (expr.isEnable()) {
            return expr;
        }
        return ExpressionRange.getInvalid();
    }   

    /**
     * 反復回数を取得する。
     * @param accesser バイト列アクセスインターフェース。
     * @return 反復回数のマッチ結果。
     */
    public ExpressionRange matchRepeat(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();
        boolean enable = false;
        int b = 0;

        // 先頭の数字を取得
        int fst = accesser.getPosition();
        boolean fena = false;
        while (true) {
            b = accesser.peek();
            if (b >= '0' && b <= '9') {
                accesser.read();
                enable = true;
                fena = true;
            }
            else {
                break;
            }
        }
        if (fena) {
            ranges.add(new ExpressionRange(this, accesser.span(fst, accesser.getPosition())));
        }

        b = accesser.peek();
        if (b == '*') {
            accesser.read();
            int nst = accesser.getPosition();
            enable = true;

            // 続く数字を取得
            boolean nena = false;
            while (true) {
                b = accesser.peek();
                if (b >= '0' && b <= '9') {
                    accesser.read();
                    nena = true;
                }
                else {
                    break;
                }
            }
            if (nena) {
                ranges.add(new ExpressionRange(this, accesser.span(nst, accesser.getPosition())));
            }
        }

        // マッチ結果を返す
        if (enable) {
            return new ExpressionRange(this, accesser.span(start, accesser.getPosition()), ranges);
        }
        else {
            // マッチング失敗
            mark.restore();
            return ExpressionRange.getInvalid();
        }
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.REPETITION;
    }    
    
}
