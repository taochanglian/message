package com.huntkey.rx.sceo.mpf.exception;

/**
 * Created by taochangl on 2017/5/12 0012.
 * The exception for message platform
 */
public class MsgException extends Exception {
    public MsgException(final Exception ex) {
        super(ex);
    }
    public MsgException(final String str) {
        super(str);
    }
}
