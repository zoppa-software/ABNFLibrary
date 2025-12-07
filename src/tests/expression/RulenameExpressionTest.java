package tests.expression;

import org.junit.jupiter.api.Test;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.expression.RulenameExpression;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RulenameExpressionのテストクラス。
 */
public class RulenameExpressionTest {

    @Test
    public void testValidRulename() {
        RulenameExpression expression = new RulenameExpression();
        assertTrue(expression.match(new ByteAccesser("validRulename")).isEnable());
        assertTrue(expression.match(new ByteAccesser("another_valid_rulename")).isEnable());
    }

    @Test
    public void testInvalidRulename() {
        RulenameExpression expression = new RulenameExpression();
        assertFalse(expression.match(new ByteAccesser("123Invalid")).isEnable());
        assertFalse(expression.match(new ByteAccesser(" invalid rulename")).isEnable());
    }

    @Test
    public void testEmptyString() {
        RulenameExpression expression = new RulenameExpression();
        assertFalse(expression.match(new ByteAccesser("")).isEnable());
    }
}