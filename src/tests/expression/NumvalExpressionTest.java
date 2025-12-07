package tests.expression;

import org.junit.jupiter.api.Test;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.expression.NumvalExpression;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NumvalExpressionのテストクラス。
 */
public class NumvalExpressionTest {

    @Test
    public void testValidNumval() {
        NumvalExpression expression = new NumvalExpression();
        assertTrue(expression.match(new ByteAccesser("%d65")).isEnable());
        assertTrue(expression.match(new ByteAccesser("%d65-90")).isEnable());
        assertTrue(expression.match(new ByteAccesser("%d65.66.67")).isEnable());

        assertTrue(expression.match(new ByteAccesser("%b11")).isEnable());
        assertTrue(expression.match(new ByteAccesser("%b00-01")).isEnable());
        assertTrue(expression.match(new ByteAccesser("%b01.10.11")).isEnable());

        assertTrue(expression.match(new ByteAccesser("%x41")).isEnable());
        assertTrue(expression.match(new ByteAccesser("%x41-5A")).isEnable());
        assertTrue(expression.match(new ByteAccesser("%x41.42.43")).isEnable());
    }

    @Test
    public void testInvalidNumval() {
        NumvalExpression expression = new NumvalExpression();
        assertFalse(expression.match(new ByteAccesser("%da")).isEnable());
        assertFalse(expression.match(new ByteAccesser("%d@-90")).isEnable());
        assertFalse(expression.match(new ByteAccesser("%dpp.65.67")).isEnable());

        assertFalse(expression.match(new ByteAccesser("%b2")).isEnable());
        assertFalse(expression.match(new ByteAccesser("%b20-2")).isEnable());
        assertFalse(expression.match(new ByteAccesser("%b31.1a.11")).isEnable());

        assertFalse(expression.match(new ByteAccesser("%xG4")).isEnable());
        assertFalse(expression.match(new ByteAccesser("%xZ1-5Z")).isEnable());
        assertFalse(expression.match(new ByteAccesser("%xL1.H2.43")).isEnable());
    }

    @Test
    public void testEmptyString() {
        NumvalExpression expression = new NumvalExpression();
        assertFalse(expression.match(new ByteAccesser("")).isEnable());
    }

}