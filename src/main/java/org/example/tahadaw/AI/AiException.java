package org.example.tahadaw.AI;

/**
 * Thrown when the AI provider is not configured or the API call fails.
 * Handled by {@link org.example.tahadaw.ControllerAdvise.ControllerAdvisor}.
 */
public class AiException extends RuntimeException {

    public AiException(String message) {
        super(message);
    }

    public AiException(String message, Throwable cause) {
        super(message, cause);
    }
}
