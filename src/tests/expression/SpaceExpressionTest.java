package tests.expression;

import org.junit.jupiter.api.Test;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.expression.SpaceExpression;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SpaceExpressionのテストクラス。
 */
public class SpaceExpressionTest {

    @Test
    public void testMatchSingleSpace() {
        SpaceExpression expression = new SpaceExpression();
        assertTrue(expression.match(new ByteAccesser(" ")).isEnable());
    }

    @Test
    public void testMatchMultipleSpaces() {
        SpaceExpression expression = new SpaceExpression();
        assertTrue(expression.match(new ByteAccesser("   ")).isEnable());
    }

    @Test
    public void testNoMatchNonSpace() {
        SpaceExpression expression = new SpaceExpression();
        assertFalse(expression.match(new ByteAccesser("a")).isEnable());
        assertFalse(expression.match(new ByteAccesser("1")).isEnable());
        assertFalse(expression.match(new ByteAccesser("@")).isEnable());
    }

    @Test
    public void testEmptyString() {
        SpaceExpression expression = new SpaceExpression();
        assertFalse(expression.match(new ByteAccesser("")).isEnable());
    }
}