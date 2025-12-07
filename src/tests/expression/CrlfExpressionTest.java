package tests.expression;

import org.junit.jupiter.api.Test;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.expression.CrLfExpression;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CrlfExpressionのテストクラス。
 */
public class CrlfExpressionTest {

    @Test
    public void testValidCrlf() {
        CrLfExpression expression = new CrLfExpression();
        assertTrue(expression.match(new ByteAccesser("\r\n")).isEnable());
        assertTrue(expression.match(new ByteAccesser("\n")).isEnable());
        assertTrue(expression.match(new ByteAccesser("\r")).isEnable());
        assertTrue(expression.match(new ByteAccesser("\n\r")).isEnable());
    }

    @Test
    public void testInvalidCrlf() {
        CrLfExpression expression = new CrLfExpression();
        assertFalse(expression.match(new ByteAccesser("123")).isEnable());
    }

    @Test
    public void testEmptyString() {
        CrLfExpression expression = new CrLfExpression();
        assertFalse(expression.match(new ByteAccesser("")).isEnable());
    }
}