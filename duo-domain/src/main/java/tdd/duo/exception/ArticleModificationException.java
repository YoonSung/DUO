package tdd.duo.exception;

/**
 * Created by yoon on 15. 4. 14..
 */
public class ArticleModificationException extends Exception {
    private String errorMessage;

    public ArticleModificationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        if (errorMessage == null)
            return super.getMessage();

        return errorMessage;
    }
}
