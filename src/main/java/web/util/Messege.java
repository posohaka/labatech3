package web.util;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Messege {

    @JsonProperty("Messege")
    private String mess;

    public Messege(String mess) {
        this.mess = mess;
    }

    public Messege() {
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    @Override
    public String toString() {
        return "Messege{" +
                "mess='" + mess + '\'' +
                '}';
    }


}
