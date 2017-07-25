package xyz.yapapa.rebuses;

/**
 * Created by Misha on 24.07.2017.
 */

public class ItemObjects {
        private int photo;
        private  String answer="";

    public ItemObjects( int photo, String answer) {
        this.photo = photo;
        this.answer = answer;
    }



    public int getPhoto() {
        return photo;
    }

    public String getAnswer() {
        return answer;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
