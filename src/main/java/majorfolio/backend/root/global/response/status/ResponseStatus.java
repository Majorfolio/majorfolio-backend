/**
 * ResponseStatus
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.response.status;

public interface ResponseStatus {
    int getCode();

    int getStatus();

    String getMessage();
}
