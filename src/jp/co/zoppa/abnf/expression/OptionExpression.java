package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * オプション式。
 * "[" *c-wsp alternation *c-wsp "]"
 */
public final class OptionExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();

        // '['
        int fbkt = accesser.peek();
        if (fbkt == '[') {
            accesser.read();
        }
        else {
            mark.restore();
            return ExpressionRange.getInvalid();
        }

        // 空白読み捨て
        ExpressionDefines.getCommentWspExpr().match(accesser);

        // 選択式をマッチングする
        ExpressionRange alterRange = ExpressionDefines.getAlterExpr().match(accesser);
        if (!alterRange.isEnable()) {
            mark.restore();
            return ExpressionRange.getInvalid();
        }
        ranges.add(alterRange);

        // 空白読み捨て
        ExpressionDefines.getCommentWspExpr().match(accesser);

        // ']'
        int ebkt = accesser.peek();
        if (ebkt == ']') {
            accesser.read();
        }
        else {
            mark.restore();
            return ExpressionRange.getInvalid();
        }

        // グループ式のマッチ結果を返す
        return new ExpressionRange(this, accesser.span(start, accesser.getPosition()), ranges);
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.OPTION;
    }    

}
