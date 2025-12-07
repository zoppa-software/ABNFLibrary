package tests;

import jp.co.zoppa.abnf.*;
import jp.co.zoppa.abnf.accesser.*;
import jp.co.zoppa.abnf.compiled.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ABNFCompiledRules のテストクラス。
 */
public class ABNFCompiledRulesTest {

    @Test
    public void testGetAlphaExpr() {
        RuleCompiledExpression alphaExpr = ABNFCompiledRules.getAlphaExpr();
        IByteAccesser accesser = new ByteAccesser("a".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(alphaExpr.getExpression().analyze(null, accesser, answer), "アルファベット小文字 'a' を解析できるべき");

        accesser = new ByteAccesser("Z".getBytes());
        assertTrue(alphaExpr.getExpression().analyze(null, accesser, answer), "アルファベット大文字 'Z' を解析できるべき");

        accesser = new ByteAccesser("1".getBytes());
        assertFalse(alphaExpr.getExpression().analyze(null, accesser, answer), "数字 '1' を解析できないべき");
    }

    @Test
    public void testGetDigitExpr() {
        RuleCompiledExpression digitExpr = ABNFCompiledRules.getDigitExpr();
        IByteAccesser accesser = new ByteAccesser("5".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(digitExpr.getExpression().analyze(null, accesser, answer), "数字 '5' を解析できるべき");

        accesser = new ByteAccesser("a".getBytes());
        assertFalse(digitExpr.getExpression().analyze(null, accesser, answer), "アルファベット 'a' を解析できないべき");
    }

    @Test
    public void testGetHexdigExpr() {
        RuleCompiledExpression hexdigExpr = ABNFCompiledRules.getHexdigExpr();
        IByteAccesser accesser = new ByteAccesser("A".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(hexdigExpr.getExpression().analyze(null, accesser, answer), "16進数文字 'A' を解析できるべき");

        accesser = new ByteAccesser("f".getBytes());
        assertTrue(hexdigExpr.getExpression().analyze(null, accesser, answer), "16進数文字 'f' を解析できるべき");

        accesser = new ByteAccesser("G".getBytes());
        assertFalse(hexdigExpr.getExpression().analyze(null, accesser, answer), "16進数文字以外 'G' を解析できないべき");
    }

    public void testGetDquoteExpr() {
        RuleCompiledExpression dquoteExpr = ABNFCompiledRules.getDquoteExpr();
        IByteAccesser accesser = new ByteAccesser("\"".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(dquoteExpr.getExpression().analyze(null, accesser, answer), "二重引用符 '\"' を解析できるべき");

        accesser = new ByteAccesser("a".getBytes());
        assertFalse(dquoteExpr.getExpression().analyze(null, accesser, answer), "アルファベット 'a' を解析できないべき");
    }

    @Test
    public void testGetSpExpr() {
        RuleCompiledExpression spExpr = ABNFCompiledRules.getSpExpr();
        IByteAccesser accesser = new ByteAccesser(" ".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(spExpr.getExpression().analyze(null, accesser, answer), "空白 ' ' を解析できるべき");

        accesser = new ByteAccesser("\t".getBytes());
        assertFalse(spExpr.getExpression().analyze(null, accesser, answer), "水平タブ '\t' を解析できないべき");
    }

    @Test
    public void testGetHtabExpr() {
        RuleCompiledExpression htabExpr = ABNFCompiledRules.getHtabExpr();
        IByteAccesser accesser = new ByteAccesser("\t".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(htabExpr.getExpression().analyze(null, accesser, answer), "水平タブ '\t' を解析できるべき");

        accesser = new ByteAccesser(" ".getBytes());
        assertFalse(htabExpr.getExpression().analyze(null, accesser, answer), "空白 ' ' を解析できないべき");
    }

    @Test
    public void testGetWspExpr() {
        RuleCompiledExpression wspExpr = ABNFCompiledRules.getWspExpr();
        IByteAccesser accesser = new ByteAccesser(" ".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(wspExpr.getExpression().analyze(null, accesser, answer), "空白 ' ' を解析できるべき");

        accesser = new ByteAccesser("\t".getBytes());
        assertTrue(wspExpr.getExpression().analyze(null, accesser, answer), "水平タブ '\t' を解析できるべき");

        accesser = new ByteAccesser("a".getBytes());
        assertFalse(wspExpr.getExpression().analyze(null, accesser, answer), "アルファベット 'a' を解析できないべき");
    }

    @Test
    public void testGetLwspExpr() {
        RuleCompiledExpression lwspExpr = ABNFCompiledRules.getLwspExpr();
        IByteAccesser accesser = new ByteAccesser(" \t\n\r".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(lwspExpr.getExpression().analyze(null, accesser, answer), "線型空白（改行含む）を解析できるべき");

        accesser = new ByteAccesser("a".getBytes());
        assertFalse(lwspExpr.getExpression().analyze(null, accesser, answer), "アルファベット 'a' を解析できないべき");
    }

    @Test
    public void testGetVcharExpr() {
        RuleCompiledExpression vcharExpr = ABNFCompiledRules.getVcharExpr();
        IByteAccesser accesser = new ByteAccesser("!".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(vcharExpr.getExpression().analyze(null, accesser, answer), "印字可能文字 '!' を解析できるべき");

        accesser = new ByteAccesser("\n".getBytes());
        assertFalse(vcharExpr.getExpression().analyze(null, accesser, answer), "改行文字 '\n' を解析できないべき");
    }

    @Test
    public void testGetCharExpr() {
        RuleCompiledExpression charExpr = ABNFCompiledRules.getCharExpr();
        IByteAccesser accesser = new ByteAccesser("A".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(charExpr.getExpression().analyze(null, accesser, answer), "任意の7ビットASCII文字 'A' を解析できるべき");

        accesser = new ByteAccesser("\0".getBytes());
        assertFalse(charExpr.getExpression().analyze(null, accesser, answer), "NUL文字 '\0' を解析できないべき");
    }

    @Test
    public void testGetOctetExpr() {
        RuleCompiledExpression octetExpr = ABNFCompiledRules.getOctetExpr();
        IByteAccesser accesser = new ByteAccesser(new byte[]{0x7F});
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(octetExpr.getExpression().analyze(null, accesser, answer), "8ビットデータ 0x7F を解析できるべき");

        accesser = new ByteAccesser(new byte[]{(byte) 0x80});
        assertFalse(octetExpr.getExpression().analyze(null, accesser, answer), "8ビットデータ 0x80 を解析できないべき");
    }

    @Test
    public void testGetCtlExpr() {
        RuleCompiledExpression ctlExpr = ABNFCompiledRules.getCtlExpr();
        IByteAccesser accesser = new ByteAccesser(new byte[]{0x1F});
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(ctlExpr.getExpression().analyze(null, accesser, answer), "制御文字 0x1F を解析できるべき");

        accesser = new ByteAccesser("A".getBytes());
        assertFalse(ctlExpr.getExpression().analyze(null, accesser, answer), "アルファベット 'A' を解析できないべき");
    }

    @Test
    public void testGetCrExpr() {
        RuleCompiledExpression crExpr = ABNFCompiledRules.getCrExpr();
        IByteAccesser accesser = new ByteAccesser("\r".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(crExpr.getExpression().analyze(null, accesser, answer), "復帰コード '\r' を解析できるべき");

        accesser = new ByteAccesser("\n".getBytes());
        assertFalse(crExpr.getExpression().analyze(null, accesser, answer), "改行コード '\n' を解析できないべき");
    }

    @Test
    public void testGetLfExpr() {
        RuleCompiledExpression lfExpr = ABNFCompiledRules.getLfExpr();
        IByteAccesser accesser = new ByteAccesser("\n".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(lfExpr.getExpression().analyze(null, accesser, answer), "改行コード '\n' を解析できるべき");

        accesser = new ByteAccesser("\r".getBytes());
        assertFalse(lfExpr.getExpression().analyze(null, accesser, answer), "復帰コード '\r' を解析できないべき");
    }

    @Test
    public void testGetCrlfExpr() {
        RuleCompiledExpression crlfExpr = ABNFCompiledRules.getCrlfExpr();
        IByteAccesser accesser = new ByteAccesser("\r\n".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(crlfExpr.getExpression().analyze(null, accesser, answer), "改行 '\r\n' を解析できるべき");

        accesser = new ByteAccesser("\n\r".getBytes());
        assertFalse(crlfExpr.getExpression().analyze(null, accesser, answer), "順序が逆の '\n\r' を解析できないべき");
    }

    @Test
    public void testGetBitExpr() {
        RuleCompiledExpression bitExpr = ABNFCompiledRules.getBitExpr();
        IByteAccesser accesser = new ByteAccesser("0".getBytes());
        List<ABNFAnalyzeItem> answer = new ArrayList<>();

        assertTrue(bitExpr.getExpression().analyze(null, accesser, answer), "ビット '0' を解析できるべき");

        accesser = new ByteAccesser("1".getBytes());
        assertTrue(bitExpr.getExpression().analyze(null, accesser, answer), "ビット '1' を解析できるべき");

        accesser = new ByteAccesser("2".getBytes());
        assertFalse(bitExpr.getExpression().analyze(null, accesser, answer), "ビット以外の '2' を解析できないべき");
    }
}