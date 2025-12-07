package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 連結式。
 * concatenation *(1*c-wsp concatenation)
 */
public final class ConcatenationExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();

        // 最初の式を取得
        ExpressionRange repetRange = ExpressionDefines.getRepeatExpr().match(accesser);
        if (repetRange.isEnable()) {
            ranges.add(repetRange);
        }
        else {
            mark.restore();
            return ExpressionRange.getInvalid();
        }   

        // 以降の選択する式を取得
        while (accesser.peek() != -1) {
            IByteAccesser.IPosition nextmark = accesser.mark();

            // コメントまたは空白
            ExpressionRange comExpr = ExpressionDefines.getCommentWspExpr().match(accesser);
            if (!comExpr.isEnable()) {
                nextmark.restore();
                break;
            } 

            // 次の式を取得
            ExpressionRange nextRange = ExpressionDefines.getRepeatExpr().match(accesser);
            if (nextRange.isEnable()) {
                ranges.add(nextRange);
            }
            else {
                nextmark.restore();
                break;
            }  
        }

        // マッチ結果を返す
        return new ExpressionRange(this, accesser.span(start, accesser.getPosition()), ranges);  
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.CONCATENATION;
    }
}
