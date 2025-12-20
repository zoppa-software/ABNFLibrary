package jp.co.zoppa.abnf.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.zoppa.abnf.ABNFAnalyzeException;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

/**
 * 文法全体を表す式。
 * 1*( rule / (*WSP c-nl) )
 */
public final class RuleListExpression implements IExpression {

    @Override
    public ExpressionRange match(IByteAccesser accesser) throws ABNFAnalyzeException {
        int start = accesser.getPosition();
        List<ExpressionRange> ranges = new ArrayList<>();
        Map<String, ExpressionRange> tree = new TreeMap<>();
        ExpressionRange range;
        int prevPos = -1;

        while (accesser.peek() != -1) {
            // ルール式をマッチングする
            range = ExpressionDefines.getRuleExpr().match(accesser);
            if (range.isEnable()) {
                String key = range.getSubRanges().get(0).getSpan().toString();

                if (!tree.containsKey(key)) {
                    ranges.add(range);
                    tree.put(key, range);
                }
                else if (range.getSubRanges().size() > 2 && 
                         tree.get(key).getSubRanges().size() == 2) {
                    tree.get(key).getSubRanges().get(1).getSubRanges().addAll(range.getSubRanges().get(2).getSubRanges());
                }
            }
            else {
                // SP / HTAB をスキップする
                ExpressionDefines.getSpaceExpr().match(accesser);

                // コメント式をマッチングする
                range = ExpressionDefines.getCommentNlExpr().match(accesser);
                if (range.isEnable()) {
                    ranges.add(range);
                } 
            }

            // 改行のみが続く場合はスキップする
            ExpressionDefines.getCrlfExpr().match(accesser);

            // 進捗チェック
            if (prevPos == accesser.getPosition()) {
                throw new ABNFAnalyzeException(
                    String.format("解析エラー：'%s'", accesser.span(accesser.getPosition()).toString(20))
                );
            }
            prevPos = accesser.getPosition();
        }

        // マッチ結果を返す
        return new ExpressionRange(this, accesser.span(start, accesser.getPosition()), ranges);
    }

    @Override
    public ExpressionEnum getNo() {
        return ExpressionEnum.RULELIST;
    }    

}
