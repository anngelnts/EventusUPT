package com.desarrollo.eventusupt.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FcmResponse {
    @SerializedName("multicast_id")
    @Expose
    private String multicast_id;
    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("failure")
    @Expose
    private int failure;
    @SerializedName("canonical_ids")
    @Expose
    private int canonical_ids;

    public String getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(String multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }
}
