package tests.expression;

import org.junit.jupiter.api.Test;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.expression.ProsevalExpression;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ProsevalExpressionのテストクラス。
 */
public class ProsevalExpressionTest {

    @Test
    public void testValidProseval() {
        ProsevalExpression expression = new ProsevalExpression();
        assertTrue(expression.match(new ByteAccesser("<valid proseval>")).isEnable());
        assertTrue(expression.match(new ByteAccesser("<another valid proseval>")).isEnable());
    }

    @Test
    public void testInvalidProseval() {
        ProsevalExpression expression = new ProsevalExpression();
        assertFalse(expression.match(new ByteAccesser("valid proseval>")).isEnable());
        assertFalse(expression.match(new ByteAccesser("<invalid proseval")).isEnable());
        assertFalse(expression.match(new ByteAccesser("invalid proseval")).isEnable());
    }

    @Test
    public void testEmptyString() {
        ProsevalExpression expression = new ProsevalExpression();
        assertFalse(expression.match(new ByteAccesser("")).isEnable());
    }
}