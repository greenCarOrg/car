package com.itee.bingsheng.support;


public class ExecuteResult<T> implements java.io.Serializable {

    private ResultCode resultCode;
    private T result;
    private boolean success;
    private String message;

    public ExecuteResult() {
        resultCode = ResultCode.SUCCESS;
        success = true;
    }

    public ExecuteResult(ResultCode resultCode) {
        this.resultCode = resultCode;
        if (ResultCode.SUCCESS.equals(resultCode)) {
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public ExecuteResult(ResultCode resultCode, T result) {
        this.resultCode = resultCode;
        this.result = result;
        if (ResultCode.SUCCESS.equals(resultCode)) {
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public ExecuteResult(ResultCode resultCode, T result, String message) {
        this.resultCode = resultCode;
        this.result = result;
        this.message = message;
        if (ResultCode.SUCCESS.equals(resultCode)) {
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        if (ResultCode.SUCCESS.equals(resultCode)) {
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
        if (success) {
            this.resultCode = ResultCode.SUCCESS;
        } else if (this.resultCode != null) {
            this.resultCode = ResultCode.FAIL;
        }
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
