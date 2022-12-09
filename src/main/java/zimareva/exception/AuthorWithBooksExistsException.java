package zimareva.exception;

import java.text.MessageFormat;

public class AuthorWithBooksExistsException extends RuntimeException {
    public AuthorWithBooksExistsException(String name, Long id, int countBooks) {
        super(MessageFormat.format("Author {0} with id {1} have {2, number, integer} books. Cant be removed"
                , name, id, countBooks));
    }
}
