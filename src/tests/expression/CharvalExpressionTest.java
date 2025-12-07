package tests.expression;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.accesser.Span;
import jp.co.zoppa.abnf.expression.CharvalExpression;
import jp.co.zoppa.abnf.expression.ExpressionRange;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CharvalExpressionのテストクラス。
 */
public class CharvalExpressionTest {

    @Test
    public void testValidCharval() {
        byte[] data = "\"valid charval\"".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CharvalExpression expression = new CharvalExpression();

        ExpressionRange range = expression.match(accesser);
        assertTrue(range.isEnable());
        Span span = range.getSpan();
        assertEquals("\"valid charval\"", span.toString());
    }

    @Test
    public void testInvalidCharval() {
        byte[] data = "invalid charval".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CharvalExpression expression = new CharvalExpression();

        ExpressionRange range = expression.match(accesser);
        assertFalse(range.isEnable());
    }

    @Test
    public void testEmptyInput() {
        byte[] data = "".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CharvalExpression expression = new CharvalExpression();

        ExpressionRange range = expression.match(accesser);
        assertFalse(range.isEnable());
    }

    @Test
    public void testCharvalWithoutQuotes() {
        byte[] data = "valid charval".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CharvalExpression expression = new CharvalExpression();

        ExpressionRange range = expression.match(accesser);
        assertFalse(range.isEnable());
    }
}