package jp.co.zoppa.abnf.expression;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * コメントまたは空白式。
 * WSP / (c-nl WSP)
 */
public final class CommentWhiteSpaceExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        int b = accesser.peek();
        if (b == 0x20 || b == 0x09) {
            // 空白のみを試みる
            return ExpressionDefines.getSpaceExpr().match(accesser);
        }
        else {
            // コメントと空白の組み合わせを試みる
            ExpressionRange comment = ExpressionDefines.getCommentNlExpr().match(accesser);    
            ExpressionDefines.getSpaceExpr().match(accesser);
            return comment;
        }
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.COMMENT_WHITESPACE;
    }

}
