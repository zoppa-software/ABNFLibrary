package jp.co.zoppa.abnf.expression;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * コメント改行式。
 * comment / CRLF
 */
public final class CommentNewLineExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();

        // コメントの開始文字でない場合は無効とする
        ExpressionRange comment = ExpressionDefines.getCommentExpr().match(accesser);
        if (comment.isEnable()) {
            ExpressionRange crlfExper = ExpressionDefines.getCrlfExpr().match(accesser);
            if (crlfExper.isEnable()) {
                return comment;
            }
        }

        mark.restore();
        return ExpressionRange.getInvalid();
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.COMMENT_NEWLINE;
    }
}
