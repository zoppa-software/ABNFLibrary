package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 文字値式。
 * DQUOTE *(%x20-21 / %x23-7E) DQUOTE
 */
public final class CharvalExpression implements IExpression {
    
    @Override
    public ExpressionRange match(jp.co.zoppa.abnf.accesser.IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();

        // '"'
        int fbkt = accesser.peek();
        if (fbkt == '"') {
            accesser.read();
        }
        else {
            mark.restore();
            return ExpressionRange.getInvalid();
        }

        int instart = accesser.getPosition();

        // 内部文字列を取得
        while (true) {
            int b = accesser.peek();
            if ((b >= 0x20 && b <= 0x21) ||
                (b >= 0x23 && b <= 0x7E)) {
                accesser.read();
            }
            else {
                break;
            }
        }

        int inend = accesser.getPosition();

        // '"'
        int ebkt = accesser.peek();
        if (ebkt == '"') {
            accesser.read();
        }
        else {
            mark.restore();
            return ExpressionRange.getInvalid();
        }

        // 文字値式のマッチ結果を返す
        ranges.add(new ExpressionRange(this, accesser.span(instart, inend)));
        return new ExpressionRange(this, accesser.span(start, accesser.getPosition()), ranges);
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.CHARVAL;
    }
    
}
