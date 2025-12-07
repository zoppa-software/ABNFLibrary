package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 選択式。
 * concatenation *(*c-wsp "/" *c-wsp concatenation)
 */
public final class AlternativeExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();

        // 最初の式を取得
        ExpressionRange concatRange = ExpressionDefines.getConcatExpr().match(accesser);
        if (concatRange.isEnable()) {
            ranges.add(concatRange);
        }
        else {
            mark.restore();
            return ExpressionRange.getInvalid();
        }   

        // 以降の選択する式を取得
        while (accesser.peek() != -1) {
            IByteAccesser.IPosition nextmark = accesser.mark();

            // コメントまたは空白
            ExpressionDefines.getCommentWspExpr().match(accesser);

            // '/' がなければ終了する
            int b = accesser.peek();
            if (b == '/') {
                accesser.read();
            }
            else {
                nextmark.restore();
                break;
            }

            // コメントまたは空白
            ExpressionDefines.getCommentWspExpr().match(accesser);

            // 次の式を取得
            ExpressionRange nextRange = ExpressionDefines.getConcatExpr().match(accesser);
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
        return ExpressionEnum.ALTERNATIVE;
    }
    
}
