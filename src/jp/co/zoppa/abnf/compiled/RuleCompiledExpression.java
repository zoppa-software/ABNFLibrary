package jp.co.zoppa.abnf.compiled;

import java.util.ArrayList;
import java.util.List;

import jp.co.zoppa.abnf.expression.ExpressionRange;

/**
 * ルールのコンパイル済み表現式クラス。
 */
public final class RuleCompiledExpression {

    /** ルール名。 */ 
    private final String name;

    /** コンパイル済み表現式。 */
    private final ICompiledExpression expression;

    /**
     * ルール名を取得する。
     * @return ルール名。
     */
    public String getName() {
        return name;
    }

    /**
     * コンパイル済み表現式を取得する。
     * @return コンパイル済み表現式。
     */
    public ICompiledExpression getExpression() {
        return expression;
    }

    /**
     * コンストラクタ。
     * @param name ルール名。
     * @param expression 表現式。
     */
    public RuleCompiledExpression(String name, ExpressionRange expression) {
        this.name = name;
        this.expression = compile(expression);
    }

    /**
     * コンストラクタ。
     * @param name ルール名。
     * @param expression コンパイル済み式。
     */
    public RuleCompiledExpression(String name, ICompiledExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    /**
     * 表現式をコンパイルする。
     * @param expression 表現式。
     * @return コンパイル済み式。
     */
    private static ICompiledExpression compile(ExpressionRange expression) {
        switch (expression.getExpression().getNo()) {
        case ALTERNATIVE:
            return (
                expression.getSubRanges().size() > 1 ?
                new AlternationCompiledExpression(compileSubRanges(expression)) :
                compile(expression.getSubRanges().get(0))
            );

        case CONCATENATION:
            return (
                expression.getSubRanges().size() > 1 ?
                new ConcatenationCompiledExpression(compileSubRanges(expression)) :
                compile(expression.getSubRanges().get(0))
            );

        case REPETITION:
            return (
                expression.getSubRanges().size() > 1 ?
                new RepetitionCompiledExpression(
                    expression.getSpan().toString(),
                    expression.getSubRanges().get(0),
                    compile(expression.getSubRanges().get(1))
                ) :
                compile(expression.getSubRanges().get(0))
            );

        case RULENAME:
            return new RulenameCompiledExpression(expression);

        case GROUP:
            return compile(expression.getSubRanges().get(0));

        case OPTION:
            return new OptionCompiledExpression(compile(expression.getSubRanges().get(0)));
        
        case CHARVAL:
            return new CharvalCompiledExpression(expression);

        case NUMVAL:
        case NUMVAL_RANGE:
        case NUMVAL_CONCAT:
            return new NumvalCompiledExpression(expression);
        
        case PROSEVAL:
            return new RulenameCompiledExpression(expression.getSubRanges().get(0));

        case RULE:
        case ELEMENTS:
        case COMMENT:
        case COMMENT_WHITESPACE:
        case COMMENT_NEWLINE:
        case CR_LF:
        case SPACE:
            return null;

        default:
            throw new IllegalArgumentException("Cannot compile expression: " + expression.getClass().getName());
        }
    }

    /**
     * すべての表現式をコンパイルする。
     * @param expressions 対象表現式。
     * @return
     */
    private static List<ICompiledExpression> compileSubRanges(ExpressionRange expressions) {
        List<ICompiledExpression> compiled = new ArrayList<>();
        for (ExpressionRange expr : expressions.getSubRanges()) {
            ICompiledExpression compd = compile(expr);
            if (compd != null) {
                compiled.add(compd);
            }
        }
        return compiled;
    }

}
