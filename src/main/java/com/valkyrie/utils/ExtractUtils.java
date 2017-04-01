package com.valkyrie.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ToanDQ on 4/1/2017.
 */
public class ExtractUtils {
    private static String DIGITS_MODIFIER_PATTERN = "\\d+st|\\d+th|\\d+rd|first|second|third|fourth|fifth|sixth|seventh|eighth|nineth|tenth|next|last";
    private static String DIGITS_PATTERN = "\\d+";
    private static String DAYS_PATTERN = "monday|tuesday|wednesday|thursday|friday|saturday|sunday|mon|tue|tues|wed|thur|thurs|fri|sat|sun";
    private static String MONTHS_PATTERN = "january|february|march|april|may|june|july|august|september|october|november|december|jan|feb|mar|apr|may|jun|jul|aug|sep|sept|oct|nov|dec";
    private static String TIMEZONES_PATTERN = "ACDT|ACST|ACT|ACWDT|ACWST|ADDT|ADMT|ADT|AEDT|AEST|AFT|AHDT|AHST|AKDT|AKST|AKTST|AKTT|ALMST|ALMT|AMST|AMT|ANAST|ANAT|ANT|APT|AQTST|AQTT|ARST|ART|ASHST|ASHT|AST|AWDT|AWST|AWT|AZOMT|AZOST|AZOT|AZST|AZT|BAKST|BAKT|BDST|BDT|BEAT|BEAUT|BIOT|BMT|BNT|BORT|BOST|BOT|BRST|BRT|BST|BTT|BURT|CANT|CAPT|CAST|CAT|CAWT|CCT|CDDT|CDT|CEDT|CEMT|CEST|CET|CGST|CGT|CHADT|CHAST|CHDT|CHOST|CHOT|CIST|CKHST|CKT|CLST|CLT|CMT|COST|COT|CPT|CST|CUT|CVST|CVT|CWT|CXT|ChST|DACT|DAVT|DDUT|DFT|DMT|DUSST|DUST|EASST|EAST|EAT|ECT|EDDT|EDT|EEDT|EEST|EET|EGST|EGT|EHDT|EMT|EPT|EST|ET|EWT|FET|FFMT|FJST|FJT|FKST|FKT|FMT|FNST|FNT|FORT|FRUST|FRUT|GALT|GAMT|GBGT|GEST|GET|GFT|GHST|GILT|GIT|GMT|GST|GYT|HAA|HAC|HADT|HAE|HAP|HAR|HAST|HAT|HAY|HDT|HKST|HKT|HLV|HMT|HNA|HNC|HNE|HNP|HNR|HNT|HNY|HOVST|HOVT|HST|ICT|IDDT|IDT|IHST|IMT|IOT|IRDT|IRKST|IRKT|IRST|ISST|IST|JAVT|JCST|JDT|JMT|JST|JWST|KART|KDT|KGST|KGT|KIZST|KIZT|KMT|KOST|KRAST|KRAT|KST|KUYST|KUYT|KWAT|LHDT|LHST|LINT|LKT|LMT|LMT|LMT|LMT|LRT|LST|MADMT|MADST|MADT|MAGST|MAGT|MALST|MALT|MART|MAWT|MDDT|MDST|MDT|MEST|MET|MHT|MIST|MIT|MMT|MOST|MOT|MPT|MSD|MSK|MSM|MST|MUST|MUT|MVT|MWT|MYT|NCST|NCT|NDDT|NDT|NEGT|NEST|NET|NFT|NMT|NOVST|NOVT|NPT|NRT|NST|NT|NUT|NWT|NZDT|NZMT|NZST|OMSST|OMST|ORAST|ORAT|PDDT|PDT|PEST|PET|PETST|PETT|PGT|PHOT|PHST|PHT|PKST|PKT|PLMT|PMDT|PMMT|PMST|PMT|PNT|PONT|PPMT|PPT|PST|PT|PWT|PYST|PYT|QMT|QYZST|QYZT|RET|RMT|ROTT|SAKST|SAKT|SAMT|SAST|SBT|SCT|SDMT|SDT|SET|SGT|SHEST|SHET|SJMT|SLT|SMT|SRET|SRT|SST|STAT|SVEST|SVET|SWAT|SYOT|TAHT|TASST|TAST|TBIST|TBIT|TBMT|TFT|THA|TJT|TKT|TLT|TMT|TOST|TOT|TRST|TRT|TSAT|TVT|ULAST|ULAT|URAST|URAT|UTC|UYHST|UYST|UYT|UZST|UZT|VET|VLAST|VLAT|VOLST|VOLT|VOST|VUST|VUT|WARST|WART|WAST|WAT|WDT|WEDT|WEMT|WEST|WET|WFT|WGST|WGT|WIB|WIT|WITA|WMT|WSDT|WSST|WST|WT|XJT|YAKST|YAKT|YAPT|YDDT|YDT|YEKST|YEKST|YEKT|YEKT|YERST|YERT|YPT|YST|YWT|zzz";
    private static String NA_TIMEZONES_PATTERN = "pacific|eastern|mountain|central";
    private static String ALL_TIMEZONES_PATTERN = TIMEZONES_PATTERN + "|" + NA_TIMEZONES_PATTERN;
    private static String DELIMITERS_PATTERN = "[/\\:\\-\\,\\s\\_\\+\\@]+";
    private static String TIME_PERIOD_PATTERN = "a\\.m\\.|am|p\\.m\\.|pm";
    private static String EXTRA_TOKENS_PATTERN = "due|by|on|standard|daylight|savings|time|date|of|to|until|z|at";
    private static String RELATIVE_PATTERN = "before|after|next|last|ago";
    private static String TIME_SHORTHAND_PATTERN = "noon|midnight|today|yesterday";
    private static String UNIT_PATTERN = "second|minute|hour|day|week|month|year";
    private static String TIME_PATTERN = "(?<time>((?<hours>(\\d{1,2})\\:(?<minutes>\\d{1,2})(\\:(?<seconds>\\d{1,2}))?([\\.\\,](?<microseconds>\\d{1,6}))?\\s*(?<timeperiods>"
            + TIME_PERIOD_PATTERN + ")?\\s*(?<timezones>" + ALL_TIMEZONES_PATTERN + ")?)|((?<hours1>\\d{1,2})\\s*(?<timeperiods1>" + TIME_PERIOD_PATTERN + ")\\s*(?<timezones1>" + ALL_TIMEZONES_PATTERN + ")*)))";
    private static String DATES_PATTERN = String.format("((%s|(?<digitsmodifier>%s)|(?<digits>%s)|(?<days>%s)|(?<months>%s)|(?<delimiters>%s)|(?<extratokens>%s)){3,})", TIME_PATTERN,
            DIGITS_MODIFIER_PATTERN, DIGITS_PATTERN, DAYS_PATTERN, MONTHS_PATTERN, DELIMITERS_PATTERN, EXTRA_TOKENS_PATTERN);
    private static String STRIP_CHARS = " \\n\\t:-.,_";

    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
        put("^[a-z]{4,}\\s\\d{1,2},\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "MMMM dd, yyyy HH:mm");
    }};

    private static void extractDate(String text) throws ParseException {
//        Pattern timePattern = Pattern.compile(TIME_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.DOTALL);
        Pattern datePattern = Pattern.compile(DATES_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.DOTALL);
        Matcher m = datePattern.matcher(text.trim().replaceAll(" +", " "));
        while (m.find()) {
            String time = m.group("time");
            String digits = m.group("digits");
            String digits_modifiers = m.group("digitsmodifier");
            String days = m.group("days");
            String months = m.group("months");
            String timezones = m.group("timezones");
            String timezones1 = m.group("timezones1");
            String delimiters = m.group("delimiters");
            String time_periods = m.group("timeperiods");
            String time_periods1 = m.group("timeperiods1");
            String extra_tokens = m.group("extratokens");
            parseDate(m.group());
        }
    }

    private static void parseDate(String rawDate) throws ParseException {
        rawDate = rawDate.replaceAll(EXTRA_TOKENS_PATTERN, " ");
        rawDate = rawDate.replaceAll(TIME_PERIOD_PATTERN, " ");
        rawDate = rawDate.replaceAll("th|st|rd", "");
        rawDate = rawDate.trim().replaceAll(" +", " ");
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (rawDate.toLowerCase().matches(regexp)) {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_REGEXPS.get(regexp));
                Date date = formatter.parse(rawDate);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String nowAsISO = df.format(date);

                System.out.println(nowAsISO);
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        extractDate("...: entries are due by January         4th, 2017 at     8:00pm \n created 01/15/2005 by ACME Inc. and associates");
    }

}
