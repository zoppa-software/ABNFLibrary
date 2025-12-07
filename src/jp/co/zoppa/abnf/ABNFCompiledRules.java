package jp.co.zoppa.abnf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.accesser.Span;
import jp.co.zoppa.abnf.compiled.ICompiledExpression;
import jp.co.zoppa.abnf.compiled.RuleCompiledExpression;
import jp.co.zoppa.abnf.expression.ExpressionEnum;
import jp.co.zoppa.abnf.expression.ExpressionRange;

/**
 * コンパイル済みルールクラス。
 */
public final class ABNFCompiledRules {

    /** ASCIIの大文字と小文字。 */
    private static RuleCompiledExpression alphaExpr;

    /** 十進数字 (0-9)。 */
    private static RuleCompiledExpression digitExpr;

    /** 16進数字 (0-9 A-F a-f)。 */
    private static RuleCompiledExpression hexdigExpr;

    /** 二重引用符。 */
    private static RuleCompiledExpression dquoteExpr;

    /** 空白。 */
    private static RuleCompiledExpression spExpr;

    /** 水平タブ。 */
    private static RuleCompiledExpression htabExpr;

    /** 空白と水平タブ。 */
    private static RuleCompiledExpression wspExpr;

    /** 線型空白（改行も含む）。 */
    private static RuleCompiledExpression lwspExpr;

    /** 印字される文字。 */
    private static RuleCompiledExpression vcharExpr;

    /** NUL 以外の任意の7ビットASCII文字。 */
    private static RuleCompiledExpression charExpr;

    /** 8ビットのデータ。 */
    private static RuleCompiledExpression octetExpr;

    /** 制御文字。 */
    private static RuleCompiledExpression ctlExpr;

    /** 復帰コード。 */
    private static RuleCompiledExpression crExpr;

    /** 改行コード。 */
    private static RuleCompiledExpression lfExpr;

    /** 改行。 */
    private static RuleCompiledExpression crlfExpr;

    /** ビット。 */
    private static RuleCompiledExpression bitExpr;

    /**
     * ASCIIの大文字と小文字式を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getAlphaExpr() {
        if (alphaExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (alphaExpr == null) {
                    alphaExpr = new RuleCompiledExpression("ALPHA", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if ((0x41 <= rval && rval <= 0x5a) || (0x61 <= rval && rval <= 0x7a)) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return alphaExpr;
    }

    /**
     * 十進数字 (0-9)を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getDigitExpr() {
        if (digitExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (digitExpr == null) {
                    digitExpr = new RuleCompiledExpression("DIGIT", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if (0x30 <= rval && rval <= 0x39) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return digitExpr;
    }

    /**
     * 16進数字 (0-9 A-F a-f)を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getHexdigExpr() {
        if (hexdigExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (hexdigExpr == null) {
                    hexdigExpr = new RuleCompiledExpression("HEXDIG", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if (('0' <= rval && rval <= '9') || 
                                ('a' <= rval && rval <= 'f') || 
                                ('A' <= rval && rval <= 'F')) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return hexdigExpr;
    }

    /**
     * 二重引用符を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getDquoteExpr() {
        if (dquoteExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (dquoteExpr == null) {
                    dquoteExpr = new RuleCompiledExpression("DQUOTE", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            if (accesser.read() == '"') {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return dquoteExpr;
    }

    /**
     * 空白を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getSpExpr() {
        if (spExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (spExpr == null) {
                    spExpr = new RuleCompiledExpression("SP", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            if (accesser.read() == 0x20) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return spExpr;
    }

    /**
     * 水平タブを取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getHtabExpr() {
        if (htabExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (htabExpr == null) {
                    htabExpr = new RuleCompiledExpression("HTAB", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            if (accesser.read() == 0x09) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return htabExpr;
    }

    /**
     * 空白と水平タブを取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getWspExpr() {
        if (wspExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (wspExpr == null) {
                    wspExpr = new RuleCompiledExpression("WSP", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if (rval == 0x20 || rval == 0x09) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return wspExpr;
    }

    /**
     * 線型空白（改行も含む）を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getLwspExpr() {
        if (lwspExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (lwspExpr == null) {
                    lwspExpr = new RuleCompiledExpression("LWSP", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            boolean matched = false;
                            // 空白を1つ以上読み取る
                            while (true) {
                                int b = accesser.peek();
                                if (b == 0x20 || b == 0x09 || b == 0x0d || b == 0x0a) {
                                    accesser.read();
                                    matched = true;
                                }
                                else {
                                    break;
                                }
                            }
                            return matched;
                        }
                    });
                }
            }
        }
        return lwspExpr;
    }

    /**
     * 印字される文字を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getVcharExpr() {
        if (vcharExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (vcharExpr == null) {
                    vcharExpr = new RuleCompiledExpression("VCHAR", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if (0x21 <= rval && rval <= 0x7e) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return vcharExpr;
    }

    /**
     * NUL 以外の任意の7ビットASCII文字を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getCharExpr() {
        if (charExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (charExpr == null) {
                    charExpr = new RuleCompiledExpression("CHAR", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if (0x01 <= rval && rval <= 0x7f) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return charExpr;
    }

    /**
     * 8ビットのデータを取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getOctetExpr() {
        if (octetExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (octetExpr == null) {
                    octetExpr = new RuleCompiledExpression("OCTET", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if (0x01 <= rval && rval <= 0x7f) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return octetExpr;
    }

    /**
     * 制御文字を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getCtlExpr() {
        if (ctlExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (ctlExpr == null) {
                    ctlExpr = new RuleCompiledExpression("CTL", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if ((0x00 <= rval && rval <= 0x1f) || rval == 0x7f) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return ctlExpr;
    }

    /**
     * 復帰コードを取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getCrExpr() {
        if (crExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (crExpr == null) {
                    crExpr = new RuleCompiledExpression("CR", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            if (accesser.read() == 0x0d) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return crExpr;
    }

    /**
     * 改行コードを取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getLfExpr() {
        if (lfExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (lfExpr == null) {
                    lfExpr = new RuleCompiledExpression("LF", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            if (accesser.read() == 0x0a) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return lfExpr;
    }

    /**
     * 改行を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getCrlfExpr() {
        if (crlfExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (crlfExpr == null) {
                    crlfExpr = new RuleCompiledExpression("CRLF", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            if (accesser.read() == 0x0d && accesser.read() == 0x0a) {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return crlfExpr;
    }

    /**
     * ビットを取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getBitExpr() {
        if (bitExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (bitExpr == null) {
                    bitExpr = new RuleCompiledExpression("BIT", new ICompiledExpression() {
                        @Override
                        public boolean analyze(ABNFCompiledRules rules, IByteAccesser accesser, List<ABNFAnalyzeItem> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            int rval = accesser.read();
                            if (rval == '0' || rval == '1') {
                                return true;
                            }
                            else {
                                mark.restore();
                                return false;
                            }
                        }
                    });
                }
            }
        }
        return bitExpr;
    }

    /** コンパイル済みルール。 */
    private final Map<String, RuleCompiledExpression> compiledRules;

    /** 未マッチルール。 */
    private String unmatchedRuleName = "";

    /** 未マッチ範囲。 */
    private Span unmatchedSpan = null;

    /**
     * コンストラクタ。
     * @param compiledRules コンパイル済みルールマップ。
     */
    public ABNFCompiledRules(Map<String, RuleCompiledExpression> compiledRules) {
        this.compiledRules = compiledRules;
        this.compiledRules.put("ALPHA", getAlphaExpr());
        this.compiledRules.put("DIGIT", getDigitExpr());
        this.compiledRules.put("HEXDIG", getHexdigExpr()); 
        this.compiledRules.put("DQUOTE", getDquoteExpr());   
        this.compiledRules.put("SP", getSpExpr());
        this.compiledRules.put("HTAB", getHtabExpr());
        this.compiledRules.put("WSP", getWspExpr());
        this.compiledRules.put("LWSP", getLwspExpr());
        this.compiledRules.put("VCHAR", getVcharExpr());
        this.compiledRules.put("CHAR", getCharExpr());
        this.compiledRules.put("OCTET", getOctetExpr());
        this.compiledRules.put("CTL", getCtlExpr());
        this.compiledRules.put("CR", getCrExpr());
        this.compiledRules.put("LF", getLfExpr());
        this.compiledRules.put("CRLF", getCrlfExpr());
        this.compiledRules.put("BIT", getBitExpr());
    }

    /**
     * 指定されたルール名で入力バイト情報を解析する。
     * @param ruleName ルール名。
     * @param input 入力バイト情報。
     * @return 解析結果リスト。
     */
    public ABNFAnalyzeItem analyze(String ruleName, IByteAccesser input) {
        this.unmatchedRuleName = "";
        this.unmatchedSpan = null;
        if (compiledRules.containsKey(ruleName)) {
            ArrayList<ABNFAnalyzeItem> answer = new ArrayList<ABNFAnalyzeItem>();
            compiledRules.get(ruleName).getExpression().analyze(this, input, answer);
            return new ABNFAnalyzeItem(ExpressionEnum.RULE, ruleName, input.span(0), answer);
        } else {
            throw new IllegalArgumentException("対象のルールは存在しません: " + ruleName);
        }
    }

    /**
     * 指定されたルール名のコンパイル済み表現式を取得する。
     * @param ruleName ルール名。
     * @return コンパイル済み表現式。
     */
    public RuleCompiledExpression getRule(String ruleName) {
        if (this.compiledRules.containsKey(ruleName)) {
            return this.compiledRules.get(ruleName);
        }
        else {
            throw new IllegalArgumentException("対象のルールは存在しません: " + ruleName);
        }
    }

    /**
     * 未マッチルール情報を設定する。
     * @param ruleName ルール名。
     * @param span 未マッチ範囲。
     */
    public void setUnmatchedRule(String ruleName, Span span) {
        this.unmatchedRuleName = ruleName;
        this.unmatchedSpan = span;
    }

    /**
     * 未マッチルール情報のメッセージを取得する。
     * @return 未マッチルール情報メッセージ。
     */
    public String getUnmatchedRuleMessage() {
        return String.format("ルール:%s が一致しませんでした。'%s'", 
                this.unmatchedRuleName, this.unmatchedSpan.toString(30));
    }

}
