package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 数値値式。
 * num-val = "%" (bin-val / dec-val / hex-val)
 * bin-val = "b" 1*BIT [ 1*("." 1*BIT) / ("-" 1*BIT) ]
 * dec-val = "d" 1*DIGIT [ 1*("." 1*DIGIT) / ("-" 1*DIGIT) ]
 * hex-val = "x" 1*HEXDIG [ 1*("." 1*HEXDIG) / ("-" 1*HEXDIG) ]
 */
public final class NumvalExpression implements IExpression {
    
    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();

        // '%'
        int prefix = accesser.peek();
        if (prefix == '%') {
            accesser.read();
        }
        else {
            mark.restore();
            return ExpressionRange.getInvalid();
        }

        // 数値判定
        ExpressionRange expr;
        switch (accesser.read()) {
            case 'b':
                expr = matchValue(accesser, new int[] { 
                    '0', '1' 
                });
                break;
            case 'd':
                expr = matchValue(accesser, new int[] { 
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 
                });
                break;
            case 'x':
                expr = matchValue(accesser, new int[] { 
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F',
                    'a', 'b', 'c', 'd', 'e', 'f' 
                });
                break;
            default:
                mark.restore();
                return ExpressionRange.getInvalid();
        }

        // マッチ結果を返す
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
     * 数値部分のマッチングを行う。
     * @param accesser 入力バイトアクセス
     * @param numbers マッチ対象の数値配列
     * @return マッチ結果
     */
    private ExpressionRange matchValue(IByteAccesser accesser, int numbers[]) {
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();

        // マッチ用の数値セットを作成
        boolean[] numSet = new boolean[256];
        for (int n : numbers) {
            numSet[n] = true;
        }

        boolean enable = false;
        int b = 0;

        // 先頭の数字を取得
        int fst = accesser.getPosition();
        boolean fena = false;
        while (true) {
            b = accesser.peek();
            if (b >= 0 && numSet[b]) {
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

        if (accesser.peek() == '.') {
            // 連結取得
            accesser.read();

            // 続く数字を取得
            while (true) {
                int nst = accesser.getPosition();
                boolean nena = false;
                while (true) {
                    b = accesser.peek();
                    if (b >= 0 && numSet[b]) {
                        accesser.read();
                        enable = true;
                        nena = true;
                    }
                    else {
                        break;
                    }
                }
                if (nena) {
                    ranges.add(new ExpressionRange(ExpressionDefines.getNumvalConcatExpr(), accesser.span(nst, accesser.getPosition())));
                }

                // 次のピリオドを確認
                if (accesser.peek() == '.') {
                    accesser.read();
                }
                else {
                    break;
                }
            }
        }
        else if (accesser.peek() == '-') {
            // 範囲取得
            accesser.read();

            // 続く数字を取得
            int nst = accesser.getPosition();
            boolean nena = false;
            while (true) {
                b = accesser.peek();
                if (b >= 0 && numSet[b]) {
                    accesser.read();
                    enable = true;
                    nena = true;
                }
                else {
                    break;
                }
            }
            if (nena) {
                ranges.add(new ExpressionRange(ExpressionDefines.getNumvalRangeExpr(), accesser.span(nst, accesser.getPosition())));
            }
        }

        // マッチ結果を返す
        if (enable) {
            return new ExpressionRange(this, accesser.span(start, accesser.getPosition()), ranges);
        }
        else {
            // マッチング失敗
            return ExpressionRange.getInvalid();
        }
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.NUMVAL;
    }    

    /**
     * 数値範囲式。
     */
    public static class Range implements IExpression {

        @Override
        public ExpressionRange match(IByteAccesser accesser) {
            return null;
        }

        @Override
        public ExpressionEnum getNo() {
            return ExpressionEnum.NUMVAL_RANGE;
        }
    }

    /**
     * 数値連結式。
     */
    public static class Concat implements IExpression {

        @Override
        public ExpressionRange match(IByteAccesser accesser) {
            return null;
        }

        @Override
        public ExpressionEnum getNo() {
            return ExpressionEnum.NUMVAL_CONCAT;
        }
    }
}
