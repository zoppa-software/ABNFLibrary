package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * ルール表現式クラス。
 * rulename *c-wsp ("=" / "=/") *c-wsp elements c-nl
 */
public class RuleExpression implements IExpression {

    @Override
    public ExpressionRange match(jp.co.zoppa.abnf.accesser.IByteAccesser accesser) {
        IByteAccesser.IPosition mark = accesser.mark();
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();

        // 左辺の識別子を取得
        ExpressionRange nameRange = ExpressionDefines.getRuleNameExpr().match(accesser);
        if (!nameRange.isEnable()) {
            mark.restore();
            return ExpressionRange.getInvalid();
        }
        ranges.add(nameRange);

        {
            // コメントまたは空白
            ExpressionDefines.getCommentWspExpr().match(accesser);

            // '=' を取得
            int eq = accesser.peek();
            if (eq == '=') {
                accesser.read();
            }
            else {
                // マッチング失敗
                mark.restore();
                return ExpressionRange.getInvalid();
            }

            // '/' を取得
            int or = accesser.peek();
            if (or == '/') {
                accesser.read();
                ranges.add(new ExpressionRange(ExpressionDefines.getAlterExpr(), accesser.span(accesser.getPosition() - 1, accesser.getPosition())));
            }
        }

        // コメントまたは空白
        ExpressionDefines.getCommentWspExpr().match(accesser);

        // 右辺の定義を取得
        ExpressionRange eleRange = ExpressionDefines.getElementsExpr().match(accesser);
        if (!eleRange.isEnable()) {
            mark.restore();
            return ExpressionRange.getInvalid();
        }
        ranges.add(eleRange);

        // コメントと改行をスキップする
        ExpressionDefines.getCommentNlExpr().match(accesser);

        return new ExpressionRange(this, accesser.span(start, accesser.getPosition()), ranges);
    }
    
    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.RULE;
    }    

}
