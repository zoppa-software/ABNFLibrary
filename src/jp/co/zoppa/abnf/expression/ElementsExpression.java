package jp.co.zoppa.abnf.expression;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 定義式。
 * alternation *WSP
 */
public final class ElementsExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        ExpressionRange alter = ExpressionDefines.getAlterExpr().match(accesser);
        ExpressionDefines.getSpaceExpr().match(accesser);
        return alter;
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.ELEMENTS;
    }

}
