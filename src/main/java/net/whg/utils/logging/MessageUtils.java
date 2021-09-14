package net.whg.utils.logging;

/**
 * A collection of utility methods dealing with sending messages to the console
 * or to players.
 */
public final class MessageUtils {
    private MessageUtils() {
    }

    public static final MessageFormatStyle INFO_MESSAGE_STYLE = new BasicMessageStyle();
    public static final MessageFormatStyle WARNING_MESSAGE_STYLE = new WarningMessageStyle();
    public static final MessageFormatStyle ERROR_MESSAGE_STYLE = new ErrorMessageStyle();

    /**
     * Applies the chat color formatting to the given message.
     * 
     * @param style   - The message formatting style.
     * @param message - The message to modify.
     * @param args    - The message argument variables.
     * @return The formatted message.
     */
    public static String formatMessage(MessageFormatStyle style, String message, Object... args) {
        var formatted = new Object[args.length];

        for (var i = 0; i < formatted.length; i++) {
            if (args[i]instanceof Object[] arr) {
                formatted[i] = formatArray(arr, style) + style.getBaseColor();
                continue;
            }

            if (args[i] instanceof Double)
                formatted[i] = style.getNumberColor() + args[i].toString() + style.getBaseColor();
            else
                formatted[i] = style.getArgumentColor() + args[i].toString() + style.getBaseColor();
        }

        message = String.format(message, formatted);
        return style.getBaseColor() + message;
    }

    /**
     * Formats an object array into a color-coded object list string.
     * 
     * @param array - The array to format.
     * @param style - The message formatting style.
     * @return The color-coded list string.
     */
    private static String formatArray(Object[] array, MessageFormatStyle style) {
        var str = new StringBuilder(style.getArgumentColor().toString());

        var first = true;
        for (var elem : array) {
            if (!first) {
                str.append(style.getBaseColor());
                str.append(", ");
                str.append(style.getArgumentColor());
            }

            str.append(elem);
            first = false;
        }

        return str.toString();
    }
}
