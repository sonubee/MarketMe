public class Question {

    public String question;
    public String typeQuestion;
    public String creator;

    public Question() {
    }

    public Question(String question, String typeQuestion, String creator) {
        this.question = question;
        this.typeQuestion = typeQuestion;
        this.creator = creator;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(String typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
