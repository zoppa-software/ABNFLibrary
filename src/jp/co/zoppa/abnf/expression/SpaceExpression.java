package jp.co.zoppa.abnf.expression;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 空白式。
 * 1*(SP / HTAB)
 */
public final class SpaceExpression implements IExpression {
    
    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        int start = accesser.getPosition();
        boolean matched = false;
        
        // SP または HTAB を1つ以上読み取る
        while (true) {
            int b = accesser.peek();
            if (b == 0x20 || b == 0x09) {
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
        return ExpressionEnum.SPACE;
    }    

}
