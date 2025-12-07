package jp.co.zoppa.abnf.expression;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * CRLF を表す式。
 */
public final class CrLfExpression implements IExpression {
    
    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        // CR または LF をマッチングする
        // 本来は CRLF のみをマッチングすべきだが、便宜上 CR または LF をマッチングするようにする
        int start = accesser.getPosition();
        boolean matched = false;
        while (true) {
            int b = accesser.peek();
            if (b == 0x0D || b == 0x0A) {
                accesser.read();
                matched = true;
            }
            else {
                break;
            }
        }

        // マッチ結果を返す
        if (matched) {
            return new ExpressionRange(this, accesser.span(start, accesser.getPosition()));
        }
        else {
            return ExpressionRange.getInvalid();
        }
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.CR_LF;
    }

}