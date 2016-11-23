package com.zing.util.security;

import com.zing.util.random_string_util.RandomUtil;

/**
 * Created by zing on 2016/11/20.
 */
public class MD5WithSalt {
    private String formerString;
    private String salt = "";
    private String MD5Result;
    private int digestTimes = 1;

    public MD5WithSalt(String formerString, String salt, int digestTimes) {
        this.formerString = formerString;
        this.salt = salt;
        this.digestTimes = digestTimes;
    }

    public MD5WithSalt(String formerString, String salt) {
        new MD5WithSalt(formerString, salt, 1);
    }

    public MD5WithSalt(String formerString) {
        new MD5WithSalt(formerString, "");
    }

    private MD5WithSalt() {
        this.salt = "";
        this.digestTimes = 1;

    }

    public String getFormerString() {
        return formerString;
    }

    public void setFormerString(String formerString) {
        this.formerString = formerString;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMD5Result() {
        return MD5Result;
    }

    public void setMD5Result(String MD5Result) {
        this.MD5Result = MD5Result;
    }

    public int getDigestTimes() {
        return digestTimes;
    }

    public void setDigestTimes(int digestTimes) {
        this.digestTimes = digestTimes;
    }


    /**
     * 根据MD5WithSalt配置，为formerString计算digestTimes次加盐salt的MD5摘要值
     *
     * @return
     */
    public MD5WithSalt MD5Self() {
        if (formerString == null || formerString.equals("")) {
            return this;
        }
        String result = formerString;
        for (int i = 0; i < digestTimes; i++) {
            result = MD5Util.MD5(result + salt);
        }
        MD5Result = result;
        return this;
    }

}
