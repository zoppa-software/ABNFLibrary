package jp.co.zoppa.abnf.expression;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * コメント式。
 * ";" *(WSP / VCHAR)
 */
public final class CommentExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();

        // コメントの開始文字でない場合は無効とする
        if (accesser.peek() != ';') {
            mark.restore();
            return ExpressionRange.getInvalid();
        }
        accesser.read();

        // 改行文字が現れるまで読み飛ばす
        while (accesser.peek() != -1) {
            int b = accesser.peek();
            if (b != 0x0D && b != 0x0A) {
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
        return ExpressionEnum.COMMENT;
    }

}
