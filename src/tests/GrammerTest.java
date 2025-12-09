package tests;

import jp.co.zoppa.abnf.ABNFAnalyzeException;
import jp.co.zoppa.abnf.ABNFAnalyzeItem;
import jp.co.zoppa.abnf.ABNFCompiledRules;
import jp.co.zoppa.abnf.ABNFSyntaxAnalysis;
import jp.co.zoppa.abnf.accesser.ByteAccesser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GrammerTest {
    
    @Test
    public void testCase1() {
        String grammer = """
; アドレス帳の郵便住所のABNF文法例
fu = %x61-7A / %x41-5A ;
ccc = %d13.10
aaa=bbb ; コメント行
postal-address   = [name-part] street zip-part

name-part        = *(personal-part SP) last-name [SP suffix] CRLF
name-part        =/ personal-part CRLF

personal-part    = first-name / (initial \".\")
first-name       = *ALPHA
initial          = ALPHA
last-name        = *ALPHA
suffix           = (\"Jr.\" / \"Sr.\" / 1*(\"I\" / \"V\" / \"X\"))

street           = [apt SP] house-num SP street-name CRLF
apt              = 1*4DIGIT
house-num        = 1*8(DIGIT / ALPHA)
street-name      = 1*VCHAR

zip-part         = town-name \",\" SP state 1*2SP zip-code CRLF
town-name        = 1*(ALPHA / SP)
state            = 2ALPHA
zip-code         = 5DIGIT [\"-\" 4DIGIT]
""";
        ByteAccesser accesser = new ByteAccesser(grammer);
        assertEquals(0, accesser.getPosition());

        try {
            ABNFCompiledRules rules = ABNFSyntaxAnalysis.compile(grammer);

            ABNFAnalyzeItem answer = rules.analyze("postal-address", new ByteAccesser("123 Main Street\r\nLos Angeles, CA 90012\r\n"));
        
            ABNFAnalyzeItem node;
            node = answer.searchByRuleName("street").get(0).searchByRuleName("apt").get(0);
            assertEquals("123", node.getSpan().toString());

            node = answer.searchByRuleName("street").get(0).searchByRuleName("house-num").get(0);
            assertEquals("Main", node.getSpan().toString());

            node = answer.searchByRuleName("street").get(0).searchByRuleName("street-name").get(0);
            assertEquals("Street", node.getSpan().toString());
        }
        catch (ABNFAnalyzeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCase2() {
        String grammer = """
numpattern = 1*4DIGIT \"-\" 1*4DIGIT
""";
        ByteAccesser accesser = new ByteAccesser(grammer);
        assertEquals(0, accesser.getPosition());

        try {
            ABNFCompiledRules rules = ABNFSyntaxAnalysis.compile(grammer);

            ABNFAnalyzeItem answer = rules.analyze("numpattern", new ByteAccesser("123-456"));
            assertEquals("123", answer.getSubRanges().get(0).getSpan().toString());
            assertEquals("456", answer.getSubRanges().get(2).getSpan().toString());

            assertThrows(
                ABNFAnalyzeException.class,
                () -> rules.analyze("numpattern", new ByteAccesser("123456"))
            );

            assertThrows(
                ABNFAnalyzeException.class,
                () -> rules.analyze("numpattern", new ByteAccesser("12345-678"))
            );
        }
        catch (ABNFAnalyzeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCase3() {
        String grammer = """
pattern = %x30-39
grammer = 1*pattern
""";
        ByteAccesser accesser = new ByteAccesser(grammer);
        assertEquals(0, accesser.getPosition());

        try {
            ABNFCompiledRules rules = ABNFSyntaxAnalysis.compile(grammer);

            ABNFAnalyzeItem answer = rules.analyze("grammer", new ByteAccesser("123456"));
            ABNFAnalyzeItem node;
            node = answer.searchByRuleName("1*pattern").get(0);
            assertEquals("123456", node.getSpan().toString());

            ABNFAnalyzeItem answer2 = rules.analyze("grammer", new ByteAccesser("123A456"));
            ABNFAnalyzeItem node2;
            node2 = answer2.searchByRuleName("1*pattern").get(0);
            assertEquals("123", node2.getSpan().toString());
        } catch (ABNFAnalyzeException e) {
            e.printStackTrace();
        }
    }

}
