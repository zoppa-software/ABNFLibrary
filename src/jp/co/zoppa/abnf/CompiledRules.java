package jp.co.zoppa.abnf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.compiled.ICompiledExpression;
import jp.co.zoppa.abnf.compiled.RuleCompiledExpression;

/**
 * コンパイル済みルールクラス。
 */
public final class CompiledRules {

    /** ASCIIの大文字と小文字。 */
    private static RuleCompiledExpression alphaExpr;

    /** 十進数字 (0-9)。 */
    private static RuleCompiledExpression digitExpr;

    /** 空白。 */
    private static RuleCompiledExpression spExpr;

    /** 印字される文字。 */
    private static RuleCompiledExpression vcharExpr;

    /** 改行。 */
    private static RuleCompiledExpression crlfExpr;

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
                        public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer) {
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
                        public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer) {
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
     * 空白を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getSpExpr() {
        if (spExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (spExpr == null) {
                    spExpr = new RuleCompiledExpression("SP", new ICompiledExpression() {
                        @Override
                        public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer) {
                            IByteAccesser.IPosition mark = accesser.mark();
                            if (0x20 == accesser.read()) {
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
     * 印字される文字を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getVcharExpr() {
        if (vcharExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (vcharExpr == null) {
                    vcharExpr = new RuleCompiledExpression("CRLF", new ICompiledExpression() {
                        @Override
                        public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer) {
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
     * 改行を取得する。
     * @return 式。
     */
    public static RuleCompiledExpression getCrlfExpr() {
        if (crlfExpr == null) {
            synchronized (RuleCompiledExpression.class) {
                if (crlfExpr == null) {
                    crlfExpr = new RuleCompiledExpression("CRLF", new ICompiledExpression() {
                        @Override
                        public boolean analyze(CompiledRules rules, IByteAccesser accesser, List<AnalyzeRange> answer) {
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

    /** コンパイル済みルール。 */
    private final Map<String, RuleCompiledExpression> compiledRules;

    /**
     * コンストラクタ。
     * @param compiledRules コンパイル済みルールマップ。
     */
    public CompiledRules(Map<String, RuleCompiledExpression> compiledRules) {
        this.compiledRules = compiledRules;
        this.compiledRules.put("ALPHA", getAlphaExpr());
        this.compiledRules.put("DIGIT", getDigitExpr());
        this.compiledRules.put("SP", getSpExpr());
        this.compiledRules.put("VCHAR", getVcharExpr());
        this.compiledRules.put("CRLF", getCrlfExpr());
    }

    /**
     * 指定されたルール名で入力バイト情報を解析する。
     * @param ruleName ルール名。
     * @param input 入力バイト情報。
     * @return 解析結果リスト。
     */
    public List<AnalyzeRange> analyze(String ruleName, IByteAccesser input) {
        if (compiledRules.containsKey(ruleName)) {
            List<AnalyzeRange> answer = new ArrayList<>();
            compiledRules.get(ruleName).getExpression().analyze(this, input, answer);
            return answer;
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
}
