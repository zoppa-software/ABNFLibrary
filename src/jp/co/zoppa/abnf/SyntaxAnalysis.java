package jp.co.zoppa.abnf;

import java.util.Map;
import java.util.TreeMap;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.compiled.RuleCompiledExpression;
import jp.co.zoppa.abnf.expression.ExpressionDefines;
import jp.co.zoppa.abnf.expression.ExpressionEnum;
import jp.co.zoppa.abnf.expression.ExpressionRange;

public final class SyntaxAnalysis {

    /**
     * ABNF文法をコンパイルする。
     * @param accesser ABNF文法のバイトアクセス。
     * @return コンパイル済みルール。
     */
    public static CompiledRules compile(ByteAccesser accesser) {
        ExpressionRange answer = ExpressionDefines.getRuleListExpr().match(accesser);

        Map<String, RuleCompiledExpression> compiled = new TreeMap<>();
        for (ExpressionRange expr : answer.getSubRanges()) {
            if (expr.getExpression().getNo() == ExpressionEnum.RULE) {
                String key = expr.getSubRanges().get(0).getSpan().toString();
                compiled.put(
                    key, 
                    new RuleCompiledExpression(key, expr.getSubRanges().get(1))
                );
            }
        }
        return new CompiledRules(compiled);
    }

    /**
     * ABNF文法をコンパイルする。
     * @param input ABNF文法文字列。
     * @return コンパイル済みルールマップ。
     */
    public static CompiledRules compile(String input) {
        return compile(new ByteAccesser(input));
    }
    
}
