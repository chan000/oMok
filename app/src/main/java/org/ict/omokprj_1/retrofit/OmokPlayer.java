package org.ict.omokprj_1.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OmokPlayer {

    @SerializedName("ono")
    @Expose
    private Integer ono;
    @SerializedName("oname")
    @Expose
    private String oname;
    @SerializedName("win")
    @Expose
    private Integer win;
    @SerializedName("lose")
    @Expose
    private Integer lose;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getOno() {
        return ono;
    }

    public void setOno(Integer ono) {
        this.ono = ono;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
