package jp.co.zoppa.abnf.expression;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 識別子名を表す式。
 * ALPHA *(ALPHA / DIGIT / "-")
 */
public final class RulenameExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        int b = 0;

        // 識別子の先頭文字を取得
        b = accesser.peek();
        if ((b >= 'a' && b <= 'z') || 
            (b >= 'A' && b <= 'Z')) {
            accesser.read();
        }
        else {
            // マッチング失敗
            mark.restore();
            return ExpressionRange.getInvalid();
        }

        while (true) {
            b = accesser.peek();
            if ((b >= 'a' && b <= 'z') || 
                (b >= 'A' && b <= 'Z') ||
                (b == '_') ||
                (b == '-') ||
                (b >= '0' && b <= '9')) {
                accesser.read();
            }
            else {
                break;
            }
        }

        return new ExpressionRange(this, accesser.span(start, accesser.getPosition()));
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.RULENAME;
    }    
    
}
