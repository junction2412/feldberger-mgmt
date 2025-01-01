package de.code.junction.feldberger.mgmt.presentation.preferences;

/**
 * A record to provide preference metadata used for client-side preference management.
 *
 * @param scope        the scope this preference is used for, a different lifetime should be chosen as well
 * @param name         the name of the preference
 * @param defaultValue default value
 * @param type         value type
 * @param lifetime     preference lifetime allowing for out-of-scope cleanup
 * @param description  description
 * @author J. Murray
 */
public record UIPreference(
        String scope,
        String name,
        Object defaultValue,
        Class<?> type,
        PreferenceLifetime lifetime,
        String description
) {
}
